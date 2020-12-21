package Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Data.DBHelper;
import androidx.annotation.Nullable;

public class WelcomeActivity extends Activity {
    private ImageButton startBT;

    private DBHelper CFPDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        startBT = (ImageButton) findViewById(R.id.startBT);
        startBT.setEnabled(false);

        startBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        CFPDB = new DBHelper(this);
        CFPDB.createDatabase();
        //while(!CFPDB.checkDatabase());
        startBT.setEnabled(true);
    }
}
