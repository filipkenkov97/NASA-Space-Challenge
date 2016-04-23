package com.example.julian.clearfortake_off;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button lista;
    EditText departure1;
    EditText arrival1;
    EditText yr;
    EditText mnt;
    EditText dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dpoint);
        lista = (Button) findViewById(R.id.Blist);
        yr = (EditText)findViewById(R.id.year);
        mnt = (EditText)findViewById(R.id.month);
        dy = (EditText)findViewById(R.id.day);
        departure1 = (EditText)findViewById(R.id.departure);
        arrival1 = (EditText)findViewById(R.id.arrival);
        lista.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)   {

                String dep; String arr;
                dep = departure1.getText().toString();
                arr = arrival1.getText().toString();
                int year,month,day;


                String str = yr.getText().toString(); year = Integer.parseInt(str);
                month = Integer.parseInt(mnt.getText().toString());
                day = Integer.parseInt(dy.getText().toString());



                Intent intent = new Intent(Main2Activity.this, ListActivity.class);
                intent.putExtra("dep", dep);
                intent.putExtra("arr", arr);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
             //   intent.putExtra("dep", dep);


                startActivity(intent);

                //setContentView(R.layout.list);

               /* FlightData fd = new FlightData();
                try {
                    fd.selectFlight(dep,arr,year,month,day);

                } catch (IOException e) {


                }

                ArrayList<Flight> arlf = fd.flightList;
                Flight ft = arlf.get(0);
                TextView kur = (TextView)findViewById(R.id.textView3);

                StringBuilder sb = new StringBuilder();
                sb.append("Airline: " + ft.Airline);
                sb.append("Number: " + ft.FlightNumber);
                sb.append("Departure: " + ft.depHour + ":" + ft.depMinute);
                sb.append("Arrival: " + ft.arrHour + ":" + ft.arrMinute);

                kur.setText(sb.toString());
                */
    }

});
    }}
