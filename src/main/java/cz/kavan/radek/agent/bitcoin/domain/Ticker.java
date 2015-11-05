package cz.kavan.radek.agent.bitcoin.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {

    private BigDecimal bid;
    private BigDecimal ask;
    private long timestamp;
    private BigDecimal high;
    private BigDecimal last;
    private BigDecimal volume;
    private BigDecimal low;
    private BigDecimal vwap;

    public Ticker() {
        super();
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(final BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(final BigDecimal last) {
        this.last = last;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(final BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(final BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(final BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(final BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getVwap() {
        return vwap;
    }

    public void setVwap(final BigDecimal vwap) {
        this.vwap = vwap;
    }

}
