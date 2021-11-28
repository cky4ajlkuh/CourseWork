package com.example.demo.service;

import com.example.demo.entity.Owner;
import com.example.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner findByID(Integer id) {
        return ownerRepository.getById(id);
    }

    public Owner findByName(String name) {
        return ownerRepository.findByName(name);
    }

    public Owner create(String name) {
        return ownerRepository.save(new Owner(name));
    }

}
