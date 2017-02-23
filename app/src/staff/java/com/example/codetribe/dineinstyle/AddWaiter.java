package com.example.codetribe.dineinstyle;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddWaiter extends AppCompatActivity {

    //Views
    private EditText waiterName;
    private EditText waiterSurname;
    private EditText waiterDOB;
    private EditText waiterAge;
    private EditText waiterExperience;
    private EditText newEmail;
    private EditText newPassword;

    private Button selectDOB;
    private Button addNewWaiter;

    private ImageView waiterProPic;

    //Constants
    static final int DATE_DIALOG_ID = 0;
    private static final int GALLERY_INTENT = 1;
    private StaffLoginActivity.UserLoginTask mAuthTaskTwo = null;
    private Uri imageUri = null;


    //Firebase Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    //Firebase Database
    private DatabaseReference mDatabaseUsers;


    //Firebase storage
    private StorageReference storage;


    //Variables
    String name;
    String surname;
    String gender;
    String dob;
    String age;
    String experience;
    String uId;
    String userEmail;
    String downloadUri;


    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waiter);


        //Selecting the waiter profile picture

        waiterProPic = (ImageView) findViewById(R.id.set_waiter_pic);

        waiterProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setProfilePicture();
            }
        });

        storage = FirebaseStorage.getInstance().getReference();


        //********************************************************************************


        //Code for First and Last Name Edit Views

        waiterName = (EditText) findViewById(R.id.provide_name);

        waiterSurname = (EditText) findViewById(R.id.provide_surname);

        waiterExperience = (EditText) findViewById(R.id.provide_experience);




        addNewWaiter = (Button) findViewById(R.id.add_new_waiter);


        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("waiters");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Toast.makeText(AddWaiter.this, "Signed Up successful", Toast.LENGTH_LONG).show();

                } else {
                    // User is signed out

                }

            }
        };





        addNewWaiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewWaiterAccount();
            }
        });


        selectDOB = (Button) findViewById(R.id.select_dob);

        waiterDOB = (EditText) findViewById(R.id.dob_view);

        waiterAge = (EditText) findViewById(R.id.age_view);



        selectDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }
        });

        //Get current year

        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        age = String.valueOf(2017 - mYear);

        updateDisplay();

    }

    private void updateDisplay(){

        waiterDOB.setText(new StringBuilder()
        .append(mMonth + 1).append("-")
        .append(mDay).append("-")
        .append(mYear).append(" "));

        dob = waiterDOB.getText().toString();
        
        waiterDOB.setFocusable(false);
        waiterDOB.setClickable(false);
        
        waiterAge.setText(new StringBuilder()
        .append(age).append(" "));
        
        waiterAge.setFocusable(false);
        waiterAge.setClickable(false);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
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

    //Code for selecting and cropping waiter profile picture


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            imageUri = data.getData();


            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

            StorageReference filePath = storage.child("waiter_profile_pictures").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    downloadUri = taskSnapshot.getDownloadUrl().toString();


                }
            });

        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();

                waiterProPic.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //****************************************PRO PIC CODE ENDS HERE*********************************************

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void createNewWaiterAccount(){

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


        boolean cancel = false;
        View focusView = null;

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

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddWaiter.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(AddWaiter.this, "Successful",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


            name = waiterName.getText().toString();
            surname = waiterSurname.getText().toString();
            experience = waiterExperience.getText().toString();

            FirebaseUser logedUser = FirebaseAuth.getInstance().getCurrentUser();

            if (logedUser != null) {

                userEmail = logedUser.getEmail();
                uId = logedUser.getUid();
            }


            Waiter newWaiter = new Waiter(name, surname, gender, dob, age, experience, userEmail);
            DatabaseReference waiter = mDatabaseUsers.child(uId);
            waiter.setValue(newWaiter);


            DatabaseReference userImage = mDatabaseUsers.child(uId);
            userImage.child("image_uri").setValue(downloadUri);


        }
    }

    public void generateWaiterGender(View view){

        boolean checked = ((RadioButton) view).isChecked();

        Toast toast; // Creating a new toast object


        Context context = getApplicationContext(); // Creating a context and assigning it the application context

        CharSequence message; // Creating a CharSequence variable that will hold the message to be displayed in the toast


        int duration = Toast.LENGTH_LONG;



        switch (view.getId())
        {
            case R.id.male_gender:
                if (checked){

                    gender = "Male";
                    message = gender;

                    //creating a toast with text
                    toast = Toast.makeText(context, message, duration);

                    toast.show();

                }
                break;

            case R.id.female_gender:
                if (checked){

                    gender = "Female";
                    message = gender;

                    //creating a toast with text
                    toast = Toast.makeText(context, message, duration);

                    toast.show();

                }

                break;
        }


    }

    private void setProfilePicture() {

        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, GALLERY_INTENT);


    }


}
