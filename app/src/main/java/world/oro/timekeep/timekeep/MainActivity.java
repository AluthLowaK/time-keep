package world.oro.timekeep.timekeep;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private MediaRecorder mRecorder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void startRecording(View view) {
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

    private void doneRecording(View view) {
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
