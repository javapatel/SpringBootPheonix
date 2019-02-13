package com.rest.api.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApiApplication {

    @Value("${purl}")
    private String databaseUri;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public Connection connection() throws ClassNotFoundException {
        Connection con = null;
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            con = DriverManager.getConnection(databaseUri);
            /*con = DataSourceBuilder.create()
                    .driverClassName("org.apache.phoenix.jdbc.PhoenixDriver")
                    .url(databaseUri)
                    .build().getConnection();*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        //dataSource.setDriverClassName();
        System.out.println("Initialized hbase");

        return con;
    }

    /*
    * @Bean(name="phoenixDataSource")
    @DependsOn(value = "placeholderConfigurer")
    public DataSource phoenixDataSource() {
        SimpleDriverDataSource phoenixDataSource = new SimpleDriverDataSource();
        phoenixDataSource.setUrl( phoenixUrl );
        try {
            Class<?> driverClass = this.getClass().getClassLoader().loadClass("org.apache.phoenix.jdbc.PhoenixDriver");
            phoenixDataSource.setDriverClass((Class<? extends Driver>) driverClass);
        } catch( ClassNotFoundException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       );
        return phoenixDataSource;
    }

    @Bean(name = "phoenixJdbcTemplate")
    public JdbcTemplate phoenixJdbcTemplate(@Qualifier("phoenixDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
    * */
}

