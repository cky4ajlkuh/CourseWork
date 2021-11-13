package com.example.demo;

import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.service.ListService;
import com.example.demo.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.OneToMany;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ListService listService;

    @Autowired
    private OwnerService ownerService;

    @Test
    void contextLoads() {
        Owner owner = ownerService.findByID(1);
        List list = listService.create(owner);
    }

}
