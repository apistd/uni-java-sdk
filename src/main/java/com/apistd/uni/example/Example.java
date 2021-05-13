package com.apistd.uni.example;

import java.util.HashMap;
import java.util.Map;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniSMS;
import com.apistd.uni.sms.UniMessage;

public class Example {
    public static String ACCESS_KEY_ID = System.getenv().getOrDefault("UNI_ACCESS_KEY_ID", "your access key id");
    private static String ACCESS_KEY_SECRET = System.getenv().getOrDefault("UNI_ACCESS_KEY_SECRET", "your access key secret");

    public static void main(String[] args) {
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", "6666");

        UniMessage message = UniSMS.buildMessage()
            .setTo("your phone number")
            .setSignature("UniSMS")
            .setTemplateId("login_tmpl")
            .setTemplateData(templateData);

        try {
            UniResponse result = message.send();
            System.out.println(result.data);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}
