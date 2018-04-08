package www.h_g.co.kr.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btn_pulse;
    Button btn_sleep;
    Button btn_medecine;
    Button btn_hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_pulse = (Button)findViewById(R.id.btn_pulseinfo);
        btn_sleep = (Button)findViewById(R.id.btn_sleepinfo);
        btn_medecine = (Button)findViewById(R.id.btn_medecine);
        btn_hospital = (Button)findViewById(R.id.btn_hospital);





        btn_pulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,pulseActivity.class);
                startActivity(intent);
            }
        });
        btn_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,sleepActivity.class);
                startActivity(intent);
            }
        });

        btn_medecine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,medicineActivity.class);
                startActivity(intent);

            }
        });

        btn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,HospitalActivity.class);
                startActivity(intent);

            }
        });

    }
}
