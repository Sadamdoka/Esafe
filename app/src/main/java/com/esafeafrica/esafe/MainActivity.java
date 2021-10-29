package com.esafeafrica.esafe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Fragments.Corona_alert;
import com.esafeafrica.esafe.Fragments.alertChooser;
import com.esafeafrica.esafe.Model.Corona;
import com.esafeafrica.esafe.Model.Emergency;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.Model.UserWorkerSingle;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.Aboutapp;
import static com.esafeafrica.esafe.Config.ErrorMgt.DataError;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.ZeroReturn;
import static com.esafeafrica.esafe.Config.General_Actions.getAddress;
import static com.esafeafrica.esafe.Config.Validation.ConvertImage;
import static com.esafeafrica.esafe.Config.getIntents.callIntent;
import static com.esafeafrica.esafe.Config.getIntents.sendEmail;


public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    SessionManager session;
    int PERMISSION_ID = 44;
    private String latitude, longitude,names,email,tel_num;
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private AppBarConfiguration mAppBarConfiguration;
    private ImageView prof;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
    }

    public void InitView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this.context=getApplicationContext();
        session = new SessionManager(getApplicationContext());
        FloatingActionButton abuse = findViewById(R.id.abuse);
        FloatingActionButton mapup = findViewById(R.id.loca_update);
        FloatingActionButton calls = findViewById(R.id.callsos);
        FloatingActionButton viro = findViewById(R.id.virus);
        //prof=findViewById(R.id.image_profile);

        //Get Last Known Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        //prof.setImageBitmap(ConvertImage(setAccount().getPic()));

        //Normal Clicks
        abuse.setOnClickListener(view -> {
            try {
                alertChooser(new Emergency(null,null,null,null,null,null,null,null,latitude,getAddress(getApplicationContext(),latitude,longitude),longitude,setAccount().getAddress(),setAccount().getPassport(),setAccount().getLcompany(),null,setAccount().getUserid()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mapup.setOnClickListener(view -> {
            //Update Locations
            Toast.makeText(getApplicationContext(),"Location Updated",Toast.LENGTH_LONG).show();
        });
        calls.setOnClickListener(view -> {
           callIntent(getApplicationContext(),"0751073507");
        });
        viro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoronaAlert(new Corona(null,null,null,null,null,latitude,longitude,names,null,tel_num,null,null,null,null,null));
            }
        });
        //Long Clicks
        abuse.setOnLongClickListener(view -> {
            return true;
        });
        mapup.setOnLongClickListener(view -> {
            return true;
        });
        calls.setOnLongClickListener(view -> {
            return true;
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account,
                R.id.nav_announce,R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Getting account details
        loadAccount(setAccount());
    }

    public UserWorker getAcc() {
        Bundle data = getIntent().getExtras();
        UserWorker account = data.getParcelable("account");
        return account;
    }

    private UserWorker setAccount(){
        UserWorker account=new UserWorker();
        if (session.isLoggedIn()) {
            HashMap<String, String> userDetails = session.getUserDetails();
            Log.d("User",userDetails.get(SessionManager.KEY_USER_NAME));
            // account=new Account(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_LATI),userDetails.get(SessionManager.KEY_USER_LCOMPANY),userDetails.get(SessionManager.KEY_USER_LOCATION),userDetails.get(SessionManager.KEY_USER_LONGI),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_PHONE),null,userDetails.get(SessionManager.KEY_USER_USERID));
            account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));
        } else {
            Toast.makeText(getApplicationContext(),"Not User signed in", Toast.LENGTH_SHORT).show();
        }
        return account;
    }

    private Bundle setBasic(UserWorker account) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("account", account);
        return bundle;
    }

    private void loadAccount(UserWorker  account) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<UserWorkerSingle> singleCall = apiInterface.getAccountCon(account.getNin());
        singleCall.enqueue(new Callback<UserWorkerSingle>() {
            @Override
            public void onResponse(Call<UserWorkerSingle> call, Response<UserWorkerSingle> response) {
                if (response.isSuccessful()) {
                    UserWorkerSingle accountSingle = response.body();
                    if (accountSingle.getUserWorker().getEmail() != null) {
                        names=accountSingle.getUserWorker().getNames();
                        email=accountSingle.getUserWorker().getEmail();
                        tel_num=accountSingle.getUserWorker().getPhone();
                        Toast.makeText(getApplicationContext(),names,Toast.LENGTH_SHORT).show();
                        setBasic(accountSingle.getUserWorker());
                    } else {
                        ZeroReturn(getApplicationContext(), getLayoutInflater());
                    }
                } else {
                    ServerError(getApplicationContext(), getLayoutInflater());
                }

            }

            @Override
            public void onFailure(Call<UserWorkerSingle> call, Throwable t) {
                NoInternet(getApplicationContext(), getLayoutInflater());
                t.printStackTrace();
                Log.d("Error",t.toString());
            }
        });
    }

    private void Loca(UserWorker account) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody ninreq = RequestBody.create(MultipartBody.FORM, account.getNin());
        RequestBody locreq = RequestBody.create(MultipartBody.FORM, account.getLocation());
        RequestBody latreq = RequestBody.create(MultipartBody.FORM, account.getLati());
        RequestBody lonreq = RequestBody.create(MultipartBody.FORM, account.getLongi());
        Call<Feedback> call = apiInterface.updateLoca(ninreq,locreq,latreq,lonreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        Log.d("Success", "Client Successs");

                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        DataError(getApplicationContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Error", "Error Server");
                    ServerError(getApplicationContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getApplicationContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public  void baseOut(){
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,
                                "You have been signed out.",
                                Toast.LENGTH_LONG)
                                .show();

                        // Close activity
                        finish();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Aboutapp(getApplicationContext(), getLayoutInflater());
            return true;
        } else if (id == R.id.action_share) {
            Share();
            return true;
        }else if(id==R.id.action_feedback){
            sendEmail(getApplicationContext(),"info@moh-rss.org");
            return true;
        }
         else if (id == R.id.action_logout) {
            //baseOut();
            session.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void Share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hey you should download this app");
        intent.putExtra(Intent.EXTRA_TEXT, "https://wakaimalabs.com/SafeApp/ssd/");
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = String.valueOf(location.getLatitude());
                                    longitude = String.valueOf(location.getLongitude());
                                     try {
                                     Loca(new UserWorker(latitude,getAddress(getApplicationContext(),latitude,longitude),longitude,setAccount().getNin()));
                                     } catch (IOException e) {
                                     e.printStackTrace();
                                     }
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    public void Notify(Context context, String title, String detail) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_safe)
                .setContentTitle(title)
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //Add as Notifcation
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // manager.notify();
        manager.notify(001, builder.build());
    }

    private void CoronaAlert(Corona cor){
        Bundle bundle=new Bundle();
        bundle.putParcelable("corona", cor);
        FragmentManager manager = getSupportFragmentManager();
        Corona_alert corona=new Corona_alert();
        corona.setArguments(bundle);
        corona.show(manager, "CORONA VIRUS");

    }

    private void alertChooser(Emergency emergency) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("emergency", emergency);
        FragmentManager manager = getSupportFragmentManager();
        alertChooser selected = new alertChooser();
        selected.setArguments(bundle);
        selected.show(manager, "emergency");
    }

}
