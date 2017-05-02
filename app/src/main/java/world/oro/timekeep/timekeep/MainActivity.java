package world.oro.timekeep.timekeep;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private MediaRecorder mRecorder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile("/tmp/cache.3gp");
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
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
//        Log.v(getClass().getSimpleName(), "sPhotoUri=" + Uri.parse("file:/"+ sPhotoFileName));
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/"+ sPhotoFileName));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));


    }

    public void getObjective(View view) {
        startRecording();
    }
}
