package com.example.deliveryboy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryboy.utils.Global;
import com.example.deliveryboy.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainUI extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    Context context=this;
    ActionBar actionBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView status;
    SessionManager sessionManager;
    private String URLline = Global.BASE_URL+"api_delivery_boy/del_boy_online_status/";


    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    boolean doubleBackToExitPressedOnce = false;
    private Handler mHandler;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Switch switchTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Upcoming Delivery");
        adapter.addFragment(new Tab2Fragment(), "Delivery History");
        adapter.addFragment(new Tab3Fragment(),"Pending Delivery");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        sessionManager = new SessionManager(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawer,R.string.openDrawer,R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //actionBarDrawerToggle.getDrawerArrowDrawable().setColor(R.color.blu);
        actionBar=getSupportActionBar();
        actionBar.setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blu)));
        //   actionBar.setTitle(Html.fromHtml("<font color='#1AAEF4'>Name</font>"));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
       // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
    //    imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources



        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();


    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Anuja");
        txtWebsite.setText("anuja@creopedia.com");

        // loading header background image
      /*  Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
*/
        // showing dot next to notifications label
       // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        Toast.makeText(MainUI.this,"You clicked home",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_photos:
                        //startActivity(new Intent(MainActivity.this,TripHistory.class));
                        startActivity(new Intent(MainUI.this, changepassword.class));
                        break;
                    case R.id.logout:
                        sessionManager.setTokens(null);
                        startActivity(new Intent(MainUI.this, MainActivity.class));
                        finish();

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                //loadHomeFragment();

                return true;
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
              //  CURRENT_TAG = TAG_HOME;
              //  loadHomeFragment();
                return;
            }
        }

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainUI.this,"Press again to exit",Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = (MenuItem) menu.findItem(R.id.switchT);
        item.setActionView(R.layout.toggle_top_button);
        status = (TextView) findViewById(R.id.ondt);
        switchTop = item.getActionView().findViewById(R.id.switchTop);
        if (Global.flag){
//            status = (TextView) findViewById(R.id.ondt);
//            status.setText("online");
            switchTop.setChecked(true);
        }
        if (!Global.flag){
//            status = (TextView) findViewById(R.id.ondt);
//            status.setText("offline");
            switchTop.setChecked(false);
        }


        switchTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {


                if (isChecked) {

                        status = findViewById(R.id.ondt);
                        status.setText("online");
                        sessionManager.setID(status.getText().toString());
                        //sessionManager.setCard(true);
                        callonline();



                } if (!isChecked){



                        status = findViewById(R.id.ondt);
                        status.setText("offline");
                        Toast.makeText(MainUI.this,"Please be online to get the request",Toast.LENGTH_SHORT).show();
                        sessionManager.setID(status.getText().toString());
                        //sessionManager.setCard(false);
                        callonline();





                }
            }
        });
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }*/

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    // show or hide the fab

    private void callonline(){
        Toast.makeText(MainUI.this,"",Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status1=jsonObject.optString("code");
                            String token=jsonObject.optString("Token");





                            Log.d("otp","mm"+token);
                            Log.d("code","mm"+status1);
                            if(status1.equals("200")){
                               // Toast.makeText(MainUI.this, "Successful", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(Login.this, MainUI.class);
//                                startActivity(intent);
                                if (status.getText().toString().equals("offline")){
                                    switchTop.setChecked(false);
                                    sessionManager.setCard(false);
                                    Global.flag = false;
                                    status.setText("offline");

                                    startActivity(new Intent(MainUI.this,MainUI.class));
                                    Toast.makeText(MainUI.this,"Please be online to get the request",Toast.LENGTH_SHORT).show();
                                }
                                if (status.getText().toString().equals("online")){
                                    switchTop.setChecked(true);
                                    sessionManager.setCard(true);
                                    Global.flag = true;
                                    status.setText("online");
                                    startActivity(new Intent(MainUI.this,MainUI.class));
                                }

                            }
                            else{
                                Toast.makeText(MainUI.this, "Failed."+ot, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //   Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("online_status",status.getText().toString());


                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                Log.d("Tokenccccc","mm"+sessionManager.getTokens());
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }



}
