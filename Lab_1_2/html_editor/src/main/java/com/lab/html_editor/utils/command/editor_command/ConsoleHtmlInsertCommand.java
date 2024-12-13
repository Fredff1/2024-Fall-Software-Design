package com.lab.html_editor.utils.command.editor_command;


import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.exceptions.HtmlInsertException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;




public class ConsoleHtmlInsertCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlController controller;
    private final HtmlDocument document;
    private HtmlElement insertElement;
    private final String brotherId;
    private final String tagName;
    private final String targetId;
    private final String content;

    private int insertLocation;
    private String parentId;


    public ConsoleHtmlInsertCommand(HtmlController controller,String tagName,String targetId,String brotherId,String content){
        this.controller=controller;
        this.document=controller.getActiveDocument();
        this.brotherId=brotherId;
        this.tagName=tagName;
        this.targetId=targetId;
        this.content=content;
    }
    
    
    public boolean execute(){
        try{
            document.insert(tagName, targetId, brotherId, content);
            this.insertLocation=document.getElementIndex(targetId);
            TreeNode insertedNode=(TreeNode)document.search(targetId);
            this.insertElement=(HtmlElement)insertedNode;
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(controller.getSpellCheckService(),false);
            insertElement.accept(spellCheckVisitor);
            var parent=(HtmlElement)insertedNode.getFather();
            if(parent!=null){
                this.parentId=parent.getId();
            }
            document.notifyObservers(new StatusEvent("Successfully insert element before "+brotherId, true));
            return true;
        }catch(HtmlInsertException e){
            document.notifyObservers(new StatusEvent("Failed to insert element before "+brotherId+" because "+e.getMessage(), false,e));
            return false;
        }
        
    }
    public boolean undo(){
        try{
            document.delete(targetId);
            document.notifyObservers(new StatusEvent("Successfully undo ", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to undo "+" because "+e.getMessage(), false,e));
            return false;
        }
        
    }

    public boolean redo(){
        try{
            document.setElementAsChild(parentId, insertElement, insertLocation);
            document.notifyObservers(new StatusEvent("Successfully redo ", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo "+" because "+e.getMessage(), false,e));
            return false;
        }
        
    }
}
