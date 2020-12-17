import requests
from bs4 import BeautifulSoup

headers = {
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'
    }
html = requests.get("http://www.wikicfp.com/cfp/servlet/event.showcfp?eventid=119589", headers=headers)
content = BeautifulSoup(html.text, "lxml")

print("title:")
print(content.title.text)

print("abbreviation:")
print(content.title.text.split(" : ")[0])

print("url:")
print(content.find("a", target="_newtab").text)

print("location:")
print(content.find("table", class_="gglu").find_all("tr")[1].find("td").text)

print("")