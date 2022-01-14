package com.rfid.pdaapp.common.updateapp;

/**
 * Created by ydh on 2021/4/27
 */
public class VersionInfoEntity {
    private String name;//护理端
    private String version;//100
    private String install_url;
    private String versionShort;//1.0.0
    private String changelog;//更新日志

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }
}
