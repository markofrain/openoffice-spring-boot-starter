package com.github.openoffice.properties;

import com.github.openoffice.StartType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;

/**
* OpenOffice属性配置
* @author cgq_r
* @date 2020/9/18
*/
@ConfigurationProperties(prefix = "com.github.open-office")
public class OpenOfficeProperties {

    private static final ArrayList<String> LOCAL_PATH = new ArrayList<>();

    static {
        LOCAL_PATH.add("127.0.0.1");
        LOCAL_PATH.add("localhost");
        LOCAL_PATH.add("0.0.0.0");
    }

    private String path;
    private String ip = "127.0.0.1";
    private Boolean enable = false;
    private StartType type = StartType.NONE;

    private Boolean local = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public StartType getType() {
        return type;
    }

    public void setType(StartType type) {
        this.type = type;
    }

    public Boolean getLocal() {
        return LOCAL_PATH.contains(ip);
    }
}
