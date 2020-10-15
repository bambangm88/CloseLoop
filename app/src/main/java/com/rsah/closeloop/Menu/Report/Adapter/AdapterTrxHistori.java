package com.rsah.closeloop.Menu.Report.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.Model.ModelReport;
import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.util.Helper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;




public class AdapterTrxHistori extends RecyclerView.Adapter< AdapterTrxHistori.AdapterHolder>{

    private List<ModelPayment> AllReportList;
    private Context mContext;

    private  SessionManager sessionManager ;


    public AdapterTrxHistori(Context context, List<ModelPayment> reportList){
        this.mContext = context;
        AllReportList = reportList;
        sessionManager = new SessionManager(mContext);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_histori_payment, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ModelPayment report = AllReportList.get(position);

       String idsiswa = report.getSiswaId();
       String ref = report.getMerchantReferenceNumber();
       String tanggal = report.getTrxDate();
       String amount = report.getAmount();
       String desc = report.getDescription();



       holder.textidsiswa.setText("IDS "+idsiswa);
       holder.texref.setText(ref);
       holder.texttanggal.setText(tanggal);
       holder.textamount.setText(Helper.changeToRupiah(amount));
       holder.textdesc.setText(desc);


    }

    @Override
    public int getItemCount() {
        return AllReportList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{

        TextView textidsiswa, texttanggal , textamount , texref , textdesc;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            textidsiswa = itemView.findViewById(R.id.textidsiswa);
            texttanggal = itemView.findViewById(R.id.texttanggal);
            textamount = itemView.findViewById(R.id.textamount);
            texref = itemView.findViewById(R.id.textref);
            textdesc = itemView.findViewById(R.id.textdesc);


        }
    }






}
