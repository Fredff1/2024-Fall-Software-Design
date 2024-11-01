package com.lab.html_editor.controller;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.io.HtmlIO;
import com.lab.html_editor.io.JsoupHtmlIO;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.*;
import com.lab.html_editor.utils.observer.Observer;
import com.lab.html_editor.view.HtmlView;


import java.util.*;

/**
 * MVC核心组件，负责命令的执行、
 */
public class HtmlController implements Observer{
    private final ConsoleCommandManager commandManager;
    private final HtmlView view;
    private final HtmlIO ioManager=new JsoupHtmlIO();
    private final SpellCheckService spellCheckService;
    private final Queue<Event> events=new LinkedList<>();
    private final Map<String,HtmlDocument> documentMap=new HashMap<>();
    private HtmlDocument activeDocument;

    

   
    public HtmlController(HtmlDocument document, HtmlView view,ConsoleCommandManager consoleCommandManager,SpellCheckService spellCheckService) {
        this.activeDocument = document;
        this.view = view;
        view.addObserver(this);
        this.commandManager=consoleCommandManager;
        this.spellCheckService=spellCheckService;
        commandManager.addObserver(this);
        commandManager.setInit(true);
    }

    public HtmlController(HtmlView view,ConsoleCommandManager consoleCommandManager,SpellCheckService spellCheckService ){
        this.activeDocument=null;
        this.view = view;
        view.addObserver(this);
        this.commandManager=consoleCommandManager;
        this.spellCheckService=spellCheckService;
        commandManager.addObserver(this);;
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

    public void addDocument(HtmlDocument document){
        document.addObserver(this);
        documentMap.put(document.getDocumentName(), document);
        setActiveDocument(document.getDocumentName());
    }

    public void setActiveDocument(String documentName){
        this.activeDocument=documentMap.get(documentName);
        view.setActiveDocument(activeDocument);
    }

    public HtmlDocument getActivDocument(){
        return this.activeDocument;
    }
    
    /* 一系列具体的命令*/

    public void initCommand(String documentName,String title){
        ConsoleCommand initCommand= new ConsoleHtmlInitCommand(this, documentName, title);
        commandManager.executeCommand(initCommand);
    }

    public void editElementId(String oldId, String newId) {
        ConsoleCommand editCommand = new ConsoleHtmlEditIdCommand(activeDocument, oldId, newId);
        commandManager.executeCommand(editCommand);
    }

    public void editElementText(String targetId,String text){
        ConsoleCommand editTextCommand= new ConsoleHtmlEditContentCommand(activeDocument, targetId, text,spellCheckService);
        commandManager.executeCommand(editTextCommand);
    }

    public void insertElement(String tagName,String targetId,String brotherId,String content){
        ConsoleCommand insertCommand= new ConsoleHtmlInsertCommand(activeDocument,  tagName, targetId, brotherId,content);
        commandManager.executeCommand(insertCommand);;
    }

    public void deleteElement(String targetId){
        ConsoleCommand deleteCommand = new ConsoleHtmlDeleteCommand(activeDocument, targetId);
        commandManager.executeCommand(deleteCommand);
    }

    public void appendElement(String tagName,String targetId,String parentId,String content){
        ConsoleCommand appendCommand = new ConsoleHtmlAppendCommand(activeDocument,tagName, targetId,parentId, content);
        commandManager.executeCommand(appendCommand);
    }

    public void readFile(String filePath){
        ConsoleCommand readCommand= new ConsoleHtmlReadFileCommand(this, filePath);
        commandManager.executeCommand(readCommand);
    }

    public void writeFile(String filePath){
        ConsoleCommand writeCommand= new ConsoleHtmlWriteFileCommand(this, filePath);
        commandManager.executeCommand(writeCommand);
    }

    public void printCommandForTest(){
        var visitor=activeDocument.getRenderInfo();
        System.out.println(visitor.geStringRepresentation());
    }

    // 处理撤销请求
    public void undoLastCommand() {
        commandManager.undo();
    }

    // 处理重做请求
    public void redoLastCommand() {
        commandManager.redo();
    }

    public void spellCheck(){
        ConsoleHtmlSpellCheckCommand command=new ConsoleHtmlSpellCheckCommand(activeDocument, spellCheckService);
        commandManager.executeCommand(command);
    }

    public void printTree(){
        ConsoleHtmlPrintTreeCommand printCommand=new ConsoleHtmlPrintTreeCommand(activeDocument);
        commandManager.executeCommand(printCommand);
    }

    public void printIndent(int indent){
        ConsoleHtmlPrintIndentCommand printCommand=new ConsoleHtmlPrintIndentCommand(activeDocument,view,indent);
        commandManager.executeCommand(printCommand);
    }


    private void handleStatusEvent(StatusEvent event){
        boolean isSuccessful=event.isSuccessful();
        String message=event.getMessage();
        events.add(event);
        view.updateView(activeDocument);
        if(isSuccessful){
            view.displayMessage(message);
        }else{
            //view.displayMessage("An Error Occurred :");
            view.displayMessage(message);
        }
        view.displaySplitLine();
    }

    public boolean hasActiveDocument(){
        return this.activeDocument!=null;
    }

    /*可以对历史的event进行处理 */

    public Queue<Event> getAllEvents(){
        return events;
    }

    public Event getLastEvent(){
        return events.peek();
    }

    public void clearEvents(){
        events.clear();
    }

    public StatusEvent getLastStatusEvent(){
        Event lastStatusEvent = null;
        for (Event event : events) {
            if (event instanceof StatusEvent) {
                lastStatusEvent = (StatusEvent) event;
            }
        }
        return (StatusEvent) lastStatusEvent;
    }

    

    public void handleUnknownCommand(String command,Set<String> commandKeys){
        view.displayMessage("Unknown command: " + command);
        String closest=spellCheckService.findClosestCommand(command, commandKeys);
        if(closest!="None"){
            view.displayMessage("The closest command is "+closest);
        }
    }

    
    

    
    

    
}
