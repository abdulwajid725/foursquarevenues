package com.example.abdulwajid.bluegapefoods;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abdul wajid on 11/10/2015.
 */
public class GetImageUrl extends AsyncTask<String,Integer,Bitmap>
{
    String id;
    String lat;
    String lng;
    String temp;
    Context context;
    SndUrl2MyListAdapter sndUrl2MyListAdapter;
    FourSquareVenues fourSquareVenues;
    int position;
    final String CLIENT_ID = "VFFXFANXHFPJESQRVV54BTCUYJSUQQQRPLT41S1RLEE2ZJS2";
    final String CLIENT_SECRET = "VF02C2XT4H1XPX2YMVAFT2YB4HODDZ0SXGMTMQGQA2UP3LP3";

    public GetImageUrl(MyListAdapter myListAdapter,String id, String lat, String lng,int posn) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        sndUrl2MyListAdapter=(MyListAdapter)myListAdapter;
        position=posn;
    }

    @Override
    protected Bitmap doInBackground(String... strings)
    {
        temp = makeCall("https://api.foursquare.com/v2/venues/" + id + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=" + lat + "," + lng);
        String imgurl = jsonParser(temp);
        if(imgurl==null)
        {
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.imgntavailable);
            return icon;
        }
        else
        {
            Bitmap bitmap=downloadBitmap(imgurl);
            return bitmap;
        }


    }
    private Bitmap downloadBitmap(String url)
    {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK)
            {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null)
            {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        }
        catch (Exception e)
        {
            try
            {
                urlConnection.disconnect();
            }
            catch (Exception e1)
            {
                Log.w("ImageDownloader", "Error downloading image from " + url);
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        super.onPostExecute(bitmap);
      /*  if (isCancelled())
        {
            bitmap = null;
        }*/

        sndUrl2MyListAdapter.passImg(bitmap,position,id);

    }

    public String makeCall(String url) {
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(buffer_string.toString());

        try {
            HttpResponse response = httpClient.execute(httpGet);
            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream((is));
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            replyString = new String(baf.toByteArray());
            int flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyString.trim();
    }


    public String jsonParser(String s) {
        String imgurl = "";
        try {
            JSONObject jsonObject = new JSONObject(s).getJSONObject("response").getJSONObject("venue").getJSONObject("photos");
            JSONObject jsonObject1 = jsonObject.getJSONArray("groups").getJSONObject(0).getJSONArray("items").getJSONObject(0);
            String prefix = jsonObject1.getString("prefix");
            String suffix = jsonObject1.getString("suffix");
            imgurl = prefix + "250x250" + suffix;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imgurl;
    }

    public interface SndUrl2MyListAdapter
    {
        public void passImg(Bitmap bitmap, int position,String id);
    }
}