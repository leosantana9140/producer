package br.com.producer.controller;

import br.com.lib.model.Product;
import br.com.producer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.lib.constant.RabbitMQConstant.PRODUCT_QUEUE_NAME;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PutMapping
    private ResponseEntity updateInventory(@RequestBody Product product) {
        this.productService.sendMessage(PRODUCT_QUEUE_NAME, product);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
