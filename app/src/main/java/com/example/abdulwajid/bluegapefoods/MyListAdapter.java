package com.example.abdulwajid.bluegapefoods;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by abdul wajid on 11/7/2015.
 */
public class MyListAdapter extends ArrayAdapter<VenuePrototype> implements GetImageUrl.SndUrl2MyListAdapter
{
    ArrayList<VenuePrototype> veneuelist;
    ImageView imageView;
    String externalstoragedirectory= Environment.getExternalStorageDirectory().toString() + "/BlueGape/myImages//";
    File sdImageStorageDir = new File(externalstoragedirectory);
    public MyListAdapter(Context context, int resource,ArrayList<VenuePrototype> venuelist)
    {
        super(context, resource,venuelist);
        this.veneuelist=venuelist;
        count = new boolean[20];
        for (int i=0;i<20;i++)
            count[i]=false;
    }


    //@Override
    int flag=0;
    boolean[] count;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.listitemdetail,parent,false);
        veneuelist.get(position);
        TextView name=(TextView)v.findViewById(R.id.name);
        String id=(veneuelist).get(position).id;
        VenuePrototype venuePrototype=veneuelist.get(position);
        if(venuePrototype.image==null&&count[position]==false)
        {
            count[position]=true;
            venuePrototype.image = VenueListFragment.loadImage(venuePrototype.id+".jpg");
            if (venuePrototype.image == null)
            {
                GetImageUrl getImageUrl = new GetImageUrl(this, id, veneuelist.get(position).lat, veneuelist.get(position).lng, position);
                getImageUrl.execute();
            }

        }
        TextView distance=(TextView)v.findViewById(R.id.distance);
        TextView address=(TextView)v.findViewById(R.id.address);
        name.setText(veneuelist.get(position).name);
        distance.setText(veneuelist.get(position).distance);
        address.setText(veneuelist.get(position).address);
        LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.itemdetaillayout);
        linearLayout.setBackground(new BitmapDrawable(veneuelist.get(position).image));
        return v;
    }

    @Override
    public void passImg(Bitmap bitmap,int position,String id)
    {
        VenuePrototype venuePrototype=veneuelist.get(position);
        venuePrototype.image = bitmap;
        veneuelist.remove(position);
        veneuelist.add(position, venuePrototype);
        notifyDataSetChanged();
        Boolean bool=storeImage(bitmap,id);
    }


    private Boolean storeImage(Bitmap bitmap,String id)
    {
        sdImageStorageDir.mkdirs();
        try
        {
            if(bitmap!=null)
            {
                String filepath=sdImageStorageDir.toString()+"/"+id+".jpg";
                FileOutputStream fout=new FileOutputStream(filepath);
                BufferedOutputStream bos=new BufferedOutputStream(fout);
                if(bos!=null)
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                bos.flush();
                bos.close();
            }


        }
        catch (FileNotFoundException fne)
        {
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
