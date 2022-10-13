package pl.sebastian.CMS.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrderConfig {

    @Bean
    CommandLineRunner commandLineRunner(OrderRepository orderRepository){
        return args -> {

            Order pierwsze = new Order("SAMSUNG GALAXY J5 2017", 1000, Order.OrderPriority.INPOST , Order.OrderStatus.ZLOZONE);
            Order drugie = new Order("MOTOROLA MOTO G20", 689, Order.OrderPriority.POCZTA, Order.OrderStatus.ZLOZONE);


            orderRepository.saveAll(List.of(pierwsze,drugie));
        };
    }

}
