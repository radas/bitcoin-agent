package cz.kavan.radek.agent.bitcoin.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bitcoin_status")
public class TickerEntity {

    @Column(name = "sell", nullable = false)
    private BigDecimal sell;

    @Column(name = "buy", nullable = false)
    private BigDecimal buy;

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

}
