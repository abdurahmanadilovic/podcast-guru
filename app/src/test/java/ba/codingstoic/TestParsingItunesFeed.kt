package ba.codingstoic

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.XmlReader
import junit.framework.Assert.assertEquals
import okio.Buffer
import org.junit.Test

/**
 * Created by Abdurahman Adilovic on 5/6/20.
 */

class TestParsingItunesFeed {

    @Test
    fun parseXml(){
        val xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<rss xmlns:art19=\"https://art19.com/xmlns/rss-extensions/1.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:content=\"http://purl.org/rss/1.0/modules/content/\" xmlns:googleplay=\"https://www.google.com/schemas/play-podcasts/1.0/\" xmlns:itunes=\"http://www.itunes.com/dtds/podcast-1.0.dtd\" version=\"2.0\">\n" +
                "  <channel>\n" +
                "    <title>The Daily</title>\n" +
                "    <description>\n" +
                "      <![CDATA[<p>This is what the news should sound like. The biggest stories of our time, told by the best journalists in the world. Hosted by Michael Barbaro. Twenty minutes a day, five days a week, ready by 6 a.m.</p>]]>\n" +
                "    </description>\n" +
                "    <managingEditor>thedaily@nytimes.com (The New York Times)</managingEditor>\n" +
                "    <copyright>© 2018-2019 THE NEW YORK TIMES COMPANY; The New York Times encourages the use of RSS feeds for personal use in a news reader or as part of a non-commercial blog, subject to your agreement to our Terms of Service. Any commercial use of the RSS feed, and thus our content accessible via it, is prohibited without a written specific permission from The New York Times . We require proper format and attribution whenever New York Times content is posted on other's properties, and we reserve the right to require that you cease distributing our content.</copyright>\n" +
                "    <generator>ART19</generator>\n" +
                "    <atom:link href=\"https://rss.art19.com/the-daily\" rel=\"self\" type=\"application/rss+xml\"/>\n" +
                "    <link>https://www.nytimes.com/the-daily</link>\n" +
                "    <itunes:owner>\n" +
                "      <itunes:name>The New York Times</itunes:name>\n" +
                "      <itunes:email>thedaily@nytimes.com</itunes:email>\n" +
                "    </itunes:owner>\n" +
                "    <itunes:author>The New York Times</itunes:author>\n" +
                "    <itunes:summary>\n" +
                "      <![CDATA[<p>This is what the news should sound like. The biggest stories of our time, told by the best journalists in the world. Hosted by Michael Barbaro. Twenty minutes a day, five days a week, ready by 6 a.m.</p>]]>\n" +
                "    </itunes:summary>\n" +
                "    <language>en</language>\n" +
                "    <itunes:explicit>no</itunes:explicit>\n" +
                "    <itunes:category text=\"News\">\n" +
                "      <itunes:category text=\"Daily News\"/>\n" +
                "    </itunes:category>\n" +
                "    <itunes:type>episodic</itunes:type>\n" +
                "    <itunes:image href=\"https://content.production.cdn.art19.com/images/01/1b/f3/d6/011bf3d6-a448-4533-967b-e2f19e376480/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg\"/>\n" +
                "    <image>\n" +
                "      <url>https://content.production.cdn.art19.com/images/01/1b/f3/d6/011bf3d6-a448-4533-967b-e2f19e376480/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg</url>\n" +
                "      <link>https://www.nytimes.com/the-daily</link>\n" +
                "      <title>The Daily</title>\n" +
                "    </image>\n" +
                "    <item>\n" +
                "      <title>Kicked Out of China</title>\n" +
                "      <description>\n" +
                "        <![CDATA[<p><em>Note: This episode contains strong language.</em></p><p>The New York Times’s reporters working in China have been expelled by the Chinese government, alongside reporters covering China for The Wall Street Journal and The Washington Post. Today, we speak with one of our correspondents about his experience learning that he would have to leave the place he has called home for the last decade — and about the last story he reported before he left. Guest: <a href=\"https://www.nytimes.com/by/paul-mozur?smid=pc-thedaily\" target=\"_blank\">Paul Mozur</a>, the Asia technology reporter for The New York Times, formerly based in Shanghai. For more information on today’s episode, visit nytimes.com/thedaily.&nbsp;</p><p>Background reading:&nbsp;</p><ul><li><a href=\"https://www.nytimes.com/2020/03/17/business/media/china-expels-american-journalists.html?smid=pc-thedaily\" target=\"_blank\">China’s announcement</a> of the journalists’ expulsion came weeks after President Trump limited the number of Chinese citizens who can work in the United States for five state-run Chinese news organizations.</li><li>While the Chinese government’s official statement cited diplomatic tension as the reasoning for the expulsion, state media outlets pointed to our critical reporting of<a href=\"https://www.nytimes.com/interactive/2019/11/16/world/asia/china-xinjiang-documents.html?smid=pc-thedaily\" target=\"_blank\"> China’s mass detention of Muslims</a>,<a href=\"https://www.nytimes.com/2019/12/17/technology/china-surveillance.html?smid=pc-thedaily\" target=\"_blank\"> government surveillance</a> and its<a href=\"https://www.nytimes.com/interactive/2020/03/22/world/coronavirus-spread.html?smid=pc-thedaily\" target=\"_blank\"> response to the coronavirus outbreak</a> in Wuhan as reasons for the move.</li></ul>]]>\n" +
                "      </description>\n" +
                "      <itunes:title>Kicked Out of China</itunes:title>\n" +
                "      <itunes:episodeType>full</itunes:episodeType>\n" +
                "      <itunes:summary>Note: This episode contains strong language.\n" +
                "\n" +
                "The New York Times’s reporters working in China have been expelled by the Chinese government, alongside reporters covering China for The Wall Street Journal and The Washington Post. Today, we speak with one of our correspondents about his experience learning that he would have to leave the place he has called home for the last decade — and about the last story he reported before he left. Guest: Paul Mozur, the Asia technology reporter for The New York Times, formerly based in Shanghai. For more information on today’s episode, visit nytimes.com/thedaily. \n" +
                "\n" +
                "Background reading: China’s announcement of the journalists’ expulsion came weeks after President Trump limited the number of Chinese citizens who can work in the United States for five state-run Chinese news organizations.While the Chinese government’s official statement cited diplomatic tension as the reasoning for the expulsion, state media outlets pointed to our critical reporting of China’s mass detention of Muslims, government surveillance and its response to the coronavirus outbreak in Wuhan as reasons for the move.</itunes:summary>\n" +
                "      <content:encoded>\n" +
                "        <![CDATA[<p><em>Note: This episode contains strong language.</em></p><p>The New York Times’s reporters working in China have been expelled by the Chinese government, alongside reporters covering China for The Wall Street Journal and The Washington Post. Today, we speak with one of our correspondents about his experience learning that he would have to leave the place he has called home for the last decade — and about the last story he reported before he left. Guest: <a href=\"https://www.nytimes.com/by/paul-mozur?smid=pc-thedaily\" target=\"_blank\">Paul Mozur</a>, the Asia technology reporter for The New York Times, formerly based in Shanghai. For more information on today’s episode, visit nytimes.com/thedaily.&nbsp;</p><p>Background reading:&nbsp;</p><ul><li><a href=\"https://www.nytimes.com/2020/03/17/business/media/china-expels-american-journalists.html?smid=pc-thedaily\" target=\"_blank\">China’s announcement</a> of the journalists’ expulsion came weeks after President Trump limited the number of Chinese citizens who can work in the United States for five state-run Chinese news organizations.</li><li>While the Chinese government’s official statement cited diplomatic tension as the reasoning for the expulsion, state media outlets pointed to our critical reporting of<a href=\"https://www.nytimes.com/interactive/2019/11/16/world/asia/china-xinjiang-documents.html?smid=pc-thedaily\" target=\"_blank\"> China’s mass detention of Muslims</a>,<a href=\"https://www.nytimes.com/2019/12/17/technology/china-surveillance.html?smid=pc-thedaily\" target=\"_blank\"> government surveillance</a> and its<a href=\"https://www.nytimes.com/interactive/2020/03/22/world/coronavirus-spread.html?smid=pc-thedaily\" target=\"_blank\"> response to the coronavirus outbreak</a> in Wuhan as reasons for the move.</li></ul>]]>\n" +
                "      </content:encoded>\n" +
                "      <guid isPermaLink=\"false\">gid://art19-episode-locator/V0/icaW9wAQ10-8VHnqaNwZxaNuHwIf51tMAc2M_ZwNTts</guid>\n" +
                "      <pubDate>Thu, 16 Apr 2020 09:50:04 -0000</pubDate>\n" +
                "      <itunes:explicit>no</itunes:explicit>\n" +
                "      <itunes:image href=\"https://content.production.cdn.art19.com/images/01/1b/f3/d6/011bf3d6-a448-4533-967b-e2f19e376480/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg\"/>\n" +
                "      <itunes:duration>00:28:32</itunes:duration>\n" +
                "      <enclosure length=\"27398060\" type=\"audio/mpeg\" url=\"https://dts.podtrac.com/redirect.mp3/rss.art19.com/episodes/51806688-a29e-4250-89b3-0894f7a5362e.mp3\"/>\n" +
                "    </item>\n" +
                " <item>\n" +
                "      <title>Wednesday, Feb. 1, 2017</title>\n" +
                "      <description>\n" +
                "        <![CDATA[In a ceremony made for prime-time television, President Trump announced his Supreme Court nominee: Neil M. Gorsuch, a conservative judge with a sterling résumé. We spent the night at The New York Times talking with some of our most insightful colleagues about what the nomination means. We also get on the phone with the chief executive of Hobby Lobby, a company at the center of one of Judge Gorsuch’s most important cases.]]>\n" +
                "      </description>\n" +
                "      <itunes:title>Wednesday, Feb. 1, 2017</itunes:title>\n" +
                "      <itunes:episodeType>full</itunes:episodeType>\n" +
                "      <itunes:summary>In a ceremony made for prime-time television, President Trump announced his Supreme Court nominee: Neil M. Gorsuch, a conservative judge with a sterling résumé. We spent the night at The New York Times talking with some of our most insightful colleagues about what the nomination means. We also get on the phone with the chief executive of Hobby Lobby, a company at the center of one of Judge Gorsuch’s most important cases.</itunes:summary>\n" +
                "      <content:encoded>\n" +
                "        <![CDATA[In a ceremony made for prime-time television, President Trump announced his Supreme Court nominee: Neil M. Gorsuch, a conservative judge with a sterling résumé. We spent the night at The New York Times talking with some of our most insightful colleagues about what the nomination means. We also get on the phone with the chief executive of Hobby Lobby, a company at the center of one of Judge Gorsuch’s most important cases.]]>\n" +
                "      </content:encoded>\n" +
                "      <guid isPermaLink=\"false\">gid://art19-episode-locator/V0/8fJKNp6i648MNqY585bYwmeqWbklSt3qUuh0mzpMetA</guid>\n" +
                "      <pubDate>Wed, 01 Feb 2017 10:18:28 -0000</pubDate>\n" +
                "      <itunes:explicit>no</itunes:explicit>\n" +
                "      <itunes:image href=\"https://content.production.cdn.art19.com/images/01/1b/f3/d6/011bf3d6-a448-4533-967b-e2f19e376480/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg\"/>\n" +
                "      <itunes:duration>00:19:32</itunes:duration>\n" +
                "      <enclosure length=\"18763441\" type=\"audio/mpeg\" url=\"https://dts.podtrac.com/redirect.mp3/rss.art19.com/episodes/d5e71a9d-3b08-4b2c-b948-453ca66c4eba.mp3\"/>\n" +
                "    </item>\n" +
                "  </channel>\n" +
                "</rss>"

        val reader = XmlReader.of(Buffer().writeUtf8(xml))

        assertEquals(1, reader.beginElement())
    }
}
