package com.alvinhkh.buseta.kmb;

import com.alvinhkh.buseta.App;
import com.alvinhkh.buseta.kmb.model.KmbEtaRoutes;
import com.alvinhkh.buseta.kmb.model.network.KmbAnnounceRes;
import com.alvinhkh.buseta.kmb.model.network.KmbEtaRes;
import com.alvinhkh.buseta.kmb.model.network.KmbRouteBoundRes;
import com.alvinhkh.buseta.kmb.model.network.KmbSpecialRouteRes;
import com.alvinhkh.buseta.kmb.model.network.KmbStopsRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface KmbService {
    
    String ANNOUNEMENT_PICTURE = "http://search.kmb.hk/KMBWebSite/AnnouncementPicture.ashx?url=";

    Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    Retrofit webSearch = new Retrofit.Builder()
            .client(App.httpClient)
            .baseUrl("http://192.168.60.162/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("FunctionRequest.ashx?action=getAnnounce")
    Observable<KmbAnnounceRes> getAnnounce(@Query("route") String route, @Query("bound") String bound);

    @GET("parking/getroutebound.json")
    Observable<KmbRouteBoundRes> getRouteBound();

    @GET("parking/getspecialroute.json")
    Observable<KmbSpecialRouteRes> getSpecialRoute();

    @GET("parking/A41.json")
    Observable<KmbStopsRes> getStops();

    Retrofit webSearchHtml = new Retrofit.Builder()
            .client(App.httpClient)
            .baseUrl("http://search.kmb.hk/KMBWebSite/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("AnnouncementPicture.ashx")
    Observable<ResponseBody> getAnnouncementPicture(@Query("url") String url);

    Retrofit etav3 = new Retrofit.Builder()
            .client(App.httpClient)
            .baseUrl("http://etav3.kmb.hk")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET
    Observable<KmbEtaRes> getEta(@Url String url);

    @GET("?action=geteta")
    Observable<KmbEtaRes> getEta(@Query("route") String route, @Query("bound") String bound,
                                 @Query("stop") String stop, @Query("stop_seq") String stop_seq,
                                 @Query("serviceType") String serviceType, @Query("lang") String lang,
                                 @Query("updated") String updated);

    Retrofit etadatafeed = new Retrofit.Builder()
            .client(App.httpClient)
            .baseUrl("http://192.168.60.162/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("/parking/bus_no.json")
    Observable<List<KmbEtaRoutes>> getEtaRoutes();
}
