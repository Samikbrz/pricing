package com.example.pricing.service;

import com.example.pricing.constant.Messages;
import com.example.pricing.exception.AlreadyExistException;
import com.example.pricing.exception.NotFoundException;
import com.example.pricing.model.Product;
import com.example.pricing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product saveProduct(Product product) {
        if (!findProductByName(product.getName())) {
            throw new AlreadyExistException(Messages.PRODUCT_ALREADY_EXIST);
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Product product) {
        if (findProductById(product.getId())) {
            throw new NotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        return productRepository.save(product);
    }

    private boolean findProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.isEmpty();
    }

    private boolean findProductByName(String productName) {
        Optional<Product> optionalProduct = productRepository.findProductByName(productName);
        return optionalProduct.isEmpty();
    }
}
