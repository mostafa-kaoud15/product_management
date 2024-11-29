package com.springboot.spring_security.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.spring_security.dto.ProductDTO;
import com.springboot.spring_security.exception.ProductNotFoundException;
import com.springboot.spring_security.model.Product;
import com.springboot.spring_security.reposiroty.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;

    public ResponseEntity<ProductDTO> insertProduct(Product product) {
        Product savedProduct = productRepo.save(product);
        ProductDTO productDTO = new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }   
    public ResponseEntity<ProductDTO> updateProduct(Long id, Product product) {
        Product findProduct = productRepo.findById(id).orElseThrow(()-> new ProductNotFoundException(product.getId()));
        findProduct.setName(product.getName());
        findProduct.setAmount(product.getAmount());
        findProduct.setPrice(product.getPrice());
        Product updatedProduct = productRepo.save(findProduct);
        return ResponseEntity.ok(new ProductDTO(id, updatedProduct.getName(), updatedProduct.getPrice()));
    }

    public ResponseEntity<ProductDTO> getProductById(long id) {
        Product findProduct = productRepo.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        return ResponseEntity.ok(new ProductDTO(id, findProduct.getName(), findProduct.getPrice()));
    }
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<Product> allProduct = productRepo.findAll();
        List<ProductDTO> allProductDTO = allProduct.stream().map((product)->new ProductDTO(product.getId(), product.getName(), product.getPrice())).toList();
        return ResponseEntity.ok(allProductDTO);
    }

    public void removeProduct(long id) {

        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepo.delete(product);
    }
}
