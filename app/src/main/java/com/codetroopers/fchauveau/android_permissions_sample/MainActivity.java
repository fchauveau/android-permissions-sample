package com.codetroopers.fchauveau.android_permissions_sample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public final static int REQUEST_CODE_ONE = 1;
    public final static int REQUEST_CODE_TWO = 2;
    public final static int REQUEST_CODE_THREE = 3;
    public final static int REQUEST_CODE_FOUR = 4;
    public final static int REQUEST_CODE_FIVE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayPermissionStatus();

        Button askForOnePermissionButton = (Button) findViewById(R.id.button_ask_for_one_permission);
        askForOnePermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ONE);
            }
        });


        Button askForTwoPermissionsButton = (Button) findViewById(R.id.button_ask_for_two_permissions);
        askForTwoPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_TWO);
            }
        });


        Button listenOnRequestResultButton = (Button) findViewById(R.id.button_listen_on_request_result);
        listenOnRequestResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        REQUEST_CODE_THREE);
            }
        });

        Button askForPermissionOnlyIfNeededButton = (Button) findViewById(R.id.button_ask_for_one_permission_if_needed);
        askForPermissionOnlyIfNeededButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_FOUR);

                } else {
                    Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button showExplanationMessageButton = (Button) findViewById(R.id.button_show_explanation_message);
        showExplanationMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Custom message to explain why you need a permission")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            REQUEST_CODE_FIVE);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CODE_FIVE);
            }
        });
    }

    private void displayPermissionStatus() {
        TextView cameraStatus = (TextView) findViewById(R.id.permission_status_camera);
        cameraStatus.setText("Pemission Camera : " + getPermissionStatus(Manifest.permission.CAMERA));

        TextView readContactsStatus = (TextView) findViewById(R.id.permission_status_read_contact);
        readContactsStatus.setText("Pemission read contacts : " + getPermissionStatus(Manifest.permission.READ_CONTACTS));

        TextView fineLocationStatus = (TextView) findViewById(R.id.permission_status_fine_location);
        fineLocationStatus.setText("Pemission fine location : " + getPermissionStatus(Manifest.permission.ACCESS_FINE_LOCATION));

        TextView sendSmsStatus = (TextView) findViewById(R.id.permission_status_send_sms);
        sendSmsStatus.setText("Pemission send SMS : " + getPermissionStatus(Manifest.permission.SEND_SMS));

        TextView readStorageStatus = (TextView) findViewById(R.id.permission_status_read_storage);
        readStorageStatus.setText("Pemission read storage: " + getPermissionStatus(Manifest.permission.READ_EXTERNAL_STORAGE));

        TextView callPhoneStatus = (TextView) findViewById(R.id.permission_status_call_phone);
        callPhoneStatus.setText("Pemission call phone : " + getPermissionStatus(Manifest.permission.CALL_PHONE));
    }

    private String getPermissionStatus(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return "granted";
        } else {
            return "not granted";
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_THREE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

        displayPermissionStatus();
    }
}
