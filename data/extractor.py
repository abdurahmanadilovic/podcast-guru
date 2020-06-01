import json
import requests
from xml.etree import ElementTree
from xmlToDict import XmlDictConfig


def parse():
    parsed_podcasts = []
    with open('itunesTop.json', encoding="utf8") as json_file:
        data = json.load(json_file)
        for podcast in data['feed']['entry']:
            current_podcast = {
                "id": podcast["id"]["attributes"]["im:id"],
                "name": podcast["im:name"]["label"]
            }
            podcast_details_response = requests.get(
                f'https://itunes.apple.com/lookup?id={current_podcast["id"]}').json()
            current_podcast["feedUrl"] = podcast_details_response["results"][0]["feedUrl"]
            current_podcast["imageUrl"] = podcast_details_response["results"][0]["artworkUrl600"]

            xmlString = requests.get(current_podcast["feedUrl"]).content
            xmlRoot = ElementTree.XML(xmlString)
            xml_dict = XmlDictConfig(xmlRoot)

            episodes = []
            for episode in xml_dict['channel']["item"][:10]:
                current_episode = {
                    "title": episode["title"].strip(' \t\n\r'),
                    "date": episode["pubDate"],
                    "mp3Url": episode["enclosure"]["url"]
                }
                for key in episode:
                    if "duration" in key:
                        current_episode["duration"] = episode[key]

                episodes.append(current_episode)

            current_podcast["episodes"] = episodes

            parsed_podcasts.append(current_podcast)

    with open("data.json", 'w') as outfile:
        json.dump(parsed_podcasts, outfile)


parse()
