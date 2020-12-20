from Crawler import Crawler_Info
from DBHelper import DBHelper
import time

EVENT_ID = "_id"
TITLE = "title"
ABBREVIATION = "abbreviation"
URL = "url"
LOCATION = "location"
EVENT_DATE_BEGIN = "event_date_begin"
EVENT_DATE_END = "event_date_end"
SUMISSION_DEADLINE = "submission_deadline"
OUTLINE = "outline"

db = DBHelper()

create_table_query = (
    "CREATE TABLE IF NOT EXISTS {table}" + "("
    + "_id INTEGER PRIMARY KEY, "
    + TITLE + " TEXT, "
    + ABBREVIATION + " TEXT, "
    + URL + " TEXT, "
    + LOCATION + " TEXT, "
    + EVENT_DATE_BEGIN + " TEXT, "
    + EVENT_DATE_END + " TEXT, "
    + SUMISSION_DEADLINE + " TEXT, "
    + OUTLINE + " TEXT "
    + ")").format(table="CFPInfo")
db.query(create_table_query)

create_table_query = (
    "CREATE TABLE IF NOT EXISTS {table}" + "("
    + "eventid" + " INTEGER PRIMARY KEY, "
    + "pass" + " INTEGER "
    + ")").format(table="whiteList")
db.query(create_table_query)

print("start crawling...")
for eventid in range(93740, 120550):
    print("try ", eventid, "...", end="")

    cursor = db.query(f"SELECT pass FROM whiteList WHERE eventid = {eventid}")
    v = cursor.fetchone()
    if v != None and v[0] != 1:
        print("skip")
        continue

    params = {
        'eventid' : eventid
    }
    crawler = Crawler_Info()
    
    crawler.crawl(params)
    
    if crawler.check():
        crawler.format()
        lis = crawler.get()

        cursor = db.query(f"SELECT _id FROM CFPInfo WHERE _id = {eventid}")
        v = cursor.fetchone()
        '''
        if v != None:
            print("duplicate")
            #time.sleep(3.0)
            continue'''

        insert_query = ("INSERT OR IGNORE INTO CFPInfo ( " 
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
            + "VALUES ( \
                {0}, \
                '{1}', \
                '{2}', \
                '{3}', \
                '{4}', \
                '{5}', \
                '{6}', \
                '{7}', \
                \"{8}\" \
                )".format(eventid, lis[0], lis[1], lis[2], lis[3], lis[4], lis[5], lis[6], lis[7].replace("\"", "'"))
        )

        #print(insert_query)
        #db.query(insert_query)
        try:
            db.query(insert_query)
            print(f"insert {eventid}: {lis[0]}")
            pass_query = (
                "INSERT OR IGNORE INTO whiteList ("
                + "eventid, " 
                + "pass " 
                + ")"
                + "VALUES ('{0}', '{1}')".format(eventid, 0)
            )
            if v != None:
                pass_query = (
                    f"UPDATE whiteList SET pass='0' WHERE eventid='{eventid}'"
                )
            db.query(pass_query)
            time.sleep(3.0)
        except:
            print(f"insert {eventid}: DB fail")
            pass_query = (
                "INSERT OR IGNORE INTO whiteList ("
                + "eventid, " 
                + "pass " 
                + ")"
                + "VALUES ('{0}', '{1}')".format(eventid, 1)
            )
            db.query(pass_query)
            pass
    else:
        print(f"insert {eventid}: page fail")
        pass_query = (
                "INSERT OR IGNORE INTO whiteList ("
                + "eventid, " 
                + "pass " 
                + ")"
                + "VALUES ('{0}', '{1}')".format(eventid, 2)
            )
        db.query(pass_query)
    db.commit()
    #time.sleep(3.0)

cursor = db.query("SELECT title FROM CFPInfo")

for it in cursor:
    print(it)

db.close()