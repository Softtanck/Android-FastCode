package com.tangce.fastcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tangce.fastcode.network.FastHttp;
import com.tangce.fastcode.routerlib.Router;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        FastHttp.test(App.class);
        Router.create("Tanck://login").open(MainActivity.this);
    }
}
