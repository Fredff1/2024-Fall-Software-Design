package com.lab.html_editor.utils.command;

import java.util.Stack;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.controller.exceptions.RedoFailedException;
import com.lab.html_editor.controller.exceptions.UndoFailedException;
import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.utils.observer.Observable;
import com.lab.html_editor.utils.observer.Observer;

import java.util.*;

/**
 * 命令执行管理器
 */
public class ConsoleCommandManager implements Observable{
    private final Stack<ConsoleCommand> undoStack = new Stack<>();
    private final Stack<ConsoleCommand> redoStack = new Stack<>();
    private final List<Observer> observers=new ArrayList<>();
    private boolean initialized=true;


    public boolean executeCommand(ConsoleCommand command) {
            
        
        boolean flag=command.execute();  // 执行命令
        if(flag&&command.supportsUndo()){
            undoStack.push(command);  // 将命令放入撤销栈
        }
        redoStack.clear();
        return flag;
    }

    

    public void removeLastUndoCommand(){
        if(initialized==false){
            throw new UninitializedException("The document has not been initialized!");
        }
        if(!undoStack.isEmpty()){
            undoStack.pop();
        }
    }

    public void removeLastRedoCommand(){
        if(initialized==false){
            throw new UninitializedException("The document has not been initialized!");
        }
        if(!redoStack.isEmpty()){
            redoStack.pop();
        }
    }

    public boolean undo() {
        if(initialized==false){
            throw new UninitializedException("The document has not been initialized!");
        }
        if (!undoStack.isEmpty()) {
            ConsoleCommand command = undoStack.pop();
            boolean flag=command.undo();  // 执行撤销操作
            if(flag){
                redoStack.push(command);  // 放入重做栈
            }
            return true;
        }else{
            notifyObservers(new StatusEvent("No command to undo", false,new UndoFailedException("No command to undo")));
            return false;
        }
    }

    public boolean redo() {
        if(initialized==false){
            throw new UninitializedException("The document has not been initialized!");
        }
        if (!redoStack.isEmpty()) {
            ConsoleCommand command = redoStack.pop();
            boolean flag=command.redo();  // 重新执行命令
            if(flag){
                undoStack.push(command);  // 放回撤销栈
            }
            return true;
        }else{
            notifyObservers(new StatusEvent("No command to redo", false,new RedoFailedException("No command to redo")));
            return false;
        }
    }

    public boolean isUndoStackEmpty(){
        return undoStack.empty();
    }

    public void setInit(final boolean flag){
        initialized=flag;
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

