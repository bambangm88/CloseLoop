package com.rsah.closeloop.Menu.Report;

import androidx.appcompat.app.AppCompatActivity;

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
import com.rsah.closeloop.Model.ModelMutasi;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.R;
import com.rsah.closeloop.util.Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportByReference extends AppCompatActivity {


    private EditText txtref , txtsiswaid , txtamount , txtdate , txtdesc , txtsettle , txtrefcari ;
    private ApiService API;
    private Context mContext;
    private Button btnCari;
    private RelativeLayout rlprogress;
    private LinearLayout dataReport ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_by_reference);

        mContext = this;
        API = Server.getAPIService();

        txtref = findViewById(R.id.merchantref);
        txtsiswaid = findViewById(R.id.siswaid);
        txtamount = findViewById(R.id.amount);
        txtdate = findViewById(R.id.trxdate);
        txtdesc = findViewById(R.id.desc);
        txtsettle = findViewById(R.id.settle);
        txtrefcari = findViewById(R.id.txtrefcari);

        btnCari = findViewById(R.id.btnCari);
        rlprogress = findViewById(R.id.rlprogress);
        dataReport = findViewById(R.id.dataReport);


        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ref = txtrefcari.getText().toString();

                if (ref.equals(Constant.FIELD_KOSONG)) {
                    txtrefcari.setError(Constant.FIELD_REQUIRED);
                }else {

                    //request get Report
                    getReportByReference(ref);

                }

            }
        });





    }


    private void getReportByReference(String ref) {

        showBarReportByReference(false);

        showProgress(true);

        Call<ModelPayment> call = API.ReportByReference(ref);
        call.enqueue(new Callback<ModelPayment>() {
            @Override
            public void onResponse(Call<ModelPayment>call, Response<ModelPayment> response) {
                if (response.isSuccessful()) {

                    showProgress(false);


                        if (response.body().getId() != null) {

                            showBarReportByReference(true);


                            String ref = response.body().getMerchantReferenceNumber();
                            String siswaid = response.body().getSiswaId();
                            String amount = response.body().getAmount();
                            String date = response.body().getTrxDate();
                            String desc = response.body().getDescription();
                            String settle = response.body().getSettlementStatus();


                            txtref.setText(ref);
                            txtsiswaid.setText(siswaid);
                            txtamount.setText(Helper.changeToRupiah(amount));
                            txtdate.setText(date);
                            txtdesc.setText(desc);
                            txtsettle.setText(settle);



                        } else {

                            Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();

                        }



                } else {
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {
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

    private void showBarReportByReference(Boolean bool) {

        if (bool) {
            dataReport.setVisibility(View.VISIBLE);
        } else {
            dataReport.setVisibility(View.GONE);
        }
    }

}