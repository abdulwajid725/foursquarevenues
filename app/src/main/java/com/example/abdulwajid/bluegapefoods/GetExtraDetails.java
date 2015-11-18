package com.example.abdulwajid.bluegapefoods;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by abdul wajid on 11/8/2015.
 */
public class GetExtraDetails extends AsyncTask<String,Integer,String>
{
    SendToListDetailActivity sendToListDetailActivity;
    String id;
    String temp;
    String lat;
    String lng;
    ArrayList<VenueDetailPrototype> venuedetaillist;
    final String CLIENT_ID="VFFXFANXHFPJESQRVV54BTCUYJSUQQQRPLT41S1RLEE2ZJS2";
    final String CLIENT_SECRET="VF02C2XT4H1XPX2YMVAFT2YB4HODDZ0SXGMTMQGQA2UP3LP3";
    public GetExtraDetails(Context c,String id,String lat,String lng)
    {
        this.id=id;
        this.lat=lat;
        this.lng=lng;
        sendToListDetailActivity=(SendToListDetailActivity)c;
    }

    @Override
    protected String doInBackground(String... urls)
    {
        temp = makeCall("https://api.foursquare.com/v2/venues/" +id+ "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll="+lat+","+lng);
        int flag1=0;
        return temp;

        //return null;
    }
    public String makeCall(String url)
    {
        StringBuffer buffer_string=new StringBuffer(url);
        String replyString="";
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(buffer_string.toString());

        try
        {
            HttpResponse response=httpClient.execute(httpGet);
            InputStream is=response.getEntity().getContent();
            BufferedInputStream bis=new BufferedInputStream((is));
            ByteArrayBuffer baf=new ByteArrayBuffer(20);
            int current=0;
            while((current=bis.read())!=-1)
            {
                baf.append((byte)current);
            }
            replyString =new String(baf.toByteArray());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return replyString.trim();

    }

    @Override
    protected void onPostExecute(String result)
    {
        VenueDetailPrototype venuedetail;
        if(result!=null)
        {
            venuedetail=parseFourSquare(result);
            sendToListDetailActivity.sendtolistdetail(venuedetail);
            //passToVenueListFragment.onTaskComplete(venuelist);
            //passToInputFragmentListener.onTaskCompleted(venuelist);

        }
    }
    private VenueDetailPrototype parseFourSquare(String result)
    {
        VenueDetailPrototype venueDetailPrototype=null;
        ArrayList<VenueDetailPrototype> list=new ArrayList<VenueDetailPrototype>();
        try
        {
            //insert these items into
            JSONObject jsonObject=new JSONObject(result);
            JSONObject jsonObject1;
            String address="";
            String contact="";
            String likes="";
            String isopen="";
            String reason = null;

            String rating;
            String name=jsonObject.getJSONObject("response").getJSONObject("venue").getString("name");
            try
            {
                JSONArray jsonArray1=jsonObject.getJSONObject("response").getJSONObject("venue").getJSONObject("reasons").getJSONArray("items");
                reason=jsonArray1.getJSONObject(0).getString("summary");
            }
            catch(Exception e)
            {
                reason="NO REVIEWS";
            }

            try
            {
                jsonObject1=jsonObject.getJSONObject("response").getJSONObject("venue");
                contact=jsonObject1.getJSONObject("contact").getString("phone");

            }
            catch (Exception e2)
            {
                contact="No Contact Info";
            }
            try
            {
                int rati=jsonObject.getJSONObject("response").getJSONObject("venue").getInt("rating");
                rating=Integer.toString(rati);
            }
            catch(Exception e7)
            {
                rating="N.A";
            }


            String url=jsonObject.getJSONObject("response").getJSONObject("venue").getString("canonicalUrl");
            JSONArray addressArray=jsonObject.getJSONObject("response").getJSONObject("venue").getJSONObject("location").getJSONArray("formattedAddress");
            for(int j=0;j<addressArray.length();j++)
            {
                address=address +"\n"+ addressArray.getString(j);
            }
            int flag1=0;
            try
            {
                likes=jsonObject.getJSONObject("response").getJSONObject("venue").getJSONObject("likes").getString("summary");
            }
            catch (Exception e1)
            {
                likes="No Likes";
                int flag=0;
            }

            try
            {
                jsonObject1=jsonObject.getJSONObject("response").getJSONObject("venue").getJSONObject("popular");
                isopen = jsonObject1.getString("status");
            }
            catch(Exception e)
            {
                isopen="No Information";
                int flag=0;
            }
            venueDetailPrototype=new VenueDetailPrototype(name,contact,url,address,likes,isopen,rating,reason);
            //list.add(venueDetailPrototype);
            //sendToListDetailActivity.sendtolistdetail(venueDetailPrototype);
            //list.add(venueDetailPrototype);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return venueDetailPrototype;
    }
    public interface SendToListDetailActivity
    {
        void sendtolistdetail(VenueDetailPrototype v);
    }
}
