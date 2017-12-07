package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.store.pawan.pawanstore.Adapter.FinaltemAdapter;
import com.store.pawan.pawanstore.Adapter.ItemAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreEditTextBold;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.Constants;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.subjects.PublishSubject;


public class AddItemFragment extends Fragment {


    LinearLayout gst_1,gst_2,gst_3,gst_4,gst_5;
    TextView gst_perc_1,gst_perc_2,gst_perc_3,gst_perc_4,gst_perc_5;
    EditText rate_1,rate_2,rate_3,rate_4,rate_5;
    ImageButton dec_amnt_bnt_1,dec_amnt_bnt_2,dec_amnt_bnt_3,dec_amnt_bnt_4,dec_amnt_bnt_5;
    ImageButton inc_amnt_bnt_1,inc_amnt_bnt_2,inc_amnt_bnt_3,inc_amnt_bnt_4,inc_amnt_bnt_5;
    EditText count_1,count_2,count_3,count_4,count_5;
    EditText gst_amnt_1,gst_amnt_2,gst_amnt_3,gst_amnt_4,gst_amnt_5;
    EditText total_amnt_1,total_amnt_2,total_amnt_3,total_amnt_4,total_amnt_5;
    EditText gst_amnt_tot;
    EditText total_amnt;
    TextView total;




    public static List<EntryItem> items=new LinkedList<>();
    private PublishSubject<Integer> listCount;

    public static AddItemFragment newInstance() {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.new_bill_record_layout, container, false);

        gst_1=view.findViewById(R.id.gst_1);
        gst_2=view.findViewById(R.id.gst_2);
        gst_3=view.findViewById(R.id.gst_3);
        gst_4=view.findViewById(R.id.gst_4);
        gst_5=view.findViewById(R.id.gst_5);

        gst_perc_1=view.findViewById(R.id.gst_perc_1);
        gst_perc_2=view.findViewById(R.id.gst_perc_2);
        gst_perc_3=view.findViewById(R.id.gst_perc_3);
        gst_perc_4=view.findViewById(R.id.gst_perc_4);
        gst_perc_5=view.findViewById(R.id.gst_perc_5);

        rate_1=view.findViewById(R.id.rate_1);
        rate_2=view.findViewById(R.id.rate_2);
        rate_3=view.findViewById(R.id.rate_3);
        rate_4=view.findViewById(R.id.rate_4);
        rate_5=view.findViewById(R.id.rate_5);

        dec_amnt_bnt_1=view.findViewById(R.id.dec_amnt_bnt_1);
        dec_amnt_bnt_2=view.findViewById(R.id.dec_amnt_bnt_2);
        dec_amnt_bnt_3=view.findViewById(R.id.dec_amnt_bnt_3);
        dec_amnt_bnt_4=view.findViewById(R.id.dec_amnt_bnt_4);
        dec_amnt_bnt_5=view.findViewById(R.id.dec_amnt_bnt_5);

        inc_amnt_bnt_1=view.findViewById(R.id.inc_amnt_bnt_1);
        inc_amnt_bnt_2=view.findViewById(R.id.inc_amnt_bnt_2);
        inc_amnt_bnt_3=view.findViewById(R.id.inc_amnt_bnt_3);
        inc_amnt_bnt_4=view.findViewById(R.id.inc_amnt_bnt_4);
        inc_amnt_bnt_5=view.findViewById(R.id.inc_amnt_bnt_5);

        count_1=view.findViewById(R.id.count_1);
        count_2=view.findViewById(R.id.count_2);
        count_3=view.findViewById(R.id.count_3);
        count_4=view.findViewById(R.id.count_4);
        count_5=view.findViewById(R.id.count_5);

        gst_amnt_1=view.findViewById(R.id.gst_amnt_1);
        gst_amnt_2=view.findViewById(R.id.gst_amnt_2);
        gst_amnt_3=view.findViewById(R.id.gst_amnt_3);
        gst_amnt_4=view.findViewById(R.id.gst_amnt_4);
        gst_amnt_5=view.findViewById(R.id.gst_amnt_5);

        total_amnt_1=view.findViewById(R.id.total_amnt_1);
        total_amnt_2=view.findViewById(R.id.total_amnt_2);
        total_amnt_3=view.findViewById(R.id.total_amnt_3);
        total_amnt_4=view.findViewById(R.id.total_amnt_4);
        total_amnt_5=view.findViewById(R.id.total_amnt_5);

        gst_amnt_tot=view.findViewById(R.id.gst_amnt_tot);
        total_amnt=view.findViewById(R.id.total_amnt);
        total=view.findViewById(R.id.total);

        Observable<CharSequence> gst_perc_1_obv= RxTextView.textChanges(gst_perc_1);
        Observable<CharSequence> gst_perc_2_obv= RxTextView.textChanges(gst_perc_2);
        Observable<CharSequence> gst_perc_3_obv= RxTextView.textChanges(gst_perc_3);
        Observable<CharSequence> gst_perc_4_obv= RxTextView.textChanges(gst_perc_4);
        Observable<CharSequence> gst_perc_5_obv= RxTextView.textChanges(gst_perc_4);

        Observable<CharSequence> rate_1_obv= RxTextView.textChanges(rate_1);
        Observable<CharSequence> rate_2_obv= RxTextView.textChanges(rate_2);
        Observable<CharSequence> rate_3_obv= RxTextView.textChanges(rate_3);
        Observable<CharSequence> rate_4_obv= RxTextView.textChanges(rate_4);
        Observable<CharSequence> rate_5_obv= RxTextView.textChanges(rate_4);

        Observable<CharSequence> count_1_obv= RxTextView.textChanges(count_1);
        Observable<CharSequence> count_2_obv= RxTextView.textChanges(count_2);
        Observable<CharSequence> count_3_obv= RxTextView.textChanges(count_3);
        Observable<CharSequence> count_4_obv= RxTextView.textChanges(count_4);
        Observable<CharSequence> count_5_obv= RxTextView.textChanges(count_4);

        Observable.combineLatest(gst_perc_1_obv , rate_1_obv, count_1_obv, (charSequence, charSequence2, charSequence3)->{
                if(charSequence2.length()!=0){
                    return true;
                }return false;
        }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float gst = Integer.parseInt(gst_perc_1.getText().toString())/2.0f;
                float rate=Float.parseFloat(rate_1.getText().toString());
                int qnty= Integer.parseInt(count_1.getText().toString());
                double gst_amount=rate*qnty*gst/100;
                double tot_amount=rate*qnty;
                gst_amnt_1.setText(String.format("%.2f", gst_amount));
                total_amnt_1.setText(String.format("%.2f", (tot_amount-gst_amount*2)));
            }else{
                gst_amnt_1.setText("");
                total_amnt_1.setText("");
            }
        });

        Observable.combineLatest(gst_perc_2_obv , rate_2_obv, count_2_obv, (charSequence, charSequence2, charSequence3)->{
                    if(charSequence2.length()!=0){
                        return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float gst = Integer.parseInt(gst_perc_2.getText().toString())/2.0f;
                float rate=Float.parseFloat(rate_2.getText().toString());
                int qnty= Integer.parseInt(count_2.getText().toString());
                double gst_amount=rate*qnty*gst/100;
                double tot_amount=rate*qnty;
                gst_amnt_2.setText(String.format("%.2f", gst_amount));
                total_amnt_2.setText(String.format("%.2f", (tot_amount-gst_amount*2)));
            }else{
                gst_amnt_2.setText("");
                total_amnt_2.setText("");
            }
        });


        Observable.combineLatest(gst_perc_3_obv , rate_3_obv, count_3_obv, (charSequence, charSequence2, charSequence3)->{
                    if(charSequence2.length()!=0){

                        return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float gst = Integer.parseInt(gst_perc_3.getText().toString())/2.0f;
                float rate=Float.parseFloat(rate_3.getText().toString());
                int qnty= Integer.parseInt(count_3.getText().toString());
                double gst_amount=rate*qnty*gst/100;
                double tot_amount=rate*qnty;
                gst_amnt_3.setText(String.format("%.2f", gst_amount));
                total_amnt_3.setText(String.format("%.2f", (tot_amount-gst_amount*2)));
            }else{
                gst_amnt_3.setText("");
                total_amnt_3.setText("");
            }
        });

        Observable.combineLatest(gst_perc_4_obv , rate_4_obv, count_4_obv, (charSequence, charSequence2, charSequence3)->{
                    if(charSequence2.length()!=0){

                        return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float gst = Integer.parseInt(gst_perc_4.getText().toString())/2.0f;
                float rate=Float.parseFloat(rate_4.getText().toString());
                int qnty= Integer.parseInt(count_4.getText().toString());
                double gst_amount=rate*qnty*gst/100;
                double tot_amount=rate*qnty;
                gst_amnt_4.setText(String.format("%.2f", gst_amount));
                total_amnt_4.setText(String.format("%.2f", (tot_amount-gst_amount*2)));
            }else{
                gst_amnt_4.setText("");
                total_amnt_4.setText("");

            }
        });


        Observable.combineLatest(gst_perc_5_obv , rate_5_obv, count_5_obv, (charSequence, charSequence2, charSequence3)->{
                    if(charSequence2.length()!=0){
                      return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float gst = Integer.parseInt(gst_perc_5.getText().toString())/2.0f;
                float rate=Float.parseFloat(rate_5.getText().toString());
                int qnty= Integer.parseInt(count_5.getText().toString());
                double gst_amount=rate*qnty*gst/100;
                double tot_amount=rate*qnty;
                gst_amnt_5.setText(String.format("%.2f", gst_amount));
                total_amnt_5.setText(String.format("%.2f", (tot_amount-gst_amount*2)));
            }else{
                gst_amnt_5.setText("");
                total_amnt_5.setText("");

            }
        });


        Observable<CharSequence> total_amnt_1_obv= RxTextView.textChanges(total_amnt_1);
        Observable<CharSequence> total_amnt_2_obv= RxTextView.textChanges(total_amnt_2);
        Observable<CharSequence> total_amnt_3_obv= RxTextView.textChanges(total_amnt_3);
        Observable<CharSequence> total_amnt_4_obv= RxTextView.textChanges(total_amnt_4);
        Observable<CharSequence> total_amnt_5_obv= RxTextView.textChanges(total_amnt_4);


        Observable.combineLatest(total_amnt_1_obv , total_amnt_2_obv, total_amnt_3_obv,total_amnt_4_obv,total_amnt_5_obv, (charSequence, charSequence2, charSequence3,charSequence4,charSequence5)->{
                    if(charSequence.length()!=0 || charSequence2.length()!=0||charSequence3.length()!=0||charSequence4.length()!=0||charSequence5.length()!=0){
                        return true;
                    }return false;
        }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float t1=total_amnt_1.getText().length()!=0?Float.parseFloat(total_amnt_1.getText().toString()):0.0f;
                float t2=total_amnt_2.getText().length()!=0?Float.parseFloat(total_amnt_2.getText().toString()):0.0f;
                float t3=total_amnt_3.getText().length()!=0?Float.parseFloat(total_amnt_3.getText().toString()):0.0f;
                float t4=total_amnt_4.getText().length()!=0?Float.parseFloat(total_amnt_4.getText().toString()):0.0f;
                float t5=total_amnt_5.getText().length()!=0?Float.parseFloat(total_amnt_5.getText().toString()):0.0f;
                total_amnt.setText(String.format("%.2f",(t1+t2+t3+t4+t5)));
            }else{
                total_amnt.setText("");
            }
        });




        Observable<CharSequence> gst_amnt_1_obv= RxTextView.textChanges(gst_amnt_1);
        Observable<CharSequence> gst_amnt_2_obv= RxTextView.textChanges(gst_amnt_2);
        Observable<CharSequence> gst_amnt_3_obv= RxTextView.textChanges(gst_amnt_3);
        Observable<CharSequence> gst_amnt_4_obv= RxTextView.textChanges(gst_amnt_4);
        Observable<CharSequence> gst_amnt_5_obv= RxTextView.textChanges(gst_amnt_4);

        Observable.combineLatest(gst_amnt_1_obv, gst_amnt_2_obv, gst_amnt_3_obv,gst_amnt_4_obv,gst_amnt_5_obv, (charSequence, charSequence2, charSequence3,charSequence4,charSequence5)->{
                    if(charSequence.length()!=0 || charSequence2.length()!=0||charSequence3.length()!=0||charSequence4.length()!=0||charSequence5.length()!=0){

                        return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float t1=gst_amnt_1.getText().length()!=0?Float.parseFloat(gst_amnt_1.getText().toString()):0.0f;
                float t2=gst_amnt_2.getText().length()!=0?Float.parseFloat(gst_amnt_2.getText().toString()):0.0f;
                float t3=gst_amnt_3.getText().length()!=0?Float.parseFloat(gst_amnt_3.getText().toString()):0.0f;
                float t4=gst_amnt_4.getText().length()!=0?Float.parseFloat(gst_amnt_4.getText().toString()):0.0f;
                float t5=gst_amnt_5.getText().length()!=0?Float.parseFloat(gst_amnt_5.getText().toString()):0.0f;
                gst_amnt_tot.setText(String.format("%.2f",(t1+t2+t3+t4+t5)));
            }else{
                gst_amnt_tot.setText("");

            }
        });

        Observable<CharSequence> gst_amnt_tot_obv= RxTextView.textChanges(gst_amnt_tot);
        Observable<CharSequence> total_amnt_obv= RxTextView.textChanges(total_amnt);
        Observable.combineLatest(gst_amnt_tot_obv,total_amnt_obv, (charSequence, charSequence2)->{
                    if(charSequence.length()!=0 || charSequence2.length()!=0){
                        return true;
                    }return false;
                }
        ).subscribe(aBoolean -> {
            if(aBoolean){
                float t1=gst_amnt_tot.getText().length()!=0?Float.parseFloat(gst_amnt_tot.getText().toString()):0.0f;
                float t2=total_amnt.getText().length()!=0?Float.parseFloat(total_amnt.getText().toString()):0.0f;
                total.setText("Rs. "+String.format("%.2f",(t1*2+t2)));
            }else{
                total.setText("");
            }
        });


        dec_amnt_bnt_1.setOnClickListener(click->{
            changeCount(count_1,0);
        });
        dec_amnt_bnt_2.setOnClickListener(click->{
            changeCount(count_2,0);
        });
        dec_amnt_bnt_3.setOnClickListener(click->{
            changeCount(count_3,0);
        });
        dec_amnt_bnt_4.setOnClickListener(click->{
            changeCount(count_4,0);
        });
        dec_amnt_bnt_5.setOnClickListener(click->{
            changeCount(count_5,0);
        });
        inc_amnt_bnt_1.setOnClickListener(click->{
            changeCount(count_1,1);
        });
        inc_amnt_bnt_2.setOnClickListener(click->{
            changeCount(count_2,1);
        });
        inc_amnt_bnt_3.setOnClickListener(click->{
            changeCount(count_3,1);
        });
        inc_amnt_bnt_4.setOnClickListener(click->{
            changeCount(count_4,1);
        });
        inc_amnt_bnt_5.setOnClickListener(click->{
            changeCount(count_5,1);
        });

        gst_perc_1.setOnClickListener(click->{
            changeGSTSlab(gst_perc_1);
        });
        gst_perc_2.setOnClickListener(click->{
            changeGSTSlab(gst_perc_2);
        });
        gst_perc_3.setOnClickListener(click->{
            changeGSTSlab(gst_perc_3);
        });
        gst_perc_4.setOnClickListener(click->{
            changeGSTSlab(gst_perc_4);
        });
        gst_perc_5.setOnClickListener(click->{
            changeGSTSlab(gst_perc_5);
        });


        gst_1.setOnClickListener(click->{
            changeGSTSlab(gst_perc_1);
        });
        gst_2.setOnClickListener(click->{
            changeGSTSlab(gst_perc_2);
        });
        gst_3.setOnClickListener(click->{
            changeGSTSlab(gst_perc_3);
        });
        gst_4.setOnClickListener(click->{
            changeGSTSlab(gst_perc_4);
        });
        gst_5.setOnClickListener(click->{
            changeGSTSlab(gst_perc_5);
        });



        total.setOnClickListener(click->{
            if(total.getText().length()!=0) {
                new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert)
                        .setTitle("New Bill")
                        .setMessage("Are you sure you want to delete the current bill?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            refreshBill();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        }).show();
            }
        });

        return  view;
    }





















    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("GENERATE BILL");
    }

    /**

     * @param count
     * @param order dec(0) or inc(1)
     */
    void changeCount(EditText count, int order){
        int c=Integer.parseInt(count.getText().toString());
        if(order==0 && c!=0){
            count.setText(String.valueOf(c-1));
        }else if(order==1){
            count.setText(String.valueOf(c+1));
        }

    }

    void changeGSTSlab(TextView gstSlab){
        int gst=Integer.parseInt(gstSlab.getText().toString());
        int index= Arrays.binarySearch(Constants.GSTSlab,gst);
        index=index<Constants.GSTSlab.length-1?index+1:0;
        gstSlab.setText(String.valueOf(Constants.GSTSlab[index]));
    }

    void refreshBill(){
        rate_1.setText("");
        rate_2.setText("");
        rate_3.setText("");
        rate_4.setText("");
        rate_5.setText("");

        gst_perc_1.setText(String.valueOf(Constants.GSTSlab[3]));
        gst_perc_2.setText(String.valueOf(Constants.GSTSlab[3]));
        gst_perc_3.setText(String.valueOf(Constants.GSTSlab[3]));
        gst_perc_4.setText(String.valueOf(Constants.GSTSlab[3]));
        gst_perc_5.setText(String.valueOf(Constants.GSTSlab[3]));


        count_1.setText("1");
        count_2.setText("1");
        count_3.setText("1");
        count_4.setText("1");
        count_5.setText("1");

        gst_amnt_1.setText("");
        gst_amnt_2.setText("");
        gst_amnt_3.setText("");
        gst_amnt_4.setText("");
        gst_amnt_5.setText("");

        gst_amnt_tot.setText("");
        total_amnt.setText("");
        total.setText("");
    }


}
