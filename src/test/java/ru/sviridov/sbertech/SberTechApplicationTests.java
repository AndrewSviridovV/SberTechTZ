package ru.sviridov.sbertech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.sviridov.sbertech.model.Product;
import ru.sviridov.sbertech.repo.db1.ProductRepository1;



import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SberTechApplicationTests {

    @Test
    void contextLoads() {

    }



        @Autowired
        private ProductRepository1 productRepository1;

        @Test
        @Transactional("productTransactionManager1")
        public void create_check_product() {
            Product product = new Product("name1","Running Shoes");
            product = productRepository1.save(product);

            assertNotNull(productRepository1.findById(product.getId()));
        }



}

