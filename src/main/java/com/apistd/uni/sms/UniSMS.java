package com.apistd.uni.sms;

public class UniSMS {
    private UniSMS() {}

    public static UniMessage buildMessage() {
        return new UniMessage();
    }
}
