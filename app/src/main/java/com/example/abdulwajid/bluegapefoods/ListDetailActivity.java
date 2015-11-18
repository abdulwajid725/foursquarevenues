package com.example.abdulwajid.bluegapefoods;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListDetailActivity extends Activity implements GetExtraDetails.SendToListDetailActivity
{
    String id;
    String lat;
    String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Button about=(Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast toast=Toast.makeText(ListDetailActivity.this,"CREATED BY ABDUL WAJID ....Jamia Millia Islamia",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        lat=intent.getStringExtra("lat");
        lng=intent.getStringExtra("lng");
        GetExtraDetails getExtraDetails=new GetExtraDetails(this,id,lat,lng);
        getExtraDetails.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_detail, menu);
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
    public void sendtolistdetail(VenueDetailPrototype venueDetailPrototype)
    {
        if(venueDetailPrototype!=null)
        {
            TextView name=(TextView)findViewById(R.id.name);
            TextView contact=(TextView)findViewById(R.id.textView4);
            TextView url=(TextView)findViewById(R.id.textView5);
            TextView address=(TextView)findViewById(R.id.textView6);
            TextView likes=(TextView)findViewById(R.id.textView7);
            TextView isopen=(TextView)findViewById(R.id.textView8);
            name.setText(venueDetailPrototype.name);
            contact.setText("CONTACT : "+venueDetailPrototype.contact);
            url.setText("URL : "+venueDetailPrototype.url);
            address.setText("ADDRESS : "+venueDetailPrototype.address);
            likes.setText("LIKES : "+venueDetailPrototype.likes);
            isopen.setText("IS OPEN : "+venueDetailPrototype.isopen);
            String rating=venueDetailPrototype.rating;

            TextView ratng=(TextView)findViewById(R.id.rating);
            ratng.setText("RATING : "+rating);
            TextView reason=(TextView)findViewById(R.id.reason);
            reason.setText("REVIEW : "+venueDetailPrototype.review);
        }
        else
        {
            Toast t=Toast.makeText(this,"NOT CONNECTED!!",Toast.LENGTH_LONG);
            t.show();
        }

    }


}

