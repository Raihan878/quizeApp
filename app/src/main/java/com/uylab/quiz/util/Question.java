package com.uylab.quiz.util;

import android.widget.TextView;

public class Question {
    private String id,questionText,aText,bText,cText,dText,currectAns;

    public Question() {
    }

    public Question(String id, String questionText, String aText, String bText, String cText, String dText, String currectAns) {
        this.id = id;
        this.questionText = questionText;
        this.aText = aText;
        this.bText = bText;
        this.cText = cText;
        this.dText = dText;
        this.currectAns = currectAns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getaText() {
        return aText;
    }

    public void setaText(String aText) {
        this.aText = aText;
    }

    public String getbText() {
        return bText;
    }

    public void setbText(String bText) {
        this.bText = bText;
    }

    public String getcText() {
        return cText;
    }

    public void setcText(String cText) {
        this.cText = cText;
    }

    public String getdText() {
        return dText;
    }

    public void setdText(String dText) {
        this.dText = dText;
    }

    public String getCurrectAns() {
        return currectAns;
    }

    public void setCurrectAns(String currectAns) {
        this.currectAns = currectAns;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", aText='" + aText + '\'' +
                ", bText='" + bText + '\'' +
                ", cText='" + cText + '\'' +
                ", dText='" + dText + '\'' +
                ", currectAns='" + currectAns + '\'' +
                '}';
    }
}
