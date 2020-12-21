package Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import Data.CFPInfo;
import Data.DBHelper;
import androidx.annotation.Nullable;

public class SearchResultActivity extends Activity {
    private DBHelper CFPDB;
    private CFPInfo info;

    private Integer queryId;

    private TextView CFP_title_text;
    private TextView CFP_abbreviation_text;
    private TextView CFP_url_text;
    private TextView CFP_location_text;
    private TextView CFP_event_date_text;
    private TextView CFP_submission_deadline_text;
    private TextView CFP_outline_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);

        CFP_title_text = (TextView) findViewById(R.id.CFP_title_text);
        CFP_abbreviation_text = (TextView) findViewById(R.id.CFP_abbreviation_text);
        CFP_url_text = (TextView) findViewById(R.id.CFP_url_text);
        CFP_location_text = (TextView) findViewById(R.id.CFP_location_text);
        CFP_event_date_text = (TextView) findViewById(R.id.CFP_event_date_text);
        CFP_submission_deadline_text = (TextView) findViewById(R.id.CFP_submission_deadline_text);
        CFP_outline_text = (TextView) findViewById(R.id.CFP_outline_text);


        Intent intent = getIntent();
        Integer eventId = intent.getIntExtra("eventId", 0);

        CFPDB = new DBHelper(this);

        CFPInfo result = CFPDB.getInfoByEventId(eventId);

        CFP_title_text.setText(result.getTitle());
        CFP_abbreviation_text.setText(result.getAbbreviation());
        CFP_url_text.setText(result.getUrl());
        CFP_location_text.setText(result.getLocation());
        CFP_event_date_text.setText(String.format("%s - %s", result.getEventDateBegin(), result.getEventDateEnd()));
        CFP_submission_deadline_text.setText(result.getSubmissionDeadline());
        CFP_outline_text.setText(result.getOutline());
    }

}
