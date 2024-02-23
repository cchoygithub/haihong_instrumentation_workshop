
package com.nr.workshop.orderservice.controller;

import com.nr.workshop.orderservice.data.Product;
import com.nr.workshop.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final Map<Long, String> staticProducts = new HashMap<>() {{
        put(10001L, "Apple is always available! won't delete");
        put(10002L, "Samsung is always available! keep it");
        put(10003L, "Huawei is always available! save it");
    }};

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

       String productStatus= checkID(id, staticProducts);

        if (productStatus == "ok") {
            return ResponseEntity.ok(productService.deleteProduct(id));
        }else{
            return ResponseEntity.badRequest().body(productStatus);
        }

    }

    public static String checkID(Long id, Map<Long, String> staticProducts) {
        // Check if the ID exists in the map
        if (staticProducts.containsKey(id)) {
            return staticProducts.get(id);
        } else {
            return "ok";
        }
    }

}
