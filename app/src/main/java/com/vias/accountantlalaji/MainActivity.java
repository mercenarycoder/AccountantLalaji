package com.vias.accountantlalaji;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout invoiceLL;
    LinearLayout saleListLL;
    DrawerLayout drawerLayoutMainActivity;
    Toolbar toolbarMainActivity;
    ActionBarDrawerToggle actionBarDrawerToggleMainActivity;
    NavigationView navigationView_MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        navigationView_MainActivity = (NavigationView) findViewById(R.id.navigation_view_MainActivity);

       // View hearder = navigationView_MainActivity.getHeaderView(0);
       // TextView assName_tv = (TextView) hearder.findViewById(R.id.headerUserNameTV);
       // assName_tv.setText("Welcome " + cus_NAME);

        invoiceLL = (LinearLayout)findViewById(R.id.invoiceLL);
        invoiceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Sale.class);
                startActivity(i);

            }
        });

        saleListLL = (LinearLayout)findViewById(R.id.saleListLL);
        saleListLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Sale_List.class);
                startActivity(i);

            }
        });



    }

    private void setUpToolbar() {
        drawerLayoutMainActivity = (DrawerLayout) findViewById(R.id.drawerlayoutMainActivity);
        toolbarMainActivity = (Toolbar) findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbarMainActivity);
        actionBarDrawerToggleMainActivity = new ActionBarDrawerToggle(this, drawerLayoutMainActivity,
                toolbarMainActivity, R.string.app_name, R.string.app_name);
        drawerLayoutMainActivity.addDrawerListener(actionBarDrawerToggleMainActivity);
        actionBarDrawerToggleMainActivity.getDrawerArrowDrawable().
                setColor(getResources().getColor(R.color.colorDrawer));
        actionBarDrawerToggleMainActivity.syncState();
    }




}
