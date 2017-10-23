package com.cptd.mobile;

import android.app.Fragment;

public class MainActivity extends SampleFragmentActivity {

	protected Fragment createFragment() {
		return new MainFragment();
	}
}