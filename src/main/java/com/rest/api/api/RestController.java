package com.rest.api.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.Action;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    DataSourceService dataSourceService;

    @RequestMapping(value = "/product/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProduct() {
        List<Product> products = new ArrayList<>();
        //products=findFromHBase();
        products = dataSourceService.search("");
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/company/{company}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByCompany(@PathVariable("company") String company) {
        List<Product> products = findFromHBase().stream().filter(product -> product.getCreatedBy().equals(company)).collect(Collectors.toList());
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> productByType(@PathVariable("type") String type) {
        List<Product> products = findFromHBase().stream().filter(product -> product.getByType().equals(type)).collect(Collectors.toList());
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    private List<Product> findFromHBase() {
        Product product1 = new Product("Music Player Walkman", "Sony", "player", "q1");
        Product product3 = new Product("Mobile", "Sony", "player", "q2");
        Product product2 = new Product("Philiphs Food Processor", "Philips", "mixer", "q3");
        return Arrays.asList(product1, product2, product3);
    }
}
