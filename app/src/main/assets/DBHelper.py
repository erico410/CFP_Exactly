import sqlite3

class DBHelper():
    def __init__(self):
        self.DBFile = "./CFP_Exactly.db"
        self.connect = sqlite3.connect(self.DBFile)

    def query(self, query):
        return self.connect.execute(query)

    def commit(self):
        self.connect.commit()

    def close(self):
        self.connect.close()