package com.example.julian.clearfortake_off;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FlightData {

    ArrayList < Flight > flightList;
    String departCity;
    String arriveCity;
    FlightData () {

        flightList = new ArrayList<Flight>();
        departCity=new String();
        arriveCity = new String();
    }



    String getAirline (String arp) throws IOException {

        StringBuilder src = new StringBuilder();
        src.append("https://api.flightstats.com/flex/airlines/rest/v1/xml/iata/");
        src.append(arp);
        src.append("?appId=78c7460d&appKey=8d3b2caf75e8ac0ac80f03fcfff08d9a");

        URL url = new URL(src.toString());

        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String resp = response.toString();
        int i = resp.indexOf("<name>");
        i+=6;
        int k = i;
        while (resp.charAt(k)!='<') k++;
        return resp.substring(i,k);

    }
    String putTogether (String dep, String arr, Integer year, Integer month, Integer day) {
        StringBuilder src = new StringBuilder( "https://api.flightstats.com/flex/schedules/rest/v1/xml");
        src.append("/from/");
        src.append(dep);
        src.append("/to/");
        src.append(arr);
        src.append("/departing/");
        src.append(year.toString());
        src.append("/" + month.toString() + "/" + day.toString());
        src.append("?appId=78c7460d&appKey=8d3b2caf75e8ac0ac80f03fcfff08d9a");
        return src.toString();

    }


    String getXML (String src) throws IOException {



        URL url = new URL(src);

        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }


    public void parseFlight (String xml, String dep, String arr) throws IOException {

        //	ArrayList<Flight> list = new ArrayList<Flight>();
        System.out.println("VLAGAM U FUNKCIJAVA");
        while (1==1) {

            String fn;
            Flight f = new Flight();
            int i = xml.indexOf("<carrierFsCode>");
            if (i==-1) return;
            System.out.println("DO TUKA");
            i+=15;
            int s=i;
            while(xml.charAt(i)!='<') i++;

            fn=xml.substring(s,i);
            fn = this.getAirline(fn);
            f.Airline=fn;

            f.destinationAirport = dep;
            f.arrivingAirport = arr;
            i = xml.indexOf("<flightNumber>");
            //if (i==-1) return list;
            //Flight f = new Flight();
            //String fn = new String();
            i+=14;
            int k = i;
            while (xml.charAt(k)!='<') k++;
            fn = xml.substring(i,k);
            f.FlightNumber = fn;


            i = xml.indexOf("<departureTime>");
            i+=15;
            while (xml.charAt(i)!='T') i++;
            i++;

            fn=xml.substring(i,i+2);
            f.depHour = Integer.parseInt(fn);

            i+=3;
            fn=xml.substring(i,i+2);
            f.depMinute = Integer.parseInt(fn);

            i = xml.indexOf("<arrivalTime>");
            i+=13;
            while(xml.charAt(i)!='T') i++;
            i++;

            fn=xml.substring(i, i+2);
            f.arrHour = Integer.parseInt(fn);

            i+=3;
            fn=xml.substring(i,i+2);
            f.arrMinute = Integer.parseInt(fn);

            f.destinationAirport=dep;
            f.arrivingAirport=arr;
			
			
			
			
			/*System.out.println(f.FlightNumber);
			System.out.println(f.depHour);
			System.out.println(f.depMinute);
			System.out.println(f.arrHour);
			System.out.println(f.arrMinute);
			System.out.println(f.Airline);  */


            flightList.add(f);
            xml=xml.substring(i,xml.length()-1);
        }

    }



    public String putTogether (String cityName) {

        StringBuilder sb = new StringBuilder("https://airport.api.aero/airport/match/");
        sb.append(cityName);
        sb.append("?user_key=3bcae6d671e2e7112bb6bb9ab454c62c");
        return sb.toString();


    }

    public void selectFlight (String dep, String arr, int year, int month, int day) throws IOException {

        ArrayList <String> departAirports=new ArrayList <String>();
        ArrayList <String> arriveAirports=new ArrayList <String>();
        String depStr = this.getXML(this.putTogether(dep));
        String arrStr = this.getXML(this.putTogether(arr));

        //Toast.makeText(this,dep, Toast.LENGTH_LONG).show();
        this.departCity=dep;
        this.arriveCity=arr;


        while (1==1) {
            String str;
            int i = depStr.indexOf("code");
            if (i==-1) break;
            i+=7;
            int k = i;

            while (depStr.charAt(k)!=',' ) k++;
            k--;
            str = depStr.substring(i,k);
          //  System.out.println("NOV KOD: " + str);
            departAirports.add(str);
            depStr=depStr.substring(k,depStr.length()-1);


        }

        //	System.out.println();

        while (1==1) {
            String str = new String();
            int i = arrStr.indexOf("code");
            if (i==-1) break;
            i+=7;
            int k = i;
            while (arrStr.charAt(k)!=',' ) k++;
            k--;
            str = arrStr.substring(i,k);
            System.out.println("NOV KOD: " + str);
            arriveAirports.add(str);
            arrStr=arrStr.substring(k,arrStr.length()-1);


        }

        ///sega mi treba sekoj so sekoj da vidam letovi i toa da bide listata

        for (int i=0;i<departAirports.size();i++) {

            for (int j=0;j<arriveAirports.size();j++) {

                //pobaraj let za ovie dva aerodromi
                this.parseFlight(getXML(putTogether(departAirports.get(i),arriveAirports.get(j),year,month,day)),departAirports.get(i),arriveAirports.get(j));
            }

        }


        //    System.out.println("KENKOV");
     //   System.out.println(flightList.size());


        for (int i=0;i<flightList.size();i++) {

      //      System.out.println("NEW FLIGHT");
            System.out.println(flightList.get(i).FlightNumber);
            System.out.println(flightList.get(i).Airline);
            System.out.println(flightList.get(i).destinationAirport + " to " + flightList.get(i).arrivingAirport);
            System.out.println(flightList.get(i).depHour + ":" + flightList.get(i).depMinute + " to " + flightList.get(i).arrHour + ":" + flightList.get(i).arrMinute);

        }



    }






  /*  public static void main (String args[]) throws IOException {

        FlightData fd = new FlightData();
        fd.selectFlight("London", "Istanbul", 2016, 4, 24);
		
		
		
	/*	for (int i=0;i < arl.size(); i++) {
			
			System.out.println(arl.get(i).Airline);
			System.out.println(arl.get(i).FlightNumber);
			System.out.println("Departure: " + arl.get(i).depHour + ":" + arl.get(i).depMinute);
			System.out.println("Arrival: " + arl.get(i).arrHour + ":" + arl.get(i).arrMinute);
			
		}
    }  */
}