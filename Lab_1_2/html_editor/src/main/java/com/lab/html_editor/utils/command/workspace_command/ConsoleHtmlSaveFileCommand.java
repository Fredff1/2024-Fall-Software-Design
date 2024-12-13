package com.lab.html_editor.utils.command.workspace_command;



import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlDocumentManager;
import com.lab.html_editor.controller.HtmlEditor;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;
import com.lab.html_editor.utils.strategy.HtmlTreeRepresentation;

public class ConsoleHtmlSaveFileCommand implements ConsoleCommand{
    private final HtmlController controller;

    private final HtmlEditor editor;



    public ConsoleHtmlSaveFileCommand(HtmlController controller){
        this.controller=controller;
        this.editor=controller.getDocumentManager().getActiveEditor();
    }

    @Override
    public boolean execute(){
        try{
            controller.getDocumentManager().saveEditorToFile(editor);
            editor.notifyObservers(new StatusEvent("Successfully write document to file", true));
            return true;
        }catch(Exception e){
            editor.notifyObservers(new StatusEvent("Failed to save document to file", false,e));
            return false;
        }
    }

    @Override
    public boolean undo(){
        throw new RuntimeException("Cannot undo write file command");
        // try{
        //     controller.setDocument(previousDocument);
        //     previousDocument.notifyObservers(new StatusEvent("Successfully undo init document", true));
        //     return true;
        // }catch(Exception e){
        //     throw new RuntimeException("Failed to undo initialize");
        //     //controller.getDocument().notifyObservers(new StatusEvent("Failed to undo init document", false));
        // }
        
    }

    @Override 
    public boolean redo(){
        throw new RuntimeException("Cannot redo write file command");
        // try{
        //     controller.setDocument(currentDocument);
        //     currentDocument.notifyObservers(new StatusEvent("Successfully redo init document", true));
        //     return true;
        // }catch(Exception e){
        //     throw new RuntimeException("Failed to redo initialize");
        //     //controller.getDocument().notifyObservers(new StatusEvent("Failed to redo init document", false));
        // }
        
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }
}
