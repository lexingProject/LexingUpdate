package com.lexing360.app.lexingupdate;

import com.lexing360.app.lexingupdate.model.JwtModel;
import com.lexing360.app.lexingupdate.model.LayoutModel;
import com.lexing360.app.lexingupdate.model.UpDatePutModel;
import com.lexing360.app.lexingupdate.model.ResponseModel;
import com.lexing360.app.lexingupdate.model.UpDateModel;
import com.lexing360.app.lexingupdate.model.UpDatePutResponseModel;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by fenglingfeng on 2018/2/2.
 */

public class Api {

    public static final String URL_BASE_PRE = "http://gateway-";
    public static final String URL_BASE_AFTER = ".lexing360.com/v1/";

    public static final String URL_BASE = "http://gateway-dev.lexing360.com/v1/";

    //更新升级
    public static final String URL_BASE_UPDATE = URL_BASE + "app/versions/";


    public static void subscribe(Flowable flowable, Subscriber subscriber){
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public interface ApiServices {

        @PUT("messageSettings")
        @Headers("Content-Type: application/json")
        Flowable<ResponseModel> putXmlInfo(@Body LayoutModel.DataBean s);

        @GET("messageSettings")
        Call<LayoutModel> getXmlInfo();

        @FormUrlEncoded
        @POST("vendors/auth-by-password")
        Flowable<JwtModel> getJwt(@Field("phone") String phone,
                                  @Field("password") String password,
                                  @Field("device_id") String deviceId,
                                  @Field("device_type") String deviceType);

        @GET("app/versions/{version}/{channel}")
        Call<UpDateModel> getUpdateUrl(@Path("version") String num,
                                             @Path("channel") String channel);

        @Headers("Content-Type: application/json")
        @PUT("app/versions/{version}/{channel}")
        Flowable<UpDatePutResponseModel> putUpDate(@Header("Authorization") String token,
                                                   @Path("version") String num,
                                                   @Path("channel") String channel,
                                                   @Body UpDatePutModel s);

    }
}
