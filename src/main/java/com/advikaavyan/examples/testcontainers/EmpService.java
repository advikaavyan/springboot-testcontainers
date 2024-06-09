package com.advikaavyan.examples.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmpService {

    @Autowired
    private EmpRepository empRepo;


    public Emp saveEmp(Emp resume) {
        return empRepo.save(resume);
    }


    public List<Emp> getAllEmps() {
        return empRepo.findAll();
    }


}



