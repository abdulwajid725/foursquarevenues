package com.example.abdulwajid.bluegapefoods;

/**
 * Created by abdul wajid on 11/8/2015.
 */
public class VenueDetailPrototype
{
    String name;
    String contact;
    String url;
    String address;
    String likes;
    String isopen;
    String rating;
    String review;
    public VenueDetailPrototype(String name, String contact, String url, String address, String likes, String isopen,String rating,String review)
    {
        this.name=name;
        this.contact=contact;
        this.url=url;
        this.address=address;
        this.likes=likes;
        this.isopen=isopen;
        this.rating=rating;
        this.review=review;
        int flag=0;
    }
}
