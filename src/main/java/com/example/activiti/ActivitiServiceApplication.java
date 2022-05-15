package com.example.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActivitiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiServiceApplication.class, args);
    }

//    @Override
//    public void run(String... args) {
//
////        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
////                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti")
////                .setJdbcUsername("root")
////                .setJdbcPassword("root")
////                .setJdbcDriver("com.mysql.jdbc.Driver")
////                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//
//        //ProcessEngine processEngine = cfg.buildProcessEngine();
//
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        System.out.println(processEngine.getName());
//    }

    @Bean ProcessEngine getProcessEngine(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        return processEngine;
    }
}
