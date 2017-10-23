package com.cptd.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment {

	private TextView latitude, longitude;
	private BroadcastReceiver receiver = null;
	private Location lastLocation = null;
	private AppManager appManager = null;
	private Button senderButton;
	private Button clearButton;
	private Button startTrackingButton;
	private Button stopTrackingButton;
	private EditText userId = null;
	private Integer userIdValue = null;

	public void onCreate(Bundle s) {
		super.onCreate(s);
		appManager = AppManager.getManager(MainFragment.this.getActivity());
		receiver = new CoordinateReceiver() {
			public void locationReceived(Context context, Location location) {
				lastLocation = location;
				MainFragment.this.updateUi();
			}
		};

		appManager.startLocationUpdates();

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle s) {

		View view = inflater.inflate(R.layout.fragment_main, parent, false);
		latitude = (TextView) view.findViewById(R.id.latitude);
		longitude = (TextView) view.findViewById(R.id.longitude);
		userId = (EditText) view.findViewById(R.id.userIdEditText);
		clearButton = (Button) view.findViewById(R.id.clearUserId);
		startTrackingButton = (Button) view.findViewById(R.id.startTracking);
		stopTrackingButton = (Button) view.findViewById(R.id.stopTracking);
		senderButton = (Button) view.findViewById(R.id.sender);

		senderButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new CoordinateSender()
						.execute("http://10.0.3.2/Gps/rest/position");

			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				userId.setText("");

			}
		});
		startTrackingButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					userIdValue = Integer.parseInt(String.valueOf(userId
							.getText()));
					Intent intent = new Intent(MainFragment.this.getActivity(),
							SenderService.class);

					intent.putExtra(SenderService.USER_ID_VALUE, userIdValue);
					MainFragment.this.getActivity().startService(intent);
					MainFragment.this.startTrackingButton.setEnabled(false);
					MainFragment.this.stopTrackingButton.setEnabled(true);

				} catch (Exception e) {

					Toast.makeText(MainFragment.this.getActivity(),
							"Please enter a valid userId", Toast.LENGTH_SHORT)
							.show();
					userId.setText("");

				}

			}
		});

		stopTrackingButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainFragment.this.getActivity(),
						SenderService.class);
				MainFragment.this.getActivity().stopService(intent);
				// senderTimer.cancel();
				MainFragment.this.stopTrackingButton.setEnabled(false);
				MainFragment.this.startTrackingButton.setEnabled(true);

			}
		});

		return view;

	}

	private String sendJSON(String url) throws Exception {
		try {
			HttpPost request = new HttpPost(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "text/plain");

			Integer userIdValue = MainFragment.this.getUserId();
			JSONObject obj = new JSONObject();
			obj.put(this.getString(R.string.jsonLatitude),
					String.valueOf(lastLocation.getLatitude()));
			obj.put(this.getString(R.string.jsonLongitude),
					String.valueOf(lastLocation.getLongitude()));
			obj.put(this.getString(R.string.jsonUserId), userIdValue);

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
			
			return sb.toString();

		} catch (Exception e) {
			throw e;
		}
	}

	private Integer getUserId() {
		try {
			Integer userIdValue = Integer.parseInt(String.valueOf(userId
					.getText()));
			return userIdValue;
		} catch (Exception e) {
			throw e;
		}
	}

	private class CoordinateSender extends AsyncTask<String, Void, String> {
		public String doInBackground(String... urls) {

			try {

				return MainFragment.this.sendJSON(urls[0]);

			} catch (Exception e) {

				return "An error has occured!";

			}

		}

		public void onPostExecute(String params) {
			Toast.makeText(MainFragment.this.getActivity(), params,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void updateUi() {

		latitude.setText(lastLocation.getLatitude() + "");
		longitude.setText(lastLocation.getLongitude() + "");
	}

	public void onStart() {
		super.onStart();
		MainFragment.this.getActivity().registerReceiver(receiver,
				new IntentFilter(AppManager.ACTION_LOCATION));

	}

	public void onStop() {
		super.onStop();
		MainFragment.this.getActivity().unregisterReceiver(receiver);
	}

}