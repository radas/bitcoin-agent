package cz.kavan.radek.agent.bitcoin.auth;

import org.springframework.http.HttpEntity;

public interface BitstampAuth {

    HttpEntity<?> getHttpAuthEntity();

}
