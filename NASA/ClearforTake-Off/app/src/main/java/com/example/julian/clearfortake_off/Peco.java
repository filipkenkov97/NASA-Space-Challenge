package com.example.julian.clearfortake_off;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;


public class Peco {


    ArrayList<City> list = new ArrayList<City>();

    public String getData (String s) throws MalformedURLException,IOException {

        StringBuilder urlText = new StringBuilder();
        urlText.append("http://api.openweathermap.org/data/2.5/weather?q=");
        urlText.append(s);
        urlText.append("&APPID=ffae24d18e2d276437a34a2f4be88093");
        String text = urlText.toString();
        URL url = new URL(text);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder htmlString = new StringBuilder();
        String line;
        while ((line = rdr.readLine()) != null) {
            htmlString.append(line);
        }

        return htmlString.toString();
    }

    public City HTMLParser (String s) {

        City c = new City();
        String x="temp";
        int i=s.indexOf(x)+6;
        int k=i;
        while(((s.charAt(k) == '0') || (s.charAt(k) == '1')||(s.charAt(k) == '2') ||(s.charAt(k) == '3')||(s.charAt(k) == '4') ||(s.charAt(k) == '5') ||(s.charAt(k) == '6') ||(s.charAt(k) == '7') ||(s.charAt(k) == '8') ||(s.charAt(k) == '9')) || (s.charAt(k) == '.'))
        {
            k++;
        }

        String newS=s.substring(i, k);
        //=Float.parseFloat(newS);
        float value = Float.valueOf(newS.trim()).floatValue();
        c.temp=value;
        x="id";
        i=s.indexOf(x)+4;
        k=i+3;

        newS=s.substring(i, k);
        c.ID=newS;

        x="pressure";
        i=s.indexOf(x)+10;
        k=i;
        while((s.charAt(k)>='0' && s.charAt(k)<='9')||s.charAt(k)=='.')
        {
            k++;
        }
        k--;
        newS=s.substring(i, k);
        value=Float.parseFloat(newS);
        c.pressure=value;

        x="humidity";
        i=s.indexOf(x)+10;
        k=i;
        while((s.charAt(k)>='0' && s.charAt(k)<='9')||s.charAt(k)=='.')
        {
            k++;
        }
        k--;
        newS=s.substring(i, k);
        value=Float.parseFloat(newS);
        c.humidity=value;

        x="speed";
        i=s.indexOf(x)+7;
        k=i;
        while((s.charAt(k)>='0' && s.charAt(k)<='9')||s.charAt(k)=='.')
        {
            k++;
        }
        k--;
        newS=s.substring(i, k);
        value=Float.parseFloat(newS);
        c.windSpeed=value;

        x="description";
        i=s.indexOf(x)+14;
        k=i;
        while(s.charAt(k)!='"')
            k++;
        newS=s.substring(i,k);
        c.description=newS;

        return c;
    }

    public static float CheckCondition(City city)
    {
        float x = 0;


        //General Weather
        TreeSet<String> v1 = new TreeSet<String>();
        v1.add("221");
        v1.add("231");
        v1.add("314");
        v1.add("521");
        v1.add("522");
        v1.add("731");

        TreeSet<String> v2 = new TreeSet<String>();
        v2.add("232");
        v2.add("531");
        v2.add("622");
        v2.add("761");
        v2.add("751");
        v2.add("961");
        v2.add("741");

        TreeSet<String> v3 = new TreeSet<String>();
        v3.add("762");
        v3.add("771");
        v3.add("781");
        v3.add("962");

        if(v1.contains(city.ID))
            x=6;
        if(v2.contains(city.ID))
            x=14;
        if(v3.contains(city.ID))
            x=97;

        //Wind
        x+=(city.windSpeed*0.043);
        //Temperature
        x+=(city.temp*0.003);
        //Pressure
        x+=(city.pressure*0.00012);
        //Humidity
        x+=(city.humidity*0.005);
        if(x<100)
            return x;
        return 100;
    }

    public static void input() throws MalformedURLException, IOException
    {
        Peco p = new Peco();
        Peco l = new Peco();
        String A = p.getData("Skopje");
        City Atest=p.HTMLParser(A);
        String B = l.getData("Sydney");
        City Btest=l.HTMLParser(B);
        System.out.println("Skopje ID: " + Atest.ID);
        System.out.println("Moscow ID: " + Btest.ID);
        float delayA = CheckCondition(Atest);
        float delayB = CheckCondition(Btest);
        float finalised=delayA+delayB;
        System.out.println("Chance of delay is: " + finalised);
    }

    public static void main(String[] args) throws MalformedURLException, IOException {

        input();
    }
}
