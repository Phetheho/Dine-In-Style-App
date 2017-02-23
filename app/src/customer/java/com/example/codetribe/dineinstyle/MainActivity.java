package com.example.codetribe.dineinstyle;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {


    private TextView mCurrentUser;
    private TextView mEmailAddress;
    private ImageView mProPic;
    private Uri proPic;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;


    private Button atTheRestaurant;
    private Button atHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("users");

        //reference = database.getReference("https://dineinstyle-80f2a.firebaseio.com/");


        if (user != null){

            String currentUser = user.getDisplayName();

            mCurrentUser = (TextView) findViewById(R.id.current_user);

            mCurrentUser.setText(currentUser);


            String userEmail = user.getEmail();

            mEmailAddress = (TextView) findViewById(R.id.user_email);

            mEmailAddress.setText(userEmail);


            String uId = user.getUid();



            proPic = user.getPhotoUrl();

            mProPic = (ImageView) findViewById(R.id.circleView);


            Glide.with(this)
                    .load(proPic)
                    .into(mProPic);

            reference.child(uId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String userName = dataSnapshot.child("mUserFirstName").getValue().toString();

                    String userLastName = dataSnapshot.child("mUserLastName").getValue().toString();


                    String userNameSurname = userName + " " + userLastName;

                    mCurrentUser.setText(userNameSurname);


                    String newProPic = dataSnapshot.child("image").getValue().toString();

                    Glide.with(MainActivity.this)
                            .load(newProPic)
                            .into(mProPic);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }



        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };


        atTheRestaurant = (Button) findViewById(R.id.restaurant_button);

        atTheRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AtTheRestaurant.class));
            }
        });


        atHome = (Button) findViewById(R.id.home_button);

        atHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, AtHome.class));

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem)
    {
       int selected = menuItem.getItemId();

        if (selected == R.id.action_logout)
        {
           userLogOut();

        }
        else if (selected == R.id.setup_profile){
            startActivity(new Intent(MainActivity.this, UpdateProfile.class));
        }

        return super.onOptionsItemSelected(menuItem);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void userLogOut(){

       mAuth.signOut();
    }

}



