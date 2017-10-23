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

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class CoordinateTimer extends CoordinateReceiver {

	private Location location = null;
	private Timer timer = null;
	private SendCoordinatesTask task = null;

	public CoordinateTimer() {

		timer = new Timer();
		task = new SendCoordinatesTask();

		try {
			timer.scheduleAtFixedRate(task, 2000, 5000);
		} catch (Exception e) {

		}
		Log.d("Aici", "Here");

	}

	public void locationReceived(Context context, Location location) {

		this.location = location;

	}

	private String sendJSON(String url) throws Exception {
		try {
			HttpPost request = new HttpPost(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "text/plain");

			JSONObject obj = new JSONObject();
			obj.put("latitude", String.valueOf(location.getLatitude()));
			obj.put("longitude", String.valueOf(location.getLongitude()));
			obj.put("userId", 2000);

			StringEntity stringEntity = new StringEntity(obj.toString());

			request.setEntity(stringEntity);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			Log.d("From Coordinate Timer", sb.toString());
			return sb.toString();

		} catch (Exception e) {
			throw e;
		}
	}

	private class Sender extends AsyncTask<String, Void, String> {
		public String doInBackground(String... urls) {
			try {
				return CoordinateTimer.this.sendJSON(urls[0]);
			} catch (Exception e) {
				return "An exception has occurred! " + e.getMessage();
			}
		}

		public void onPostExecute(String value) {
			// timer.cancel();

		}
	}

	private class SendCoordinatesTask extends TimerTask {
		public void run() {
			Sender sender = new Sender();
			sender.execute("http://10.0.3.2/Gps/rest/position");
		}
	}
}