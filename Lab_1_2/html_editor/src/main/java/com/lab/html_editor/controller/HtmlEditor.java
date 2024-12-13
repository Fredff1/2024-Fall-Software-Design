package com.lab.html_editor.controller;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.utils.command.editor_command.ConsoleUpdateCommand;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementShowIdVisitor;
import com.lab.html_editor.utils.observer.Observable;
import com.lab.html_editor.utils.observer.Observer;

import java.util.*;

public class HtmlEditor implements Observer, Observable{
    private final HtmlDocument document;
    private final FileNode fileNode;
    private final ConsoleCommandManager commandManager;
    private final Deque<Event> events;
    private final List<Observer> observers=new ArrayList<>();
    private boolean updated=false;
    private boolean showId=true;
    private boolean fileExist=true;

    public HtmlEditor(HtmlDocument document,FileNode node) {
        this.document = document;
        this.fileNode=node;
        this.commandManager = new ConsoleCommandManager();
        commandManager.addObserver(this);
        document.addObserver(this);
        this.events = new LinkedList<>();
    }

    public HtmlEditor(HtmlDocument document,FileNode node,boolean fileExist) {
        this(document, node);
        this.fileExist=fileExist;
    }

    public void update(){
        setShowId(showId);
    }

    public void setFileExist(boolean fileExist){
        this.fileExist=fileExist;
    }

    public boolean isFileExist(){
        return this.fileExist;
    }

    public void setShowId(boolean showId){
        this.showId=showId;
        HtmlElementShowIdVisitor visitor=new HtmlElementShowIdVisitor(showId);
        document.visitRoot(visitor);
    }

    public boolean isShowId(){
        return showId;
    }

    public FileNode getFileNode(){
        return fileNode;
    }

    public Queue<Event> getAllEvents(){
        return events;
    }

    public Event getLastEvent(){
        return events.peek();
    }

    public void clearEvents(){
        events.clear();
    }

    public void update(Event event){
        events.add(event);
        notifyObservers(event);
    }

    @Override
    public String toString(){
        return fileNode.getAbsolutePath();
    }

    public boolean isUpdated(){
        return updated;
    }

    public void setUpdated(boolean flag){
        updated=flag;
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)fileNode.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        decorator.setUpdateStatus(flag);
    }

    public void setUpdated(boolean flag,StatusEvent statusEvent){
        updated=flag;
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)fileNode.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        decorator.setUpdateStatus(flag);
        notifyObservers(statusEvent);
    }


    public StatusEvent getLastStatusEvent(){
        Iterator<Event> reverseIterator = events.descendingIterator(); // 获取反向迭代器
        while (reverseIterator.hasNext()) {
            Event event = reverseIterator.next();
            if (event instanceof StatusEvent) {
                return (StatusEvent) event;
            }
        }
        return null; // 如果未找到，返回 null
    }

    public void execute(ConsoleCommand command){
        commandManager.executeCommand(command);
        if(command instanceof ConsoleUpdateCommand){
            var status=getLastStatusEvent();
            if(status.isSuccessful()){
                setUpdated(true,status);
            }
        }
    }

    public void redo(){
        commandManager.redo();
        var status=getLastStatusEvent();
        if(status.isSuccessful()){
            setUpdated(true,status);
        }
    }

    public void undo(){
        commandManager.undo();
        var status=getLastStatusEvent();
        if(status.isSuccessful()&&commandManager.isUndoStackEmpty()){
            setUpdated(false,status);
        }
    }

    public ConsoleCommandManager getCommandManager(){
        return commandManager;
    }

    public HtmlDocument getDocument(){
        return this.document;
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
}
