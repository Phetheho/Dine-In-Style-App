package com.example.codetribe.dineinstyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import static com.google.android.gms.auth.api.zza.ii;

public class SetUpProfile extends AppCompatActivity {


    //Add this dependency
    //compile 'com.facebook.android:facebook-android-sdk:[4,5)'


    private EditText userName;
    private EditText userLastName;
    private Button finishProfile;

    private ImageView newProPic;
    private Uri imageUri = null;

    private DatabaseReference mDatabaseUsers;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    //For Firebase storage
    private StorageReference storage;
    private static final int GALLERY_INTENT = 1;

    private ProgressDialog progressDialog;


    String email;
    String name;
    String surname;
    String uId;
    String downloadUri;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);


        userName = (EditText) findViewById(R.id.provide_name);

        userLastName = (EditText) findViewById(R.id.provide_surname);


        finishProfile = (Button) findViewById(R.id.finish_profile_button);


        mAuth = FirebaseAuth.getInstance();


        FirebaseUser logedUser = FirebaseAuth.getInstance().getCurrentUser();

        if (logedUser != null) {

            email = logedUser.getEmail();

            uId = logedUser.getUid();

        }

        //storage = FirebaseStorage.getInstance().getReference().child("Profile_picture");


        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

        /*User myUser = new User("Lesego", "Lekolo", "lekolole@gmail.com");
        DatabaseReference auser = mDatabaseUsers.child("users");
        mDatabaseUsers.setValue(myUser);*/


        finishProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpUserProfile();
            }
        });


        newProPic = (ImageView) findViewById(R.id.set_pro_pic);


        //For Firebase storage

        storage = FirebaseStorage.getInstance().getReference();


        newProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setProfilePicture();

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            imageUri = data.getData();


            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

            StorageReference filePath = storage.child("profile_pictures").child(imageUri.getLastPathSegment());

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

                newProPic.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setUpUserProfile() {


        name = userName.getText().toString();
        surname = userLastName.getText().toString();


        FirebaseUser logedUser = FirebaseAuth.getInstance().getCurrentUser();

        if (logedUser != null) {

            email = logedUser.getEmail();


        }

        User newUser = new User(name, surname, email);
        DatabaseReference user = mDatabaseUsers.child(uId);
        user.setValue(newUser);

        DatabaseReference userImage = mDatabaseUsers.child(uId);

        userImage.child("image").setValue(downloadUri);



        Intent intent = new Intent(SetUpProfile.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    private void setProfilePicture() {

        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, GALLERY_INTENT);


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SetUpProfile Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
