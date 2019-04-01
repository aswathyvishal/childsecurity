package com.example.childsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView status;
    String statusdata=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        status=(TextView)findViewById(R.id.status);
        String data= (String) new SaveSharedPreference().getUserName(getApplicationContext());
        if(data.equals("")){

            Intent i=new Intent(Homepage.this,MainActivity.class);
            startActivity(i);
        }
apicall();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new SaveSharedPreference().setUserName(getApplicationContext(),"");
            Intent intent = new Intent(Homepage.this, MainActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void apicall(){
        Log.e("apicall","works");
        String targetURL="https://api.thingspeak.com/channels/719776/feeds.json?api_key=GSKX3Y3DGBTXJZJ7&results=2";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(targetURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

             // String   place= extras.getString("place");
                Log.e("error",responseBody.toString());
                try{
                    String data=new String(responseBody);
                    JSONObject parentObject = new JSONObject(new String(responseBody));
                    JSONObject userDetails = parentObject.getJSONObject("channel");
                    Log.e("works",userDetails.toString());

                    JSONObject ob = new JSONObject(data);
                    int l=ob.length();
                    String[] result=null;
                    Log.e("length",String.valueOf(l));
                    JSONArray arr = ob.getJSONArray("feeds");
                    Log.e("works",arr.toString());
                    String field1=null;
                    for(int i=0; i<arr.length(); i++){
                        JSONObject o = arr.getJSONObject(i);
                        Log.e("field1",o.getString("field1"));
                        field1=o.getString("field1");

                        Log.e("success",o.getString("field1"));


                    }
                    result= field1.split("-");
                    // result=field1.split("$");



                    if(field1.equals("1")){
                        status.setText("OFF");
                    }else{
                        status.setText("ON");
                    }

                    /*while (){
                        String key = (String) x.next();
                        Log.e("worksing",key);
                    }
*/
                   /* String free=new String(responseBody);
                JSONObject obj = new JSONObject(new String(responseBody));
                JSONArray contacts = obj.getJSONArray("channel");
                ArrayList data = new ArrayList<>();

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String field = c.getString("field1");

         */
                    for(String s:result){
                        Log.e("result",s);}





                }catch(Exception e){
                    Log.e("error",e.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("error",error.toString());
            }




        });
    }

}
