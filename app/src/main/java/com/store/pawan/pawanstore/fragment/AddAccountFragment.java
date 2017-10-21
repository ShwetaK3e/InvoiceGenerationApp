package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.store.pawan.pawanstore.Adapter.AccountPagerAdapter;
import com.store.pawan.pawanstore.Adapter.FinaltemAdapter;
import com.store.pawan.pawanstore.Adapter.ItemAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreEditTextBold;
import com.store.pawan.pawanstore.CustomWidgets.PStoreEditTextItalic;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;


public class AddAccountFragment extends Fragment {



    ViewPager accounts_pager;
    AccountPagerAdapter adapter;
    TabLayout accounts_tab;


    PStoreDataBase dataBase;




    public static AddAccountFragment newInstance() {
        AddAccountFragment fragment = new AddAccountFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accounts_management, container, false);

        dataBase=PStoreDataBase.getPStoreDatabaseInstance(getContext());
        accounts_pager=view.findViewById(R.id.accounts_viewPager);
        adapter=new AccountPagerAdapter(getFragmentManager());
        accounts_pager.setAdapter(adapter);
        accounts_tab=view.findViewById(R.id.accounts_tab);
        accounts_tab.setupWithViewPager(accounts_pager);
        createTabIcons();

        return  view;
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_view_title, null);
        tabOne.setText("LEND");
        tabOne.setTextColor(getResources().getColor(R.color.activeColor));
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list, 0, 0);
        accounts_tab.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_view_title, null);
        tabTwo.setText("PAY");
        tabTwo.setTextColor(getResources().getColor(R.color.red));
       // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list, 0, 0);
        accounts_tab.getTabAt(1).setCustomView(tabTwo);

    }















    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ACCOUNTS");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("ABCD","called destroy");
        dataBase.destroyInstance();
        accounts_tab.removeAllTabs();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("ABCD","called stop");
        dataBase.destroyInstance();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i("ABCD","called pause");

    }






}
