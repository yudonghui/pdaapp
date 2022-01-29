package com.rfid.pdaapp.common.network;

import com.rfid.pdaapp.common.ResponeEntity;
import com.rfid.pdaapp.entitys.LoginEntity;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    //退出登录
    @POST("K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.Logout.common.kdsvc")
    Call<Object> loginQuite(@Body RequestBody body);

    //库存查询
    @POST("K3Cloud/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc")
    Call<List<List<Object>>> locationForm(@Body RequestBody body);

    //库存查询 仓库查询
    @POST("K3Cloud/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc")
    Call<ResponseBody> locationFormStr(@Body RequestBody body);

    //收货入库 收货上架保存
    @POST("K3Cloud/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc")
    Call<ResponeEntity> savePutStockBox(@Body RequestBody body);
   /* @GET("mylike-crm/api/outbound/getBasDomainList.do")
    Call<BaseEntity<BasePageEntity<BookbuildingEntity>>> getBasDomainList(@Query("domainNamespace") String domainNamespace);

  //判断是否客户是否出院了
    @POST("his-api/v1.0/zy/getIsInHos")
    Call<BaseNewEntity> judgeIsLeaveHospital(@Body RequestBody body);
    */
}
