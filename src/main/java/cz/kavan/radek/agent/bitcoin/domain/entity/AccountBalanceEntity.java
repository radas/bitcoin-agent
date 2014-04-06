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
    private BigDecimal btcAvailable;

    @Column(name = "usd_available", nullable = false)
    private BigDecimal usdAvailable;

    @Column(name = "usd_balance", nullable = false)
    private BigDecimal usdBalance;

    @Column(name = "btc_balance", nullable = false)
    private BigDecimal btcBalance;

    @Column(name = "usd_reserved", nullable = false)
    private BigDecimal usdReserved;

    @Column(name = "btc_reserved", nullable = false)
    private BigDecimal btcReserved;

    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public AccountBalanceEntity() {
        super();
    }

    public BigDecimal getBtcAvailable() {
        return btcAvailable;
    }

    public void setBtcAvailable(BigDecimal btcAvailable) {
        this.btcAvailable = btcAvailable;
    }

    public BigDecimal getUsdAvailable() {
        return usdAvailable;
    }

    public void setUsdAvailable(BigDecimal usdAvailable) {
        this.usdAvailable = usdAvailable;
    }

    public BigDecimal getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(BigDecimal usdBalance) {
        this.usdBalance = usdBalance;
    }

    public BigDecimal getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(BigDecimal btcBalance) {
        this.btcBalance = btcBalance;
    }

    public BigDecimal getUsdReserved() {
        return usdReserved;
    }

    public void setUsdReserved(BigDecimal usdReserved) {
        this.usdReserved = usdReserved;
    }

    public BigDecimal getBtcReserved() {
        return btcReserved;
    }

    public void setBtcReserved(BigDecimal btcReserved) {
        this.btcReserved = btcReserved;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

}
