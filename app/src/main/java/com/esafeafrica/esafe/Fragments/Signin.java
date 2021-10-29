package com.esafeafrica.esafe.Fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.BarcodeFragment;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.MainActivity;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.Model.UserWorkerSingle;
import com.esafeafrica.esafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.esafeafrica.esafe.Config.ErrorMgt.EmptyList;
import static com.esafeafrica.esafe.Config.ErrorMgt.IncorrectDetails;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.Validation.isFieldValid;
import static com.esafeafrica.esafe.Config.Validation.isPasswordValid;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Signin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signin extends Fragment {

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private static final String EXTRA_CODE="code";
    private static final int TARGET_FRAGMENT_REQUEST_CODE=1;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    SessionManager session;
    int PERMISSION_ID = 44;
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private LinearLayout linearLayout;
    private Context context;
    private EditText nin;
    private Button confirm;
    private ImageButton scan;
    private FirebaseAuth auth;
    private IntentIntegrator qrScan;
    private ArrayList<String> permissionsToRequest;
    public Signin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Signin.
     */
    // TODO: Rename and change types and number of parameters
    public static Signin newInstance() {
        Signin fragment = new Signin();
        return fragment;
    }

    public static Intent newIntent(String code){
        Intent intent=new Intent();
        intent.putExtra(EXTRA_CODE,code);
        return intent;
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
        View root = inflater.inflate(R.layout.fragment_signin, container, false);
        permissions.add(CAMERA);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(ACCESS_FINE_LOCATION);
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
        session = new SessionManager(context);
        nin = item.findViewById(R.id.et_nin);
        scan = item.findViewById(R.id.btn_scancode);
        confirm = item.findViewById(R.id.btn_signin);
        progressSync = new ProgressSync(context);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleQRScan();
                //Toast.makeText(context, "Function Disabled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Signin() {
        String nin_no = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            nin_no = Objects.requireNonNull(nin.getText()).toString();
        }
        int err = 0;
        if (!isFieldValid(nin_no)) {
            err++;
            nin.setError("Invalid NationalID or Passport");
        }
        if (err == 0) {
            //user = new User(null, null, null, mail, null, pass, null, mail, null);
            UserWorker account = new UserWorker(null, null, nin_no, null, null);
            Authentication(account);
        }
    }

    private void googleQRScan(){
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        //bundle.putParcelable("barcode", barcode);
        FragmentManager manager = getParentFragmentManager();
        BarcodeFragment selected = BarcodeFragment.getInstance();
        selected.setTargetFragment(Signin.this,TARGET_FRAGMENT_REQUEST_CODE);
        //selected.setArguments(bundle);
        selected.show(manager, "barcode");
    }
    private void ScanQrCode(){

        try{
            Intent intent=new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE","QR_CODE_MODE");
            startActivityForResult(intent,0);
        }catch(Exception e){
            Toast.makeText(getContext(),"Error occured while scanning Code",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void Authentication(UserWorker account) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody nin = RequestBody.create(MultipartBody.FORM, account.getNin());
        Call<Feedback> call = apiInterface.Authenticate(nin);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        //createFirebase(account.getEmail(),account.getPassword());
                        getAccount(account.getNin());
                        progressSync.hideDialog();
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressSync.hideDialog();
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
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


    public void getAccount(String nin){
        progressSync.showDialog();
        apiInterface=RetroClient.getClient().create(ApiInterface.class);
        Call<UserWorkerSingle> call=apiInterface.getAccountCon(nin);
        call.enqueue(new Callback<UserWorkerSingle>() {
            @Override
            public void onResponse(Call<UserWorkerSingle> call, Response<UserWorkerSingle> response) {
                if(response.isSuccessful()){
                    UserWorkerSingle accountSingle=response.body();
                    if(accountSingle.getUserWorker().getNin() != null){
                        Log.d("NIN",accountSingle.getUserWorker().getNin());
                        session.createLoginSession(accountSingle.getUserWorker().getId(),accountSingle.getUserWorker().getUserid(),accountSingle.getUserWorker().getNin(),accountSingle.getUserWorker().getNames(),accountSingle.getUserWorker().getPassport(),accountSingle.getUserWorker().getAddress(),accountSingle.getUserWorker().getEmail(),accountSingle.getUserWorker().getPhone(),accountSingle.getUserWorker().getGender(),accountSingle.getUserWorker().getMarital(),accountSingle.getUserWorker().getNationality(),accountSingle.getUserWorker().getDob(),accountSingle.getUserWorker().getPob(),accountSingle.getUserWorker().getVillage(),accountSingle.getUserWorker().getParish(),accountSingle.getUserWorker().getSubcounty(),accountSingle.getUserWorker().getDistrict(),accountSingle.getUserWorker().getJobtype(),accountSingle.getUserWorker().getLcompany(),accountSingle.getUserWorker().getFcompany(),accountSingle.getUserWorker().getLocation(),accountSingle.getUserWorker().getLati(),accountSingle.getUserWorker().getLongi(),accountSingle.getUserWorker().getPtpic(),accountSingle.getUserWorker().getNpic(),accountSingle.getUserWorker().getPic(),accountSingle.getUserWorker().getFpic(),accountSingle.getUserWorker().getCode(),accountSingle.getUserWorker().getBankName(),accountSingle.getUserWorker().getBankAccount(),accountSingle.getUserWorker().getKinName(),accountSingle.getUserWorker().getKinPhone(),accountSingle.getUserWorker().getExPhone());
                        gotoMain(accountSingle.getUserWorker());
                        progressSync.hideDialog();
                    }else {
                        EmptyList(getContext(),getLayoutInflater());
                    }
                }else{
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
                }
            }

            @Override
            public void onFailure(Call<UserWorkerSingle> call, Throwable t) {
                //progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }
    public void gotoMain(UserWorker account) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        //intent.putExtra("account", account);
        startActivity(intent);
    }


    public void createFirebase(String email,String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Sign In Successfull",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getActivity(), MainActivity.class));
                    //finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode != RESULT_OK){
                /**
                String contents =data.getStringExtra("SCAN_RESULT");
                Log.d("Scanned Userid",contents);
                UserWorker account = new UserWorker(null, null, contents, null, null);
                Authentication(account);
                 */
                Toast.makeText(getContext(),"Action Cancelled",Toast.LENGTH_SHORT).show();
                return;
            }if(requestCode == TARGET_FRAGMENT_REQUEST_CODE){
                String code=data.getStringExtra(EXTRA_CODE);
            UserWorker account = new UserWorker(null, null, code, null, null);
            Authentication(account);
                //Toast.makeText(getContext(),code,Toast.LENGTH_SHORT).show();
        }
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


}
