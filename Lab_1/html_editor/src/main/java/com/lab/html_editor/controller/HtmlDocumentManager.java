package com.lab.html_editor.controller;

import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.utils.command.ConsoleHtmlInitCommand;

import java.util.*;

public class HtmlDocumentManager {
    private final Map<String, DocumentSession> sessions = new HashMap<>();
    private DocumentSession activeSession;
    private final HtmlController controller;
    private ConsoleCommandManager defaultCommandManager=new ConsoleCommandManager();

    public HtmlDocumentManager(HtmlController controller){
        this.controller=controller;
    }

    
    public void addDocumentSession(HtmlDocument document) {
        document.addObserver(controller);
        DocumentSession session = new DocumentSession(document);
        session.getCommandManager().addObserver(controller);
        sessions.put(document.getDocumentName(), session);
        setActiveSession(document.getDocumentName());
    }

    public void addNewDefaultDocument(ConsoleHtmlInitCommand command){
        command.execute();
    }

    
    public boolean removeDocumentSession(String documentName) {
        if (sessions.containsKey(documentName)) {
            sessions.remove(documentName);
            if (activeSession != null && activeSession.getDocument().getDocumentName().equals(documentName)) {
                activeSession = sessions.isEmpty() ? null : sessions.values().iterator().next();
            }
            return true;
        }
        return false;
    }

    
    public boolean setActiveSession(String documentName) {
        if (sessions.containsKey(documentName)) {
            activeSession = sessions.get(documentName);
            return true;
        }
        return false;
    }

    
    public DocumentSession getActiveSession() {
        if(this.activeSession==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeSession;
    }

    public HtmlDocument getActiveDocument(){
        if(this.activeSession==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeSession.getDocument();
    }

    public ConsoleCommandManager getActiveCommandManager(){
        if(this.activeSession==null){
            throw new UninitializedException("There are no active sessions available");
        }
        return activeSession.getCommandManager();
    }

    public ConsoleCommandManager getdefaultCommandManager(){
        return defaultCommandManager;
    }

    
    public DocumentSession getSession(String documentName) {
        return sessions.get(documentName);
    }

    public boolean hasActiveDocument(){
        return this.activeSession!=null;
    }
}
