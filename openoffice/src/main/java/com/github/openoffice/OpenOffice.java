package com.github.openoffice;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.github.openoffice.properties.OpenOfficeProperties;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * OpenOffice调用工具
 *
 * @author cgq_r
 * @date 2020/9/18
 */
public class OpenOffice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 5,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setDaemon(false).setNameFormat("openOffice-%d").build());

    private OpenOfficeProperties properties;


    /**
     * office 文档转换pdf,自动截取替换后缀
     *
     * @param startFile 原始文件
     */
    public void officeToPdf(String startFile) {
        if (StringUtils.isEmpty(startFile)) {
            logger.error("传入的参数为空,无法转换");
            return;
        }

        File temp = new File(startFile);
        if (!temp.exists()) {
            logger.error("要转换的文件不存在,文件路径参数:" + startFile);
            return;
        }

        String overFile = startFile.substring(0, startFile.lastIndexOf(".")) + ".pdf";
        change(startFile, overFile);
    }

    /**
     * office 文档转换pdf,自动截取替换后缀
     *
     * @param startFile 原始文件
     * @throws FileNotFoundException 可能存在找不到文件的情况
     */
    public void officeToPdfThrow(String startFile) throws FileNotFoundException {
        if (StringUtils.isEmpty(startFile)) {
            throw new NullPointerException("传入的文件参数为空,无法转换");
        }

        File temp = new File(startFile);
        if (!temp.exists()) {
            throw new FileNotFoundException("要转换的文件不存在,文件路径参数:" + startFile);
        }

        String overFile = startFile.substring(0, startFile.lastIndexOf(".")) + ".pdf";
        change(startFile, overFile);
    }


    /**
     * 异步线程执行转换操作
     *
     * @param startFile 原始文件
     */
    public void asyncOfficeToPdf(String startFile) {
        executor.execute(() -> officeToPdf(startFile));
    }

    /**
     * 本地部署较快，远程服务较慢，建议window下为本地服务
     *
     * @param startFile
     * @param overFile
     */
    private void change(String startFile, String overFile) {
        File inputFile = new File(startFile);

        File outputFile = new File(overFile);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        OpenOfficeConnection connection = null;
        try {
            connection = new SocketOpenOfficeConnection(properties.getIp(), 8100);
            connection.connect();

            DocumentConverter converter = null;
            if (properties.getLocal()) {
                converter = new OpenOfficeDocumentConverter(connection);
            } else {
                converter = new StreamOpenOfficeDocumentConverter(connection);
            }

            converter.convert(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("转换PDF出现异常,异常信息:" + e.getMessage());
        } finally {
            if (connection != null && connection.isConnected()) {
                connection.disconnect();
            }
        }
    }

    public OpenOffice(OpenOfficeProperties properties) {
        this.properties = properties;
    }

    public OpenOfficeProperties getProperties() {
        return properties;
    }

    public void setProperties(OpenOfficeProperties properties) {
        this.properties = properties;
    }
}
