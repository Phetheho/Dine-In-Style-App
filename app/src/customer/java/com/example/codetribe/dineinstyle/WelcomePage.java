package com.example.codetribe.dineinstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {


    private Button mSignUpButton;
    private Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        mSignUpButton = (Button) findViewById(R.id.sign_up_button_one);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WelcomePage.this,SignUp.class));

            }
        });


        /*if(Constants.Type.STAFF == Constants.type){

            startActivity(new Intent(WelcomePage.this, WelcomePagesStaff.class));
        }*/


        mSignInButton = (Button) findViewById(R.id.sign_in_button_one);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WelcomePage.this, LoginActivity.class));
            }
        });
    }
}
