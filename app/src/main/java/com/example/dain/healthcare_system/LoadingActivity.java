package www.h_g.co.kr.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    Thread  splashThread;

    final String now_ver = "0.1";
    String check_ver;

    GetData urlConnect;
    ImageView  imv_logo;
    ImageView  imv_name;
    TextView checker;

    Animation animation;
    Animation animation2;


    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        imv_logo = (ImageView)findViewById(R.id.logo);
        imv_name = (ImageView)findViewById(R.id.name);
        checker = (TextView)findViewById(R.id.checker);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wava1);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.loadding);

        urlConnect = new GetData();

        imv_logo.startAnimation(animation);
        imv_name.startAnimation(animation2);

        imv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count  ==  3){
                    finish();
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                imv_logo.startAnimation(animation);
                imv_name.startAnimation(animation2);
            }
        });


        imv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count  ==  3){
                    finish();
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                imv_logo.startAnimation(animation);
                imv_name.startAnimation(animation2);
            }
        });
        splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        //urlConnect.getData("35.194.224.80/App_ver.php");
                        urlConnect.execute("http://35.194.224.80/App_ver.php");
                        check_ver = urlConnect.get_result();
                        checker.setText(check_ver);
                        wait(4001);


                    }
                } catch (InterruptedException ex) {
                }
                if("0.1".equals(now_ver)){

                    finish();

                    // Run next activity
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {

                }
            }
        };
        splashThread.start();



    }




}


