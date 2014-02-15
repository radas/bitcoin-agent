package cz.kavan.radek.agent.bitcoin.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-web-service-IT.xml" })
@Transactional
public class RatingEntityIT {

    @Autowired
    private RatingDAO ratingDAO;

    private RatingEntity rating;

    @Before
    public void setUp() throws Exception {
        rating = new RatingEntity();
    }

    @Test
    public void shouldHaveASessionFactory() {
        assertNotNull(ratingDAO);
    }

    @Test
    public void shouldBeAbleToPersistAnObject() throws ParseException {

        rating.setRating(new BigDecimal("800.0"));

        ratingDAO.addRating(rating);

        assertEquals(new BigDecimal("800.0"), ratingDAO.getRating());
    }
}
