package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.store.pawan.pawanstore.Adapter.AccountAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.Constants;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PayAccountFragment extends Fragment {





    ImageButton add_account;

    RecyclerView accounts_list;
    AccountAdapter adapter;
    private PStoreDataBase dataBase;
    List<Account> payAccounts=new ArrayList<>();

    PStoreTextViewItalic no_acc_text;



    //Add Dialog
    Dialog add_account_dialog;




    public static List<EntryItem> items=new LinkedList<>();

    public static PayAccountFragment newInstance() {
        PayAccountFragment fragment = new PayAccountFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accounts_management, container, false);

        accounts_list=view.findViewById(R.id.account_list);
        accounts_list.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter=new AccountAdapter(getContext(),payAccounts,pos->{
            showAddItemDialog(payAccounts.get(pos));
        });
        accounts_list.setAdapter(adapter);
        getAllPayAccounts();


        no_acc_text=view.findViewById(R.id.no_acc_text);

        add_account=view.findViewById(R.id.add_account);
        add_account.setOnClickListener(click->{
            showAddItemDialog(null);
        });


        dataBase=PStoreDataBase.getPStoreDatabaseInstance(getActivity().getApplicationContext());




        return  view;
    }






    boolean select_instrument_show=true;
    int item_no=0;

    boolean inc=false,dec=false;

    void showAddItemDialog(Account acc){
        add_account_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_account_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_account_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_account_dialog.setContentView(R.layout.purchase_entry_dialog);

        Account account;

        final EntryHolder holder=new EntryHolder(add_account_dialog);


        Observable<CharSequence> name_obv= RxTextView.textChanges(holder.account_name);
        Observable<CharSequence> tot_amnt_obv= RxTextView.textChanges(holder.total_amnt);
        Observable<CharSequence> new_amnt_obv= RxTextView.textChanges(holder.update_amnt);

        new_amnt_obv.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        if (charSequence.length() != 0 && acc!=null) {
                            if (inc) {
                                    acc.setAmount(acc.getAmount() + Float.parseFloat(charSequence.toString()));
                                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()));
                            } else {
                                   acc.setAmount(acc.getAmount() - Float.parseFloat(charSequence.toString()));
                                   acc.setPaid_amount(acc.getPaid_amount() + Float.parseFloat(charSequence.toString()));
                                   holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()));

                            }

                        }
                    }
                });


        if(acc!=null){
            holder.account_name.setText(acc.getName());
            holder.total_amnt.setText(String.valueOf(acc.getAmount()));
            holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
            holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()));
        }


       Observable.combineLatest(name_obv,tot_amnt_obv,new_amnt_obv,(charSequence, charSequence2, charSequence3) -> {
           if(charSequence.length()!=0 && charSequence2.length()!=0 && Float.parseFloat(charSequence2.toString())>0.0f){
               if(charSequence3.length()!=0 && !inc && !dec){
                   return false;
               }else{
                   return true;
               }
           }return false;
       }).subscribe(aBoolean -> {
           holder.add.setEnabled(true);
           holder.add.setTextColor(getResources().getColor(R.color.colorPrimary));
       });

       holder.cancel.setOnClickListener(view -> {
           add_account_dialog.dismiss();
       }
       );

       holder.add.setOnClickListener(view -> {

           int id=payAccounts.size();
           String name=holder.account_name.getText().toString();
           int accountMode=Constants.AccountMode.PAY.getAccountMode();
           float paid_amnt= (holder.paid_amnt.getText().toString().length()!=0) ? Float.parseFloat(holder.paid_amnt.getText().toString()):0.0f;
           float total_amnt=Float.parseFloat(holder.total_amnt.getText().toString());
           Account account =new Account(id,name,accountMode,total_amnt,paid_amnt);
           addPayAccount(account);
           add_account_dialog.dismiss();


       });



       holder.inc_amnt_btn.setOnClickListener(click->{
           if(inc){
               inc=false;
               holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green);
           }else{
               inc=true;
               dec=false;
               holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
               holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
           }
       });

        holder.dec_amnt_btn.setOnClickListener(click->{
            if(dec){
                dec=false;
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red);
            }else{
                dec=true;
                inc=false;
                holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
            }
        });

       add_account_dialog.setOnDismissListener(view->{

       });



       add_account_dialog.show();
       add_account_dialog.setCancelable(true);
    }




    class EntryHolder{


        EditText account_name;
        EditText total_amnt;
        EditText paid_amnt;
        EditText remaining_amnt;
        EditText update_amnt;
        LinearLayout inc_amnt;
        LinearLayout dec_amnt;
        ImageButton inc_amnt_btn;
        ImageButton dec_amnt_btn;
        Button add;
        Button cancel;



        EntryHolder(Dialog dialog){

            account_name=dialog.findViewById(R.id.account_name);
            total_amnt=dialog.findViewById(R.id.net_amnt);
            paid_amnt=dialog.findViewById(R.id.ret_amnt);
            remaining_amnt=dialog.findViewById(R.id.remain_amnt);
            update_amnt= dialog.findViewById(R.id.new_amnt);
            inc_amnt=dialog.findViewById(R.id.inc_amnt);
            dec_amnt=dialog.findViewById(R.id.dec_amnt);
            inc_amnt_btn=dialog.findViewById(R.id.inc_amnt_btn);
            dec_amnt_btn=dialog.findViewById(R.id.dec_amnt_bnt);
            add=dialog.findViewById(R.id.add);
            add.setEnabled(false);
            cancel=dialog.findViewById(R.id.cancel);

        }

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ACCOUNTS");
    }


    void getAllPayAccounts(){

        dataBase.AccountDao().getAllAccounts(Constants.AccountMode.PAY.getAccountMode()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Account> accounts) {

                        payAccounts=accounts;
                        adapter.notifyDataSetChanged();

                    }
                });

    }

    void addPayAccount(Account account){
        dataBase.AccountDao().addAccount(account).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Toast.makeText(getActivity(), "Account has been Added", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void updatePayAccount(Account account){



    }
}
