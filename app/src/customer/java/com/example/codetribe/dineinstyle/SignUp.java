package com.example.codetribe.dineinstyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {


    /*private EditText userName;
    private EditText userLastName;*/
    private EditText newEmail;
    private EditText newPassword;
    private Button signUpNewUser;


    private LoginActivity.UserLoginTask mAuthTaskTwo = null;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;


    //private DatabaseReference mDatabase;

    static String userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        newEmail = (EditText) findViewById(R.id.provide_email);

        newPassword = (EditText) findViewById(R.id.provide_password);

        /*userName = (EditText) findViewById(R.id.provide_name);

        userLastName = (EditText) findViewById(R.id.provide_surname);*/

        signUpNewUser = (Button) findViewById(R.id.create_new_user);


        mAuth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    Toast.makeText(SignUp.this, "Signed Up successful", Toast.LENGTH_LONG).show();

                    //userId = mAuth.getCurrentUser().getUid();

                }

            }
        };


        signUpNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatNewUser();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }





    //==================================Validation methods========================================================================================================


   /* private boolean isUserNameValid(String name){

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        boolean valid = (name != null) && pattern.matcher(name).matches();

        return valid;
    }


    private boolean isUserSurnameValid(String surname){

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        boolean valid = (surname != null) && pattern.matcher(surname).matches();

        return valid;
    }*/


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void creatNewUser() {

        if (mAuthTaskTwo != null) {
            return;

        }

        // Reset errors.
       /* userName.setError(null);
        userLastName.setError(null);*/
        newEmail.setError(null);
        newPassword.setError(null);

        // Store values at the time of the login attempt.

        /*String name = userName.getText().toString();
        String surname = userLastName.getText().toString();*/
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();

        userPassword = password;


        boolean cancel = false;
        View focusView = null;


        /*if (!TextUtils.isEmpty(name) && !isUserNameValid(name)){
            userName.setError(getString(R.string.error_invalid_name));
            focusView = userName;
            cancel = true;
        }

        if (!TextUtils.isEmpty(surname) && !isUserSurnameValid(surname)){
            userLastName.setError(getString(R.string.error_invalid_surname));
            focusView = userLastName;
            cancel = true;
        }*/

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            newPassword.setError(getString(R.string.error_invalid_password));
            focusView = newPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            newEmail.setError(getString(R.string.error_field_required));
            focusView = newPassword;
            cancel = true;
        } else if (!isEmailValid(email)) {
            newEmail.setError(getString(R.string.error_invalid_email));
            focusView = newEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user sign up attempt.

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {


                                /*String userId = mAuth.getCurrentUser().getUid();

                                DatabaseReference currentUserDb = mDatabase.child(userId);

                                currentUserDb.child("image").setValue("default");*/

                                Intent mainIntent = new Intent(SignUp.this, SetUpProfile.class);
                                startActivity(mainIntent);
                            }

                        }
                    });



        }


        /*User newUser = new User (name, surname, email, password);
        DatabaseReference user = mDatabase.push();
        user.setValue(newUser);*/

    }
}
