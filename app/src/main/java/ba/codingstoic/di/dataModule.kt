package ba.codingstoic.di

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import ba.codingstoic.data.NetworkSource
import ba.codingstoic.player.MediaSessionConnection
import ba.codingstoic.player.PlayerService
import ba.codingstoic.podcast.PodcastRepository
import ba.codingstoic.user.CookieManager
import ba.codingstoic.user.UserSession
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val dataModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("shared-prefs", Context.MODE_PRIVATE)
    }

    single {
        CookieManager(get())
    }

    single {
        UserSession(get(), get())
    }

    single<Interceptor> {
        val cookieManager = get<CookieManager>()
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = chain.request().newBuilder()
                val expiresAt = cookieManager.getExpiresAt()
                expiresAt?.let {
                    val date = ZonedDateTime.parse(it, DateTimeFormatter.RFC_1123_DATE_TIME)
                    val cutoffTime = date.minus(5, ChronoUnit.MINUTES)
                    if (cutoffTime <= ZonedDateTime.now(ZoneId.systemDefault())) {
                        val userSession = get<UserSession>()
                        runBlocking {
                            userSession.loginUser(
                                cookieManager.getUserNameAndPassowrd().first,
                                cookieManager.getUserNameAndPassowrd().second
                            )
                        }
                    }
                }

                newRequest.header("sessionid", cookieManager.getCookie())
                return chain.proceed(chain.request())
            }

        }
    }

    single<ExoPlayer> {
        SimpleExoPlayer.Builder(androidContext()).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA).build(), true
            )
            setHandleAudioBecomingNoisy(true)
        }
    }

    single(named("userAgent")) {
        val userAgent = Util.getUserAgent(androidContext(), "Podcast guru android app")
        userAgent
    }

    single<DataSource.Factory> {
        DefaultHttpDataSourceFactory(
            get<String>(named("userAgent")),
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

    single<Retrofit> {
        val client = OkHttpClient.Builder().addInterceptor(get<Interceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl("http://gpodder.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<NetworkSource> {
        val retrofit: Retrofit = get()
        retrofit.create(NetworkSource::class.java)
    }

    single {
        val client = OkHttpClient.Builder().addInterceptor(get<Interceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://itunes.com")
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            )
            .build()

        PodcastRepository(get())
    }

    single {
        MediaSessionConnection(
            androidContext(),
            ComponentName(androidContext(), PlayerService::class.java)
        )
    }

}