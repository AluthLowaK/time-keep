package world.oro.timekeep.timekeep;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/"+ sPhotoFileName));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));


    }
}
