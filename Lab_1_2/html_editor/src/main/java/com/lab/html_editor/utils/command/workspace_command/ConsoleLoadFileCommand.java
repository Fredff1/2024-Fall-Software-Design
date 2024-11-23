package com.lab.html_editor.utils.command.workspace_command;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlEditor;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.FileElement.FileTreeManager;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.utils.command.ConsoleWorkspaceCommand;

public class ConsoleLoadFileCommand extends ConsoleWorkspaceCommand{
    private String filePath;
    private final FileTreeManager manager;
    private final HtmlController controller;

    public ConsoleLoadFileCommand(String filePath,HtmlController controller){
        this.controller=controller;
        this.manager=controller.getFileTreeManager();
        this.filePath=filePath;
    }


    @Override
    public boolean execute(){
        try{
            AbstractFileNode node=manager.getNodeById(filePath);
            if(node==null){
                HtmlDocument newDocument=new HtmlDocument(FileTreeManager.getBasename(filePath), "new file", new HtmlService());
                manager.addNode(filePath);
                var editor=controller.getDocumentManager().addEditor(newDocument,(FileNode)manager.getNodeById(filePath));
                editor.notifyObservers(new StatusEvent("Successfully load file from "+filePath,true));
            }else{
                HtmlDocument document=controller.getIOManager().read(manager.resolvePath(filePath),new HtmlService());
                var editor=controller.getDocumentManager().addEditor(document,(FileNode)node);
                editor.notifyObservers(new StatusEvent("Successfully load file from "+filePath,true));
            }
            return true;
        }catch(Exception e){
            controller.handleStatusEvent(new StatusEvent("Failed to load document because "+e.getMessage(),false));
            return false;

        }
        
    }

    @Override
    public boolean undo(){
        return false;
        
    }

    @Override 
    public boolean redo(){
        return false;
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }
}
