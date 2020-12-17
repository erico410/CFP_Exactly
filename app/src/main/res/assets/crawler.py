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

print("title:")
print(content.title.text)

print("abbreviation:")
print(content.title.text.strip().split(" : ")[0])

print("url:")
print(content.find("a", target="_newtab").text)

print("location:")
print(content.find("table", class_="gglu").find_all("tr")[1].find("td").text)

print("eventDateBegin:")
print(content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[0])

print("eventDateEnd:")
print(content.find("table", class_="gglu").find_all("tr")[0].find("td").text.strip().split(" - ")[1])

print("submissionDeadline:")
print(content.find("table", class_="gglu").find_all("tr")[3].find("td").text.strip())

print("outline:")
for line in content.find("div", class_="cfp").text.split("<br>"):
    print(line.replace("\r", "\n"))

DBFile = "./CFP_Exactly.db"
connect = sqlite3.connect(DBFile)

'''
connect.execute(
    "DROP TABLE IF EXISTS test"
)'''

connect.execute(
    "CREATE TABLE IF NOT EXISTS test " + "("
    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
    + "name TEXT "
    + ")"
)

connect.execute(
    "INSERT INTO test (_id, name) VALUES (null, 'b')"
)
connect.commit()

cursor = connect.execute(
    "SELECT * FROM test"
)
for row in cursor:
    print(row)

connect.close()