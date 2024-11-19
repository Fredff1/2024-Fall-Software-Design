package com.lab.html_editor.app;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.view.HtmlView;

import java.util.*;


/**
 * 负责命令行读取组件
 * 依赖为controller和view
 * 在这里注册新的命令以及对应的处理逻辑
 */
public class HtmlEditorCommandParser {
    private final HtmlController controller;
    private final HtmlView view;
    private final Map<String,String> commandMap;


    public HtmlEditorCommandParser(HtmlController controller, HtmlView view) {
        this.controller = controller;
        this.view = view;
        commandMap=new HashMap<>();
        initCommandMap();
    }

    // 解析命令行输入，并调用对应的 Controller 方法
    public void parseCommand(String commandLine) {
        
        String[] parts = commandLine.split(" ");
        String command = parts[0];
        if(controller.hasActiveDocument()==false&&command.equals("init")==false&&command.equals("read")==false){
            view.displayMessage("Error: There are no active Html Documents!");
            view.displayMessage("Use command init or read to initialize!");
            return;
        }

        switch (command.toLowerCase()) {
            case "append":
                handleAppend(commandLine,parts);
                break;  
            case "delete":
                handleDelete(commandLine, parts);
                break;
            case "edit-id":
                handleEditId(commandLine, parts);
                break;
            case "edit-text":
                handleEditContent(commandLine, parts);
                break;
            case "insert":
                handleInsert(commandLine, parts);
                break;
            case "undo":
                handleUndo(commandLine, parts);
                break;
            case "redo":
                handleRedo(commandLine, parts);
                break;
            case "init":
                handleInit(commandLine, parts);
                break;
            case "read":
                handleRead(commandLine, parts);
                break;
            case "save":
                handleSave(commandLine, parts);
                break;
            case "print-indent":
                handlePrintIndent(commandLine, parts);
                break;
            case "print-tree":
                handlePrintTree(commandLine, parts);
                break;
            case "spell-check":
                handleSpellCheck(commandLine, parts);
                break;
            case "help":
                handleHelp(commandLine, parts);
                break;
            default:
                handleUnknownCommand(commandLine, parts);
                break;
        }
    }

    private void handleUnknownCommand(String commandLine,String[] parts){
        controller.handleUnknownCommand(commandLine,commandMap.keySet());
    }

    private void handleAppend(String commandLine,String[] parts){
        if (parts.length >= 4) {
            parts = commandLine.split(" ", 5);
            String tagName = parts[1];
            String targetId = parts[2];
            String parentId = parts[3];
            String content = "";
            if (parts.length >= 5) {
                content = parts[4];
            }
            controller.appendElement(tagName, targetId, parentId, content);
        } else {
            view.displayMessage("Invalid append command format.");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleInsert(String commandLine,String[] parts){
        if (parts.length >= 4) {
            parts = commandLine.split(" ", 5);
            String tagName = parts[1];
            String targetId = parts[2];
            String brotherId = parts[3];
            String content = "";
            if (parts.length >= 5) {
                content = parts[4];
            }
            controller.insertElement(tagName, targetId, brotherId, content);
        } else {
            view.displayMessage("Invalid insert command format.");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleDelete(String commandLine,String[] parts){
        if (parts.length >= 2) {
            String targetId = parts[1];
            controller.deleteElement(targetId);
        } else {
            view.displayMessage("Invalid delete command format.");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleEditId(String commandLine,String[] parts){
        if (parts.length >= 3) {
            String oldId = parts[1];
            String newId = parts[2];
            controller.editElementId(oldId, newId);
        } else {
            view.displayMessage("Invalid edit-id command format.");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleEditContent(String commandLine,String[] parts){
        if (parts.length >= 2) {
            parts = commandLine.split(" ", 3);
            String targetId = parts[1];
            String targetContent="";
            if(parts.length>=3){
                targetContent= parts[2];
            }
             
            controller.editElementText(targetId, targetContent);
        } else {
            view.displayMessage("Ivalid edit-content format");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleUndo(String commandLine,String[] parts){
        controller.undoLastCommand();
    }

    private void handleRedo(String commandLine,String[] parts){
        controller.redoLastCommand();
    }

    private void handleInit(String commandLine,String[] parts){
        if (parts.length >= 3) {
            parts = commandLine.split(" ", 3);
            String documentName = parts[1];
            String title = parts[2];
            controller.initCommand(documentName, title);
        }else{
            controller.initCommand("new_document", "");
        } 
    }

    private void handleRead(String commandLine,String[] parts){
        if (parts.length >= 2) {
            parts = commandLine.split(" ", 2);
            String path = parts[1];
            controller.readFile(path);

        } else {
            view.displayMessage("Invalid read command");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleSave(String commandLine,String[] parts){
        if (parts.length >= 2) {
            parts = commandLine.split(" ", 2);
            String path = parts[1];
            controller.writeFile(path);
        } else {
            view.displayMessage("Invalid save command");
            view.displayMessage("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handlePrintTree(String commandLine,String[] parts){
        controller.printTree();
    }

    

    private void handlePrintIndent(String commandLine,String[] parts){
        int indent=2;
        if(parts.length>=2){
            String indentStr=parts[1];
            
            try{
                indent=Integer.parseInt(indentStr);
            }catch(Exception e){

            }
        }
        controller.printIndent(indent);
    }

    private void handleSpellCheck(String commandLine,String[] parts){
        controller.spellCheck();
    }

    private void handleHelp(String commandLine,String[] parts){
        view.displaySplitLine();
        view.displayMessage("Available Commands: ");
        for(var cmd:commandMap.values()){
            view.displayMessage(cmd);
        }
        view.displaySplitLine();
    }

    private void initCommandMap(){
        
        commandMap.put("append", "append tagName idValue parentId [textContent]");
        commandMap.put("delete","delete id");
        commandMap.put("insert", "insert tagName idValue brotherId [textContent]");
        commandMap.put("edit-id","edit-id oldId newId");
        commandMap.put("edit-text","edit-text id [newTextContent]");
        commandMap.put("undo","undo");
        commandMap.put("redo","redo");
        commandMap.put("init","init [filename] [title]");
        commandMap.put("read", "read filepath");
        commandMap.put("save", "save filepath");
        commandMap.put("print-indent", "print-indent [indent]");
        commandMap.put("print-tree", "print-tree");
        commandMap.put("spell-check", "spell-check");
    }


}


