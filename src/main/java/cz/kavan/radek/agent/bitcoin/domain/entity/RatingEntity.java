package cz.kavan.radek.agent.bitcoin.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstamp_rating")
public class RatingEntity {

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Id
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
