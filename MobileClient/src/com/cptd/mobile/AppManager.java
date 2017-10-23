package com.cptd.mobile;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class AppManager {

	public final static String ACTION_LOCATION = "com.cptd.mobile.ACTION_LOCATION";
	private LocationManager locationManager = null;
	public static AppManager appManager = null;
	private Context context;

	
	private AppManager(Context context) {
		this.context = context;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

	}

	public static AppManager getManager(Context context) {
		if (appManager == null) {
			appManager = new AppManager(context);
		}
		return appManager;
	}

	private PendingIntent getLocationPendingIntent(boolean create) {
		Intent intent = new Intent(AppManager.ACTION_LOCATION);
		int flags;
		if (create) {
			flags = 0;
		} else {
			flags = PendingIntent.FLAG_NO_CREATE;
		}

		return PendingIntent.getBroadcast(context, 0, intent, flags);
	}

	public void startLocationUpdates() {
		String provider = LocationManager.GPS_PROVIDER;

		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			location.setTime(System.currentTimeMillis());
			this.sendLocation(location);

		}
		PendingIntent pi = this.getLocationPendingIntent(true);
		locationManager.requestLocationUpdates(provider, 0, 5.0f, pi);

	}

	private void sendLocation(Location location) {
		Intent intent = new Intent(AppManager.ACTION_LOCATION);
		intent.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
		context.sendBroadcast(intent);
	}

	public void stopLocationUpdates() {
		PendingIntent pi = this.getLocationPendingIntent(false);
		if (pi != null) {
			locationManager.removeUpdates(pi);
			pi.cancel();
		}
	}

	public boolean isRunning() {
		return this.getLocationPendingIntent(false) != null;
	}

	public boolean isProviderEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

}
