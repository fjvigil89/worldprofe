package es.enxenio.sife1701.model.social;

import es.enxenio.sife1701.model.generic.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A Social user.
 */
@Entity
@Table(schema = "comun", name = "usuario_social")
public class UsuarioSocial extends GenericEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "user_id", length = 255, nullable = false)
    private String userId;

    @NotNull
    @Column(name = "provider_id", length = 255, nullable = false)
    private String providerId;

    @NotNull
    @Column(name = "provider_user_id", length = 255, nullable = false)
    private String providerUserId;

    @NotNull
    @Column(nullable = false)
    private Long rank;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Column(name = "profile_url", length = 255)
    private String profileURL;

    @Column(name = "image_url", length = 255)
    private String imageURL;

    @NotNull
    @Column(name = "access_token", length = 255, nullable = false)
    private String accessToken;

    @Column(length = 255)
    private String secret;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "expire_time")
    private Long expireTime;

    //

    public UsuarioSocial() {
    }

    public UsuarioSocial(String userId,
                         String providerId,
                         String providerUserId,
                         Long rank,
                         String displayName,
                         String profileURL,
                         String imageURL,
                         String accessToken,
                         String secret,
                         String refreshToken,
                         Long expireTime) {
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileURL = profileURL;
        this.imageURL = imageURL;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    //

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    //

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioSocial user = (UsuarioSocial) o;

        if (!getId().equals(user.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuarioSocial{" +
            "id=" + getId() +
            ", userId=" + userId +
            ", providerId='" + providerId + '\'' +
            ", providerUserId='" + providerUserId + '\'' +
            ", rank=" + rank +
            ", displayName='" + displayName + '\'' +
            ", profileURL='" + profileURL + '\'' +
            ", imageURL='" + imageURL + '\'' +
            ", accessToken='" + accessToken + '\'' +
            ", secret='" + secret + '\'' +
            ", refreshToken='" + refreshToken + '\'' +
            ", expireTime=" + expireTime +
            '}';
    }
}
