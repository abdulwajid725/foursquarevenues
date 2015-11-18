package com.example.abdulwajid.bluegapefoods;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by abdul wajid on 11/6/2015.
 */
public class InputFragment extends Fragment implements FourSquareVenues.PassToInputFragmentListener
{
    CommunicateWithActivity communicateWithActivity;
    ImageButton searchbutton;
    Button gpsbutton;
    EditText searchtext;
    TextView coordinatedisplay;
    String latitude=null;
    String longitude=null;
    InputFragment c;


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        communicateWithActivity=(CommunicateWithActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.inputfragment,container,false);
        searchtext=(EditText)view.findViewById(R.id.searchText);
        searchbutton=(ImageButton)view.findViewById(R.id.searchbutton);
        //gpsbutton=(Button)view.findViewById(R.id.gpsbutton);
        //coordinatedisplay=(TextView)view.findViewById(R.id.gpstext);
        c=this;
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String address = searchtext.getText().toString();
                address= address.replaceAll(" ","+");
                //create table if does not exists and go to get location else fetch data directly from database
                //and populate it in a different list view
                // ..no need to go to get location;
             //   DataBaseHelper dataBaseHelper=new DataBaseHelper(address  );
                GetLocation getLocation = new GetLocation(address,c);
                getLocation.execute();
            }
        });
        return view;
    }
    void setresult(String lat,String lng)
    {
        latitude=lat;
        longitude=lng;

    }



    @Override
    public void onTaskCompleted(ArrayList<VenuePrototype> venuelist)
    {
       // MyListAdapter adapter;
        int flag=0;
        //adapter=new MyListAdapter(getActivity(),R.layout.listitemdetail,venuelist);
        communicateWithActivity.sendList(venuelist);
    }



    public interface CommunicateWithActivity
    {
        void sendList(ArrayList<VenuePrototype> venuelist);
    }
}
