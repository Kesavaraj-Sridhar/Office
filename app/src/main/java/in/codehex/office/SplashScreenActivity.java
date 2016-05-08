package in.codehex.office;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import in.codehex.office.Internet;
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread time = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (Internet.isInternetAvailable(getApplicationContext())){
                        Intent in = new Intent(getApplicationContext(),PasscodeActivity.class);
                        startActivity(in);
                    }
                    else {
                        Intent in = new Intent(getApplicationContext(),NetworkActivity.class);
                        startActivity(in);
                    }

                }
            }
        };
        time.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
