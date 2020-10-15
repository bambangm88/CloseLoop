package com.rsah.closeloop.Menu.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rsah.closeloop.Api.ApiService;
import com.rsah.closeloop.Api.Server;
import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Menu.Report.Adapter.AdapterTrxHistori;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.Model.ModelReport;
import com.rsah.closeloop.R;
import com.rsah.closeloop.util.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriTransaksi extends AppCompatActivity {

    private RecyclerView rvHistoriTrx;
    private List<ModelPayment> AllReportList = new ArrayList<>();
    private ApiService API;
    private Context mContext;

    private AdapterTrxHistori Adapter;

    private LinearLayout btnStartDate , btnEndDate ;

    private EditText etStartDate , etEndDate ;

    private Button btnCari ;

    private RelativeLayout rlprogress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_transaksi);

        mContext = this ;
        API = Server.getAPIService();

        btnStartDate = findViewById(R.id.btnStarDate);
        btnEndDate = findViewById(R.id.btnEndDate);

        etStartDate = findViewById(R.id.starDate);
        etEndDate = findViewById(R.id.endDate);

        btnCari = findViewById(R.id.btnCari);

        rlprogress = findViewById(R.id.rlprogress);


        rvHistoriTrx = findViewById(R.id.rvHistoriTrx);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvHistoriTrx.setLayoutManager(mLayoutManager);
        rvHistoriTrx.setItemAnimator(new DefaultItemAnimator());

        Adapter = new  AdapterTrxHistori(this,AllReportList);



        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showDateDialog(mContext,etStartDate);
            }
        });


        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showDateDialog(mContext,etEndDate);
            }
        });


        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();

                if (startDate.equals(Constant.FIELD_KOSONG)){
                    etStartDate.setError(Constant.FIELD_REQUIRED);
                }else if (endDate.equals(Constant.FIELD_KOSONG)){
                    etEndDate.setError(Constant.FIELD_REQUIRED);
                }else{

                    String MerchantID = "54" ;
                    String Page = "0" ;
                    String Max = "100" ;

                    //request get Report
                    getReport(MerchantID,startDate,endDate,Page,Max);

                }


            }
        });



    }


    private void getReport(String merchantId , String startDate , String endDate , String page , String Max ){

        showProgress(true);

        Call<ModelReport> call = API.trxReport(merchantId , startDate , endDate , page , Max);
        call.enqueue(new Callback<ModelReport>() {
            @Override
            public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                if(response.isSuccessful()) {
                    showProgress(false);
                    if (response.body().getContent() != null) {

                        if(!response.body().getContent().isEmpty()){


                            AllReportList.addAll(response.body().getContent());
                            rvHistoriTrx.setAdapter(new AdapterTrxHistori(mContext, AllReportList));
                            Adapter.notifyDataSetChanged();

                        }else{

                            Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();

                        }


                    }else{

                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelReport> call, Throwable t) {
                showProgress(false);
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }

    private void showProgress(Boolean bool){

        if (bool){
            rlprogress.setVisibility(View.VISIBLE);
        }else {
            rlprogress.setVisibility(View.GONE);
        }
    }




}