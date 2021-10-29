package com.esafeafrica.esafe.Image_picker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.LocationTrack;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Fragments.MapDialog;
import com.esafeafrica.esafe.Login;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.Organ;
import com.esafeafrica.esafe.Model.OrganList;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.SuccessAccount;
import static com.esafeafrica.esafe.Config.Validation.isEmailValid;
import static com.esafeafrica.esafe.Config.Validation.isFieldValid;
import static com.esafeafrica.esafe.Config.Validation.isPhoneValid;

public class Create_account extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public static final int REQUEST_IMAGE = 100;
    private static final String TAG = Create_account.class.getSimpleName();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    final Calendar myCalendar = Calendar.getInstance();
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    FusedLocationProviderClient mFusedLocationClient;
    SessionManager session;
    int PERMISSION_ID = 44;
    String Path, userid;
    Bitmap myBitmap;
    Uri picUri;
    LocationTrack locationTrack;
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<Organ> organArrayList;
    private ProgressSync progressSync;
    private EditText names, email, phone, loca, passport, dob, company, jobtype;
    private Spinner gender, lco;
    private ImageButton mapd;
    private String lati, longi, com;
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lati = String.valueOf(mLastLocation.getLatitude());
            longi = String.valueOf(mLastLocation.getLongitude());
        }
    };
    private CheckBox terms, other;
    private Context context;
    private ApiInterface apiInterface;
    private Button create;
    private FirebaseAuth auth;
    private ArrayList<String> permissionsToRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        InitView();
    }

    private void InitView() {
        context = getApplicationContext();
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        progressSync = new ProgressSync(this);
        // Image Cache
        loadProfileDefault();
        //Location Code

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);
        names = findViewById(R.id.ac_name);
        email = findViewById(R.id.ac_email);
        passport = findViewById(R.id.ac_passport_no);
        phone = findViewById(R.id.ac_number);
        company = findViewById(R.id.ac_company);
        dob = findViewById(R.id.ac_dob);
        gender = findViewById(R.id.ac_gender);
        jobtype = findViewById(R.id.ac_job);
        terms = findViewById(R.id.ac_terms);
        create = findViewById(R.id.ac_btn_register);
        mapd = findViewById(R.id.ac_loc_map);
        company = findViewById(R.id.ac_company);
        mapd = findViewById(R.id.ac_loc_map);
        loca = findViewById(R.id.ac_current_loca);
        lco = findViewById(R.id.ac_co_spinner);
        other = findViewById(R.id.ac_check_other);
        //Loading Organ/Local Companies
        getLocalCo("3");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        mapd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMap();
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Create_account.this, pickDate(), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        lco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String organid = organArrayList.get(position).getOrganid();
                com = organid;
                //Toast.makeText(getContext(), organid, Toast.LENGTH_SHORT).show();
                //Log.d("OrganID", organid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (other.isChecked()) {
                    company.setVisibility(View.VISIBLE);
                    lco.setVisibility(View.GONE);
                } else {
                    company.setVisibility(View.GONE);
                    lco.setVisibility(View.VISIBLE);
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    checkTerms(terms);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Called when the location has changed.
     *
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
       Log.d("Coordinates","Longitude:" + Double.toString(location.getLongitude()) + "\nLatitude:" + Double.toString(location.getLatitude()));
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (null != addresses && addresses.size() > 0) {
                Log.d("Coordinates", String.valueOf(location.getLatitude()));
                Log.d("Coordinates", String.valueOf(location.getLongitude()));
                Log.d("Areas", addresses.get(0).getAddressLine(0));
                //Adding Default Locations
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                //String city = addresses.get(0).getLocality();
                //String state = addresses.get(0).getAdminArea();
                //String country = addresses.get(0).getCountryName();
                //String postalCode = addresses.get(0).getPostalCode();
                //String knownName = addresses.get(0).getFeatureName();
                Log.d("Address", address);
                loca.setText(address);
                lati = String.valueOf(location.getLatitude());
                longi = String.valueOf(location.getLatitude());
               // Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(location.getLatitude()) + "\nLatitude:" + Double.toString(location.getLatitude()), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Location is Null", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider that has become enabled
     */
    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d("Latitude", "disable");
    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider that has become disabled
     */
    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d("Latitude", "disable");
    }

    /**
     * This callback will never be invoked on Android Q and above, and providers can be considered
     *
     * @param provider
     * @param status
     * @param extras
     * @deprecated This callback will never be invoked on Android Q and above.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private String getCompany() {
        if (other.isChecked()) {
            com = company.getText().toString();
            // Toast.makeText(getContext(), com, Toast.LENGTH_SHORT).show();
            return com;
        } else {
            //Toast.makeText(getContext(), com, Toast.LENGTH_SHORT).show();
            return com;
        }
    }

    private void checkTerms(CheckBox checkBox) throws FileNotFoundException {
        if (checkBox.isChecked()) {
            CreateAccount();
           // Path = picUri.toString();
            //Toast.makeText(context, Path, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Please Agree to the terms and conditions", Toast.LENGTH_LONG).show();
        }
    }


    private void CreateAccount() throws FileNotFoundException {
        Path = picUri.toString();
        File file = new File(Path);
        Log.d("Path For Selected Image", Path);
        Toast.makeText(context, Path, Toast.LENGTH_LONG).show();

        String passpot = null, name = null, mail = null, number = null, birth = null, job = null, com = null, location = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            name = Objects.requireNonNull(names.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            passpot = Objects.requireNonNull(passport.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mail = Objects.requireNonNull(email.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            number = Objects.requireNonNull(phone.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            birth = Objects.requireNonNull(dob.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            job = Objects.requireNonNull(jobtype.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            com = Objects.requireNonNull(company.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            location = Objects.requireNonNull(loca.getText()).toString();
        }


        int err = 0;
        if (!isFieldValid(passpot)) {
            err++;
            passport.setError("Must Fill In Passport Number");
        }
        if (!isFieldValid(name)) {
            err++;
            names.setError("Check Your Names");
        }
        if (!isEmailValid(mail)) {
            err++;
            email.setError("Check Your Email");
        }
        if (!isPhoneValid(number)) {
            err++;
            phone.setError("Check Your Number");
        }
        if (!isFieldValid(birth)) {
            err++;
            dob.setError("Check Your Date of Birth");
        }

        if (!isFieldValid(job)) {
            err++;
            jobtype.setError("Check Your Type of Job");
        }

        if (!isFieldValid(com) && other.isChecked()) {
            err++;
            company.setError("Check Your Company");
        }
        if (!isFieldValid(location)) {
            err++;
            loca.setError("Check Your Location");
        }
        if (err == 0) {
            UserWorker userWorker = new UserWorker(location, birth, mail, gender.getSelectedItem().toString(), job, lati, getCompany(), location, longi, name, passpot, number, null);
            Create(userWorker, file);
            //createFirebase(mail,password);
        }
    }

    private void callMap() {
        Bundle bundle = new Bundle();
        FragmentManager manager = getSupportFragmentManager();
        MapDialog mapDialog = new MapDialog();
        mapDialog.setArguments(bundle);
        mapDialog.show(manager, "Choose Location");
    }

    private void setDefault() {
        phone.setText(" ");
    }

    public void gotoLogin(UserWorker account) {
        Intent intent = new Intent(context, Login.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }

    private void Create(UserWorker account, File file) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, account.getNames());
        RequestBody passportreq = RequestBody.create(MultipartBody.FORM, account.getPassport());
        RequestBody addressreq = RequestBody.create(MultipartBody.FORM, account.getAddress());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, account.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, account.getPhone());
        RequestBody genreq = RequestBody.create(MultipartBody.FORM, account.getGender());
        RequestBody dobreq = RequestBody.create(MultipartBody.FORM, account.getDob());
        RequestBody jobreq = RequestBody.create(MultipartBody.FORM, account.getJobtype());
        RequestBody comreq = RequestBody.create(MultipartBody.FORM, account.getLcompany());
        RequestBody locreq = RequestBody.create(MultipartBody.FORM, account.getLocation());
        RequestBody latreq = RequestBody.create(MultipartBody.FORM, account.getLati());
        RequestBody lonreq = RequestBody.create(MultipartBody.FORM, account.getLongi());
        RequestBody ptreq = RequestBody.create(MediaType.parse(getContentResolver().getType(picUri)), file);
        //MultipartBody.Part for Picture
        MultipartBody.Part pic = MultipartBody.Part.createFormData("pic", file.getName(), ptreq);
        Call<Feedback> call = apiInterface.createAccount(namereq, passportreq, addressreq, emailreq, phonereq, genreq, dobreq, jobreq, comreq, locreq, latreq, lonreq, pic);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        progressSync.hideDialog();
                        SuccessAccount(context, getLayoutInflater(), account.getNames());
                        gotoLogin(account);
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());
                        SuccessAccount(context, getLayoutInflater(), account.getNames());
                        //DataError(getActivity(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressSync.hideDialog();
                    Log.d("Error", "Error Server");

                    SuccessAccount(context, getLayoutInflater(), account.getNames());
                    //ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(context, getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    private void getLocalCo(String type) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<OrganList> call = apiInterface.Organtype(type);
        call.enqueue(new Callback<OrganList>() {
            @Override
            public void onResponse(Call<OrganList> call, Response<OrganList> response) {
                if (response.isSuccessful()) {
                    organArrayList = response.body().getOrgan();
                    ArrayList<String> organOne = new ArrayList<>();
                    for (int i = 0; i < organArrayList.size(); i++) {
                        organOne.add(organArrayList.get(i).getNames());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, organOne);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    lco.setAdapter(arrayAdapter);
                    progressSync.hideDialog();
                } else {
                    progressSync.hideDialog();
                    ServerError(context, getLayoutInflater());
                    Log.d("Organ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OrganList> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(context, getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    /**
     * public void createFirebase(String email, String password) {
     * auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
     *
     * @Override public void onComplete(@NonNull Task<AuthResult> task) {
     * // If sign in fails, display a message to the user. If sign in succeeds
     * // the auth state listener will be notified and logic to handle the
     * // signed in user can be handled in the listener.
     * if (!task.isSuccessful()) {
     * Toast.makeText(context, "Authentication failed." + task.getException(),
     * Toast.LENGTH_SHORT).show();
     * } else {
     * Toast.makeText(context, "Firebase Account Created", Toast.LENGTH_LONG).show();
     * //startActivity(new Intent(getActivity(), MainActivity.class));
     * //finish();
     * }
     * }
     * });
     * }
     */


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getParent(),
                new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    //Date Picker Code
    private DatePickerDialog.OnDateSetListener pickDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        return date;
    }

    private void updateDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
    }


    //Loading Image and Crop
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Glide.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.ic_account)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }

    @OnClick({R.id.img_plus, R.id.img_profile})
    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    picUri = uri;
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    //Map Results
    private void getLocation(Context context) throws IOException {
        locationTrack = new LocationTrack(context);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (null != addresses && addresses.size() > 0) {
                    Log.d("Coordinates", String.valueOf(latitude));
                    Log.d("Coordinates", String.valueOf(longitude));
                    Log.d("Areas", addresses.get(0).getAddressLine(0));
                    //Adding Default Locations
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    //String city = addresses.get(0).getLocality();
                    //String state = addresses.get(0).getAdminArea();
                    //String country = addresses.get(0).getCountryName();
                    //String postalCode = addresses.get(0).getPostalCode();
                    //String knownName = addresses.get(0).getFeatureName();
                    Log.d("Address", address);
                    loca.setText(address);
                    lati = String.valueOf(latitude);
                    longi = String.valueOf(longitude);
                    //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Location is Null", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            locationTrack.showSettingsAlert();
        }
    }


}