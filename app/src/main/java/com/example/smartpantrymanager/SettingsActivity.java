package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchAlerts;
    private static final String settingsFile = "PantrySettings";
    private static final String alertsKey = "alerts_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchAlerts = findViewById(R.id.switchAlerts);

        SharedPreferences preferences = getSharedPreferences(settingsFile, MODE_PRIVATE);
        boolean isAlertsEnabled = preferences.getBoolean(alertsKey, false);
        switchAlerts.setChecked(isAlertsEnabled);

        switchAlerts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(alertsKey, isChecked);
                editor.apply();
            }
        });
    }
}