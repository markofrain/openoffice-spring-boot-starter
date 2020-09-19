package com.github.openoffice;

import com.github.openoffice.properties.OpenOfficeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
* OpenOffice启动时
* @author cgq_r
* @date 2020/9/18
*/
public class OpenOfficeStartUp {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private OpenOfficeProperties properties;

    private Process p;

    public OpenOfficeStartUp(OpenOfficeProperties properties){
        this.properties = properties;
    }

    @PostConstruct
    public void start(){
        if (properties.getPath()==null){
            logger.error("您想要启动服务,但path属性未配置无法启动,且type值为start");
            return;
        }
        if (properties.getType() !=StartType.START){
            logger.error("如您想要启动服务,请设置type属性值为start");
            return;
        }

        String command = properties.getPath() + " -headless -accept=\"socket,host=" + properties.getIp() + ",port=8100;urp;\" -nofirststartwizard ";
        try {
            p = Runtime.getRuntime().exec(command);
            logger.debug("OpenOffice启动状态:" + p.isAlive());
        } catch (IOException e) {
            e.printStackTrace();
            // 关闭进程
            if (p != null) {
                p.destroy();
            }
        }

    }

    @PreDestroy
    public void destory(){
        if (p.isAlive()){
            p.destroy();
            logger.debug("关闭OpenOffice服务进程");
        }
    }
}
