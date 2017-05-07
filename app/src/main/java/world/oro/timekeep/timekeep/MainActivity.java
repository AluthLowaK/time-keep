package world.oro.timekeep.timekeep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private static final int ACTIVITY_RECORD_SOUND = 1;
    private MediaRecorder mRecorder = null;

    private Button startButton = null;
    private Button doneButton = null;

    final int PERM = 7766;

    private Uri recordedAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestPermission(Manifest.permission.RECORD_AUDIO);
        initUI();
    }

    private void initUI() {
        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAudioRecorder();
            }
        });

        doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void openAudioRecorder() {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
    }

////    private void requestPermission(String permission) {
////        int permissionCheck = ContextCompat.checkSelfPermission(this.getBaseContext(), permission);
////        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this, new String[]{permission}, PERM);
////        }
////    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERM: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    //we never reach this for this
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//
//    public void startRecording(View view) {
//
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//
//        try {
//
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("/tmp/cache.3gp"));
//            FileDescriptor fd2 = fileOutputStream.getFD();
//
//
//            mRecorder.setOutputFile(fd2);
//            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
//            mRecorder.prepare();
//        } catch (IOException e) {
//            //Log.e(LOG_TAG, "prepare() failed");
//        }
//
//        mRecorder.start();
//    }
//
//    public void stopRecording(View view) {
//        mRecorder.stop();
//        sendEmail();
//    }

    private void sendEmail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"me@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "go on read the emails");
        emailIntent.putExtra(Intent.EXTRA_STREAM, recordedAudio);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RECORD_SOUND) {
            if (resultCode == RESULT_OK) {
                recordedAudio = data.getData();
                startButton.setText("Start (" + getFileName() + ")");
            }
        }
    }

    @NonNull
    private String getFileName() {
        return new File(recordedAudio.getPath()).getName();
    }
}
