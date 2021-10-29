package com.esafeafrica.esafe.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;


import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.MainActivity;
import com.esafeafrica.esafe.Model.Emergency;
import com.esafeafrica.esafe.Model.EmergencyList;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
import static com.esafeafrica.esafe.Config.ErrorMgt.NullString;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.checkImage;
import static com.esafeafrica.esafe.Config.Validation.getUriToDrawable;
import static com.esafeafrica.esafe.Config.Validation.getUri;
import static com.esafeafrica.esafe.Config.Validation.getUriToDrawable;
import static com.esafeafrica.esafe.Config.Validation.isFieldValid;


/**
 * Activities that contain this fragment must implement
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class alertChooser extends DialogFragment {
    private ArrayList<Emergency> emergencyArrayList;
    MainActivity mainActivity;
    private ProgressSync progressSync;
    SessionManager session;
    private Context context;
    private CheckBox box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,mebox,casebox,picbox;
    private ImageButton picload;
    private EditText name,passport,details,rela_name;
    private Button alert;
    private Spinner topic,oldcase,relation;
    private ApiInterface apiInterface;
    private LinearLayout lay_relation;
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    String Path;
    Bitmap myBitmap;
    Uri picUri;
    int PERMISSION_ID = 44;

    public alertChooser() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment alertChooser.
     */
    // TODO: Rename and change types and number of parameters
    public static alertChooser newInstance() {
        alertChooser fragment = new alertChooser();
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
        View rootview = inflater.inflate(R.layout.fragment_alert_chooser, container, false);
        permissions.add(CAMERA);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(INTERNET);
        permissions.add(CALL_PHONE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        InitViews(rootview);
        return rootview;
    }

    private void InitViews(View view) {
        context = view.getContext();
        progressSync=new ProgressSync(context);
        session=new SessionManager(context);
        mainActivity = new MainActivity();
        name=view.findViewById(R.id.rep_name);
        details=view.findViewById(R.id.rep_extra_details);
        passport=view.findViewById(R.id.rep_passport);
        alert=view.findViewById(R.id.rep_alert);
        picload=view.findViewById(R.id.rep_pic_evi);
        picbox=view.findViewById(R.id.rep_pic_check);
        casebox=view.findViewById(R.id.rep_caseid);
        mebox=view.findViewById(R.id.rep_me);
        box1=view.findViewById(R.id.rep_mark1);
        box2=view.findViewById(R.id.rep_mark2);
        box3=view.findViewById(R.id.rep_mark3);
        box4=view.findViewById(R.id.rep_mark4);
        box5=view.findViewById(R.id.rep_mark5);
        box6=view.findViewById(R.id.rep_mark6);
        box7=view.findViewById(R.id.rep_mark7);
        box8=view.findViewById(R.id.rep_mark8);
        box9=view.findViewById(R.id.rep_mark9);
        box10=view.findViewById(R.id.rep_mark10);
        box11=view.findViewById(R.id.rep_mark11);
        topic=view.findViewById(R.id.rep_category);
        oldcase=view.findViewById(R.id.rep_old_caseid);
        relation=view.findViewById(R.id.rep_relation);
        lay_relation=view.findViewById(R.id.rep_rela_lay);
        rela_name=view.findViewById(R.id.rep_rela_name);


       setValues();

        topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCheckBoxValues(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mebox.isChecked()){
                    Toast.makeText(getContext(), "No Profile Data Found", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    passport.setText("");
                    lay_relation.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(context,R.array.relation_array,android.R.layout.simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    relation.setAdapter(adapter);
                }else{
                    if(setAccount().getNames() != null){
                        name.setText(setAccount().getNames());
                        passport.setText(setAccount().getPassport());
                    }else{
                        Toast.makeText(getContext(), "No Profile Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        picload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        picbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picbox.isChecked()) {
                    picload.setVisibility(View.VISIBLE);
                }else{
                    picload.setVisibility(View.GONE);
                }
            }
        });
        casebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(casebox.isChecked()) {
                    oldcase.setVisibility(View.VISIBLE);
                    getEmergencyList(new Emergency(null,null,setAccount().getPassport(),null,setAccount().getUserid()));
                }else{
                    oldcase.setVisibility(View.GONE);
                }
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createEmergency();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void createEmergency() throws FileNotFoundException {
        if(picbox.isChecked()){
            if (getPath(picUri) != null) {
                File file = new File(getPath(picUri));
                emergencyAlert(setEmergency(), file);
            }else{
                Toast.makeText(context, "Please choose an image/picture Or Uncheck", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Please Check the Image Evidence", Toast.LENGTH_SHORT).show();
            //Uri pic =Uri.parse("android.resource://"+context.getPackageName()+"/drawable/esafe");
            //Toast.makeText(context, "No Checked Select Image", Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, String.valueOf(getUriToDrawable(context,R.drawable.esafe)), Toast.LENGTH_SHORT).show();
            //File file = new File(String.valueOf(getUriToDrawable(context,R.drawable.esafe)));
            //emergencyAlert(setEmergency(), file);
        }

    }
    private Emergency getEmergency() {
        Emergency emergency = new Emergency();
        if (getArguments() != null) {
            emergency = getArguments().getParcelable("emergency");
            return emergency;
        }
        return emergency;
    }



    private String checkResults(){
        return getCheckResults(box1) +getCheckResults(box2)+getCheckResults(box3)+getCheckResults(box4)+getCheckResults(box5)+getCheckResults(box6)+getCheckResults(box7)+getCheckResults(box8)+getCheckResults(box9)+getCheckResults(box10)+getCheckResults(box11);
    }

    private String getCheckResults(CheckBox check){

        if(check.isChecked()){
            return check.getText().toString();
        }
        return "";
    }

    private UserWorker setAccount(){
        UserWorker account=new UserWorker();
        if (session.isLoggedIn()) {
            HashMap<String, String> userDetails = session.getUserDetails();
            Log.d("User",userDetails.get(SessionManager.KEY_USER_NAME));
            account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));

        } else {
            Toast.makeText(getContext(),"Your are logged in as guest", Toast.LENGTH_SHORT).show();
        }
        return account;
    }
    private void setValues(){
        if(getEmergency().getEvent() == "Employer") {
            if (setAccount().getNames() != null) {
                name.setText(setAccount().getNames());
                passport.setText(setAccount().getPassport());
            } else {
                mebox.setVisibility(View.GONE);
                oldcase.setVisibility(View.GONE);
                casebox.setVisibility(View.GONE);
                lay_relation.setVisibility(View.VISIBLE);
                relation.setVisibility(View.GONE);
                // Toast.makeText(getContext(), "No Profile Data Found", Toast.LENGTH_SHORT).show();
            }
            picload.setVisibility(View.GONE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.report_category, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            topic.setAdapter(adapter);
            topic.setSelection(6);
            //default Old Case
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("No Old Cases");
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            oldcase.setAdapter(arrayAdapter);
            setCheckBoxValues(topic.getSelectedItem().toString());
            Log.d("Item", topic.getSelectedItem().toString());
        }else{
            if (setAccount().getNames() != null) {
                name.setText(setAccount().getNames());
                passport.setText(setAccount().getPassport());
            } else {
                mebox.setVisibility(View.GONE);
                oldcase.setVisibility(View.GONE);
                casebox.setVisibility(View.GONE);
                // Toast.makeText(getContext(), "No Profile Data Found", Toast.LENGTH_SHORT).show();
                lay_relation.setVisibility(View.VISIBLE);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.relation_array, android.R.layout.simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                relation.setAdapter(adapter);
            }
            picload.setVisibility(View.GONE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.report_category, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            topic.setAdapter(adapter);
            //default Old Case
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("No Old Cases");
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            oldcase.setAdapter(arrayAdapter);
            setCheckBoxValues(topic.getSelectedItem().toString());
            Log.d("Item", topic.getSelectedItem().toString());
        }
    }

    private void setCheckBoxValues(String cond) {
       // String cond = "Abuse";//getEmergency().getTopic();
        if (cond.equals("Abuse")) {
            box1.setText("Sexual Harassment");
            box2.setText("Physic Torture");
            box3.setText("Denied Food");
            box4.setText("Psychological Torture");
            box5.setText("Discrimination/Racism");
            box6.setVisibility(View.GONE);
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        } else if (cond.equals("Health")) {
            box1.setText("Sick");
            box2.setText("No access to Medication");
            box3.setText("Chronic Disease");
            box4.setVisibility(View.GONE);
            box5.setVisibility(View.GONE);
            box6.setVisibility(View.GONE);
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        } else if (cond.equals("Complaint")) {
            box1.setText("Assistance Needed");
            box2.setText("Employer Change");
            box3.setText("Non-Contract Work");
            box4.setText("More than 7 Members");
            box5.setText("Over Worked");
            box6.setText("No Access to Phone/Communication");
            box7.setText("Salary Issues");
            box8.setText("About Local Company");
            box9.setText("About Foreign Company");
            box10.setText("Not enough Rest/sleep");
            box11.setText("Vulgar Abusive Language");
        }else if (cond.equals("Legal Services")) {
            box1.setText("Repatriation");
            box2.setText("legal Representation");
            box3.setText("Imprisonment");
            box4.setText("Rescue(Streets/Airport)");
            box5.setText("Human Trafficking");
            box6.setVisibility(View.GONE);
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        }else if (cond.equals("Compliment")) {
            box1.setText("Good Employer");
            box2.setText("Food on time");
            box3.setText("I get enough rest/sleep");
            box4.setText("local checks on me");
            box5.setText("Access to Phone/Communication");
            box6.setText("Social bonding");
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        }  else if (cond.equals("Death")) {
            box1.setText("Was sick");
            box2.setText("Killed");
            box3.setText("Tortured to death");
            box4.setText("Natural death");
            box5.setVisibility(View.GONE);
            box6.setVisibility(View.GONE);
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        }  else if (cond.equals("Employer")) {
            box1.setText("Arrogant & Rude");
            box2.setText("Very Sick");
            box3.setText("Very Lazy");
            box4.setText("Language Problem");
            box5.setText("Request Change");
            box6.setText("Bad Habits");
            box7.setVisibility(View.GONE);
            box8.setVisibility(View.GONE);
            box9.setVisibility(View.GONE);
            box10.setVisibility(View.GONE);
            box11.setVisibility(View.GONE);
        } else {
            return;
        }

    }

    public void emergencyAlert(Emergency emergency,File file) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, emergency.getName());
        RequestBody useridreq = RequestBody.create(MultipartBody.FORM, emergency.getUserid());
        RequestBody passreq = RequestBody.create(MultipartBody.FORM, emergency.getPassport());
        RequestBody orgreq = RequestBody.create(MultipartBody.FORM, emergency.getOrgan());
        RequestBody topicreq = RequestBody.create(MultipartBody.FORM, emergency.getTopic());
        RequestBody locareq = RequestBody.create(MultipartBody.FORM, emergency.getLocation());
        RequestBody latireq = RequestBody.create(MultipartBody.FORM, emergency.getLati());
        RequestBody longireq = RequestBody.create(MultipartBody.FORM, emergency.getLongi());
        RequestBody eventreq = RequestBody.create(MultipartBody.FORM, emergency.getEvent());
        RequestBody detailsreq = RequestBody.create(MultipartBody.FORM, emergency.getDetails());
        RequestBody formerreq = RequestBody.create(MultipartBody.FORM, NullString(emergency.getFormerid()));
        //RequestBody picreq = RequestBody.create(MultipartBody.FORM, emergency.getBpic());
        RequestBody ptreq = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(picUri)), file);
        //MultipartBody.Part for Picture
        MultipartBody.Part picreq = MultipartBody.Part.createFormData("pic", file.getName(), ptreq);
        Call<Feedback> call = apiInterface.createEmergency(namereq,useridreq,passreq,orgreq,topicreq,locareq,latireq,longireq,eventreq,detailsreq,formerreq,picreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        //sendNotfication(getContext(), emergency.getName(), emergency.getEvent());
                        Toast.makeText(getContext(), "Case Reported", Toast.LENGTH_SHORT).show();
                        dismiss();
                        Log.d("Success", "Client Successs");
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        //IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Error :::::::::","Error Server");
                    ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getContext(), getLayoutInflater());
            }
        });
    }

    private void getEmergencyList(Emergency emergency){
        progressSync.showDialog();
        apiInterface=RetroClient.getClient().create(ApiInterface.class);
        Call<EmergencyList> call=apiInterface.getEmergencyList("0","null",emergency.getUserid(),emergency.getPassport(),"null");
        call.enqueue(new Callback<EmergencyList>() {
            @Override
            public void onResponse(Call<EmergencyList> call, Response<EmergencyList> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null){
                        emergencyArrayList = response.body().getEmergency();
                        //progressSync.hideDialog();
                    emergencyArrayList = response.body().getEmergency();
                    ArrayList<String> emergencyOne=new ArrayList<>();
                    for(int i= 0;i <emergencyArrayList.size();i++){
                        Log.d("ITEMS",emergencyArrayList.get(i).getName());
                        emergencyOne.add(emergencyArrayList.get(i).getEmerid() +" "+emergencyArrayList.get(i).getTopic());
                    }
                    //organArrayList.add(response.body().getOrgan().get(2));
                    ArrayAdapter arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,emergencyOne);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    oldcase.setAdapter(arrayAdapter);
                    progressSync.hideDialog();
                    // getForeignCo(organArrayList.);
                }else{
                        progressSync.hideDialog();
                        //recyclerView.setVisibility(View.GONE);
                        //oldcase.setVisibility(View.GONE);
                        casebox.setChecked(false);
                        //EmptyList(getContext(),getLayoutInflater());
                        ArrayList<String> arrayList=new ArrayList<>();
                        arrayList.add("No Old Cases");
                        ArrayAdapter arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        oldcase.setAdapter(arrayAdapter);
                    }
                } else {
                    progressSync.hideDialog();
                    casebox.setChecked(false);
                    oldcase.setVisibility(View.GONE);
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Organ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EmergencyList> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    private Emergency setEmergency() {
        if(setAccount().getUserid() != null) {
            Emergency emergency = new Emergency(null, null, null, null, NullString(details.getText().toString()), null, NullString(oldcase.getSelectedItem().toString()), checkResults(), getEmergency().getLati(), getEmergency().getLocation(), getEmergency().getLongi(), NullString(name.getText().toString()), NullString(passport.getText().toString()), setAccount().getLcompany(), NullString(topic.getSelectedItem().toString()), setAccount().getUserid());
            return emergency;
        }else{
                String name_relation = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    name_relation = Objects.requireNonNull(rela_name.getText()).toString();
                }
                int err = 0;
                if (!isFieldValid(name_relation)) {
                    err++;
                    rela_name.setError("Please provide your names!");
                }
                Emergency emergency = new Emergency(null, null, null, null, NullString(details.getText().toString()), null, NullString(oldcase.getSelectedItem().toString()), checkResults(),"N/A", "N/A", "N/A", NullString(name.getText().toString()), NullString(passport.getText().toString()), "Guest", NullString(topic.getSelectedItem().toString()),name_relation);
                return emergency;

        }
    }

    //Image Uploading

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



    public String getPath(Uri uri) {
        if(uri != null) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.context.getContentResolver().query(uri, projection, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                cursor.close();
                return path;
            } else {
                return uri.getPath();
            }
        }else{
           // Toast.makeText(getContext(), "No Image Selected!!!!!!!", Toast.LENGTH_SHORT).show();
            return null;
        }
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
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), picUri);
                    //myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);
                    picload.setImageBitmap(myBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                picload.setImageBitmap(bitmap);

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


}
