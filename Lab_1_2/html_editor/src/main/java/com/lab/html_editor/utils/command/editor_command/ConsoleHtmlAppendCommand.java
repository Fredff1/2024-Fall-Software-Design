package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.exceptions.HtmlAppendExcption;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;

public class ConsoleHtmlAppendCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlController controller;
    private final HtmlDocument document;
    private final String parentId;
    private final String tagName;
    private final String targetId;
    private final String content;

    private int insertLocation;
    private HtmlElement appendElement;



    public ConsoleHtmlAppendCommand(HtmlController controller,String tagName,String targetId,String parentId,String content){
        this.controller=controller;
        this.document=controller.getActiveDocument();
        this.parentId=parentId;
        this.tagName=tagName;
        this.targetId=targetId;
        this.content=content;
    }
    
    
    public boolean execute(){
        try{
            document.append(tagName, targetId, parentId, content);
            this.insertLocation=document.getElementIndex(targetId);
            this.appendElement=document.search(targetId);
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(controller.getSpellCheckService(),false);
            appendElement.accept(spellCheckVisitor);
            document.notifyObservers(new StatusEvent("Successfully append element to "+parentId, true));
            return true;
        }
        catch(HtmlAppendExcption e){
            document.notifyObservers(new StatusEvent("Failed to append element to "+parentId+" because "+e.getMessage(),
             false, e));
             return false;
        }
        

    }
    public boolean undo(){
        try{
            document.delete(targetId);
            document.notifyObservers(new StatusEvent("Successfully undo command append", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to undo command append because "+e.getMessage(), false,e));
            return false;
        }
        
    }

    public boolean redo(){
        try{
            document.setElementAsChild(parentId, appendElement, insertLocation);
            document.notifyObservers(new StatusEvent("Successfully redo command append ", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo command append because "+e.getMessage(), false,e));
            return false;
        }
        
    }
}
