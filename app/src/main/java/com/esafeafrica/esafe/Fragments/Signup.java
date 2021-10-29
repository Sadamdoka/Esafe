package com.esafeafrica.esafe.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Login;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.Organ;
import com.esafeafrica.esafe.Model.OrganList;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import static com.esafeafrica.esafe.Config.ErrorMgt.DataError;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.SuccessAccount;
import static com.esafeafrica.esafe.Config.Validation.isEmailValid;
import static com.esafeafrica.esafe.Config.Validation.isFieldValid;
import static com.esafeafrica.esafe.Config.Validation.isPasswordValid;
import static com.esafeafrica.esafe.Config.Validation.isPhoneValid;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment implements MapDialog.OnLocationgetter {


    private AppBarConfiguration mAppBarConfiguration;
    FusedLocationProviderClient mFusedLocationClient;
    SessionManager session;
    private ArrayList<Organ> organArrayList;
    int PERMISSION_ID = 44;
    private ProgressSync progressSync;
    private EditText names, email, phone, loca, passport, dob, company, jobtype;
    private Spinner gender, lco;
    private ImageButton mapd, picupload;
    private String lati, longi, com;
    private CheckBox terms, other;
    final Calendar myCalendar = Calendar.getInstance();
    private Context context;
    private ApiInterface apiInterface;
    private Button create;
    private FirebaseAuth auth;
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    String Path, userid;
    Bitmap myBitmap;
    Uri picUri;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lati = String.valueOf(mLastLocation.getLatitude());
            longi = String.valueOf(mLastLocation.getLongitude());
        }
    };

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance() {
        Signup fragment = new Signup();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        permissions.add(CAMERA);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(INTERNET);
        permissions.add(CALL_PHONE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        InitView(root);
        return root;
    }

    private void InitView(View item) {
        context = getActivity();
        //Clearing Image Cache
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        progressSync = new ProgressSync(context);
        names = item.findViewById(R.id.rt_name);
        email = item.findViewById(R.id.rt_email);
        passport = item.findViewById(R.id.rt_passport_no);
        phone = item.findViewById(R.id.rt_number);
        company = item.findViewById(R.id.rt_company);
        dob = item.findViewById(R.id.rt_dob);
        gender = item.findViewById(R.id.rt_gender);
        jobtype = item.findViewById(R.id.rt_job);
        terms = item.findViewById(R.id.rt_terms);
        create = item.findViewById(R.id.btn_register);
        mapd = item.findViewById(R.id.rt_loc_map);
        picupload = item.findViewById(R.id.rt_face_pic);
        company = item.findViewById(R.id.rt_company);
        mapd = item.findViewById(R.id.rt_loc_map);
        loca = item.findViewById(R.id.rt_current_loca);
        lco = item.findViewById(R.id.rt_co_spinner);
        other = item.findViewById(R.id.rt_check_other);
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
                new DatePickerDialog(context, pickDate(), myCalendar
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

        picupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), 200);
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
        } else {
            Toast.makeText(getContext(), "Please Agree to the terms and conditions", Toast.LENGTH_LONG).show();
        }
    }


    private void CreateAccount() throws FileNotFoundException {

        File file = new File(getPath(picUri));
        FileInputStream stream = new FileInputStream(file);
        Log.d("Path", getPath(picUri));

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
        //bundle.putParcelable("user", setBasic());
        FragmentManager manager = getParentFragmentManager();
        MapDialog mapDialog = new MapDialog();
        mapDialog.setArguments(bundle);
        mapDialog.setTargetFragment(this, 1);
        mapDialog.show(manager, "Choose Location");
    }

    private void setDefault() {
        phone.setText(" ");
    }

    public void gotoLogin(UserWorker account) {
        Intent intent = new Intent(getContext(), Login.class);
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
        RequestBody ptreq = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(picUri)), file);
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
                        SuccessAccount(getContext(), getLayoutInflater(), account.getNames());
                        gotoLogin(account);
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());

                        SuccessAccount(getContext(), getLayoutInflater(), account.getNames());
                        //DataError(getActivity(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressSync.hideDialog();
                    Log.d("Error", "Error Server");

                    SuccessAccount(getContext(), getLayoutInflater(), account.getNames());
                    //ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
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
                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, organOne);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    lco.setAdapter(arrayAdapter);
                    progressSync.hideDialog();
                } else {
                    progressSync.hideDialog();
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Organ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OrganList> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    public void createFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Firebase Account Created", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getActivity(), MainActivity.class));
                    //finish();
                }
            }
        });
    }

    @Override
    public void getArea(String pt) {
        loca.setText(pt);
    }

    @Override
    public void getLat(String pt) {
        lati = pt;

    }

    @Override
    public void getLong(String pt) {
        longi = pt;
    }


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

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getContext().getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            //allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            //ImageView imageView = (ImageView) findViewById(R.id.imageView);

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                Log.d("Path", picUri.toString());
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), picUri);
                    //myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 200);
                    picupload.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                picupload.setImageBitmap(myBitmap);

            }

        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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
                return (getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
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
        return ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    //Date Picker Code
    private DatePickerDialog.OnDateSetListener  pickDate() {
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

    //Load Image Chooser and Crop

}
