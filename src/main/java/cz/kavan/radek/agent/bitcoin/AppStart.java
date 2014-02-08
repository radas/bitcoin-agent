package cz.kavan.radek.agent.bitcoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppStart {

    private static final Logger logger = LoggerFactory.getLogger(AppStart.class);

    public static void main(String[] args) {
        logger.info("Starting application!");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    }

}
