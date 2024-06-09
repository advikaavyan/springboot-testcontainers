package com.example;

import com.advikaavyan.examples.testcontainers.Emp;
import com.advikaavyan.examples.testcontainers.LoggerEnum;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class EmpControllerTest {
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.1.0").withDatabaseName("testcontainer").withUsername("root").withPassword("root");


    @DynamicPropertySource
    static void configDymanic(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static public void beforeAlltests() {
        log.info(" {} mySQLContainer.getJdbcUrl() {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getJdbcUrl());
        log.info(" {} mySQLContainer.getContainerId {}   ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getContainerId());
        log.info(" {} mySQLContainer.getPassword() {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getPassword());
        log.info(" {} mySQLContainer.getUsername()  {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getUsername());
        log.info(" {} mySQLContainer.getDatabaseName()  {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getDatabaseName());
        log.info(" {} mySQLContainer.getDriverClassName() {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getDriverClassName());
        log.info(" {} mySQLContainer.getContainerName()  {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getContainerName());
        log.info(" {} mySQLContainer.getDockerImageName()  {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getDockerImageName());
        log.info(" {} mySQLContainer.getTestQueryString()  {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getTestQueryString());
        log.info(" {} mySQLContainer.getHost() {}  ", LoggerEnum.LOG_PREFIX.getText(), mySQLContainer.getHost());


    }

    @Test
    void save1Record() throws Exception {
        List<Emp> emps = getEmps();
        log.info(" {}  Before Save {}  ", LoggerEnum.LOG_PREFIX.getText(), (emps == null ? 0 : emps.size()));
        for (int i = 0; i < 10; i++) {
            Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/emps", Emp.builder().name("TEXT_NAME" + new Date()).build(),
                    Emp.class));
        }

        emps = getEmps();
        log.info(" {}  After Save {}  ", LoggerEnum.LOG_PREFIX.getText(), (emps == null ? 0 : emps.size()));
    }


    private List<Emp> getEmps() throws Exception {

        List<Emp> emps = this.restTemplate.getForObject("http://localhost:" + port + "/emps",
                List.class);

        return emps;
    }


}
