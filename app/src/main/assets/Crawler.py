import requests
from bs4 import BeautifulSoup

class Crawler_Info():
    def __init__(self):
        self.headers = {
            'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'
        }

    def crawl(self, params):
        html = requests.get("http://www.wikicfp.com/cfp/servlet/event.showcfp", headers=self.headers, params=params)
        self.content = BeautifulSoup(html.text, "lxml")

    def check(self):
        return self.content.title.text.strip().split(" : ")[0] != "WikiCFP"

    def format(self):
        self.title = self.content.title.text

        self.abbreviation = self.content.title.text.strip().split(" : ")[0]
        
        if self.content.find("a", target="_newtab") != None:
            self.url = self.content.find("a", target="_newtab").text
        else: 
            self.url = "N/A"

        self.location = self.content.find("table", class_="gglu").find_all("tr")[1].find("td").text
        
        if self.content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[0] != "N/A":
            self.event_date_begin = self.content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[0]
            self.event_date_end = self.content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[1]
        else:
            self.event_date_begin = "N/A"
            self.event_date_end = "N/A"

        self.submission_deadline = self.content.find("table", class_="gglu").find_all("tr")[2].find("td").text.strip()

        self.outline = ""
        for line in self.content.find("div", class_="cfp").text.split("<br>"):
            self.outline += line.replace("\r", "\n")

    def get(self):
        lis = [self.title, self.abbreviation, self.url, self.location, self.event_date_begin, self.event_date_end, self.submission_deadline, self.outline]
        return lis

class Crawler_Noun:
    def __init__(self):
        self.headers = {
            'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'
        }

    def crawl(self):
        html = requests.get("http://www.wikicfp.com/cfp/allcat", headers=self.headers)
        self.content = BeautifulSoup(html.text, "lxml")

    def format(self):
        self.lis = []
        table = self.content.find_all("table")[3].find_all("tr")
        for i in range(3, len(table)):
            row = table[i]
            for j in range(0, 5, 2):
                #print(row.find_all("td")[j].text)
                self.lis.append(row.find_all("td")[j].text)

    def get(self):
        return self.lis