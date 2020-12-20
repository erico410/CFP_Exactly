package Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Data.DBHelper;
import androidx.annotation.Nullable;

public class WelcomeActivity extends Activity {
    private Button searchBT;

    private DBHelper CFPDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
      
        searchBT = (Button) findViewById(R.id.searchBT);
        searchBT.setEnabled(false);
      
        searchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        CFPDB = new DBHelper(this);
        CFPDB.createDatabase();
        //while(!CFPDB.checkDatabase());
        searchBT.setEnabled(true);
    }
}
