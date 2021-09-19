package com.imager.core;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

  @Autowired
  DataSource dataSource;

  public static void main(String[] args) {

    SpringApplication.run(CoreApplication.class, args);    


  }
  
//  @PostConstruct
//  public void initDB() throws SQLException {
//
//    Statement stmt = dataSource.getConnection().createStatement();
//    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS image_model(id bigserial not null, file_name varchar(255), \"data\" oid, primary key (id))");
//  }

}
