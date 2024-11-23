package com.lab.html_editor.controller;

import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.utils.command.workspace_command.ConsoleHtmlSaveFileCommand;

import java.io.Console;
import java.util.*;

public class HtmlDocumentManager {
    private final Map<String, HtmlEditor> editors = new HashMap<>();
    private HtmlEditor activeEditor;
    private final HtmlController controller;
    private ConsoleCommandManager defaultCommandManager=new ConsoleCommandManager();

    public HtmlDocumentManager(HtmlController controller){
        this.controller=controller;
    }

    
    public HtmlEditor addEditor(HtmlDocument document,FileNode node) {
        for(var edi:editors.values()){
            if(edi.getFileNode().equals(node)){
                throw new IllegalArgumentException("File "+node.getName()+" is already loaded");
            }
        }
        
        HtmlEditor editor = new HtmlEditor(document,node);
        editor.addObserver(controller);
        editors.put(node.getAbsolutePath(), editor);
        setActiveEditor(node.getAbsolutePath());
        return editor;
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

    
    public boolean setActiveEditor(String path) {
        if (editors.containsKey(path)) {
            activeEditor = editors.get(path);
            return true;
        }
        return false;
    }

    
    public HtmlEditor getActiveEditor() {
        if(this.activeEditor==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeEditor;
    }

    public void executeOnActiveEditor(ConsoleCommand command){
        if(this.activeEditor!=null){
            activeEditor.execute(command);
        }else{
            throw new UninitializedException("There no active editor");
        }
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

    public void executeWorkspaceCommand(ConsoleCommand command){
        defaultCommandManager.executeCommand(command);
       
    }

    
    public HtmlEditor getEditor(String documentName) {
        return editors.get(documentName);
    }

    public boolean hasActiveDocument(){
        return this.activeEditor!=null;
    }

    public Map<String, HtmlEditor> getEditors(){
        return editors;
    }
}
