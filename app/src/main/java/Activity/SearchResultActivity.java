package Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import Data.CFPInfo;
import Data.DBHelper;
import androidx.annotation.Nullable;

public class SearchResultActivity extends Activity {
    private DBHelper CFPDB;
    private CFPInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);

        Intent intent = getIntent();
        
        CFPDB = new DBHelper(this);

    }

}
