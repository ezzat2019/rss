package com.example.programmer.rss.models;

public class ModelMain  {
    private String uri;
    private int  source;

    public ModelMain(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public ModelMain(String uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public ModelMain(String uri) {
        this.uri = uri;
    }

    private String name;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelMain() {
    }
}
