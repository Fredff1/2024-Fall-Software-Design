package com.lab.html_editor.utils.command;



import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlDocumentManager;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;
import com.lab.html_editor.utils.strategy.HtmlTreeRepresentation;

public class ConsoleHtmlWriteFileCommand implements ConsoleCommand{
    private final HtmlController controller;
    private final HtmlDocumentManager manager;
    private final String filePath;
    private final HtmlDocument document;


    public ConsoleHtmlWriteFileCommand(HtmlController controller,String filePath){
        this.controller=controller;
        this.filePath=filePath;
        this.manager=controller.getDocumentManager();
        this.document=controller.getActiveDocument();
    }

    @Override
    public boolean execute(){
        try{
            var prevStrategy=document.getRepresentationStrategy();
            document.setRepresentationStrategy(new HtmlIndentedRepresentation());
            controller.getIOManager().write(document,filePath);
            document.setRepresentationStrategy(prevStrategy);
            controller.getActiveDocument().notifyObservers(new StatusEvent("Successfully write document to file", true));
            return true;
        }catch(Exception e){
            if(document==null){
                return false;
            }
            document.notifyObservers(new StatusEvent("Failed to write document to file", false,e));
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
