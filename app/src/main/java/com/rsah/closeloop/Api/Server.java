package com.rsah.closeloop.Api;


import static com.rsah.closeloop.util.Utility.BASE_URL_API;
import static com.rsah.closeloop.util.Utility.BASE_URL_API_INFRAN;

public class Server {
    public static ApiService getAPIService() {
        return Client.getClient(BASE_URL_API).create(ApiService.class);
    }


    public static ApiService getAPIServiceInfran() {
        return Client.getClientInfran(BASE_URL_API_INFRAN).create(ApiService.class);
    }


}
