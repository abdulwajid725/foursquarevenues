package com.example.abdulwajid.bluegapefoods;

/**
 * Created by abdul wajid on 11/7/2015.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by abdul wajid on 11/5/2015.
 */
public class VenueListFragment extends Fragment
{
    VenueListFragment v;
    Context c;
    ArrayList<VenuePrototype> venuelist;
    View view;
    ListView listView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.venuelist,container,false);
        listView=(ListView)view.findViewById(R.id.listView);
        //String externalstoragedirectory= Environment.getExternalStorageDirectory().toString() + "/BlueGape/myImages/";
        //Bitmap b=loadImage(externalstoragedirectory);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    public static Bitmap loadImage(String imgname)
    {
        try
        {
            String externalstoragedirectory= Environment.getExternalStorageDirectory().toString() + "/BlueGape/myImages/";
            File myPath=new File(externalstoragedirectory+"/"+imgname);
            FileInputStream fis=new FileInputStream(myPath);
            Bitmap b= BitmapFactory.decodeStream(fis);
            fis.close();
            return  b;
        }
        catch (Exception e)
        {

        }
        return null;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    public void receiveData(ArrayList<VenuePrototype> list)
    {
        if (venuelist == null)
            venuelist = new ArrayList<>();
        venuelist.clear();
        venuelist.addAll(list);
        populateList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = venuelist.get(i).id;
                String lat = venuelist.get(i).lat;
                String lng = venuelist.get(i).lng;
                c = getActivity();
                /*ConnectivityManager connectivityManager=(ConnectivityManager)c.getSystemService(c.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean connected=(networkInfo!=null&&networkInfo.isAvailable()&&networkInfo.isConnected());
                if(connected==true)*/
                {
                    {
                        Intent intent = new Intent(c, ListDetailActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("lng", lng);
                        intent.putExtra("lat", lat);
                        startActivity(intent);
                    }
                }
                /*else {
                    Toast t = Toast.makeText(c, "CONNECT TO INTERNET!!", Toast.LENGTH_LONG);
                    t.show();
                }*/

            }
        });

    }
    public void populateList()
    {
        MyListAdapter mAdapter=new MyListAdapter(this.getActivity(),R.layout.listitemdetail,venuelist);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }



    /*@Override
    public void onTaskComplete(ArrayList<VenuePrototype> venuelist)
    {
        int flag=0;
    }*/
}

