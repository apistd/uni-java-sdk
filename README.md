# Uni Java SDK

[UniSMS](https://unisms.apistd.com/) - 高可用聚合短信服务平台官方 Java SDK.

## 文档

查看完整产品介绍与 API 文档请访问 [UniSMS Documentation](https://unisms.apistd.com/docs).

## 安装

Uni Java SDK 使用 Maven 托管，可从公共 [Maven 仓库](https://mvnrepository.com/artifact/com.apistd.uni/uni-sdk) 中获得。

在项目中使用以下代码添加 Maven 依赖：

```xml
<dependency>
    <groupId>com.apistd.uni</groupId>
    <artifactId>uni-sdk</artifactId>
    <version>0.0.4</version>
</dependency>
```

或使用 Gradle：

```groovy
implementation "com.apistd.uni:uni-sdk:0.0.4"
```

## 使用示例

以下示例展示如何使用 Uni Java SDK 快速调用服务。

### 发送短信

```java

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniSMS;
import com.apistd.uni.sms.UniMessage;

import java.util.HashMap;
import java.util.Map;

public class Example {
    public static String ACCESS_KEY_ID = "your access key id";
    private static String ACCESS_KEY_SECRET = "your access key secret";

    public static void main(String[] args) {
        // 初始化
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", "6666");

        // 创建信息
        UniMessage message = UniSMS.buildMessage()
            .setTo("your phone number")
            .setSignature("UniSMS")
            .setTemplateId("login_tmpl")
            .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res.data);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}

```

## 相关参考

### 其他语言 SDK

- [Go](https://github.com/apistd/uni-go-sdk)
- [Node.js](https://github.com/apistd/unisms-node-sdk)
- [Python](https://github.com/apistd/uni-python-sdk)
- [PHP](https://github.com/apistd/uni-php-sdk/)
