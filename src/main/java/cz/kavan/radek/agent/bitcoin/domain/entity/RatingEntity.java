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

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    public RatingEntity() {
        super();
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

}
