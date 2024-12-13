package com.lab.html_editor.view;

import com.lab.html_editor.controller.HtmlEditor;
import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.observer.Observable;
import com.lab.html_editor.utils.observer.Observer;

import java.util.*;

/**
 * 处理控制台打印的组件
 */
public class HtmlView implements Observable{
    private final List<Observer> observers;
    private HtmlDocument activDocument;

    public HtmlView(){
        this.observers=new ArrayList<>();
    }


   

    public void setActiveDocument(HtmlDocument document){
        this.activDocument=document;
    }

    public HtmlDocument getActiveDocument(){
        return this.activDocument;
    }

    public void displaySplitLine(){
        System.out.println("-".repeat(125));
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayMessage(String message) {
        System.out.println("[Console] "+message);
    }

    

    public void displayErrorMessage(String message){
        System.out.println("[Error] "+message);
    }

    public void displaySuccessMessage(String message){
        System.out.println("[Success] "+message);
    }

    public void displayDefault(String message){
        System.out.println(message);
    }

    public void displayInfo(String message){
        System.out.println("[Info] "+message);
    }

    public void displayWorkspaceFolder(String message){
        displaySplitLine();
        displayDefault("Current workspace directory: ");
        displayDefault(message);
        displaySplitLine();
    }



    public void displayMessageInOneLine(String message){
        System.out.print("[Console] "+message);
    }

    
    public void updateView(HtmlEditor editor) {
        if(editor==null){
            displayInfo("No Editor to show");
            return;
        }
        var document=editor.getDocument();
        clearConsole();
        var visitor = document.getRenderInfo();
        String renderedContent = visitor.geStringRepresentation();
        displaySplitLine();
        System.out.println("[Current Document]");
        System.out.println("[Document name] "+editor.getFileNode().getName());
        System.out.println("[Update status] "+editor.isUpdated());
        displaySplitLine();
        System.out.println(renderedContent+"\n");
        displaySplitLine();
    }

    

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(Event event){
        for(var observer:observers){
            observer.update(event);
        }
    }

    public void displayWelComeStage(){
        String top="HTML Editor";
        String version="Version 1.0.0";
        String copyRight="CopyRight: None";
        displaySplitLine();
        System.out.println(" ".repeat(40)+top);
        System.out.println(" ".repeat(39)+version);
        System.out.println(" ".repeat(38)+copyRight);
        displaySplitLine();
        
        
    }
}
