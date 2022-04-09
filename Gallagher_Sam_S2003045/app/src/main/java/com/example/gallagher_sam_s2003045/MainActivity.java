//Gallagher_Sam
//S2003045

package com.example.gallagher_sam_s2003045;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button plannedRoadWorksButton;
    private Button roadWorksButton;
    private Button currentIncidentsButton;
    private Button searchButton;
    private EditText searchWords;

    private ListView listView;

    private TextView listItems;

    private ArrayList<TrafficScotlandInfo> displayList = new ArrayList<>();

    private String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");

        currentIncidentsButton = findViewById(R.id.currentIncidentsButton);
        currentIncidentsButton.setOnClickListener(this);

        plannedRoadWorksButton = findViewById(R.id.plannedRoadWorksButton);
        plannedRoadWorksButton.setOnClickListener(this);

        roadWorksButton = findViewById(R.id.roadWorksButton);
        roadWorksButton.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.mainListView);

        listItems = (TextView) findViewById(R.id.itemlabel);

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        searchWords = findViewById(R.id.searchWords);

    }

    @Override
    public void onClick(View v) {

        if (v == plannedRoadWorksButton)
        {
            Log.e("MyTag","on click 1");
            readPlannedRoadWorks();
        }
        if (v == currentIncidentsButton)
        {
            readCurrentIncidents();
        }
        if (v == roadWorksButton)
        {
            readRoadWorks();
        }
        if (v == searchButton)
        {
            String userEntry = searchWords.getText().toString().toLowerCase();

            Log.d("userEntry", userEntry);

            ArrayList<TrafficScotlandInfo> searchResultsList = new ArrayList<>();

            if (displayList.isEmpty())
            {
                readRoadWorks();
            }

            for (TrafficScotlandInfo item : displayList)
            {
                if (item.getRoad().toLowerCase().contains(userEntry))
                {
                    searchResultsList.add(item);
                }

                ArrayAdapter<TrafficScotlandInfo> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_display_list, searchResultsList);

                listView.setAdapter(adapter);
            }
        }
    }

    public void readPlannedRoadWorks () {
        String urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
        startProgress(urlSource);
    }

    public void readCurrentIncidents () {
        String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        startProgress(urlSource);
    }

    public void readRoadWorks () {
        String urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
        startProgress(urlSource);
    }

    public void startProgress(String urlSource)
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    }

    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            result = "";
            Log.e("MyTag","in run");
            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new
                        InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");

                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);
                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    PullParser p = new PullParser();

                    displayList = p.parseData(result);

                    Log.d("Array Data", displayList.toString());

                    ArrayAdapter<TrafficScotlandInfo> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_display_list, displayList);
                    listView.setAdapter(adapter);
                }
            });
        }
    }
}