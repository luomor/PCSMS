# PCSMS

## 彭城短信

<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">pcsms v1.0.0</h1>
<h4 align="center" style="margin: 30px 0 30px; font-weight: bold;">pcsms -- 让发送短信变的更简单</h4>
<p align="center">
<a href="https://github.com/luomor/PCSMS/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-Apache--2.0-green"></a>
<a href="https://github.com/luomor/PCSMS"><img src="https://img.shields.io/badge/version-v1.0.0-blue"></a>
</p>
<img src="/public/logo.png">

## 在SpringBoot环境集成

1. maven引入
   
   ```xml
   <dependency>
    <groupId>com.luomor.pcsms</groupId>
    <artifactId>pcsms-spring-boot-starter</artifactId>
    <version>1.0.1</version>
   </dependency>
   ```
2. 设置配置文件
   
   ```yaml
   sms:
      config-type: yaml
      blends:
          tag1:
            accessKeyId: test
            accessKeySecret: test
            signature: test
            templateId: test
            templateName: code
            requestUrl: dysmsapi.aliyuncs.com
          tag2:
            appKey: test
            app-secret: test
            signature: 华为短信测试
            sender: 1
            template-id: test
            statusCallBack:
            url: https://test.cn-north-4.test.com:443
   ```

3. 方法使用
   
```java
@RestController
@RequestMapping("/test/")
public class TestController {
    @RequestMapping("/")
    public void send() {
        SmsFactory.getSmsBlend("tag1").sendMessage("16811116667", "123456");
        SmsFactory.getSmsBlend("tag2").sendMessage("16811116667", "000000");
    }
}
```


## 配置详解

#### 线程池配置

```yaml
sms:
  corePoolSize: 10
  maxPoolSize: 30
  queueCapacity: 50
  keepAliveSeconds: 60
  threadNamePrefix: sms-executor-
  shutdownStrategy: true
```
