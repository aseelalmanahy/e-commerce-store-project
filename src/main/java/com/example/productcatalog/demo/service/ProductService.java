package com.example.productcatalog.demo.service;

import com.example.productcatalog.demo.exceptions.ProductNotFoundExceptions;
import com.example.productcatalog.demo.exceptions.ProductOperationException;
import com.example.productcatalog.demo.entity.Product;
import com.example.productcatalog.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);



    public Product addProduct(Product  product){

        try {
            return productRepository.save(product);
        }catch (Exception e){
            throw new ProductOperationException("Failed to add product: " + product.getName(), e);
        }

    }

    public List<Product> getAllProducts(String category, Float minPrice, Float maxPrice, String sortBy) {

        Sort sort = Sort.by(sortBy != null ? sortBy : "name");

        if (category != null && minPrice != null && maxPrice != null) {
            // Filter by category and price range
            return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, sort);
        } else if (category != null) {
            // Filter by category only
            return productRepository.findByCategory(category, sort);
        } else if (minPrice != null && maxPrice != null) {
            // Filter by price range only
            return productRepository.findByPriceBetween(minPrice, maxPrice, sort);
        } else {
            // No filters; return all products with sorting
            return productRepository.findAll(sort);
        }
    }


    public Product getProductById(int id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptions("The Id : " + id + " is not found"));
    }

    public Product updateProduct(int id, Product updateProduct){

        try {
            //first step is to check on the item if its exists
            Product productExist = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundExceptions("Product with id " + id + " does not exist"));
            //second update the item entities
            productExist.setName(updateProduct.getName());
            productExist.setDescription(updateProduct.getDescription());
            productExist.setPrice(updateProduct.getPrice());
            productExist.setCategory(updateProduct.getCategory());
            productExist.setRating(updateProduct.getRating());
            productExist.setStockQuantity(updateProduct.getStockQuantity());

            //return the updated item
            return productRepository.save(productExist);
        }
        catch (Exception e){
            logger.error("Error updating with ID: ", id, e);
            throw new ProductOperationException("Failed to update product with ID: " + id, e);
        }
    }

    public void deleteProduct(int id){
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            logger.error("Error deleting product with ID: {}", id, e);
            throw new ProductNotFoundExceptions("Failed to delete product with ID: " + id);
        }
    }

    public Page<Product> getProductsUsingPageable(int page, int size){
        Pageable pageable =  PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public List<Product> getActiveSoftDeletedIteam(int id){

            Product doesSoftItemExist = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundExceptions("The Id " + id + " doesn't exist"));
           doesSoftItemExist.setDeleted(true);

           productRepository.save(doesSoftItemExist);

          return productRepository.findAllActiveProducts();
    }

    public List<Product> getProductByName(String name){

        try {
            return productRepository.findByName(name);
        }catch (Exception e){
            logger.error("The Product name: " + name + " is not available");
            throw new RuntimeException("The Product name: " + name + " is not available");
        }

    }

    public List<Product> getProductByCategory(String category){
        try {
            return productRepository.findByCategory(category);
        }catch (Exception e){
            logger.error(category + " does not exist");
            throw new RuntimeException("This category: "+ category + " does not exist");
        }
    }

    public List<Product> filterProducts(Integer minPrice, Integer maxPrice, String brand, Boolean availability, String sortBy, String order) {

        List<Product> products = productRepository.findAll();

        if (minPrice != null){
            products = products.stream()
                    .filter(product -> product.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null){
            products = products.stream()
                    .filter(product -> product.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (brand != null){
            products = products.stream()
                    .filter(product -> product.getBrand() != null && product.getBrand().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
        }

        if (availability != null){
            products = products.stream()
                    .filter(product -> product.isAvailability() == availability)
                    .collect(Collectors.toList());
        }

        if (sortBy != null){
            Comparator<Product> comparator;
            switch (sortBy.toLowerCase()){
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;

                case "name":
                comparator = Comparator.comparing(Product::getName);
                default:
                    throw new IllegalArgumentException("Invalid sortBy parameter");
            }
            if ("desc".equalsIgnoreCase(order)){
                comparator = comparator.reversed();
            }

            products = products.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }

        return products;

    }
}
