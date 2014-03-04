package cz.kavan.radek.agent.bitcoin.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstamp_ema")
public class EmaEntity {

    @Column(name = "ema_sell", nullable = false)
    private BigDecimal emaSell;

    @Column(name = "ema_buy", nullable = false)
    private BigDecimal emaBuy;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public EmaEntity() {
        super();
    }

    public BigDecimal getEmaSell() {
        return emaSell;
    }

    public void setEmaSell(BigDecimal emaSell) {
        this.emaSell = emaSell;
    }

    public BigDecimal getEmaBuy() {
        return emaBuy;
    }

    public void setEmaBuy(BigDecimal emaBuy) {
        this.emaBuy = emaBuy;
    }

}
