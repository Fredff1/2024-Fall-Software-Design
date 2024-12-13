package com.lab.html_editor.controller;

import com.lab.html_editor.app.HtmlEditorCommandParser;
import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.FileElement.FileTreeManager;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.io.HtmlEditorIO;
import com.lab.html_editor.service.io.HtmlIO;
import com.lab.html_editor.service.io.JsoupHtmlIO;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.*;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlAppendCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlDeleteCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlEditContentCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlEditIdCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlInsertCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlPrintIndentCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlPrintTreeCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlShowIdCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlSpellCheckCommand;
import com.lab.html_editor.utils.command.workspace_command.ConsoleHtmlSaveFileCommand;
import com.lab.html_editor.utils.command.workspace_command.ConsoleLoadFileCommand;
import com.lab.html_editor.utils.observer.Observer;
import com.lab.html_editor.utils.strategy.FileIndentedRepresentation;
import com.lab.html_editor.utils.strategy.FileTreeRepresentation;
import com.lab.html_editor.view.HtmlView;

import java.io.IOException;
import java.util.*;

/**
 * MVC核心组件，负责命令的执行、
 */
public class HtmlController implements Observer{
    private final HtmlView view;
    private final HtmlIO ioManager=new JsoupHtmlIO();
    private final SpellCheckService spellCheckService;
    private final FileTreeManager fileTreeManager=new FileTreeManager();
    private final HtmlDocumentManager documentManager=new HtmlDocumentManager(this);
    private HtmlEditorCommandParser parser;
    
    public void setParser(HtmlEditorCommandParser parser){
        this.parser=parser;
    }
   
    

    public HtmlController(HtmlView view,SpellCheckService spellCheckService ){
        this.view = view;
        view.addObserver(this);
        this.spellCheckService=spellCheckService;
        
    }

    public void restoreWorkspace(){
        
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
            default:
            break;
        }
    }

    public FileTreeManager getFileTreeManager(){
        return this.fileTreeManager;
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

    

    public void editElementId(String oldId, String newId) {
        ConsoleCommand editCommand = new ConsoleHtmlEditIdCommand(documentManager.getActiveDocument(), oldId, newId);
        documentManager.executeOnActiveEditor(editCommand);
    }

    public void editElementText(String targetId,String text){
        ConsoleCommand editTextCommand= new ConsoleHtmlEditContentCommand(documentManager.getActiveDocument(), targetId, text,spellCheckService);
        documentManager.executeOnActiveEditor(editTextCommand);
    }

    public void insertElement(String tagName,String targetId,String brotherId,String content){
        ConsoleCommand insertCommand= new ConsoleHtmlInsertCommand(this,  tagName, targetId, brotherId,content);
        documentManager.executeOnActiveEditor(insertCommand);
        
    }

    public void deleteElement(String targetId){
        ConsoleCommand deleteCommand = new ConsoleHtmlDeleteCommand(documentManager.getActiveDocument(), targetId);
        documentManager.executeOnActiveEditor(deleteCommand);
    }

    public void appendElement(String tagName,String targetId,String parentId,String content){
        ConsoleCommand appendCommand = new ConsoleHtmlAppendCommand(this,tagName, targetId,parentId, content);
        documentManager.executeOnActiveEditor(appendCommand);
       
    }

    public void loadFile(String filePath){
        ConsoleCommand loadCommand=new ConsoleLoadFileCommand(filePath, this);
        documentManager.executeWorkspaceCommand(loadCommand);
    }

    

    public void saveFile(){
        ConsoleCommand writeCommand= new ConsoleHtmlSaveFileCommand(this);
        documentManager.executeWorkspaceCommand(writeCommand);
    }

    public void listEditors(){
        var editors=documentManager.getEditors();
        view.displaySplitLine();
        view.displayInfo("[Active Editors]");
        for(var editor:editors.values()){
            String prefix="";
            String suffix="";
            if(editor.equals(documentManager.getActiveEditor())){
                prefix=">";
            }
            if(editor.isUpdated()){
                suffix="*";
            }
            view.displayInfo(prefix+editor.toString()+suffix);
        }
        view.displaySplitLine();
    }

    public void printCommandForTest(){
        var visitor=documentManager.getActiveEditor().getDocument().getRenderInfo();
        System.out.println(visitor.geStringRepresentation());
    }

    // 处理撤销请求
    public void undoLastCommand() {
        documentManager.getActiveEditor().undo();
    }

    // 处理重做请求
    public void redoLastCommand() {
        documentManager.getActiveEditor().redo();
    }

    public void showIdCommand(boolean showId){
        ConsoleHtmlShowIdCommand showIdCommand=new ConsoleHtmlShowIdCommand(documentManager.getActiveEditor(), showId);
        documentManager.executeOnActiveEditor(showIdCommand);
    }

    public void spellCheck(){
        ConsoleHtmlSpellCheckCommand command=new ConsoleHtmlSpellCheckCommand(documentManager.getActiveDocument(), spellCheckService);
        documentManager.executeOnActiveEditor(command);
    }

    public void printTree(){
        ConsoleHtmlPrintTreeCommand printCommand=new ConsoleHtmlPrintTreeCommand(documentManager.getActiveDocument());
        documentManager.executeOnActiveEditor(printCommand);
    }

    public void printIndent(int indent){
        ConsoleHtmlPrintIndentCommand printCommand=new ConsoleHtmlPrintIndentCommand(documentManager.getActiveDocument(),view,indent);
        documentManager.executeOnActiveEditor(printCommand);
    }

    public void printDirTree(){
        FileTreeRepresentation treeRepresentation=new FileTreeRepresentation();
        fileTreeManager.getRoot().setRepresentationStrategy(treeRepresentation);
        view.displayWorkspaceFolder(fileTreeManager.getRoot().toStringRepresentation());
    }

    public void printDirIndent(int indent){
        FileIndentedRepresentation representation=new FileIndentedRepresentation(indent);
        fileTreeManager.getRoot().setRepresentationStrategy(representation);
        view.displayWorkspaceFolder(fileTreeManager.getRoot().toStringRepresentation());
    }

    public void switchEditor(String path){
        String absolutePath=fileTreeManager.resolvePath(path);
        boolean switchSuccess=false;
        switchSuccess=documentManager.setActiveEditor(absolutePath);
        if(switchSuccess){
            documentManager.getActiveEditor().notifyObservers(new StatusEvent("Switch to active file "+absolutePath, true));
        }else{
            documentManager.getActiveEditor().notifyObservers(new StatusEvent("Failed to switch to file "+absolutePath+" check if the file is loaded", false));
        }
        
    }

    public void closeActiveEditor(){
        if(documentManager.getActiveEditor().isUpdated()){
            view.displayMessage("Do you want to save active file?[yes/no]");
            view.displayMessageInOneLine("");
            boolean isiSave=parser.confirmCommand();
            if(isiSave){
                saveFile();
            }
            documentManager.getActiveEditor().setUpdated(false);
        }
        boolean changeSuccess=documentManager.removeActiveEditor();
        
        if(changeSuccess){
            view.updateView(documentManager.getActiveEditor());
            view.displayInfo("Editor closed");
            view.displayInfo("Switch active editor to "+documentManager.getActiveEditor().getFileNode().getName());
        }else{
            view.clearConsole();
            view.displayInfo("Editor closed");
            view.displayInfo("There are no active editors now");
        }
    }

    public void recordAndExit(){
        try{
            List<HtmlEditor> editorToRemove=new ArrayList<>();
            for(var targetEditor:documentManager){
                if(targetEditor.isUpdated()){
                    view.displayMessage("Do you want to save file "+targetEditor.getFileNode().getAbsolutePath()+" [yes/no]?");
                    view.displayMessageInOneLine("");
                    boolean saveEditor=parser.confirmCommand();
                    if(saveEditor){
                        documentManager.saveEditorToFile(targetEditor);
                        view.displayInfo("Saving file to "+targetEditor.getFileNode().getAbsolutePath());
                    }else{
                        editorToRemove.add(targetEditor);
                        view.displayInfo("Discarding updated content of"+targetEditor.getFileNode().getAbsolutePath());
                    }
                }
            }
            for(var ed:editorToRemove){
                documentManager.removeEditor(ed.getFileNode().getAbsolutePath());
            }
            HtmlEditorIO.saveEditors(documentManager, documentManager.getActiveEditor());
            view.displayInfo("History saved");
            view.displayInfo("Exiting Html Editor");
        }catch(IOException e){
            view.displayErrorMessage("Error Occurred when saving workspace info: "+e.getMessage());
        }
    }

    public void restoreHistory(){
        try{
            HtmlEditorIO.loadEditors(this);
            view.updateView(documentManager.getActiveEditor());
            view.displayInfo("History restored");
        }catch(IOException e){
            view.displayErrorMessage("Error Occurred when loading cached history: "+e.getMessage());
        }catch(Exception e){
            view.displayInfo("No history detected");
        }
    }

    public HtmlDocument getActiveDocument(){
        return documentManager.getActiveDocument();
    }


    public void handleStatusEvent(StatusEvent event){
        boolean isSuccessful=event.isSuccessful();
        String message=event.getMessage();
        documentManager.getActiveEditor().getAllEvents().add(event);
        documentManager.getActiveEditor().update();
        view.updateView(documentManager.getActiveEditor());
        if(isSuccessful){
            view.displayInfo(message);
        }else{
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
        if(closest.equals("None")==false){
            view.displayMessage("The closest command is "+closest);
        }
    }

    
    

    
    

    
}
