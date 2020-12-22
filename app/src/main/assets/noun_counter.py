from DBHelper import DBHelper
import time

EVENT_ID = "_id"
CATEGORY = "category"
COUTNER = "counter"

db = DBHelper()

create_table_query = (
    "CREATE TABLE IF NOT EXISTS {table}" + "("
    + EVENT_ID + " INTEGER, "
    + CATEGORY + " TEXT, "
    + COUTNER + " INTEGER, "
    + "PRIMARY KEY (" + EVENT_ID + ", " + CATEGORY + ") "
    + ")").format(table="noun_counter")

db.query(create_table_query)

nounList = []

cursor_noun = db.query("SELECT category FROM noun")
for noun in cursor_noun:
    nounList.append(noun[0])

cursor_info = db.query("SELECT _id, outline FROM CFPInfo")
for info in cursor_info:
    print("process ", info[0], "...")
    for noun in nounList:
        nouns = info[1].lower().count(noun)
        if nouns != 0:
            #print(noun, nouns)
            insert_query = ("INSERT OR IGNORE INTO noun_counter ( " 
                + EVENT_ID + ", " 
                + CATEGORY + ", "
                + COUTNER
                + ")"
                + "VALUES ( \
                    {0}, \
                    '{1}', \
                    {2} \
                    )".format(info[0], noun, nouns)
            )
            db.query(insert_query)
            db.commit()

db.close()
