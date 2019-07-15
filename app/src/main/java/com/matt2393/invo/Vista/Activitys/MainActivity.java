package com.matt2393.invo.Vista.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Fragments.DesicionTreeFragment;
import com.matt2393.invo.Vista.Fragments.TomaDecFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main, TomaDecFragment.newInstance())
                .commit();
    }
}
