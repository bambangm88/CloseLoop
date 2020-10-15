package com.rsah.closeloop.Api;







import com.rsah.closeloop.Model.ModelInfran;
import com.rsah.closeloop.Model.ModelMutasi;
import com.rsah.closeloop.Model.ModelPayment;
import com.rsah.closeloop.Model.ModelReport;
import com.rsah.closeloop.Model.ResponseDataBalance;
import com.rsah.closeloop.Model.ResponseDataInfran;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {


    //region infran

    @Headers("Content-Type: application/json")
    @POST("api/infran/whoisit")
    Call<ResponseDataInfran> requestInfran(@Body ModelInfran body );

    //end region infran


    //region wallet

    @GET("wallet-service/accounts/nis/{id}")
    Call<ResponseDataBalance> cekBalance(@Path(value = "id") String id);

    //end region wallet


    //region close loop
    @Headers("Content-Type: application/json")
    @POST("closeloop-service/closeloops/payments/add")
    Call<ModelPayment> pay(@Body ModelPayment body);


    //end region close loop


    //region report
    @GET("closeloop-service/merchant/{merchantId}/payments/normal/startDate/{startDate}/enddate/{endDate}/page/{page}/max/{max}")
    Call<ModelReport> trxReport(
                    @Path(value = "merchantId") String merchantId,
                    @Path(value = "startDate") String startDate,
                    @Path(value = "endDate") String endDate,
                    @Path(value = "page") String page,
                    @Path(value = "max") String max
    );

    @GET("wallet-service/accounts/{accid}/mutations/startdate/{startDate}/enddate/{endDate}/page/{page}/max/{max}")
    Call<List<ModelMutasi>> MutasiReport(
            @Path(value = "accid") String merchantId,
            @Path(value = "startDate") String startDate,
            @Path(value = "endDate") String endDate,
            @Path(value = "page") String page,
            @Path(value = "max") String max
    );

    @GET("closeloop-service/closeloops/payments/referenceNumber/{ref}")
    Call<ModelPayment> ReportByReference(
            @Path(value = "ref") String ref
    );

    // end region reporti



}
