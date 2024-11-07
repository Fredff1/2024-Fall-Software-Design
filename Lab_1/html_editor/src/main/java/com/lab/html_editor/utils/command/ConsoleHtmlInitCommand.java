package com.lab.html_editor.utils.command;



import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlDocumentManager;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;

public class ConsoleHtmlInitCommand implements ConsoleCommand{
    private final HtmlDocumentManager manager;
    private final String documentName;
    private final String title;
    private HtmlDocument currentDocument;
    private HtmlDocument previousDocument;

    public ConsoleHtmlInitCommand(HtmlDocumentManager manager,String documentName,String title){
        this.manager=manager;
        this.documentName=documentName;
        this.title=title;
        if(manager.hasActiveDocument()){
            this.previousDocument=manager.getActiveDocument();
        }
        
        
    }

    @Override
    public boolean execute(){
        try{
            HtmlDocument document=new HtmlDocument(documentName, title,new HtmlService());
            this.currentDocument=document;
            manager.addDocumentSession(document);
            manager.setActiveSession(currentDocument.getDocumentName());
            document.notifyObservers(new StatusEvent("Successfully init document", true));
            
            return true;
        }catch(Exception e){
            if(previousDocument==null){
                return false;
            }
            previousDocument.notifyObservers(new StatusEvent("Failed to init document", false,e));
            return false;
        }
    }

    @Override
    public boolean undo(){
        try{
            manager.setActiveSession(previousDocument.getDocumentName());
            previousDocument.notifyObservers(new StatusEvent("Successfully undo init document", true));
            return true;
        }catch(Exception e){
            throw new RuntimeException("Failed to undo initialize");
            //controller.getDocument().notifyObservers(new StatusEvent("Failed to undo init document", false));
        }
        
    }

    @Override 
    public boolean redo(){
        try{
            manager.setActiveSession(currentDocument.getDocumentName());
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
