package com.apistd.uni;

/**
 * Singleton class to initialize Uni environment.
 */
public class Uni {
    public static final String VERSION = "0.0.4";

    public static String signingAlgorithm = "hmac-sha256";
    public static String endpoint = System.getenv().getOrDefault("UNI_ENDPOINT", "https://uni.apistd.com");
    public static String accessKeyId = System.getenv("UNI_ACCESS_KEY_ID");

    private static String accessKeySecret = System.getenv("UNI_ACCESS_KEY_SECRET");
    private static volatile UniClient client;

    private Uni() {}

    /**
     * Initialize the Uni environment (simple auth mode).
     *
     * @param accessKeyId access key ID
     */
    public static void init(final String accessKeyId) {
        Uni.setAccessKeyId(accessKeyId);
    }

    /**
     * Initialize the Uni environment (HMAC auth mode).
     *
     * @param accessKeyId access key ID
     * @param accessKeySecret access key secret
     */
    public static void init(final String accessKeyId, final String accessKeySecret) {
        Uni.setAccessKeyId(accessKeyId);
        Uni.setAccessKeySecret(accessKeySecret);
    }

    /**
     * Set the accessKeyId.
     *
     * @param accessKeyId access key ID
     */
    public static void setAccessKeyId(final String accessKeyId) {
        Uni.accessKeyId = accessKeyId;
    }

    /**
     * Set the accessKeySecret.
     *
     * @param accessKeySecret access key secret
     */
    public static void setAccessKeySecret(final String accessKeySecret) {
        Uni.accessKeySecret = accessKeySecret;
    }

    /**
     * Set the endpoint.
     *
     * @param endpoint custom API gateway endpoint
     */
    public static void setEndpoint(final String endpoint) {
        Uni.endpoint = endpoint;
    }

    /**
     * Use a custom rest client.
     *
     * @param client rest client to use
     */
    public static void setClient(final UniClient client) {
        synchronized (Uni.class) {
            Uni.client = client;
        }
    }

    /**
     * Returns (and initializes if not initialized) the Uni Client.
     *
     * @return the Uni Client
     */
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
