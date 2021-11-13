package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "word")
public class Word {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_form")
    private String first_form;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private List list;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "second_form")
    private String second_form;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "translation")
    private String translation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_form() {
        return first_form;
    }

    public void setFirst_form(String first_form) {
        this.first_form = first_form;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSecond_form() {
        return second_form;
    }

    public void setSecond_form(String second_form) {
        this.second_form = second_form;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

}
