package com.github.openoffice;

/**
* openOffice启动类型
* @author cgq_r
* @date 2020/9/18
*/
public enum StartType {

    /**
     * 不启动openOffice服务
     */
    NONE,
    /**
     * 项目启动时启动
     */
    START
    ;

    private StartType() {
    }
}
