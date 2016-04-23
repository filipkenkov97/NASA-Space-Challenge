package com.example.julian.clearfortake_off;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalCitiesActivity extends AppCompatActivity {


    TextView depView;
    TextView arrView;
    TextView delayView;
    TextView city1View;
    TextView city2View;
    TextView weather1View;
    TextView weather2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_cities);

        Intent intent = this.getIntent();
        Flight f = (Flight)intent.getSerializableExtra("MyObject");
        String dep = (String)intent.getSerializableExtra("departCity");
        String arr = (String)intent.getSerializableExtra("arriveCity");

        Peco p = new Peco();
        String a=null;
        String b=null;
        City start; City end;
        try {
            a = p.getData(dep); b=p.getData(arr);
        } catch (IOException e) {}

        start=p.HTMLParser(a);
        end=p.HTMLParser(b);

        float delayA=p.CheckCondition(start);
        float delayB=p.CheckCondition(end);

        float finalDelay=delayA+delayB;




        depView=(TextView)findViewById(R.id.textView8);
        arrView=(TextView)findViewById(R.id.textView9);
        delayView=(TextView)findViewById(R.id.textView11);
        city1View=(TextView)findViewById(R.id.textView12);
        city2View=(TextView)findViewById(R.id.textView13);
        weather1View=(TextView)findViewById(R.id.textView14);
        weather2View=(TextView)findViewById(R.id.textView15);


        StringBuilder sb = new StringBuilder();
        sb.append(dep + "     " + f.depHour + ":" + f.depMinute );
        depView.setText(sb.toString());

        sb = null;
        arrView.setText(arr + "     " + f.arrHour + ":" + f.arrMinute );

        sb = new StringBuilder();
        sb.append(finalDelay); sb.append("%        ");
        delayView.setText(sb.toString());

        sb = new StringBuilder();
        sb.append(dep);
        city1View.setText(sb.toString());

        sb = new StringBuilder();
        sb.append(arr);
        city2View.setText(sb.toString());

        sb = new StringBuilder();
        sb.append(start.description);
        weather1View.setText(sb.toString());

        sb = new StringBuilder();
        sb.append(end.description);
        weather2View.setText(sb.toString());

    }
}
