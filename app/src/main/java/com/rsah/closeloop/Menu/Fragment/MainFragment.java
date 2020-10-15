package com.rsah.closeloop.Menu.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rsah.closeloop.R;
import com.rsah.closeloop.util.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainFragment extends Fragment {



    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().setTitle("Main");

        TextView tahun = v.findViewById(R.id.tahun);
        TextView hari = v.findViewById(R.id.hari);
        TextView tanggalBulan = v.findViewById(R.id.tanggal_bulan);

        //set title date
        Helper.setTitleDate(tanggalBulan,hari,tahun);


        return v;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
