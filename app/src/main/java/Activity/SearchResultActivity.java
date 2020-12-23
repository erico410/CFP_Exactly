package Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Data.CFPInfo;
import Data.DBHelper;
import Data.NounCounter;
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

    private BarChart CFP_BarChart_noun;
    private XAxis CFP_BarChart_noun_xAxis;
    private YAxis CFP_BarChart_noun_yAxis;
    private Legend CFP_BarChart_noun_legend;

    private ArrayList<Integer> colorSets = new ArrayList<>();

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

        CFP_BarChart_noun = (BarChart) findViewById(R.id.CFP_BarChart_noun);

        colorSets = new ArrayList<>();
        colorSets.add(Color.RED);
        colorSets.add(Color.YELLOW);
        colorSets.add(Color.BLUE);
        colorSets.add(Color.GREEN);
        colorSets.add(Color.MAGENTA);
        colorSets.add(Color.GRAY);
        colorSets.add(Color.GRAY);
        colorSets.add(Color.GRAY);
        colorSets.add(Color.GRAY);
        colorSets.add(Color.GRAY);

        Intent intent = getIntent();
        Integer eventId = intent.getIntExtra("eventId", 0);

        CFPDB = new DBHelper(this);

        CFPInfo result = CFPDB.getInfoByEventId(eventId);

        CFP_title_text.setText(result.getTitle());
        CFP_abbreviation_text.setText(result.getAbbreviation());
        // chart
        initCFPBarChart();
        ArrayList<NounCounter> arrayList = CFPDB.getKeywordCounterByEventId(eventId);
        showCFPBarChart(arrayList, "Noun Chart", Color.YELLOW);

        CFP_url_text.setText(result.getUrl());
        CFP_location_text.setText(result.getLocation());
        CFP_event_date_text.setText(String.format("%s - %s", result.getEventDateBegin(), result.getEventDateEnd()));
        CFP_submission_deadline_text.setText(result.getSubmissionDeadline());
        CFP_outline_text.setText(result.getOutline());
    }

    private void initCFPBarChart(){
        CFP_BarChart_noun.setBackgroundColor(Color.argb(80, 0,0,0));
        CFP_BarChart_noun.setDrawBorders(true);
        CFP_BarChart_noun.getDescription().setEnabled(false);

        CFP_BarChart_noun_xAxis = CFP_BarChart_noun.getXAxis();
        CFP_BarChart_noun_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        CFP_BarChart_noun_xAxis.setGranularity(1f);
        CFP_BarChart_noun_xAxis.setDrawLabels(false);

        CFP_BarChart_noun_yAxis = CFP_BarChart_noun.getAxisLeft();
        CFP_BarChart_noun_yAxis.setGranularity(1f);
        CFP_BarChart_noun_yAxis.setTextSize(20);
        CFP_BarChart_noun_yAxis.setTextColor(Color.WHITE);

        CFP_BarChart_noun_legend = CFP_BarChart_noun.getLegend();
        CFP_BarChart_noun_legend.setWordWrapEnabled(true);
        CFP_BarChart_noun_legend.setTextSize(20);
        CFP_BarChart_noun_legend.setTextColor(Color.WHITE);
    }

    private void initCFPBarDataSet(BarDataSet barDataSet, int color){
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1);
        barDataSet.setFormSize(15);
        barDataSet.setDrawValues(false);
    }

    private void showCFPBarChart(ArrayList<NounCounter> data, String name, int color){
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();

        for(int i = 0; i < data.size(); ++i){
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            BarEntry entry = new BarEntry(i+1, data.get(i).getCounter());
            entries.add(entry);
            BarDataSet barDataSet = new BarDataSet(entries, data.get(i).getNoun());
            initCFPBarDataSet(barDataSet, colorSets.get(i));
            barDataSets.add(barDataSet);
        }

        BarData barData = new BarData(barDataSets);
        CFP_BarChart_noun.setData(barData);
        CFP_BarChart_noun.invalidate();
    }

    public void back(View view){
        finish();
    }
}
