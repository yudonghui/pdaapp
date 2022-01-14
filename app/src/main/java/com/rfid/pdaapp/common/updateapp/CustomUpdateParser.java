package com.rfid.pdaapp.common.updateapp;


import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateParser;


public class CustomUpdateParser implements IUpdateParser {
    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        VersionInfoEntity versionInfoEntity = new Gson().fromJson(json, VersionInfoEntity.class);
        String version = versionInfoEntity.getVersion();
        String appUrl = versionInfoEntity.getInstall_url();
        String versionName = versionInfoEntity.getVersionShort();
        String updateContent = versionInfoEntity.getChangelog();
        int remoteVersionCode = Integer.parseInt(version);//线上的版本号
        int appVersionCode = AppUtils.getAppVersionCode();//本地的版本号
        return new UpdateEntity()
                .setHasUpdate(remoteVersionCode > appVersionCode)//是否有新版本
                .setForce(false)//是否强制安装：不安装无法使用app
                .setIsIgnorable(true)//是否可忽略该版本
                .setVersionCode(remoteVersionCode)//最新版本code
                .setVersionName(versionName)//最新版本名称
                .setUpdateContent(updateContent)//更新内容
                .setDownloadUrl(appUrl);
    }
}

