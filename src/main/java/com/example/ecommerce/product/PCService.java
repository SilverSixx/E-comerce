package com.example.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PCService {
    private final PCRepository pcRepository;

    public List<PC> getPCs() {
        return pcRepository.findAll();
    }
    public void uploadPC(PC pc){
        pcRepository.save(pc);
    }
    @Transactional
    public void changePC(Long id, String name, String manufacturer, String model, byte[] image, Double price, Integer inStock){
        PC pcFoundById = pcRepository.findById(id).orElseThrow(
                () -> new PCNotFoundByIDException("PC with the id " + id + " does not exist in the database.")
        );
        if(name != null && name.length() > 0 && !name.equals(pcFoundById.getName())){
            pcFoundById.setName(name);
        }
        if(manufacturer != null && manufacturer.length() > 0 && !manufacturer.equals(pcFoundById.getManufacturer())){
            pcFoundById.setManufacturer(manufacturer);
        }
        if(model != null && model.length() > 0 && !model.equals(pcFoundById.getDescription())){
            pcFoundById.setDescription(model);
        }
        if(image != null){
            pcFoundById.setImage(image);
        }
        if(price != null && price > 0 && !price.equals(pcFoundById.getPrice())){
            pcFoundById.setPrice(price);
        }
        if(inStock != null && inStock > 0 && !inStock.equals(pcFoundById.getInStock())){
            pcFoundById.setInStock(inStock);
        }
    }
}
