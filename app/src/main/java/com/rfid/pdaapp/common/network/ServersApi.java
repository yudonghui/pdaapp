package com.rfid.pdaapp.common.network;

import com.rfid.pdaapp.entitys.LoginEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ydh on 2021/12/24
 */
public interface ServersApi {
    //登录

    @FormUrlEncoded
    @POST("K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc")
    Call<LoginEntity> login(@FieldMap Map<String, String> paramsMap);

   /* @GET("mylike-crm/api/outbound/getBasDomainList.do")
    Call<BaseEntity<BasePageEntity<BookbuildingEntity>>> getBasDomainList(@Query("domainNamespace") String domainNamespace);

  //判断是否客户是否出院了
    @POST("his-api/v1.0/zy/getIsInHos")
    Call<BaseNewEntity> judgeIsLeaveHospital(@Body RequestBody body);
    */
}
