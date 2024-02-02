package com.springapps.shop.services;

import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.dtos.ProductRequestDTO;
import com.springapps.shop.entities.Category;
import com.springapps.shop.entities.Product;
import com.springapps.shop.repositories.CategoryRepository;
import com.springapps.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

@Autowired
    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        Product productToBeSaved = new Product();
        productToBeSaved.setName(productRequestDTO.getName());
        productToBeSaved.setPrice(productRequestDTO.getPrice());
        productToBeSaved.setCategory(category);
        if(productToBeSaved.getStock()==null){
            productToBeSaved.setStock(1);
        }else {
            productToBeSaved.setStock(productToBeSaved.getStock() + 1);
        }
        return productRepository.save(productToBeSaved);
    }

    public Product findById(Long id){
          return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("product not found"));
    }


}
