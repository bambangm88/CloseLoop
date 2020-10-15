package com.rsah.closeloop.Menu.Payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rsah.closeloop.Api.ApiService;
import com.rsah.closeloop.Api.Server;
import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Model.ModelCloseLoop;
import com.rsah.closeloop.Model.ModelInfran;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.Model.ResponseDataBalance;
import com.rsah.closeloop.Model.ResponseDataInfran;
import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.util.Helper;
import com.rsah.closeloop.util.cameraKit.CameraFaceIdentity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paymentSiswa extends AppCompatActivity {

    public static Bitmap bitmapPreview ;

    private SessionManager sessionManager;
    private EditText tvName , tvAmount , tvSaldo , tvConfirm ;
    private ApiService API;
    private Context mContext ;
    private Button submitPay ;
    private RelativeLayout  rlprogress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_siswa);

        tvName = findViewById(R.id.tvname);
        tvAmount = findViewById(R.id.tvamount);
        tvName = findViewById(R.id.tvname);
        tvConfirm = findViewById(R.id.tvconfirm);
        tvSaldo = findViewById(R.id.tvbalance);
        submitPay = findViewById(R.id.submitPay);
        rlprogress = findViewById(R.id.rlprogress);

        ImageView img = findViewById(R.id.image);

        API = Server.getAPIService();
        mContext = this ;
        sessionManager = new SessionManager(mContext);


        try {

            img.setImageBitmap(bitmapPreview);
            setData();



        }catch (Exception ex){

            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "Exception: "+ex.getMessage() );

        }

        submitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String saldo = tvSaldo.getText().toString();
                String amount = tvAmount.getText().toString();
                String name = tvName.getText().toString();
                String confirm = tvConfirm.getText().toString();

                if (saldo.equals("")){
                    saldo = "100000" ;
                }

                double dSaldo = Double.parseDouble(saldo);
                double damount = Double.parseDouble(amount);

                int iSaldo = (int) Math. round(dSaldo);
                int iAmount= (int) Math. round(damount);

                if (confirm.equals("")) {
                    Toast.makeText(mContext, "Silahkan Confirm", Toast.LENGTH_SHORT).show();
                }else if (!name.equals(confirm)) {
                    Toast.makeText(mContext, "Confirm Invalid", Toast.LENGTH_SHORT).show();
                }else if(iSaldo < iAmount){
                    Toast.makeText(mContext, "Saldo Tidak Cukup", Toast.LENGTH_SHORT).show();
                }else{

                   //Toast.makeText(mContext, "Pay", Toast.LENGTH_SHORT).show();

                    ResponseDataInfran data = Helper.dataInfran(sessionManager.getInstanceSiswa());

                    List<ModelCloseLoop> listcloseLoop = new ArrayList<>();
                    ModelCloseLoop closeLoop = new ModelCloseLoop();
                    closeLoop.setDescription("Transaksi harian UQ");
                    closeLoop.setId("3");
                    closeLoop.setPayableId("1");
                    closeLoop.setTenantId("1");
                    listcloseLoop.add(closeLoop);

                    String description = "Transaksi harian UQ" ;
                    String id = "6" ;
                    String merchantId = "54" ; //tes
                    String merchantReferenceNumber = merchantId+System.currentTimeMillis();
                    String settlementStatus = "DEFAULT" ;
                    String siswaId = "1" ;

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    String trxDate = currentDateandTime;

                    pay(new ModelPayment(
                            amount,
                            closeLoop,
                            description,
                            id,
                            merchantId,
                            merchantReferenceNumber,
                            settlementStatus,
                            siswaId,
                            trxDate
                    ));


                }


            }
        });



    }

    private void setData(){

        ResponseDataInfran data = Helper.dataInfran(sessionManager.getInstanceSiswa());

        cekBalance(data.getData().get(0).getPip_nik());

        tvName.setText(data.getData().get(0).getPerson_in_picture());
        tvAmount.setText(sessionManager.getAmount());

    }





    private void cekBalance(String id ){


        Call<ResponseDataBalance> call = API.cekBalance(id);
        call.enqueue(new Callback<ResponseDataBalance>() {
            @Override
            public void onResponse(Call<ResponseDataBalance> call, Response<ResponseDataBalance> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() != null) {

                        String err_code = response.body().getError() ;
                        String status = response.body().getStatus();
                      //  Log.e("TAG", "onResponse: "+response.body().toString() );

                    }else{

                        if (response.body().getBalance() != null){

                            //String id = response.body().getWallet().get(0).getId() ;
                            String saldo = response.body().getBalance();

                            tvSaldo.setText(saldo);


                        }
                    }
                }else{
                    Log.e("TAG", "onResponse: Server Error" );
                }
            }
            @Override
            public void onFailure(Call<ResponseDataBalance  > call, Throwable t) {

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void pay(ModelPayment data ){

        showProgress(true);

        Call<ModelPayment> call = API.pay(data);
        call.enqueue(new Callback<ModelPayment>() {
            @Override
            public void onResponse(Call<ModelPayment> call, Response<ModelPayment> response) {
                if(response.isSuccessful()) {

                    showProgress(false);

                    if (response.body().getId() != null) {

                        Toast.makeText(mContext, Constant.NOTIF_PAYMENT, Toast.LENGTH_SHORT).show();
                        finish();

                    }else{

                        Toast.makeText(mContext, response.body().getError(), Toast.LENGTH_SHORT).show();

                    }

                }else{

                    showProgress(false);

                    Toast.makeText(mContext, Constant.NOTIF_PAYMENT_FAILED, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    //Log.e("TAG", "onResponse: Server Error"+response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {

                showProgress(false);

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

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