package com.example.productcatalog.demo.controller;


import com.example.productcatalog.demo.entity.Product;
import com.example.productcatalog.demo.repository.ProductRepository;
import com.example.productcatalog.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products") //this is the base url
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/health")
    public String  HealthCheck(){
        return "The app is up and running!";
    }

    //Get all products
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String sortBy) {
        return productService.getAllProducts(category, minPrice, maxPrice, sortBy);
    }

    //Get items by category
    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Boolean availability,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order

    ){
        return productService.filterProducts(minPrice, maxPrice, brand, availability, sortBy, order);
    }

    @GetMapping("/paged")
    public Page<Product> getProductsUsingPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return productService.getProductsUsingPageable(page, size);
    }

    @PatchMapping("/{id}/soft-delete")
    public List<Product> getSoftDelete(@PathVariable int id){
        return productService.getActiveSoftDeletedIteam(id);
    }

    //Get a single product by ID
    @GetMapping("{id}")
    public Product getProductById(@PathVariable int id){
        return productService.getProductById(id);
    }

    //add a new product
    @PostMapping()
    public Product createProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    // PUT to update an existing product by ID
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    // DELETE a product by ID
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);

    }
    // Search products by name
    @GetMapping("/search")
    public List<Product> getProductByName(@RequestParam("name") String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/searchByCategory")
    public List<Product> getProductByCategory(@RequestParam("category") String category){
        return productService.getProductByCategory(category);
    }

}
