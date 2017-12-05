package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.store.pawan.pawanstore.Adapter.AccountAdapter;
import com.store.pawan.pawanstore.Adapter.AccountHistoryAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.DAO.AccountViewModel;
import com.store.pawan.pawanstore.DAO.DetailsViewModelFactory;
import com.store.pawan.pawanstore.DAO.Injection;
import com.store.pawan.pawanstore.DAO.AccountViewModelFactory;
import com.store.pawan.pawanstore.DAO.PaymentDetailsViewModel;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.Constants;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.entities.PaymentDetails;
import com.store.pawan.pawanstore.model.EntryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
    AccountAdapter accountAdapter;
    private PStoreDataBase dataBase;
    List<Account> payAccounts=new ArrayList<>();

    PStoreTextViewItalic no_acc_text;



    //Add Dialog
    Dialog add_account_dialog;
    Dialog add_payment_dialog;
    Dialog account_history_dialog;

    //history
    List<PaymentDetails> payAccountHistory=new ArrayList<>();
    AccountHistoryAdapter historyAdapter;





    private AccountViewModelFactory accountViewModelFactory;
    private AccountViewModel accountViewModel;
    private DetailsViewModelFactory detailsViewModelFactory ;
    private PaymentDetailsViewModel detailsViewModel;

    private final CompositeDisposable disposable=new CompositeDisposable();

    private PublishSubject<Integer> listCount;




    public static List<EntryItem> items=new LinkedList<>();

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
        accountAdapter =new AccountAdapter(getActivity(), payAccounts, (pos, action) -> {
            if(action==null) {
                showAddPaymentDialog(payAccounts.get(pos));
            }else if(action.equalsIgnoreCase("delete")){
                new AlertDialog.Builder(getActivity(),android.R.style.Theme_Material_Dialog_Alert)
                        .setTitle("New Bill")
                        .setMessage("Are you sure you want to delete the current bill?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            deletePayAccount(payAccounts.get(pos));
                        })
                        .setNegativeButton("No",null)
                        .show();            }else if(action.equalsIgnoreCase("history")){
                showAccountHistoryDialog(payAccounts.get(pos));
            }
        });
        accounts_list.setAdapter(accountAdapter);

        no_acc_text=view.findViewById(R.id.no_acc_text);
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
            showAddAccountDialog();
        });

        accountViewModelFactory = Injection.provideAccountViewModelFactory(getActivity());
        accountViewModel= ViewModelProviders.of(this, accountViewModelFactory).get(AccountViewModel.class);
        detailsViewModelFactory = Injection.provideDetailsViewModelFactory(getActivity());
        detailsViewModel= ViewModelProviders.of(this, detailsViewModelFactory).get(PaymentDetailsViewModel.class);

        return  view;
    }







    private boolean paid=false,remaining=false;

    void showAddAccountDialog(){
        add_account_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_account_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_account_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_account_dialog.setContentView(R.layout.account_entry_dialog);

        final PayAccountFragment.AccountEntryHolder holder=new PayAccountFragment.AccountEntryHolder(add_account_dialog);

        Observable<CharSequence> name_obv= RxTextView.textChanges(holder.account_name);
        Observable<CharSequence> tot_amnt_obv= RxTextView.textChanges(holder.total_amnt);

        Observable.combineLatest(name_obv,tot_amnt_obv,(charSequence, charSequence2) -> {
            if(charSequence.length()!=0 && charSequence2.length()!=0 && Float.parseFloat(charSequence2.toString())>0.0f)
                return true;

            return false;


        }).subscribe(aBoolean -> {
            holder.add.setEnabled(aBoolean);
            holder.add.setTextColor(aBoolean?getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.light_grey));
        });

        holder.cancel.setOnClickListener(view -> {
                    add_account_dialog.dismiss();
                    paid=false;
                    remaining=false;
                }
        );

        holder.add.setOnClickListener(view -> {


            String name=holder.account_name.getText().toString();
            int accountMode= Constants.AccountMode.PAY.getAccountMode();
            float total_amnt=Float.parseFloat(holder.total_amnt.getText().toString());
            Account account = new Account(name, accountMode, total_amnt, 0.0f);
            addPayAccount(account);
            add_account_dialog.dismiss();

        });

        add_account_dialog.setOnDismissListener(view->{
            getAllPayAccounts();
        });



        add_account_dialog.show();
        add_account_dialog.setCancelable(true);


    }


    void showAddPaymentDialog(Account acc){
        add_payment_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_payment_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_payment_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_payment_dialog.setContentView(R.layout.payment_entry_dialog);



        final PayAccountFragment.PaymentEntryHolder holder=new PayAccountFragment.PaymentEntryHolder(add_payment_dialog);


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

                        if (charSequence.length() != 0 ) {
                            if (paid) {
                                holder.update.setEnabled(true);
                                holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                                holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                                holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount() + Float.parseFloat(charSequence.toString())));
                                holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-(acc.getPaid_amount()+Float.parseFloat(charSequence.toString()))));
                            } else if (remaining) {
                                holder.update.setEnabled(true);
                                holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                                holder.total_amnt.setText(String.valueOf(acc.getAmount() + Float.parseFloat(charSequence.toString())));
                                holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()+Float.parseFloat(charSequence.toString())));
                                holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                            }

                        }else{
                            holder.update.setEnabled(false);
                            holder.update.setTextColor(getResources().getColor(R.color.light_grey));
                            holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                            holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()));
                            holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                        }
                    }
                });


        holder.account_name.setText(acc.getName());
        holder.total_amnt.setText(String.valueOf(acc.getAmount()));
        holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
        holder.remaining_amnt.setText(String.valueOf(acc.getAmount()-acc.getPaid_amount()));




        holder.cancel.setOnClickListener(view -> {
                    add_payment_dialog.dismiss();
                    paid=false;
                    remaining=false;
                }
        );

        holder.update.setOnClickListener(view -> {
            int id=acc.getId();
            float paid_amnt= (holder.paid_amnt.getText().toString().length()!=0) ? Float.parseFloat(holder.paid_amnt.getText().toString()):0.0f;
            float total_amnt=Float.parseFloat(holder.total_amnt.getText().toString());
            float update_amnt=Float.parseFloat(holder.update_amnt.getText().toString());
            String desc=holder.payment_desc.getText().toString();
            int amntStatus=paid?Constants.PaymentMode.PAID.getPayment_mode():Constants.PaymentMode.REMAINING.getPayment_mode();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

            acc.setAmount(total_amnt);
            acc.setPaid_amount(paid_amnt);
            updatePayAccount(acc);

            PaymentDetails details=new PaymentDetails(id,sdf.format(new Date()),amntStatus,update_amnt,desc);
            addPayPaymentDetails(details);


            paid=false;
            remaining=false;
            add_payment_dialog.dismiss();

        });



        holder.paid_amnt_btn.setOnClickListener(click->{
            if(paid){
                paid=false;
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);

            }else{
                paid=true;
                remaining=false;
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.update.setEnabled(true);
                    holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - (acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString()))));
                }
            }
        });

        holder.paid_amnt_layout.setOnClickListener(click->{
            if(paid){
                paid=false;
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);

            }else{
                paid=true;
                remaining=false;
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.update.setEnabled(true);
                    holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.total_amnt.setText(String.valueOf(acc.getAmount()));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - (acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString()))));
                }
            }
        });
        holder.rem_amnt_btn.setOnClickListener(click->{
            if(remaining){
                remaining=false;
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
            }else{
                remaining=true;
                paid=false;
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.update.setEnabled(true);
                    holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.total_amnt.setText(String.valueOf(acc.getAmount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                }
            }
        });

        holder.rem_amnt_layout.setOnClickListener(click->{
            if(remaining){
                remaining=false;
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
            }else{
                remaining=true;
                paid=false;
                holder.paid_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
                holder.rem_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red);
                if(holder.update_amnt.getText().length()!=0) {
                    holder.update.setEnabled(true);
                    holder.update.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.total_amnt.setText(String.valueOf(acc.getAmount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                    holder.paid_amnt.setText(String.valueOf(acc.getPaid_amount()));
                    holder.remaining_amnt.setText(String.valueOf(acc.getAmount() - acc.getPaid_amount() + Float.parseFloat(holder.update_amnt.getText().toString())));
                }
            }
        });


        add_payment_dialog.setOnDismissListener(view->{
            getAllPayAccounts();
        });



        add_payment_dialog.show();
        add_payment_dialog.setCancelable(true);
    }




    void showAccountHistoryDialog(Account account){
        account_history_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        account_history_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        account_history_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        account_history_dialog.setContentView(R.layout.acount_history);

        final PayAccountFragment.AccountHistoryHolder holder=new PayAccountFragment.AccountHistoryHolder(account_history_dialog);

        holder.account_name.setText(account.getName().toUpperCase());
        holder.total_amnt.setText("Rs. "+String.valueOf(account.getAmount()));
        holder.paid_amnt.setText("Rs. "+String.valueOf(account.getPaid_amount()));
        holder.remaining_amnt.setText("Rs. "+String.valueOf(account.getAmount()-account.getPaid_amount()));

        historyAdapter=new AccountHistoryAdapter(getContext(),payAccountHistory);
        getAllPaymentDetails(account.getId());

        holder.history_list.setLayoutManager(new GridLayoutManager(getContext(),1));
        holder.history_list.setAdapter(historyAdapter);



        account_history_dialog.show();
        account_history_dialog.setCancelable(true);


    }



    class PaymentEntryHolder{


        EditText account_name;
        EditText total_amnt;
        EditText paid_amnt;
        EditText remaining_amnt;
        EditText update_amnt;
        LinearLayout paid_amnt_layout;
        LinearLayout rem_amnt_layout;
        ImageButton paid_amnt_btn;
        ImageButton rem_amnt_btn;
        Button update;
        Button cancel;
        EditText payment_desc;



        PaymentEntryHolder(Dialog dialog){

            account_name=dialog.findViewById(R.id.account_name);
            total_amnt=dialog.findViewById(R.id.net_amnt);
            paid_amnt=dialog.findViewById(R.id.ret_amnt);
            remaining_amnt=dialog.findViewById(R.id.remain_amnt);
            update_amnt= dialog.findViewById(R.id.new_amnt);
            paid_amnt_layout=dialog.findViewById(R.id.paid_amnt_layout);
            rem_amnt_layout=dialog.findViewById(R.id.rem_amnt_layout);
            paid_amnt_btn=dialog.findViewById(R.id.paid_amnt_btn);
            rem_amnt_btn=dialog.findViewById(R.id.rem_amnt_btn);
            update=dialog.findViewById(R.id.add);
            update.setEnabled(false);
            cancel=dialog.findViewById(R.id.cancel);
            payment_desc=dialog.findViewById(R.id.new_amnt_desc);

        }

    }




    class AccountEntryHolder{


        EditText account_name;
        EditText total_amnt;
        Button add;
        Button cancel;



        AccountEntryHolder(Dialog dialog){

            account_name=dialog.findViewById(R.id.account_name);
            total_amnt=dialog.findViewById(R.id.net_amnt);
            add=dialog.findViewById(R.id.add);
            add.setEnabled(false);
            cancel=dialog.findViewById(R.id.cancel);

        }

    }


    class AccountHistoryHolder{


        TextView account_name;
        TextView total_amnt;
        RecyclerView history_list;
        TextView paid_amnt;
        TextView remaining_amnt;



        AccountHistoryHolder(Dialog dialog){

            total_amnt=dialog.findViewById(R.id.total_amnt);
            account_name=dialog.findViewById(R.id.account_name);

            history_list=dialog.findViewById(R.id.history_list);

            paid_amnt=dialog.findViewById(R.id.paid);
            remaining_amnt=dialog.findViewById(R.id.remaining);

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"called start");
        getAllPayAccounts();
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"called pause");
        disposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"called destroy");
        disposable.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"called stop");
        disposable.clear();
    }

    void getAllPayAccounts(){
        disposable.add(accountViewModel.getAllAccounts(Constants.AccountMode.PAY.getAccountMode()).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(accounts -> {
                            payAccounts.clear();
                            payAccounts.addAll(accounts);
                            listCount.onNext(payAccounts.size());
                            accountAdapter.notifyDataSetChanged();
                        },
                        throwable -> Log.e(TAG, "exception getting accounts"))
        );


    }


    void getAllPaymentDetails(int accountId){
        disposable.add(detailsViewModel.getAllDetails(accountId).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(payments -> {
                            payAccountHistory.clear();
                            payAccountHistory.addAll(payments);

                            if(historyAdapter!=null) {

                                historyAdapter.notifyDataSetChanged();
                            }

                        },
                        throwable -> Log.e(TAG, "exception getting accounts"))
        );


    }


    void addPayAccount(Account account){

        disposable.add(accountViewModel.addAccount(account).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Account Added", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Add Account", Toast.LENGTH_SHORT).show();
                })
        );


    }

    void updatePayAccount(Account account){

        disposable.add(accountViewModel.updateAccount(account).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Account Added", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Add Account", Toast.LENGTH_SHORT).show();
                })
        );


    }

    void addPayPaymentDetails(PaymentDetails paymentDetails){

        disposable.add(detailsViewModel.updatePayments(paymentDetails).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Payment Added", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Add Payment", Toast.LENGTH_SHORT).show();
                })
        );


    }

    void deletePayAccount(Account account){

        disposable.add(accountViewModel.deleteAccount(account).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Toast.makeText(getActivity(), " Account Deleted", Toast.LENGTH_SHORT).show();
                    payAccounts.remove(account);
                    accountAdapter.notifyDataSetChanged();
                    listCount.onNext(payAccounts.size());
                }, throwable -> {
                    Toast.makeText(getActivity(), "Failed to Delete Account", Toast.LENGTH_SHORT).show();
                })
        );

    }



}
