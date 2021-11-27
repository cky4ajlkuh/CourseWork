package com.example.demo.service;

import com.example.demo.entity.List;
import com.example.demo.entity.Word;
import com.example.demo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public Word findByWord(String word){
        return wordRepository.findByWord(word);
    }

    public Word update(Word word, List list){
        word.setList(list);
        return wordRepository.save(word);
    }

}
