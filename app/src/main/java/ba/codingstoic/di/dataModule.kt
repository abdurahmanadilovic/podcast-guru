package ba.codingstoic.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import ba.codingstoic.data.GPodderPodcastSource
import ba.codingstoic.podcast.PodcastRepository
import ba.codingstoic.user.CookieManager
import ba.codingstoic.user.UserSession
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
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
                if (chain.request().headers["BasicAuth"] != null) {
                    newRequest.header(
                        "Authorization",
                        "Basic ${Base64.encodeToString("Test:Test".toByteArray(), Base64.NO_WRAP)}"
                    )
                } else {
                    newRequest.header("sessionid", cookieManager.getCookie())
                }

                return chain.proceed(chain.request())
            }

        }
    }

    single<ExoPlayer> {
        ExoPlayerFactory.newSimpleInstance(androidContext()).apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA).build(), true
            )
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

    single<GPodderPodcastSource> {
        val retrofit: Retrofit = get()
        retrofit.create(GPodderPodcastSource::class.java)
    }

    single {
        PodcastRepository(get())
    }

}