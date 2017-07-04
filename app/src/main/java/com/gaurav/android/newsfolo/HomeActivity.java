package com.gaurav.android.newsfolo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Gaurav Rawal on 03-Jul-17.
 */

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(HomeActivity.this, DrawerActivity.class));
        finish();
    }
}
