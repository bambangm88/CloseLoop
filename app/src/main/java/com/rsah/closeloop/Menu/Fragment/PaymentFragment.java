package com.rsah.closeloop.Menu.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.customfonts.Button_SF_Pro_Display_Medium;
import com.rsah.closeloop.util.cameraKit.CameraFaceIdentity;


public class PaymentFragment extends Fragment {

    private Button_SF_Pro_Display_Medium btnScan ;
    private EditText tvamount;
    private SessionManager sessionManager;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        sessionManager = new SessionManager(getContext());

        btnScan = v.findViewById(R.id.scan);
        tvamount = v.findViewById(R.id.tvamount);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String amount = tvamount.getText().toString();


                if (amount.equals("") || amount.equals("0")){

                    tvamount.setError("Amount Required");
                    tvamount.setText("0");
                    tvamount.setFocusable(true);

                }else{
                    sessionManager.saveAmount(amount);
                    // pindah ke PaymentCameraActivity
                    startActivity(new Intent(getContext(), CameraFaceIdentity.class));
                }



            }
        });



        return v ;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
