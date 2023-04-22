package com.example.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<Product>> getPCs(){
        return ResponseEntity.ok().body(productService.getPCs());
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPC(
            @RequestParam("name") String name,
            @RequestParam("manufacturer") String manufacturer,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam("price") Double price,
            @RequestParam("inStock") Integer inStock){
        try {
            byte[] imageData = image.getBytes();
            if(imageData.length > 5242880){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        "Cant update an image greater than 5MB, please use a different image"
                );
            }
            Product product = new Product(name, manufacturer, description, imageData, price, inStock);
            productService.uploadPC(product);
        } catch (IOException e) {
            return ResponseEntity.ok().body("Error upload product.");
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> changePC(
            @PathVariable("id") Long id,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) String manufacturer,
            @RequestParam (required = false) String model,
            @RequestParam (required = false) MultipartFile image,
            @RequestParam (required = false) Double price,
            @RequestParam (required = false) Integer inStock) {
        try{

            //pcService.changePC(id, name, manufacturer, model, image, price, inStock);
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundByIDException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Product with the id " + id + " does not exist in the database.");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Product with the id " + productId + " does not exist in the database.");
        }
    }

}
