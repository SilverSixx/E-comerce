package com.example.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getPCs() {
        return productRepository.findAll();
    }
    public void uploadPC(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        if(productRepository.findById(id).isEmpty()){
            throw new IllegalStateException("Product with the id " + id + " does not exist in the database.");
        }
        productRepository.deleteById(id);
    }
    @Transactional
    public void changePC(Long id, String name, String manufacturer, String description, MultipartFile image, Double price, Integer inStock){
        Product productFoundById = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundByIDException("PC with the id " + id + " does not exist in the database.")
        );
        if(name != null && name.length() > 0 && !name.equals(productFoundById.getName())){
            productFoundById.setName(name);
        }
        if(manufacturer != null && manufacturer.length() > 0 && !manufacturer.equals(productFoundById.getManufacturer())){
            productFoundById.setManufacturer(manufacturer);
        }
        if(description != null && description.length() > 0 && !description.equals(productFoundById.getDescription())){
            productFoundById.setDescription(description);
        }
        if(image != null){
            byte[] imgData;
            try {
                imgData = image.getBytes();
                if(imgData.length > 5242880){
                    throw new IllegalStateException("Cant update an image greater than 5MB, please use a different image");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            productFoundById.setImage(imgData);
        }
        if(price != null && price > 0 && !price.equals(productFoundById.getPrice())){
            productFoundById.setPrice(price);
        }
        if(inStock != null && inStock > 0 && !inStock.equals(productFoundById.getInStock())){
            productFoundById.setInStock(inStock);
        }
    }
}
