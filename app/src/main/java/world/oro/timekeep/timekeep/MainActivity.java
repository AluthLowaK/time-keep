package world.oro.timekeep.timekeep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.Button;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private MediaRecorder mRecorder = null;

    private Button startButton = null;

    final int PERM = 7766;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this.getBaseContext(),
                Manifest.permission.WRITE_CALENDAR);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERM);
        }


        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startRecording(view);
            }
        });

        startButton = (Button) findViewById(R.id.stop);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopRecording(view);
            }
        });
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERM: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //we never reach this for this
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void startRecording(View view) {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(new File("/tmp/cache.3gp"));
            FileDescriptor fd2 = fileOutputStream.getFD();


            mRecorder.setOutputFile(fd2);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.prepare();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording(View view) {
        mRecorder.stop();
        sendEmail();
    }


    private void sendEmail(){

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("image/jpeg");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]
                {"me@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Test Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "go on read the emails");

        emailIntent.putExtra(Intent.EXTRA_STREAM, "file://tmp/cache.3gp");
        emailIntent.setType("audio/gpp");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
