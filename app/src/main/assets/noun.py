from DBHelper import DBHelper
from Crawler import Crawler_Noun
import csv

CATEGORY = "category"
REALM = "realm"

db = DBHelper()

create_table_query = (
    "CREATE TABLE IF NOT EXISTS {table}" + "("
    + CATEGORY + " TEXT PRIMARY KEY, "
    + REALM + " TEXT "
    + ")").format(table="noun")

db.query(create_table_query)
'''
crawler = Crawler_Noun()

crawler.crawl()
crawler.format()
lis = crawler.get()

#print(lis)

for ins in lis:
    print("insert ", ins)
    insert_query = ("INSERT OR IGNORE INTO noun ( " 
        + CATEGORY + ", " 
        + REALM
        + ")"
        + "VALUES ( \
            '{0}', \
            '{1}' \
            )".format(ins, "None")
    )
    #print(insert_query)
    db.query(insert_query)
    db.commit()
'''

with open('KeyList.csv', newline='') as csvfile:
    rows = csv.reader(csvfile)
    for row in rows:
        print("insert ", row[0])
        insert_query = ("INSERT OR IGNORE INTO noun ( " 
            + CATEGORY + ", " 
            + REALM
            + ")"
            + "VALUES ( \
                '{0}', \
                '{1}' \
                )".format(row[0], "None")
        )
        #print(insert_query)
        db.query(insert_query)
        db.commit()

db.close()