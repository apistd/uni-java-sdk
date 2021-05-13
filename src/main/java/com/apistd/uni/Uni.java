package com.apistd.uni;

public class Uni {
    public static final String VERSION = "0.0.2";

    public static String signingAlgorithm = "hmac-sha256";
    public static String endpoint = System.getenv().getOrDefault("UNI_ENDPOINT", "https://uni.apistd.com");
    public static String accessKeyId = System.getenv("UNI_ACCESS_KEY_ID");

    private static String accessKeySecret = System.getenv("UNI_ACCESS_KEY_SECRET");
    private static volatile UniClient client;

    private Uni() {}

    public static void init(final String accessKeyId) {
        Uni.setAccessKeyId(accessKeyId);
    }

    public static void init(final String accessKeyId, final String accessKeySecret) {
        Uni.setAccessKeyId(accessKeyId);
        Uni.setAccessKeySecret(accessKeySecret);
    }

    public static void init(final String accessKeyId, final String accessKeySecret, final String endpoint) {
        Uni.setAccessKeyId(accessKeyId);
        Uni.setAccessKeySecret(accessKeySecret);
        Uni.setEndpoint(endpoint);
    }

    public static void setAccessKeyId(final String accessKeyId) {
        Uni.accessKeyId = accessKeyId;
    }

    public static void setAccessKeySecret(final String accessKeySecret) {
        Uni.accessKeySecret = accessKeySecret;
    }

    public static void setEndpoint(final String endpoint) {
        Uni.endpoint = endpoint;
    }

    public static void setClient(final UniClient client) {
        synchronized (Uni.class) {
            Uni.client = client;
        }
    }

    public static UniClient getClient() {
        if (Uni.client == null) {
            synchronized (Uni.class) {
                if (Uni.client == null) {
                    Uni.client = buildClient();
                }
            }
        }

        return Uni.client;
    }

    private static UniClient buildClient() {
        UniClient.Builder builder = new UniClient.Builder(Uni.accessKeyId);

        if (Uni.accessKeySecret != null) {
            builder.accessKeySecret(Uni.accessKeySecret);
        }

        builder.endpoint(Uni.endpoint);
        builder.signingAlgorithm(Uni.signingAlgorithm);

        return builder.build();
    }
}
