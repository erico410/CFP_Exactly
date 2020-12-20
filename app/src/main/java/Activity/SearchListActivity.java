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
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Data.CFPInfo;
import Data.DBHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchListActivity extends Activity {
    private DBHelper CFPDB;

    private ListView searchList;

    private ArrayList<CFPInfo> infoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_layout);

        searchList = (ListView) findViewById(R.id.searchList);

        CFPDB = new DBHelper(this);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        query = " WHERE " + " title LIKE '%" + query + "%' ";

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
            TextView text = null;
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.search_item, parent, false);
                text = (TextView) convertView.findViewById(R.id.itemList);
                convertView.setTag(text);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CFPInfo item = (CFPInfo) view.getTag();
                        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra("eventId", item.getEventId());
                        startActivity(intent);
                    }
                });
            }else{
                text = (TextView) convertView.getTag();
            }

            CFPInfo current = infoList.get(position);
            text.setText(current.getAbbreviation());
            text.setTag(current);
            return convertView;
        }
    }

}
