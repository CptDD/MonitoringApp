package com.cptd.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class CoordinateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Location location = (Location) intent
				.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if (location != null) {
			this.locationReceived(context, location);
			return;
		}
		if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
			boolean enabled = intent.getBooleanExtra(
					LocationManager.KEY_PROVIDER_ENABLED, false);
			this.onProviderChanged(enabled);
			return;
		}

	}

	public void locationReceived(Context context, Location location) {

	}

	public void onProviderChanged(boolean state) {

	}

}
