package com.lab.html_editor.utils.command;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlDocumentManager;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;

public class ConsoleHtmlReadFileCommand implements ConsoleCommand{
    private final HtmlController controller;
    private HtmlDocumentManager manager;
    private final String filePath;
    private HtmlDocument currentDocument;
    private HtmlDocument previousDocument;

    public ConsoleHtmlReadFileCommand(HtmlController controller,String filePath){
        this.controller=controller;
        this.filePath=filePath;
        this.manager=controller.getDocumentManager();
        if(controller.hasActiveDocument()){
            this.previousDocument=controller.getActiveDocument();
        }
        
    }

    @Override
    public boolean execute(){
        try{
            HtmlDocument document=controller.getIOManager().read(filePath,new HtmlService());
            this.currentDocument=document;
            manager.addEditor(document);
            manager.setActiveEditor(document.getDocumentName());
            document.notifyObservers(new StatusEvent("Successfully read document from "+filePath, true));
            return true;
        }catch(Exception e){
            throw new RuntimeException("Failed to read document because "+e.getMessage());
           // previousDocument.notifyObservers(new StatusEvent("Failed to read document", false,e));
            //return false;
        }
        
    }

    @Override
    public boolean undo(){
        try{
            manager.setActiveEditor(previousDocument.getDocumentName());
            previousDocument.notifyObservers(new StatusEvent("Successfully undo", true));
            return true;
        }catch(Exception e){
            throw new RuntimeException("Failed to undo");
            //controller.getDocument().notifyObservers(new StatusEvent("Failed to undo init document", false));
        }
        
    }

    @Override 
    public boolean redo(){
        try{
            manager.setActiveEditor(currentDocument.getDocumentName());
            currentDocument.notifyObservers(new StatusEvent("Successfully redo init document", true));
            return true;
        }catch(Exception e){
            throw new RuntimeException("Failed to redo initialize");
            //controller.getDocument().notifyObservers(new StatusEvent("Failed to redo init document", false));
        }
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }
}
