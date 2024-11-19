package com.lab.html_editor.controller;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.io.HtmlIO;
import com.lab.html_editor.service.io.JsoupHtmlIO;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.*;
import com.lab.html_editor.utils.observer.Observer;
import com.lab.html_editor.view.HtmlView;


import java.util.*;

/**
 * MVC核心组件，负责命令的执行、
 */
public class HtmlController implements Observer{
    private final HtmlView view;
    private final HtmlIO ioManager=new JsoupHtmlIO();
    private final SpellCheckService spellCheckService;
    private final HtmlDocumentManager documentManager=new HtmlDocumentManager(this);

    

   
    public HtmlController(HtmlDocument document, HtmlView view,SpellCheckService spellCheckService) {
        this.view = view;
        view.addObserver(this);
        this.spellCheckService=spellCheckService;
        this.documentManager.addEditor(document);
    }

    public HtmlController(HtmlView view,ConsoleCommandManager consoleCommandManager,SpellCheckService spellCheckService ){
        this.view = view;
        view.addObserver(this);
        this.spellCheckService=spellCheckService;
    }

    /**
     * Observer接口实现，接受来自观察对象的通知
     */
    public void update(Event event){
        switch(event.getEventType()){
            case STATUS_EVENT:
            handleStatusEvent((StatusEvent)event);
            break;
            case ERROR_EVENT:
            break;
        }
    }
    
    public HtmlIO getIOManager(){
        return this.ioManager;
    }

    public HtmlDocumentManager getDocumentManager(){
        return documentManager;
    }

    public SpellCheckService getSpellCheckService(){
        return spellCheckService;
    }

    
    
    /* 一系列具体的命令*/

    public void initCommand(String documentName,String title){
        ConsoleHtmlInitCommand initCommand= new ConsoleHtmlInitCommand(documentManager, documentName, title);
        documentManager.getDefaultCommandManager().executeCommand(initCommand);
    }

    public void editElementId(String oldId, String newId) {
        ConsoleCommand editCommand = new ConsoleHtmlEditIdCommand(documentManager.getActiveDocument(), oldId, newId);
        documentManager.getActiveEditor().execute(editCommand);
    }

    public void editElementText(String targetId,String text){
        ConsoleCommand editTextCommand= new ConsoleHtmlEditContentCommand(documentManager.getActiveDocument(), targetId, text,spellCheckService);
        documentManager.getActiveEditor().execute(editTextCommand);
    }

    public void insertElement(String tagName,String targetId,String brotherId,String content){
        ConsoleCommand insertCommand= new ConsoleHtmlInsertCommand(this,  tagName, targetId, brotherId,content);
        documentManager.getActiveEditor().execute(insertCommand);
        
    }

    public void deleteElement(String targetId){
        ConsoleCommand deleteCommand = new ConsoleHtmlDeleteCommand(documentManager.getActiveDocument(), targetId);
        documentManager.getActiveEditor().execute(deleteCommand);
    }

    public void appendElement(String tagName,String targetId,String parentId,String content){
        ConsoleCommand appendCommand = new ConsoleHtmlAppendCommand(this,tagName, targetId,parentId, content);
        documentManager.getActiveEditor().execute(appendCommand);
       
    }

    public void readFile(String filePath){
        ConsoleCommand readCommand= new ConsoleHtmlReadFileCommand(this, filePath);
        documentManager.getDefaultCommandManager().executeCommand(readCommand);
    }

    public void writeFile(String filePath){
        ConsoleCommand writeCommand= new ConsoleHtmlWriteFileCommand(this, filePath);
        documentManager.getActiveEditor().execute(writeCommand);
    }

    public void printCommandForTest(){
        var visitor=documentManager.getActiveEditor().getDocument().getRenderInfo();
        System.out.println(visitor.geStringRepresentation());
    }

    // 处理撤销请求
    public void undoLastCommand() {
        documentManager.getActiveCommandManager().undo();
    }

    // 处理重做请求
    public void redoLastCommand() {
        documentManager.getActiveCommandManager().redo();
    }

    public void spellCheck(){
        ConsoleHtmlSpellCheckCommand command=new ConsoleHtmlSpellCheckCommand(documentManager.getActiveDocument(), spellCheckService);
        documentManager.getActiveEditor().execute(command);
    }

    public void printTree(){
        ConsoleHtmlPrintTreeCommand printCommand=new ConsoleHtmlPrintTreeCommand(documentManager.getActiveDocument());
        documentManager.getActiveEditor().execute(printCommand);
    }

    public void printIndent(int indent){
        ConsoleHtmlPrintIndentCommand printCommand=new ConsoleHtmlPrintIndentCommand(documentManager.getActiveDocument(),view,indent);
        documentManager.getActiveEditor().execute(printCommand);
    }

    public HtmlDocument getActiveDocument(){
        return documentManager.getActiveDocument();
    }


    private void handleStatusEvent(StatusEvent event){
        boolean isSuccessful=event.isSuccessful();
        String message=event.getMessage();
        documentManager.getActiveEditor().getAllEvents().add(event);
        view.updateView(documentManager.getActiveEditor().getDocument());
        if(isSuccessful){
            view.displaySuccessMessage(message);
        }else{
            //view.displayMessage("An Error Occurred :");
            view.displayErrorMessage(message);
        }
        view.displaySplitLine();
    }

    public boolean hasActiveDocument(){
        return documentManager.hasActiveDocument();
    }


    public void handleUnknownCommand(String command,Set<String> commandKeys){
        view.displayErrorMessage("Unknown command: " + command);
        String closest=spellCheckService.findClosestCommand(command, commandKeys);
        if(closest!="None"){
            view.displayMessage("The closest command is "+closest);
        }
    }

    
    

    
    

    
}
