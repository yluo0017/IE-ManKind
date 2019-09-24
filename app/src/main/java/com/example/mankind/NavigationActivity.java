package com.example.mankind;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * The type Navigation activity.
 */
public class NavigationActivity extends AppCompatActivity implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor accelerateSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
		initSensor();
	}

	private void initSensor() {
		sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		accelerateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerateSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}
		super.onPause();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int type = event.sensor.getType();
		if (type == 1) {
			float[] values = event.values;
			float x = values[0];
			float y = values[1];
			float z = values[2];
			if ((Math.abs(x) > 25 || Math.abs(y) > 25 || Math
					.abs(z) > 25)) {
				sensorManager.unregisterListener(this);
				final AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this, R.style.Dialog_Fullscreen);
				LayoutInflater inflater = LayoutInflater.from(NavigationActivity.this);
				final View view = inflater.inflate(R.layout.activity_fake, null);
				builder.setView(view);
				final AlertDialog alertDialog = builder.create();
				Button close = view.findViewById(R.id.close);
				close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.e("close", "onClick" );
						alertDialog.dismiss();
						alertDialog.cancel();
						try {
							Thread.sleep(1000);
							sensorManager.registerListener(NavigationActivity.this, accelerateSensor, SensorManager.SENSOR_DELAY_NORMAL);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});

				alertDialog.show();
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}