package es.enxenio.sife1701.controller.custom.util;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication
 */
public class JWTToken {

    private String tokenId;

    public JWTToken(String tokenId) {
        this.tokenId = tokenId;
    }

    @JsonProperty("token_id")
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

}
