package com.rest.api.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component("DataSourceService")
public class DataSourceService {

    Logger logger = LoggerFactory.getLogger(DataSourceService.class);

    @Autowired
    public Connection connection;

       // querylimit
    @Value("${querylimit}")
    private String querylimit;

    /**
     * @param query - search msg
     * @return List of Twitter2
     */
    public List<Product> search(String query) {

        List<Product> crimes = new ArrayList<>();
        String sql = "";
        try {
            logger.error("Query: " + query);
            logger.error("Limit:" + querylimit);
            if (connection == null) {
                logger.error("Null connection");
                return crimes;
            }
            if (query == null || query.trim().length() <= 0) {
                query = "";
                sql = "select * from product";
            } else {
                query = "%" + query.toUpperCase() + "%";
                sql = "select * from product WHERE upper(product_code) like ? LIMIT ?";
            }

            PreparedStatement ps = connection.prepareStatement(sql);
            if (query.length() > 1) {
                ps.setString(1, query);
                ps.setInt(2, Integer.parseInt(querylimit));
            }
            ResultSet res = ps.executeQuery();
            Product product = null;
            while (res.next()) {
                String question = res.getString("question");
                String productName= res.getString("productName");
                String createdBy = res.getString("createdBy");
                String type = res.getString("type");
                product.setQuestion(question);
                product = new Product(productName,createdBy,type,question);
                crimes.add(product);
            }

            res.close();
            ps.close();
            connection.close();
            res = null;
            ps = null;
            connection = null;
            product = null;

            logger.error("Size=" + crimes.size());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in search", e);
        }

        return crimes;
    }
}