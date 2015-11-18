package com.example.abdulwajid.bluegapefoods;

import android.graphics.Bitmap;

/**
 * Created by abdul wajid on 11/7/2015.
 */
public class VenuePrototype
{
    String id;
    String name;
    String distance;
    String address;
    String lat;
    String lng;
    public Bitmap image;
    public VenuePrototype(String id,String name, String distance, String address,String lat,String lng)
    {
        this.id=id;
        this.name=name;
        this.distance=distance;
        this.address=address;
        this.lat=lat;
        this.lng=lng;
        //this.imgurl=imgurl;
        int flag=0;
    }
    public VenuePrototype(Bitmap bitmap)
    {
        image=bitmap;
    }
    public static VenuePrototype get(FetchFromDATABASEList fetchFromDATABASEList){
        VenuePrototype venuePrototype = new VenuePrototype(
                fetchFromDATABASEList.id,
                fetchFromDATABASEList.name,
                fetchFromDATABASEList.distance,
                fetchFromDATABASEList.address,
                "0","0"
        //fetchFromDATABASEList.lat,
          //      fetchFromDATABASEList.lng
        );
        return venuePrototype;
    }
}