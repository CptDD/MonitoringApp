package com.cptd.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SenderService extends Service {

	public static final String USER_ID_VALUE = "Default";

	private Integer userId = null;
	private BroadcastReceiver receiver = null;
	private Timer timer = null;
	private Location location = null;

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		receiver = new CoordinateReceiver() {
			public void locationReceived(Context context, Location location) {
				SenderService.this.location = location;
			}
		};

		SenderService.this.registerReceiver(receiver, new IntentFilter(
				AppManager.ACTION_LOCATION));
		timer = new Timer();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {

		userId = intent.getIntExtra(SenderService.USER_ID_VALUE, 0);
		Toast.makeText(SenderService.this.getApplication(),
				"User Id sent :" + userId, Toast.LENGTH_SHORT).show();

		this.doRepeat();

		return Service.START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d("In on destroy", "Destruction");
		SenderService.this.unregisterReceiver(receiver);
		if (timer != null) {
			timer.cancel();
		}

	}

	private void doRepeat() {
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				new CoordinateSender()
						.execute("http://10.0.3.2/Gps/rest/position");

			};
		}, 0, 5000);

	}

	private String sendJSON(String url) throws Exception {
		try {
			HttpPost request = new HttpPost(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "text/plain");

			JSONObject obj = new JSONObject();
			obj.put(SenderService.this.getString(R.string.jsonLatitude),
					String.valueOf(location.getLatitude()));
			obj.put(SenderService.this.getString(R.string.jsonLongitude),
					String.valueOf(location.getLongitude()));
			obj.put(SenderService.this.getString(R.string.jsonUserId), userId);

			StringEntity stringEntity = new StringEntity(obj.toString());
			request.setEntity(stringEntity);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String tempLine = null;
			StringBuilder sb = new StringBuilder();
			while ((tempLine = reader.readLine()) != null) {
				sb.append(tempLine);
			}
			Log.d("Read value", sb.toString());
			return sb.toString();

		} catch (Exception e) {
			throw e;
		}
	}

	private class CoordinateSender extends AsyncTask<String, Void, String> {
		public String doInBackground(String... urls) {

			try {

				return SenderService.this.sendJSON(urls[0]);

			} catch (Exception e) {

				return "An error has occured!";

			}

		}

	}

}
