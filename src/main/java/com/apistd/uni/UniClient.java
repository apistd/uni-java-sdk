package com.apistd.uni;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class UniClient {

    public static final String USER_AGENT = "uni-java-sdk" + "/" +  Uni.VERSION;
    public static final int HTTP_STATUS_CODE_CREATED = 201;
    public static final int HTTP_STATUS_CODE_NO_CONTENT = 204;
    public static final int HTTP_STATUS_CODE_OK = 200;

    private final String accessKeyId;
    private final String accessKeySecret;
    private final String endpoint;
    private final String signingAlgorithm;

    protected UniClient(Builder b) {
        this.accessKeyId = b.accessKeyId;
        this.accessKeySecret = b.accessKeySecret;
        this.endpoint = b.endpoint;
        this.signingAlgorithm = b.signingAlgorithm;
    }

    private static String getSignature(final String message, final String secretKey) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            hmac.init(secretKeySpec);

            byte[] bytes = hmac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    private static String queryStringify(final Map<String, Object> params) {
        Map<String, Object> sortedMap = new TreeMap<>(new MapKeyComparator());
        sortedMap.putAll(params);
        StringBuilder sb = new StringBuilder();
        Iterator<?> iter = sortedMap.entrySet().iterator();

        while (iter.hasNext()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            Entry<?, ?> entry = (Entry<?, ?>) iter.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sb.toString();
    }

    private Map<String, Object> sign(final Map<String, Object> query) {
        if (this.accessKeySecret != null) {
            query.put("algorithm", this.signingAlgorithm);
            query.put("timestamp", new Date().getTime());
            query.put("nonce", UUID.randomUUID().toString().replaceAll("-", ""));

            String strToSign = UniClient.queryStringify(query);
            query.put("signature", UniClient.getSignature(strToSign, this.accessKeySecret));
        }

        return query;
    }

    public UniResponse request(final String action, final Map<String, Object> data) throws UniException {
        Map<String, Object> query = new HashMap<String, Object>();
        JSONObject body = new JSONObject(data);

        query.put("action", action);
        query.put("accessKeyId", this.accessKeyId);

        HttpRequestWithBody request = Unirest.post(this.endpoint);
        HttpResponse<JsonNode> response = request
            .header("User-Agent", USER_AGENT)
            .header("Content-Type", "application/json;charset=utf-8")
            .header("Accept", "application/json")
            .queryString(this.sign(query))
            .body(body)
            .asJson();

        return new UniResponse(response);
    }

    public static class Builder {
        private String accessKeyId;
        private String accessKeySecret;
        private String endpoint;
        private String signingAlgorithm;

        public Builder(final String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public Builder(final String accessKeyId, final String accessKeySecret) {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
        }

        public Builder accessKeySecret(final String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
            return this;
        }

        public Builder endpoint(final String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder signingAlgorithm(final String signingAlgorithm) {
            this.signingAlgorithm = signingAlgorithm;
            return this;
        }

        public UniClient build() {
            return new UniClient(this);
        }
    }
}

class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
