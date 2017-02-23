package com.example.codetribe.dineinstyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AtTheRestaurant extends AppCompatActivity {


    public static String [] serversNames;

    public static int [] serversAges;

    public static int [] serversExperience;

    public static ArrayList<Server> serverList;

    public static ServerAdapter listAdapter;

    public static ListView rootView;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_the_restaurant);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(AtTheRestaurant.this, LoginActivity.class));
                }
            }
        };



        serversNames = new String[] {"Phetheho Sello","Thato Sello","Dineo Moloi","Ntseiseng Sello","Matshiliso Mosikili","Teboho Moskili"};

        serversAges = new int [] {33,25,27,18,22,22};

        serversExperience = new int[]{2,2,3,1,0,1};

        serverList = new ArrayList<Server>();


        serverList.add(new Server(serversNames[0], serversAges[0], serversExperience[0], R.drawable.com_facebook_profile_picture_blank_square));
        serverList.add(new Server(serversNames[1], serversAges[1], serversExperience[1], R.drawable.com_facebook_profile_picture_blank_square));
        serverList.add(new Server(serversNames[2], serversAges[2], serversExperience[2], R.drawable.com_facebook_profile_picture_blank_square));
        serverList.add(new Server(serversNames[3], serversAges[3], serversExperience[3], R.drawable.com_facebook_profile_picture_blank_square));
        serverList.add(new Server(serversNames[4], serversAges[4], serversExperience[4], R.drawable.com_facebook_profile_picture_blank_square));
        serverList.add(new Server(serversNames[5], serversAges[5], serversExperience[5], R.drawable.com_facebook_profile_picture_blank_square));




        listAdapter = new ServerAdapter (this, serverList);


        rootView = (ListView) findViewById(R.id.root_view);


        rootView.setAdapter(listAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int selected = menuItem.getItemId();

        if (selected == R.id.action_logout) {
            userLogOut();

        }

        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void userLogOut() {

        mAuth.signOut();
    }




}
