package com.msafiri.travel.msafirikenya;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Contact extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String postUrl = "https://msafirikenya.co.ke/contact-us";
    private WebView webView;
    protected ProgressBar progressBar;
    protected FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);
        progressBar.setMax(100);
        webView = (WebView) findViewById(R.id.contactweb);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //my new method
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                //setTitle(" Msafirikenya Loading...");
                if (progress >= 100) {
                    frameLayout.setVisibility(View.GONE);
                    //setTitle(view.getTitle());
                }
                super.onProgressChanged(view,progress);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                loadErrorPage(view);
            }
        });
        boolean isOnline = isOnline(Contact.this);
        if(isOnline){
            //has internet
            webView.loadUrl(postUrl);
            webView.setWebViewClient(new WebViewClient());
        }else{
            //no internet
            String errorMsg="Internet Connection required";
            Toast.makeText(Contact.this,errorMsg, Toast.LENGTH_LONG).show();
            //loadingDialog.dismiss();
        }
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //Fun Part will be here :)
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('sticky-wrapper')[0].style.display='none'; " +
                        "})()");
            }
        });
        progressBar.setProgress(0);
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
    public boolean isOnline(Context context) {
        ConnectivityManager cm
                =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    private void loadErrorPage(WebView view) {
        if (webView != null) {

            String htmlData = "its icon";
            webView.loadUrl("about:blank");
            webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
            webView.invalidate();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(Contact.this, Home.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_stay) {
            Intent i = new Intent(Contact.this, Stay.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(Contact.this, About.class);
            startActivity(i);





        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}