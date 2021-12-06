package ru.sviridov.sbertech.repo.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.sviridov.sbertech.model.Product;

@Repository
public interface ProductRepository2 extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>{
}