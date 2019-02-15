package com.mohsin.intentfilterdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    private ImageView picView;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }
        }


        picView = (ImageView) findViewById(R.id.picture);
        txtView = (TextView) findViewById(R.id.txt);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if(type.startsWith("image/")) {
                actionSendImage(intent);// Handle text being sent
            } else {
                Log.v("IntentACTION_SEND","type");
            }
        }
        else  if (Intent.ACTION_VIEW.equals(action)) {
            Log.v("IntentACTION_SEND","View");

            if (type.startsWith("text/")) {

                actionViewText(intent);

            } else if (type.startsWith("image/")) {

                actionViewImage(intent);

            } else if (type.startsWith("audio/")) {

                actionViewAudio(intent);

            } else if (type.startsWith("video/")) {

                actionViewVideo(intent);
            }
        }
    }


    void actionSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            Log.v("IntentACTION_SEND","imageUri "+imageUri );
            //Toast.makeText(this, "TextRecived", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    void actionViewText(Intent intent){

        //Toast.makeText(this, "TextRecived", Toast.LENGTH_SHORT).show();

        Log.v("IntentACTION_SEND","TextRecived");
        Uri uri2 = intent.getData();
        String uri = uri2.getEncodedPath() + "  complete: " + uri2.toString();
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        txtView.setText(uri);
    }

    void actionViewImage(Intent intent){
        txtView.setVisibility(View.GONE);
        Uri receivedUri = intent.getData();

        if (receivedUri != null) {
            Toast.makeText(this, receivedUri.toString(), Toast.LENGTH_SHORT).show();
            Log.v("IntentACTION_SEND","receivedUri "+receivedUri);
            picView.setImageURI(receivedUri);// just for demonstration
        }
    }

    void actionViewAudio(Intent intent){
        txtView.setVisibility(View.GONE);
        Log.v("IntentACTION_SEND","audio" );

        Uri receivedUri = intent.getData();

        if (receivedUri != null) {
            Toast.makeText(this, receivedUri.toString(), Toast.LENGTH_SHORT).show();
            Log.v("IntentACTION_SEND","receivedUri "+receivedUri);
        }
    }

    void actionViewVideo(Intent intent){
        txtView.setVisibility(View.GONE);
        Log.v("IntentACTION_SEND","video" );
        Uri receivedUri = intent.getData();
        Toast.makeText(this, receivedUri.toString(), Toast.LENGTH_SHORT).show();
        if (receivedUri != null) {
            Log.v("IntentACTION_SEND","receivedUri "+receivedUri);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                Log.v("grantResultsLength","grantResults Length "+String.valueOf(grantResults.length));
                Log.v("grantResultsLength","grantResults[0] "+String.valueOf(grantResults[0]));
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }




}
