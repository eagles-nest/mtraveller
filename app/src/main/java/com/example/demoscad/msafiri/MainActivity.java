package com.example.demoscad.msafiri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String postUrl = "http://msafirikenya.co.ke/";
    private WebView webView;
    SweetAlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        webView = (WebView) findViewById(R.id.mainweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                //Fun Part will be here :)
//                webView.loadUrl("javascript:(function() { " +
//                        "var head = document.getElementsByClassName('sticky-wrapper')[0].style.display='none'; " +
//                        "})()");
//
//
//            }
//        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //my new method
                loadingDialog.getProgressHelper().getProgress();
                loadingDialog.setCancelable(false);
                loadingDialog.setCanceledOnTouchOutside(true);
                loadingDialog.setTitleText("Msafirikenya Loading");
                loadingDialog.getProgressHelper().setProgress((float)progress);
                loadingDialog.setTitleText("Msafirikenya Loading "+String.valueOf(progress)+"%");
                loadingDialog.show();
//                textLoading.setText("DemosCAD loading "+ String.valueOf(progress)+"%");
                if (progress >= 100) {
                    loadingDialog.dismiss();
//                    myDialog.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                loadErrorPage(view);
            }
        });

        webView.loadUrl(postUrl);

    }

    private void loadErrorPage(WebView view) {
        if (webView != null) {

            String htmlData = "its icon";
            webView.loadUrl("about:blank");
            webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
            webView.invalidate();
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_stay) {
            Intent i = new Intent(MainActivity.this, Stay.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, About.class);
            startActivity(i);

        } else if (id == R.id.nav_contact) {
            Intent i = new Intent(MainActivity.this, Contact.class);
            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
