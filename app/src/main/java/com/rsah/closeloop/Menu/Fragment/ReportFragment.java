package com.rsah.closeloop.Menu.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.fragment.app.Fragment;

import com.rsah.closeloop.Menu.Report.HistoriTransaksi;
import com.rsah.closeloop.Menu.Report.Mutasi;
import com.rsah.closeloop.Menu.Report.ReportByReference;
import com.rsah.closeloop.R;


public class ReportFragment extends Fragment {

    private LinearLayout btnHistori , btnMutasi , btnHistoriRef ;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        btnHistori = v.findViewById(R.id.btnHistori);
        btnMutasi = v.findViewById(R.id.btnMutasi);
        btnHistoriRef = v.findViewById(R.id.btnHistoriRef);



        btnHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HistoriTransaksi.class));
            }
        });


        btnMutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Mutasi.class));
            }
        });

        btnHistoriRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ReportByReference.class));
            }
        });




        return v;
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
