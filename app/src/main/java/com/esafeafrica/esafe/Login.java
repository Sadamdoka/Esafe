package com.esafeafrica.esafe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.esafeafrica.esafe.Adaptors.mainFragmentAdaptor;
import com.esafeafrica.esafe.Fragments.SignUpChoice;
import com.esafeafrica.esafe.Fragments.Signin;
import com.esafeafrica.esafe.Fragments.Signup;
import com.esafeafrica.esafe.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitViews();
    }

    public void InitViews() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        mainFragmentAdaptor pagerAdapter = new mainFragmentAdaptor(getSupportFragmentManager());
        pagerAdapter.addFragmet(new Signin());
        pagerAdapter.addFragmet(new SignUpChoice());
        viewPager.setAdapter(pagerAdapter);
    }

}
