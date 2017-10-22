package com.store.pawan.pawanstore.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.store.pawan.pawanstore.fragment.LendAccountFragment;
import com.store.pawan.pawanstore.fragment.PayAccountFragment;

/**
 * Created by cas on 26-07-2017.
 */

public class AccountPagerAdapter extends FragmentPagerAdapter {


    private static int PAGES=2;

    public AccountPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return LendAccountFragment.newInstance();
            case 1:
                return PayAccountFragment.newInstance();
            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return PAGES;
    }
}
