package com.rsah.closeloop.Menu.Auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rsah.closeloop.Api.ApiService;
import com.rsah.closeloop.Api.Server;
import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Menu.Main.MainActivity;
import com.rsah.closeloop.Model.JsonLogin;
import com.rsah.closeloop.Model.ResponseLogin;
import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.util.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin ;
    public List<JsonLogin> EntityLogin = new ArrayList<>();
    private ApiService API;
    private Context mContext ;
    private SessionManager sessionManager ;
    private EditText etusername , etpwd ;
    private RelativeLayout rlprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        API = Server.getAPIService();
        mContext = this ;
        sessionManager = new SessionManager(mContext);

        btnLogin = findViewById(R.id.login);
        etusername = findViewById(R.id.username);
        etpwd = findViewById(R.id.pwd);
        rlprogress = findViewById(R.id.rlprogress);


        //if loggin
        if (sessionManager.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etusername.getText().toString();
                String pwd = etpwd.getText().toString();

                if (username.equals("")){
                    etusername.setError("Required");
                }else if (pwd.equals("")){
                    etpwd.setError("Required");
                }else{

                    /*String uid = "";
                   String secretkey = Utility.SECRETKEY;
                    String signature = Helper.Hash_SHA256(uid+secretkey+username+pwd);
                    requestLogin(uid,signature,username,pwd);*/

                }

            }
        });





    }




/*
    private void requestLogin(String uid , String signature ,String username, String password  ){
        showProgress(true);
        Call<ResponseLogin> call = API.requestLogin(new JsonLogin(uid,signature,username,password));
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()) {
                    showProgress(false);
                    if (response.body().getStatus() != null) {

                        String message = response.body().getMessage() ;
                        String status = response.body().getStatus() ;

                        if(status.equals(Constant.ERR_0)){
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                //sessionManager.saveUser(Helper.ConvertResponseDataLoginToJson(response.body()));
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                        }else{

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        Log.e("TAG", "onResponse: "+response.body().toString() );
                    }

                }else{
                    showProgress(false);
                    Log.e("TAG", "onResponse: "+response.body().toString() );
                    Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                showProgress(false);
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

*/
    private void showProgress(Boolean bool){

        if (bool){
            rlprogress.setVisibility(View.VISIBLE);
        }else {
            rlprogress.setVisibility(View.GONE);
        }
    }






}