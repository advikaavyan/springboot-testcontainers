package com.advikaavyan.examples.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class EmpController {

    private final EmpService empService;

    EmpController(EmpService empService) {
        this.empService = empService;
    }


    @PostMapping("/emps")
    public Emp createNewEmp(@RequestBody Emp emp) throws IOException {
        log.info(" {} New EMP Going to be saved {} ", LoggerEnum.LOG_PREFIX.getText(), emp.toString());
        return empService.saveEmp(emp);

    }

    @GetMapping("/emps")
    public List<Emp> getAllNews() {
        List<Emp> emps = empService.getAllEmps();
        log.info(" {} Total Number of employees in database  {} ", LoggerEnum.LOG_PREFIX.getText(), emps.toString());
        return emps;
    }


}
