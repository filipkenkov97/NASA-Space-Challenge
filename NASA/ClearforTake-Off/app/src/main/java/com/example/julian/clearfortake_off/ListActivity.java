package com.example.julian.clearfortake_off;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends Activity {

    TextView tv;
    String dep;


    FlightData fd;

    String arr;
    Integer yr;
    ListView view;
    Integer mnt;
    Integer dy;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        Intent intent = new Intent();
        intent = getIntent();

        dy = (Integer) intent.getExtras().get("day");
        mnt = (Integer) intent.getExtras().get("month");
        yr = (Integer) intent.getExtras().get("year");
        arr = intent.getStringExtra("arr");
        dep = intent.getStringExtra("dep");


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




      //  thread.start();

    FlightData fd = new FlightData();

        try {
            fd.selectFlight(dep,arr,yr,mnt,dy);
        } catch (IOException e) {
            e.printStackTrace();
        }
       final ArrayList<Flight> lista = fd.flightList;
        List<String> list = new ArrayList<String>();

        for (int i=0;i<lista.size();i++) {

            StringBuilder sb = new StringBuilder();
            sb.append(lista.get(i).Airline + " ");
            sb.append(lista.get(i).FlightNumber + " ");
            sb.append(lista.get(i).destinationAirport + "->" + lista.get(i).arrivingAirport+ " ");
            sb.append(lista.get(i).depHour+":" +lista.get(i).depMinute + "-" + lista.get(i).arrHour + ":" + lista.get(i).arrMinute);
            list.add(sb.toString());
        }


        view = (ListView)findViewById(R.id.listView);

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(ListActivity.this, FinalCitiesActivity.class);
                myIntent.putExtra("MyObject", lista.get(position));
                myIntent.putExtra("departCity", dep);
                myIntent.putExtra("arriveCity",arr);
                startActivity(myIntent);


            }
        });




     /*   Flight f = lista.get(0);
        StringBuilder sb = new StringBuilder();
        sb.append(f.Airline);
        sb.append(f.FlightNumber);
        //sb.append(f.)
        tv=(TextView)findViewById(R.id.textView3);
        tv.setText(sb.toString()); */



    }

    public class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
