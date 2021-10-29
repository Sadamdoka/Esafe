package com.esafeafrica.esafe.Adaptors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class mainFragmentAdaptor extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public mainFragmentAdaptor(FragmentManager fm) {
        super(fm);
    }

    public mainFragmentAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragmet(Fragment fragment) {
        fragmentList.add(fragment);

    }
}
