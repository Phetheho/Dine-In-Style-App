package com.example.codetribe.dineinstyle;

import com.google.firebase.database.IgnoreExtraProperties;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by Codetribe on 2016/12/06.
 */
public class User {

    private String mUserFirstName;
    private String mUserLastName;
    private String mUserEmail;

    public User(){

    }


    public User(String name, String surname, String email){

        mUserFirstName = name;

        mUserLastName = surname;

        mUserEmail = email;


    }


    public String getmUserFirstName() {
        return mUserFirstName;
    }

    public String getmUserLastName() {
        return mUserLastName;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

}
