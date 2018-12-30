package com.example.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String quest;
    String variants;
    String answer;
    @ManyToOne
    @JoinColumn(name = "test_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Test test;
    TypeOfQuestion typeOfQuestion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getVariants() {
        return variants;
    }

    public void setVariants(String variants) {
        this.variants = variants;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public TypeOfQuestion getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }
}
