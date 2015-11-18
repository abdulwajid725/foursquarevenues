package com.example.abdulwajid.bluegapefoods;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends Activity implements InputFragment.CommunicateWithActivity
{

    MainActivity activity;
    ArrayList<VenuePrototype> venuelist;
    PassToVenueListFragment passToVenueListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager=getFragmentManager();
        VenueListFragment fr= (VenueListFragment) manager.findFragmentById(R.id.listFragment);
        DataBaseHelper dataBaseHelper=new DataBaseHelper(this);
        ArrayList<FetchFromDATABASEList> list=dataBaseHelper.getAllVenues();
        if(list!=null)
        {
            ArrayList<VenuePrototype> venuelist=getFromFetchObj(list);
            fr.receiveData(venuelist);
        }
        //dataBaseHelper.delete();


    }

    private ArrayList<VenuePrototype> getFromFetchObj(ArrayList<FetchFromDATABASEList> list){
        ArrayList<VenuePrototype> venuePrototypes = new ArrayList<VenuePrototype>();
        for (int i=0;i<list.size();i++){
            venuePrototypes.add(
              VenuePrototype.get(list.get(i))
            );
        }
        return venuePrototypes;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void sendList(ArrayList<VenuePrototype> venuelist)
    {
        //
       // this.venuelist=venuelist;
        int flag=0;
        FragmentManager manager=getFragmentManager();
        VenueListFragment fr= (VenueListFragment) manager.findFragmentById(R.id.listFragment);
        fr.receiveData(venuelist);

    }
    public interface PassToVenueListFragment
    {
        void passToFragment(ArrayList<VenuePrototype> list);
    }

}
