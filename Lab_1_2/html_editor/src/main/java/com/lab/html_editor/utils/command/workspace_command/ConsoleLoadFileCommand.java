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
        return controller.getDocumentManager().loadEditor(filePath, manager); 
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
