package com.cptd.mobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public abstract class SampleFragmentActivity extends Activity {

	public void onCreate(Bundle s) {
		super.onCreate(s);
		this.setContentView(R.layout.activity_main);
		FragmentManager fm = this.getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.container);
		if (fragment == null) {
			fragment = SampleFragmentActivity.this.createFragment();
			fm.beginTransaction().add(R.id.container, fragment).commit();

		}

	}

	protected abstract Fragment createFragment();

}