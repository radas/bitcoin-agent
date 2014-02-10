package cz.kavan.radek.agent.bitcoin.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "bitstamp_ticker")
public class TickerEntity {

    @Column(name = "sell", nullable = false)
    private BigDecimal bid;

    @Column(name = "buy", nullable = false)
    private BigDecimal ask;

    @Column(name = "sell_diff", nullable = false)
    private BigDecimal sellDiff;

    @Column(name = "buy_diff", nullable = false)
    private BigDecimal buyDiff;

    @Column(name = "trade_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestamp;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public TickerEntity() {
        super();
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getSellDiff() {
        return sellDiff;
    }

    public void setSellDiff(BigDecimal sellDiff) {
        this.sellDiff = sellDiff;
    }

    public BigDecimal getBuyDiff() {
        return buyDiff;
    }

    public void setBuyDiff(BigDecimal buyDiff) {
        this.buyDiff = buyDiff;
    }

}
