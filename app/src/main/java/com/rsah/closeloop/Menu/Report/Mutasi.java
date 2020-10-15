package com.rsah.closeloop.Menu.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rsah.closeloop.Api.ApiService;
import com.rsah.closeloop.Api.Server;
import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Menu.Report.Adapter.AdapterMutasi;
import com.rsah.closeloop.Menu.Report.Adapter.AdapterTrxHistori;
import com.rsah.closeloop.Model.ModelMutasi;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.Model.ModelReport;
import com.rsah.closeloop.R;
import com.rsah.closeloop.util.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mutasi extends AppCompatActivity {


    private RecyclerView rvMutasi;
    private List<ModelMutasi> AllMutasiList = new ArrayList<>();
    private ApiService API;
    private Context mContext;

    private AdapterMutasi Adapter;

    private LinearLayout btnStartDate, btnEndDate;

    private EditText etStartDate, etEndDate , etAccId;

    private Button btnCari;

    private RelativeLayout rlprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutasi);

        mContext = this;
        API = Server.getAPIService();

        btnStartDate = findViewById(R.id.btnStarDate);
        btnEndDate = findViewById(R.id.btnEndDate);

        etStartDate = findViewById(R.id.starDate);
        etEndDate = findViewById(R.id.endDate);
        etAccId = findViewById(R.id.accid);

        btnCari = findViewById(R.id.btnCari);

        rlprogress = findViewById(R.id.rlprogress);


        rvMutasi = findViewById(R.id.rvMutasi);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMutasi.setLayoutManager(mLayoutManager);
        rvMutasi.setItemAnimator(new DefaultItemAnimator());

        Adapter = new AdapterMutasi(this, AllMutasiList);


        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showDateDialog(mContext, etStartDate);
            }
        });


        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showDateDialog(mContext, etEndDate);
            }
        });


        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                String accid = etAccId.getText().toString();

                if (accid.equals(Constant.FIELD_KOSONG)) {
                    etAccId.setError(Constant.FIELD_REQUIRED);
                }else if (startDate.equals(Constant.FIELD_KOSONG)) {
                    etStartDate.setError(Constant.FIELD_REQUIRED);
                } else if (endDate.equals(Constant.FIELD_KOSONG)) {
                    etEndDate.setError(Constant.FIELD_REQUIRED);
                } else {

                    //String MerchantID = "54";
                    String Page = "0";
                    String Max = "100";

                    //request get Report
                    getMutasi(accid, startDate, endDate, Page, Max);

                }

            }
        });


    }


    private void getMutasi(String merchantId, String startDate, String endDate, String page, String Max) {

        showProgress(true);

        Call<List<ModelMutasi>> call = API.MutasiReport(merchantId, startDate, endDate, page, Max);
        call.enqueue(new Callback<List<ModelMutasi>>() {
            @Override
            public void onResponse(Call<List<ModelMutasi>>call, Response<List<ModelMutasi>> response) {
                if (response.isSuccessful()) {
                    showProgress(false);
                    if (response.body() != null) {

                        if (!response.body().isEmpty()) {

                            AllMutasiList.addAll(response.body());
                            rvMutasi.setAdapter(new AdapterMutasi(mContext, AllMutasiList));
                            Adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();

                        }


                    } else {

                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                } else {
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelMutasi>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showProgress(Boolean bool) {

        if (bool) {
            rlprogress.setVisibility(View.VISIBLE);
        } else {
            rlprogress.setVisibility(View.GONE);
        }
    }

}


