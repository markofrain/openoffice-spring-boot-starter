# Office转换PDF工具

本项目采用SpringBoot2.2.5和jodconverter2.2.2作为核心依赖发开。

可将本项目作为客户端连接使用，或者将本项目作为openoffice服务提供使用。

配置`com.cgq.open-office.enable: false`,表示禁止作为服务端,但连接服务正常使用。

配置`com.cgq.open-office.type: none`,则不启动openOffice作为服务提供者。

配置`com.cgq.open-office.type: start`,表示在启动项目时同时启动openOffice服务

我们建议此服务独立部署，或使用单机节点。如果请求量过高,也可以将单独openOffice服务与gateway、nacos等共同使用。


```java
@RestController
public class OpenOfficeController {
    @Autowired
    private OpenOffice openOffice;

    @GetMapping("/change")
    public void change(){
        openOffice.officeToPdf("E:\\国土空间规划一张图系统\\空间规划审查_需求_1231_lc.docx");
    }
}
```

因office转换对excel文档格式的支持不太友好,因此后续会增加对excel文件的HTML解析渲染。