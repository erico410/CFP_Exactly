package Activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.Date;

import Data.CFPInfo;
import Data.DBHelper;
import androidx.annotation.Nullable;

public class SearchListActivity extends Activity {
    private DBHelper CFPDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_layout);

        CFPDB = new DBHelper(this);

        CFPInfo temp = new CFPInfo();
        temp.setTitle("title");
        temp.setAbbreviation("abbr");
        temp.setLocation("location");
        temp.setUrl("url");
        temp.setEventDateBegin(new Date());
        temp.setEventDateEnd(new Date());
        temp.setSubmissionDeadline(new Date());
        temp.setOutline("outline");
        
    }

}
