package Activity;

import android.app.Activity;
import android.os.Bundle;

import Data.DBHelper;
import androidx.annotation.Nullable;

public class SearchListActivity extends Activity {
    private DBHelper CFPDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_layout);

        CFPDB = new DBHelper(this);

    }

}
