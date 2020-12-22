package Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Data.CFPInfo;
import Data.DBHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchListActivity extends Activity {
    private DBHelper CFPDB;

    private ListView searchList;

    private ArrayList<CFPInfo> infoList;

    private TextView searchInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_layout);

        searchList = (ListView) findViewById(R.id.searchList);

        searchInput = (TextView) findViewById(R.id.searchInput);

        CFPDB = new DBHelper(this);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        searchInput.setText(query);
        ArrayList<String> querylist = new ArrayList<>(Arrays.asList(query.split(" ")));
        query = "";
        boolean first = true;
        for(int i = 0; i < querylist.size(); ++i){
            if(first)first = false;
            else query += " OR ";
            query += " title LIKE '%" + querylist.get(i) + "%' OR category LIKE '%" + querylist.get(i) + "%' ";
        }
        query = " WHERE " + query + " ORDER BY counter DESC ";

        ArrayList<CFPInfo> itemList = CFPDB.getInfoByQuery(query);

        InfoListAdapter ada = new InfoListAdapter(this, R.layout.search_item, itemList);
        searchList.setAdapter(ada);
    }

    private class InfoListAdapter extends ArrayAdapter<CFPInfo>{
        Context context;
        List<CFPInfo> infoList = new ArrayList<CFPInfo>();

        public InfoListAdapter(Context c, int rId, ArrayList<CFPInfo> list){
            super(c, rId, list);
            infoList = list;
            context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            RelativeLayout layout_item = null;
            TextView abbreviation_text = null;
            TextView title_text = null;
            TextView key1 = null;
            TextView key2 = null;
            TextView key3 = null;

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.search_item, parent, false);

                layout_item = (RelativeLayout) convertView.findViewById(R.id.layout_item);
                convertView.setTag(layout_item);
                layout_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CFPInfo item = (CFPInfo) view.getTag();
                        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra("eventId", item.getEventId());
                        startActivity(intent);
                    }
                });
            }
            else{
                layout_item = (RelativeLayout) convertView;
            }

            abbreviation_text = (TextView) layout_item.findViewById(R.id.abbreviation_list);
            title_text = (TextView) layout_item.findViewById(R.id.title_list);
            key1 = (TextView) layout_item.findViewById(R.id.key1);
            key2 = (TextView) layout_item.findViewById(R.id.key2);
            key3 = (TextView) layout_item.findViewById(R.id.key3);

            CFPInfo current = infoList.get(position);
            abbreviation_text.setText(current.getAbbreviation());
            title_text.setText(current.getTitle());
            ArrayList<String> keywords = CFPDB.getTopThreeKeywordsByEventId(current.getEventId());
            if(keywords.size() == 3){
                key1.setText(keywords.get(0));
                key2.setText(keywords.get(1));
                key3.setText(keywords.get(2));
            }
            layout_item.setTag(current);

            return convertView;
        }
    }

    public void back(View view){
        finish();
    }
}
