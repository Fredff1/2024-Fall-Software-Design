package com.lab.html_editor.controller;

import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.FileElement.FileTreeManager;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.utils.command.workspace_command.ConsoleHtmlSaveFileCommand;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;

import java.io.Console;
import java.io.IOException;
import java.util.*;

public class HtmlDocumentManager implements Iterable<HtmlEditor>{
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

   
    public boolean removeActiveEditor(){
        String key=activeEditor.getFileNode().getAbsolutePath();
        editors.remove(key);
        if(editors.isEmpty()==false){
            
            for(var path:editors.keySet()){
                setActiveEditor(path);
                return true;
            }
        }else{
            activeEditor=null;
            return false;
        }
        return false;
    }

    
    
    public boolean removeEditor(String documentName) {
        if (editors.containsKey(documentName)) {
            var removeTarget=editors.remove(documentName);
            if(removeTarget.equals(activeEditor)){
                if(editors.isEmpty()==false){
                    for(var path:editors.keySet()){
                        setActiveEditor(path);
                    }
                }else{
                    activeEditor=null;
                }
                
            }
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

    public void saveEditorToFile(HtmlEditor editor) throws IOException{
        
        var document=editor.getDocument();
        var prevStrategy=document.getRepresentationStrategy();
        document.setRepresentationStrategy(new HtmlIndentedRepresentation());
        controller.getIOManager().write(document,editor.getFileNode().getAbsolutePath());
        document.setRepresentationStrategy(prevStrategy);
        editor.setUpdated(false);
        editor.setFileExist(true);
    }

    public HtmlEditor loadEditor(String filePath,FileTreeManager fileManager) throws IOException{
        
        AbstractFileNode node=fileManager.getNodeById(filePath);
        if(node==null){
            HtmlDocument newDocument=new HtmlDocument(FileTreeManager.getBasename(filePath), "new file", new HtmlService());
            fileManager.addNode(filePath);
            var editor=addEditor(newDocument,(FileNode)fileManager.getNodeById(filePath));
            editor.setUpdated(true);
            editor.setFileExist(false);
            editor.notifyObservers(new StatusEvent("Successfully load file from "+filePath,true));
            return editor;
        }else{
            HtmlDocument document=controller.getIOManager().read(fileManager.resolvePath(filePath),new HtmlService());
            var editor=addEditor(document,(FileNode)node);
            editor.notifyObservers(new StatusEvent("Successfully load file from "+filePath,true));
            return editor;
        }
    }

    @Override
    public Iterator<HtmlEditor> iterator() {
        return editors.values().iterator();
    }
}
