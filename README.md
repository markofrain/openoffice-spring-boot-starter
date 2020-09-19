# OpenOffice转换工具

可将本项目作为客户端连接使用，或者将本项目作为openoffice服务提供使用。
配置`com.cgq.open-office.type: none`,则不启动openoffice作为服务提供者。
配置`com.cgq.open-office.type: start`,表示在启动项目时同时启动openOffice服务
如果配置`com.cgq.open-office.enable: false`,则客户端和服务端都无法使用。

我们建议此服务独立部署，或使用单机节点。如果请求量过高,也可以将单独openOffice服务与网关nacos等注册。


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