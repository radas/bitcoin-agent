package cz.kavan.radek.agent.bitcoin.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstamp_rating")
public class RatingEntity {

    @Column(name = "sell_rating", nullable = false)
    private BigDecimal sellRating;

    @Column(name = "buy_rating", nullable = false)
    private BigDecimal buyRating;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public RatingEntity() {
        super();
    }

    public BigDecimal getSellRating() {
        return sellRating;
    }

    public void setSellRating(BigDecimal sellRating) {
        this.sellRating = sellRating;
    }

    public BigDecimal getBuyRating() {
        return buyRating;
    }

    public void setBuyRating(BigDecimal buyRating) {
        this.buyRating = buyRating;
    }

}
