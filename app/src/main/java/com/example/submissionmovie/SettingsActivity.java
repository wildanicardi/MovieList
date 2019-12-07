package com.example.submissionmovie;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.submissionmovie.MainActivity.repeatTimeDaily;


public class SettingsActivity extends AppCompatActivity implements Switch.OnClickListener{
    private Switch aSwitch,bSwitch;
    private Settingutils settingutils;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        aSwitch = findViewById(R.id.switch1);
        bSwitch = findViewById(R.id.switch2);

        alarmReceiver = new AlarmReceiver();

        settingutils = new Settingutils(getApplicationContext());
        aSwitch.setChecked(settingutils.getDialyReminder());
        bSwitch.setChecked(settingutils.getReleaseReminder());

        aSwitch.setOnClickListener(this);
        bSwitch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch1:
                settingutils.setDialyReminder(!settingutils.getDialyReminder());
                if (settingutils.getDialyReminder()) {
                    if (!alarmReceiver.isAlarmSet(getApplicationContext(), MainActivity.ID_REPEATING)) {
                        alarmReceiver.setRepeatingAlarm(getApplicationContext(), MainActivity.ID_REPEATING, MainActivity.repeatTimeDaily, getResources().getString(R.string.active_dialy_reminder));
                    }
                } else {
                    if (alarmReceiver.isAlarmSet(getApplicationContext(), MainActivity.ID_REPEATING)) {
                        alarmReceiver.cancelAlarm(getApplicationContext(), MainActivity.ID_REPEATING, getResources().getString(R.string.non_active_dialy_reminder));
                    }
                }

                break;
            default:
                settingutils.setReleaseReminder(!settingutils.getReleaseReminder());
                if (settingutils.getReleaseReminder()) {
                    if (!alarmReceiver.isAlarmSet(getApplicationContext(), MainActivity.ID_REPEATING2)) {
                        alarmReceiver.setRepeatingAlarm(getApplicationContext(), MainActivity.ID_REPEATING2, MainActivity.repeatTimeReleased, getResources().getString(R.string.active_released_reminder));
                    }
                } else {
                    if (alarmReceiver.isAlarmSet(getApplicationContext(), MainActivity.ID_REPEATING2)) {
                        alarmReceiver.cancelAlarm(getApplicationContext(), MainActivity.ID_REPEATING2, getResources().getString(R.string.non_active_released_reminder));
                    }
                }
                break;
        }
    }
}