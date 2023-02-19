package org.sid.billing_services;

import org.sid.billing_services.entities.*;
import org.sid.billing_services.models.*;
import org.sid.billing_services.repositories.*;
import org.sid.billing_services.feign.CustomerRestClient;
import org.sid.billing_services.feign.ProductItemRestClient;
import org.sid.billing_services.web.BillingRestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class BillingServicesApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(BillingServicesApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository br,
                            ProductItemRepository pir,
                            CustomerRestClient customerRestClient,
                            ProductItemRestClient productItemRestClient)
    {
        return args -> {
            Customer customer = customerRestClient.getCustomer(1L);
            Bill bill = new Bill(null, new Date(), customer.getId(), null, null);
            br.save(bill);
            PagedModel<Product> productsPage = productItemRestClient.pageProducts(0, 20);
            productsPage.forEach(p -> {
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setProductId(p.getId());
                productItem.setQuantity(new Random().nextInt(50) + 1);
                productItem.setBill(bill);
                pir.save(productItem);
            });
        };
    }

}