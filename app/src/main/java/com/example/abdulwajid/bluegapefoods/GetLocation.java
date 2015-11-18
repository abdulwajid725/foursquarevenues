package com.example.abdulwajid.bluegapefoods;

/**
 * Created by abdul wajid on 11/6/2015.
 */


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static android.widget.Toast.makeText;

/**
 * Created by abdul wajid on 11/5/2015.
 */
public class GetLocation extends AsyncTask<String,Integer,String>
{
    String locaddress;
    InputFragment inputFragment;
    String str=null;
    String datadisplay;
    String toprocessString;
    String location;
    ArrayList venuelist;
    //public  AsyncResponse delegate=null;

    public GetLocation(String address,InputFragment context)
    {
        locaddress=address;
        inputFragment = context;
    }

    protected String doInBackground(String... urls)
    {
        toprocessString=makecall("https://maps.googleapis.com/maps/api/geocode/json?address="+locaddress+"&sensor=true");
        return toprocessString;
    }

    private String makecall(String url)
    {
        String replyString="";
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try
        {
            HttpResponse httpResponse=httpClient.execute(httpGet);
            InputStream is=httpResponse.getEntity().getContent();
            BufferedInputStream bis=new BufferedInputStream(is);
            ByteArrayBuffer baf=new ByteArrayBuffer(20);
            int current=0;
            while ((current=bis.read())!=-1)
            {
                baf.append((byte)current);
            }
            replyString=new String(baf.toByteArray());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return replyString.trim();
    }


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
       /* ConnectivityManager connectivityManager=(ConnectivityManager)activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean connected=networkInfo!=null&&networkInfo.isAvailable()&&networkInfo.isConnected();
        if(connected==true)
        {
            Toast t= Toast.makeText(activity, "Connected",Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
            Toast t= Toast.makeText(activity, "Not Connected",Toast.LENGTH_SHORT);
            t.show();
        }*/
    }


    protected void onPostExecute(String processString)
    {

        try
        {
            JSONObject jsonObject=new JSONObject(processString);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            // for(int i=0;i<jsonArray.length();i++)
            // {
            String lat=jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
            String lng=jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
            location="LAT : "+lat+" ,LNG : "+lng;
            FourSquareVenues fourSquareVenues=new FourSquareVenues(lat,lng,inputFragment);
            fourSquareVenues.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}

