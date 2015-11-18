package com.example.abdulwajid.bluegapefoods;


 import android.content.Context;
 import android.content.res.Resources;
 import android.os.AsyncTask;
 import android.widget.ArrayAdapter;
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
 import java.util.List;

/**
 * Created by abdul wajid on 11/6/2015.
 */
public class FourSquareVenues extends AsyncTask<String,Integer,String>
{
    PassToInputFragmentListener passToInputFragmentListener;
    InputFragment inputFragment;
    //PassToVenueListFragment passToVenueListFragment;
    String latitude;
    String longitude;
    String temp;
    String temp1="";
    Context context;
    String imagejson;
    final String CLIENT_ID="VFFXFANXHFPJESQRVV54BTCUYJSUQQQRPLT41S1RLEE2ZJS2";
    final String CLIENT_SECRET="VF02C2XT4H1XPX2YMVAFT2YB4HODDZ0SXGMTMQGQA2UP3LP3";
    ArrayList<VenuePrototype> venuelist;
    ArrayAdapter madapter;
    FourSquareVenues f;

    public FourSquareVenues(String lat,String lng,PassToInputFragmentListener listener)
    {
        this.context=context;
        passToInputFragmentListener=listener;
        inputFragment=(InputFragment)listener;
        latitude=lat;
        longitude=lng;
    }
   /* public FourSquareVenues(PassToVenueListFragment v)
    {
        passToVenueListFragment=v;
    }*/


    protected String doInBackground(String... urls)
    {
        temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll="+latitude+","+longitude);
        return temp;

        //f=(FourSquareVenues)passToInputFragmentListener;


    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result!=null)
        {
            venuelist=(ArrayList)parseFourSquare(result);
           // passToVenueListFragment.onTaskComplete(venuelist);
            passToInputFragmentListener.onTaskCompleted(venuelist);

        }
    }



    private ArrayList parseFourSquare(String result)
    {
        ArrayList<VenuePrototype> list=new ArrayList<VenuePrototype>();
        //f=(FourSquareVenues)passToInputFragmentListener;
        try
        {
            DataBaseHelper dataBaseHelper=new DataBaseHelper(inputFragment.getActivity());
            dataBaseHelper.delete();
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONObject("response").getJSONArray("venues");
            for(int i=0;i<20;i++)
            {
                //insert these data into table created in Input Fragment
                String address="";
                String id=jsonArray.getJSONObject(i).getString("id");
                String name=jsonArray.getJSONObject(i).getString("name");
                String lat=jsonArray.getJSONObject(i).getJSONObject("location").getString("lat");
                String lng=jsonArray.getJSONObject(i).getJSONObject("location").getString("lng");
                String distance=jsonArray.getJSONObject(i).getJSONObject("location").getString("distance")+" metres";
              //  GetImageUrl getImageUrl=new GetImageUrl(id,lat,lng);
               // getImageUrl.execute();
                //String imgurl1=imgurl;
                //getImageUrl(this).execute();
                int flag=0;
                //String imageurl=getImageUrl(id,lat,lng);
                JSONArray addressArray=jsonArray.getJSONObject(i).getJSONObject("location").getJSONArray("formattedAddress");
                for(int j=0;j<addressArray.length();j++)
                {
                    address=address +"\n"+ addressArray.getString(j);
                }
                VenuePrototype venuePrototype=new VenuePrototype(id,name,distance,address,lat,lng);
                list.add(venuePrototype);

                dataBaseHelper.insert(id,name,address,distance);

            }
            dataBaseHelper.close();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return list;
    }

  /*  @Override
    public void passImgUrl(String url)
    {
        imagejson=url;
    }*/


    public interface PassToInputFragmentListener
    {
        void onTaskCompleted(ArrayList<VenuePrototype> venuelist);
    }
    /*public interface PassToVenueListFragment
    {
        void onTaskComplete(ArrayList<VenuePrototype> venuelist);
    }*/

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
            int flag=0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return replyString.trim();
    }




}

