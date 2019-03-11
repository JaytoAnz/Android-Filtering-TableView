package numazu.apppengadilan.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import numazu.apppengadilan.R;
import numazu.apppengadilan.controller.ProgressBarAnimation;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView loadText;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = (ProgressBar) findViewById(R.id.load_progress);
        loadText = (TextView) findViewById(R.id.loadText);

        progress();

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
    }

    private void progress(){
        ProgressBarAnimation animation = new ProgressBarAnimation(this, progressBar, loadText, 0f, 100f);
        animation.setDuration(4000);
        progressBar.setAnimation(animation);
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
