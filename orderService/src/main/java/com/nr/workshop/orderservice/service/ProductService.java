package com.nr.workshop.orderservice.service;

import com.nr.workshop.orderservice.data.Product;
import com.nr.workshop.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public String deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return "deleted successfully!";
        }catch(Exception e){

        }
        return "failed to delete!";
    }
}
