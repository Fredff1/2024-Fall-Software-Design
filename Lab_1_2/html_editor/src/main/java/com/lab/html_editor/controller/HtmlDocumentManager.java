package com.lab.html_editor.controller;

import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.utils.command.ConsoleHtmlInitCommand;

import java.util.*;

public class HtmlDocumentManager {
    private final Map<String, ActiveEditor> editors = new HashMap<>();
    private ActiveEditor activeEditor;
    private final HtmlController controller;
    private ConsoleCommandManager defaultCommandManager=new ConsoleCommandManager();

    public HtmlDocumentManager(HtmlController controller){
        this.controller=controller;
    }

    
    public void addEditor(HtmlDocument document) {
        document.addObserver(controller);
        ActiveEditor editor = new ActiveEditor(document);
        editor.getCommandManager().addObserver(controller);
        editors.put(document.getDocumentName(), editor);
        setActiveEditor(document.getDocumentName());
    }

    public void addNewDefaultDocument(ConsoleHtmlInitCommand command){
        command.execute();
    }

    
    public boolean removeEditor(String documentName) {
        if (editors.containsKey(documentName)) {
            editors.remove(documentName);
            if (activeEditor != null && activeEditor.getDocument().getDocumentName().equals(documentName)) {
                activeEditor = editors.isEmpty() ? null : editors.values().iterator().next();
            }
            return true;
        }
        return false;
    }

    
    public boolean setActiveEditor(String documentName) {
        if (editors.containsKey(documentName)) {
            activeEditor = editors.get(documentName);
            return true;
        }
        return false;
    }

    
    public ActiveEditor getActiveEditor() {
        if(this.activeEditor==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeEditor;
    }

    public HtmlDocument getActiveDocument(){
        if(this.activeEditor==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeEditor.getDocument();
    }

    public ConsoleCommandManager getActiveCommandManager(){
        if(this.activeEditor==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeEditor.getCommandManager();
    }

    public ConsoleCommandManager getDefaultCommandManager(){
        return defaultCommandManager;
    }

    
    public ActiveEditor getEditor(String documentName) {
        return editors.get(documentName);
    }

    public boolean hasActiveDocument(){
        return this.activeEditor!=null;
    }
}
