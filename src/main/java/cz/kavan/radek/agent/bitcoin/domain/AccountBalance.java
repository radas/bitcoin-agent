package cz.kavan.radek.agent.bitcoin.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalance {

    @JsonProperty("usd_balance")
    private BigDecimal usdBalance;

    @JsonProperty("btc_balance")
    private BigDecimal btcBalance;

    @JsonProperty("usd_reserved")
    private BigDecimal usdReserved;

    @JsonProperty("btc_reserved")
    private BigDecimal btcReserved;

    @JsonProperty("usd_available")
    private BigDecimal usdAvailable;

    @JsonProperty("btc_available")
    private BigDecimal btcAvailable;

    private BigDecimal fee;
    private String error;

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

    public BigDecimal getUsdAvailable() {
        return usdAvailable;
    }

    public void setUsdAvailable(BigDecimal usdAvailable) {
        this.usdAvailable = usdAvailable;
    }

    public BigDecimal getBtcAvailable() {
        return btcAvailable;
    }

    public void setBtcAvailable(BigDecimal btcAvailable) {
        this.btcAvailable = btcAvailable;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
