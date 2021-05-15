package com.apistd.uni;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;

public class UniResponse {
    public static final String REQUEST_ID_HEADER_KEY = "x-uni-request-id";
    public String requestId;
    public String code;
    public String message;
    public int status;
    public JSONObject data = null;
    public Object raw;

    /**
     * Create a new Uni Response.
     *
     * @param response raw HTTP response
     * @throws UniException if catch error
     */
    public UniResponse(final HttpResponse<JsonNode> response) throws UniException {
        JSONObject body = response.getBody().getObject();
        this.status = response.getStatus();
        this.requestId = response.getHeaders().getFirst(UniResponse.REQUEST_ID_HEADER_KEY);
        this.raw = body;

        if (body != null && body.has("code")) {
            String code = body.getString("code");
            String message = body.getString("message");

            if (!code.equals("0")) {
                throw new UniException(message, code, this.requestId);
            }

            this.code = code;
            this.message = message;
            this.data = body.has("data") ? body.getJSONObject("data") : null;
        }
        else {
            throw new UniException(response.getStatusText(), "-1");
        }
    }
}
