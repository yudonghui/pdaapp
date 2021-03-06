package com.rfid.pdaapp.common.network;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.rfid.pdaapp.common.base.BaseEntity;
import com.rfid.pdaapp.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static String BASE_URL = "http://47.101.192.134:8090/";//统一地址
    private static ServersApi serversApi = null;
    public static long timeOut = 30000;//连接超时,30秒


    public static ServersApi getHttpApi() {
        return getHttpApi(timeOut);
    }

    public static ServersApi getHttpApi(long timeOut) {
        if (serversApi == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.contains("trace-id")) {
                        LogUtils.e("返回结果: " + message);
                    }
                    if (message.startsWith("[[")) {
                        // LogUtils.e("请求结果" + message);
                        LogUtils.e("请求结果" + message);
                    } else if (message.contains("-->") && message.contains("http://")) {
                        LogUtils.e("请求接口" + message);
                    } else if (message.contains("{")) {
                        LogUtils.e("请求参数" + message);
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuiler = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder());
            //对所有请求添加请求头
            OkHttpClient.Builder builder = httpClientBuiler.connectTimeout(timeOut, TimeUnit.MILLISECONDS)//连接超时,30秒
                    .readTimeout(timeOut, TimeUnit.MILLISECONDS)//读取数据超时，30秒
                    .addInterceptor(new RequestParamInterceptor());//请求头添加上token
            builder.addInterceptor(loggingInterceptor);

            serversApi = new Retrofit.Builder()
                    .client(httpClientBuiler.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ServersApi.class);
        }
        return serversApi;
    }

    /**
     * 设置工作线程
     */
    public static Flowable getFlowable(Flowable<? extends BaseEntity> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return flowable;
    }

    //map参数请求数据
    public static RequestBody getRequestBody(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }

    //list参数请求数据
    public static RequestBody getRequestBody(List<String> list) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }

    //对象参数请求数据
    public static RequestBody getRequestBody(Object o) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(o);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }

    //传递字符串
    public static RequestBody getRequestBody(String string) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
    }

    //对象参数请求数据
    public static RequestBody getRequestBodyImage(String path) {
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("header", file.getName(), fileRQ)
                .build();
        return body;
    }

    /**
     * multipart/form-data 形式上传多个文件
     *
     * @Multipart 注解
     * @PartMap Map<String, RequestBody> maps 作为参数
     */
    public static Map<String, RequestBody> uploadMultipartFile(List<File> files, Map<String, String> paramsMap, Handler mHandler) {
        Map<String, RequestBody> map = new HashMap<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            UploadFileRequestBody requestFile = new UploadFileRequestBody(requestBody, new ProgressListener() {
                @Override
                public void onProgress(long progress, long total, boolean done) {
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.obj = done;
                    obtain.arg1 = (int) (progress / 1000);
                    mHandler.sendMessage(obtain);
                    LogUtils.e("结果：" + progress + "    total: " + total);
                }
            });
            //注意：file就是与服务器对应的key,后面filename是服务器得到的文件名
            map.put("multipartFile\"; filename=\"" + file.getName(), requestFile);
        }
        for (Map.Entry<String, String> entity : paramsMap.entrySet()) {
            String key = entity.getKey();
            String value = entity.getValue();
            map.put(key, toRequestBody(value));
        }
        return map;
    }

    /**
     * multipart/form-data 形式上传多个文件
     *
     * @Multipart 注解
     * @PartMap Map<String, RequestBody> maps 作为参数
     */
    public static Map<String, RequestBody> getMultipartMap(List<String> imgStrs, Map<String, String> paramsMap) {
        Map<String, RequestBody> map = new HashMap<>();
        for (String imgUrl : imgStrs) {
            File file = new File(BitmapUtils.compressImage(imgUrl));
            //  File file = new File(imgUrl);
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            //注意：file就是与服务器对应的key,后面filename是服务器得到的文件名
            map.put("multipartFile\"; filename=\"" + file.getName(), requestFile);
        }
        for (Map.Entry<String, String> entity : paramsMap.entrySet()) {
            String key = entity.getKey();
            String value = entity.getValue();
            map.put(key, toRequestBody(value));
        }
        return map;
    }

    private static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    /**
     * multipart/form-data 形式上传多个文件
     *
     * @Multipart 注解
     * @Part MultipartBody.Part body  作为参数
     */
    public static MultipartBody.Part getMultipartPart(List<String> paths) {
        File file = new File(paths.get(0));
        // RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        return part;
    }

}

