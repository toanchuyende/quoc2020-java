//package vn.agileviet.quoc2020;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//@SpringBootApplication
//public class Configuration extends MainApplication {
//
//    @Bean
//    public ValidatingMongoEventListener validatingMongoEventListener() {
//        return new ValidatingMongoEventListener(validator());
//    }
//
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        return new LocalValidatorFactoryBean();
//    }
//}
