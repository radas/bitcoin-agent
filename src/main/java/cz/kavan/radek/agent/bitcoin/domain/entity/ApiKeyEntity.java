package cz.kavan.radek.agent.bitcoin.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstamp_api")
public class ApiKeyEntity {

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "secret", nullable = false)
    private String apiSecret;

    @Column(name = "client_id", nullable = false)
    private String apiClientId;

    public ApiKeyEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getApiClientId() {
        return apiClientId;
    }

    public void setApiClientId(String apiClientId) {
        this.apiClientId = apiClientId;
    }

}
