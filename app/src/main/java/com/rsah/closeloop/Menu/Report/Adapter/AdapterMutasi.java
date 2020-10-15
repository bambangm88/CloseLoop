package com.rsah.closeloop.Menu.Report.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Model.ModelMutasi;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.util.Helper;

import java.util.List;

import butterknife.ButterKnife;


public class AdapterMutasi extends RecyclerView.Adapter< AdapterMutasi.AdapterHolder>{

    private List<ModelMutasi> AllMutasiList;
    private Context mContext;

    private  SessionManager sessionManager ;


    public AdapterMutasi(Context context, List<ModelMutasi> reportList){
        this.mContext = context;
        AllMutasiList = reportList;
        sessionManager = new SessionManager(mContext);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_histori_payment, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ModelMutasi report = AllMutasiList.get(position);

       String tanggal = report.getTrxDate();
       String amount = report.getAmount();
       String ref = report.getReferenceId();
       String tagid = report.getTagId();


        holder.texttanggal.setText(tanggal);
        holder.textamount.setText(Helper.changeToRupiah(amount));
        holder.texref.setText(ref);


        //hide
       holder.textdesc.setVisibility(View.INVISIBLE);



       // change color by type mutation
       if (tagid.equals(Constant.TAG_TRX_TOPUP)){
           holder.textamount.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
           holder.txtimage.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
           holder.textidsiswa.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
           holder.textidsiswa.setText(Constant.TOPUP);
       }else if(tagid.equals(Constant.TAG_TRX_PAYMENT)){
           holder.textamount.setTextColor(mContext.getResources().getColor(R.color.biru));
           holder.txtimage.setTextColor(mContext.getResources().getColor(R.color.biru));
           holder.textidsiswa.setTextColor(mContext.getResources().getColor(R.color.biru));
           holder.textidsiswa.setText(Constant.PAYMENT);
       }else if(tagid.equals(Constant.TAG_TRX_REVERSAL)){
           holder.textamount.setTextColor(mContext.getResources().getColor(R.color.red));
           holder.txtimage.setTextColor(mContext.getResources().getColor(R.color.red));
           holder.textidsiswa.setTextColor(mContext.getResources().getColor(R.color.red));
           holder.textidsiswa.setText(Constant.REVERSAL);
       }else if(tagid.equals(Constant.TAG_TRX_WITHDRAWAL)){
           holder.textamount.setTextColor(mContext.getResources().getColor(R.color.warning));
           holder.txtimage.setTextColor(mContext.getResources().getColor(R.color.warning));
           holder.textidsiswa.setTextColor(mContext.getResources().getColor(R.color.warning));
           holder.textidsiswa.setText(Constant.WITHDRAW);
       }



    }

    @Override
    public int getItemCount() {
        return AllMutasiList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{

        TextView textidsiswa, texttanggal , textamount , texref , textdesc , txtimage;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            textidsiswa = itemView.findViewById(R.id.textidsiswa);
            texttanggal = itemView.findViewById(R.id.texttanggal);
            textamount = itemView.findViewById(R.id.textamount);
            texref = itemView.findViewById(R.id.textref);
            textdesc = itemView.findViewById(R.id.textdesc);
            txtimage = itemView.findViewById(R.id.txtimage);


        }
    }






}
