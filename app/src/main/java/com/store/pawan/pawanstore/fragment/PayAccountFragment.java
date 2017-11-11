package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.store.pawan.pawanstore.Adapter.AccountAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.DAO.AccountViewModel;
import com.store.pawan.pawanstore.DAO.Injection;
import com.store.pawan.pawanstore.DAO.AccountViewModelFactory;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.Constants;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;
import com.store.pawan.pawanstore.entities.Account;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;


public class PayAccountFragment extends Fragment {

    private static String TAG=PayAccountFragment.class.getSimpleName();


    ImageButton add_account;

    RecyclerView accounts_list;
    AccountAdapter adapter;
    private PStoreDataBase dataBase;
    List<Account> payAccounts=new ArrayList<>();

    PStoreTextViewItalic no_acc_text;



    //Add Dialog
    Dialog add_account_dialog;




    private AccountViewModelFactory accountViewModelFactory;
    private AccountViewModel accountViewModel;
    private final CompositeDisposable disposable=new CompositeDisposable();

    private PublishSubject<Integer> listCount;




    public static PayAccountFragment newInstance() {
        PayAccountFragment fragment = new PayAccountFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.pager_frag_account_list, container, false);
        dataBase=PStoreDataBase.getPStoreDatabaseInstance(getActivity().getApplicationContext());

        accounts_list=view.findViewById(R.id.account_list);
        accounts_list.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter=new AccountAdapter(getActivity(), payAccounts, (pos,action) -> {
            if(action==null) {
                showAddItemDialog(payAccounts.get(pos));
            }else if(action.equalsIgnoreCase("delete")){
                deletePayAccount(payAccounts.get(pos));
            }
        });
        accounts_list.setAdapter(adapter);


        listCount=PublishSubject.create();
        listCount.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                if(integer!=0){
                    no_acc_text.setVisibility(View.INVISIBLE);
                }else{
                    no_acc_text.setVisibility(View.VISIBLE);
                }
            }
        });


        no_acc_text=view.findViewById(R.id.no_acc_text);
        Observable<Integer> account_list_size_obv= Observable.fromCallable(() -> payAccounts.size());
        account_list_size_obv.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if(integer!=0){
                            no_acc_text.setVisibility(View.INVISIBLE);
                        }
                    }
                });




        add_account=view.findViewById(R.id.add_account);
        add_account.setOnClickListener(click->{
            showAddItemDialog(null);
        });

        accountViewModelFactory = Injection.provideViewModelFactory(getActivity());
        accountViewModel= ViewModelProviders.of(this, accountViewModelFactory).get(AccountViewModel.class);

        return  view;
    }







    private boolean inc=false,dec=false;


    void showAddItemDialog(Account acc){
        add_account_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_account_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_account_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_account_dialog.setContentView(R.layout.account_entry_dialog);



        final PayAccountFragment.EntryHolder holder=new PayAccountFragment.EntryHolder(add_account_dialog);


        if(acc!=null){
            holder.paid_amnt.setEnabled(false);
            holder.remaining_amnt.setEnabled(false);
        }

        Observable<CharSequence> name_obv= RxTextView.textChanges(holder.account_name);
        Observable<CharSequence> tot_amnt_obv= RxTextView.textChanges(holder.total_amnt);
        Observable<CharSequence> new_amnt_obv= RxTextView.textChanges(holder.update_amnt);


        new_amnt_obv.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG+"update", "this is it"+" "+e.getMessage());
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {

                        if (charSequence.length() != 0 && acc!=null) {
                            if (inc) {
                                holder.total_amnt.setText(String.valueOf(acc.getAmount() + Float.parseFloat(charSequence.toString())));
                                holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()+Float.parseFloat(charSequence.toString())));
                                holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                            } else if (dec) {
                                holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                                holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount() + Float.parseFloat(charSequence.toString())));
                                holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-(acc.getPaid_amount()+Float.parseFloat(charSequence.toString()))));
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
                if(acc!=null){
                    if(charSequence3.length()!=0 && (inc || dec)) {
                        return true;
                    }
                    else{
                        return false;
                    }
                }else{
                    return true;
                }
            }return false;
        }).subscribe(aBoolean -> {
            holder.add.setEnabled(aBoolean);
            holder.add.setTextColor(aBoolean?getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.light_grey));
        });

        holder.cancel.setOnClickListener(view -> {
                    add_account_dialog.dismiss();
                    inc=false;
                    dec=false;
                }
        );

        holder.add.setOnClickListener(view -> {
            int id;
            if(acc==null) {
                id = payAccounts.size()+1;
            }else{
                id=acc.getId();
            }
            String name=holder.account_name.getText().toString();
            int accountMode= Constants.AccountMode.PAY.getAccountMode();
            float paid_amnt= (holder.paid_amnt.getText().toString().length()!=0) ? Float.parseFloat(holder.paid_amnt.getText().toString()):0.0f;
            float total_amnt=Float.parseFloat(holder.total_amnt.getText().toString());

            if(acc!=null) {
                acc.setAmount(Float.parseFloat(holder.total_amnt.getText().toString()));
                acc.setPaid_amount((holder.paid_amnt.getText().toString().length()!=0) ? Float.parseFloat(holder.paid_amnt.getText().toString()):0.0f);
                addPayAccount(acc);
            }else{
                Account account = new Account(name, accountMode, total_amnt, paid_amnt);
                addPayAccount(account);
            }

            inc=false;
            dec=false;
            add_account_dialog.dismiss();

        });



        holder.inc_amnt_btn.setOnClickListener(click->{
            if(inc){
                inc=false;
                holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);

            }else{
                inc=true;
                dec=false;
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
                holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.total_amnt.setText(String.valueOf(acc.getAmount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                }
            }
        });

        holder.dec_amnt_btn.setOnClickListener(click->{
            if(dec){
                dec=false;
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
            }else{
                dec=true;
                inc=false;
                holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - (acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString()))));
                }
            }
        });

        add_account_dialog.setOnDismissListener(view->{
            getAllPayAccounts();
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
    public void onStart() {
        super.onStart();
        getAllPayAccounts();
    }


    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }

    void getAllPayAccounts(){
        disposable.add(accountViewModel.getAllAccounts(Constants.AccountMode.PAY.getAccountMode()).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(accounts -> {
                            payAccounts.clear();
                            payAccounts.addAll(accounts);
                            listCount.onNext(payAccounts.size());
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> Log.e(TAG, "exception getting accounts"))
        );


    }

    void addPayAccount(Account account){

        disposable.add(accountViewModel.updateUser(account).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Account Added", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Add Account", Toast.LENGTH_SHORT).show();
                })
        );


    }

    void deletePayAccount(Account account){

        disposable.add(accountViewModel.deleteUser(account).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Account Deleted", Toast.LENGTH_SHORT).show();
                    payAccounts.remove(account);
                    adapter.notifyDataSetChanged();
                    listCount.onNext(payAccounts.size());
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Delete Account", Toast.LENGTH_SHORT).show();
                })
        );

    }



}
