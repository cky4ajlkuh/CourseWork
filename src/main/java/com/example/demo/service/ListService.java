package com.example.demo.service;

import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    @Autowired
    private ListRepository listRepository;

    public List create(Owner owner) {
        List list = new List();
        list.setOwner(owner);
        return listRepository.save(list);
    }

    public List findByID(Integer id) {
        return listRepository.getById(id);
    }
}
