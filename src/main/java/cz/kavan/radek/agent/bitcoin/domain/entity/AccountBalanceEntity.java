package cz.kavan.radek.agent.bitcoin.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstamp_balance")
public class AccountBalanceEntity {

    @Column(name = "btc_available", nullable = false)
    private BigDecimal btc_available;

    @Column(name = "usd_available", nullable = false)
    private BigDecimal usd_available;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public AccountBalanceEntity() {
        super();
    }

    public BigDecimal getBtc_available() {
        return btc_available;
    }

    public void setBtc_available(BigDecimal btc_available) {
        this.btc_available = btc_available;
    }

    public BigDecimal getUsd_available() {
        return usd_available;
    }

    public void setUsd_available(BigDecimal usd_available) {
        this.usd_available = usd_available;
    }

}
