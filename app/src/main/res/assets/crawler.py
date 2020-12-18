import requests
from bs4 import BeautifulSoup
import sqlite3

headers = {
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'
    }

params = {
    'eventid' : 119589
} 

html = requests.get("http://www.wikicfp.com/cfp/servlet/event.showcfp", headers=headers, params=params)
content = BeautifulSoup(html.text, "lxml")

g_title = content.title.text
print("title:")
print(g_title)

g_abbreviation = content.title.text.strip().split(" : ")[0]
print("abbreviation:")
print(g_abbreviation)

g_url = content.find("a", target="_newtab").text
print("url:")
print(g_url)

g_location = content.find("table", class_="gglu").find_all("tr")[1].find("td").text
print("location:")
print(g_location)

g_event_date_begin = content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[0]
print("eventDateBegin:")
print(g_event_date_begin)

g_event_date_end = content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[1]
print("eventDateEnd:")
print(g_event_date_end)

g_submission_deadline = content.find("table", class_="gglu").find_all("tr")[3].find("td").text.strip()
print("submissionDeadline:")
print(g_submission_deadline)

g_outline = ""
print("outline:")
for line in content.find("div", class_="cfp").text.split("<br>"):
    print(line.replace("\r", "\n"))
    g_outline.append(line.replace("\r", "\n"))

DBFile = "./CFP_Exactly.db"
connect = sqlite3.connect(DBFile)


connect.execute(
    "DROP TABLE IF EXISTS test"
)

TITLE = "title"
ABBREVIATION = "abbreviation"
URL = "url"
LOCATION = "location"
EVENT_DATE_BEGIN = "event_date_begin"
EVENT_DATE_END = "event_date_end"
SUMISSION_DEADLINE = "submission_deadline"
OUTLINE = "outline"

connect.execute(
    "CREATE TABLE IF NOT EXISTS test " + "("
    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
    + TITLE + " TEXT, "
    + ABBREVIATION + " TEXT, "
    + URL + " TEXT, "
    + LOCATION + " TEXT, "
    + EVENT_DATE_BEGIN + " TEXT, "
    + EVENT_DATE_END + " TEXT, "
    + SUMISSION_DEADLINE + " TEXT, "
    + OUTLINE + " TEXT "
    + ")"
)

insert_query = "INSERT INTO test ( " 
    + "_id," 
    + TITLE + ", " 
    + ABBREVIATION + ", "
    + URL + ", "
    + LOCATION + ", "
    + EVENT_DATE_BEGIN + ", "
    + EVENT_DATE_END + ", "
    + SUMISSION_DEADLINE + ", "
    + OUTLINE
    + ")"
    + "VALUES ("
    + g_title
    + g_abbreviation
    + g_url
    + g_location
    + g_event_date_begin
    + g_event_date_end
    + g_submission_deadline
    + g_outline
    + ")"
connect.execute(
    insert_query
)
connect.commit()

cursor = connect.execute(
    "SELECT * FROM test"
)
for row in cursor:
    print(row)

connect.close()