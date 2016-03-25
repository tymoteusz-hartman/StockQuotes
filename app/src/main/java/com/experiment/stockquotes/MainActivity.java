package com.experiment.stockquotes;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private QuoteFragment quoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        quoteFragment = QuoteFragment.newInstance(getString(R.string.co_aapl));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, quoteFragment).commit();
        drawer.openDrawer(GravityCompat.START);
        ((NavigationView) findViewById(R.id.nav_view)).setCheckedItem(R.id.nav_aapl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id) {
            case R.id.nav_aapl: {
                if(!quoteFragment.getName().equals(getString(R.string.co_aapl))) {
                    quoteFragment = QuoteFragment.newInstance(getString(R.string.co_aapl));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, quoteFragment).commit();
                }
                break;
            }
            case R.id.nav_goog: {
                if(!quoteFragment.getName().equals(getString(R.string.co_goog))) {
                    quoteFragment = QuoteFragment.newInstance(getString(R.string.co_goog));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, quoteFragment).commit();
                }
                break;
            }
            case R.id.nav_msft: {
                if(!quoteFragment.getName().equals(getString(R.string.co_msft))) {
                    quoteFragment = QuoteFragment.newInstance(getString(R.string.co_msft));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, quoteFragment).commit();
                }
                break;
            }
            case R.id.nav_yhoo: {
                if(!quoteFragment.getName().equals(getString(R.string.co_yhoo))) {
                    quoteFragment = QuoteFragment.newInstance(getString(R.string.co_yhoo));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, quoteFragment).commit();
                }
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changePeriod(View view) {
        switch (view.getId()) {
            case R.id.button_1y:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p1Y);
                break;
            case R.id.button_6m:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p6M);
                break;
            case R.id.button_3m:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p3M);
                break;
            case R.id.button_1m:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p1M);
                break;
            case R.id.button_2w:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p2W);
                break;
            case R.id.button_1w:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p1W);
                break;
            case R.id.button_5d:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p5D);
                break;
            case R.id.button_3d:
                quoteFragment.setChartData(QuoteFragment.ChartPeriod.p3D);
                break;
        }
    }
}
