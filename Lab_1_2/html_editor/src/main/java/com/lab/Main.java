package com.lab;

import com.lab.html_editor.app.HtmlEditorApp;


public class Main {

    private final HtmlEditorApp app;

    public Main(){
        this.app=new HtmlEditorApp();
    }

    public void start(){
        app.start();
    }
    public static void main(String[] args) {
        final Main main=new Main();
        main.start();
    }
}