package Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SearchActivity extends Activity {

    private SearchView searchBar;

    private Button searchBT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        searchBar = (SearchView) findViewById(R.id.search_bar);

        searchBT = (Button) findViewById(R.id.searchBT);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getBaseContext(), SearchListActivity.class);
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("query", s);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchListActivity.class);
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("query", searchBar.getQuery().toString());
                startActivity(intent);
            }
        });

    }
}
