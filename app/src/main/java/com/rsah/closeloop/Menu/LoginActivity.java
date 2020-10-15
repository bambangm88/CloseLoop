package com.rsah.closeloop.Menu;

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


import com.rsah.closeloop.Menu.Main.MainActivity;
import com.rsah.closeloop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin ;

    private Context mContext ;

    private EditText etusername , etpwd ;
    private RelativeLayout rlprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this ;


        btnLogin = findViewById(R.id.login);
        etusername = findViewById(R.id.username);
        etpwd = findViewById(R.id.pwd);
        rlprogress = findViewById(R.id.rlprogress);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etusername.getText().toString();
                String pwd = etpwd.getText().toString();

                if (username.equals("")){
                    etusername.setError("Required");
                }else if (pwd.equals("")){
                    etpwd.setError("Required");
                }else if(username.equals("it") && pwd.equals("123")){

                    //String uid = Utility.UID;
                    //String secretkey = Utility.SECRETKEY;
                   // String signature = Helper.Hash_SHA256(uid+secretkey+username+pwd);
                    //requestLogin(uid,signature,username,pwd);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }else{
                    showProgress(false);
                    Toast.makeText(mContext, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }

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