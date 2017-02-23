package com.example.codetribe.dineinstyle;

/**
 * Created by Codetribe on 2016/11/25.
 */
public class Server {


    private String mServerName;
    private Integer mServerAge;
    private Integer mServerExperience;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;


    public Server(String name, Integer age, Integer experience, int imageResource){

        mServerName = name;

        mServerAge = age;

        mServerExperience = experience;

        mImageResourceId = imageResource;
    }


    public String getmServerName(){

        return mServerName;
    }


    public Integer getmServerAge(){

        return mServerAge;
    }

    public Integer getmServerExperience(){

        return mServerExperience;
    }

    public int getmImageResourceId(){

        return mImageResourceId;
    }

    public boolean hasImage(){

        return mImageResourceId != NO_IMAGE_PROVIDED;
    }


}
