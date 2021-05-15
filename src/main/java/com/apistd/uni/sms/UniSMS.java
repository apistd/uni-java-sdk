package com.apistd.uni.sms;

public class UniSMS {
    private UniSMS() {}

    /**
     * Build a Uni Message object.
     *
     * @return UniMessage object
     */
    public static UniMessage buildMessage() {
        return new UniMessage();
    }
}
