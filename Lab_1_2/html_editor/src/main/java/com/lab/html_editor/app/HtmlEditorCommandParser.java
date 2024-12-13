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
    private final HtmlEditorApp app;
    private final Map<String,String> commandMap;
    private final Scanner scanner;


    public HtmlEditorCommandParser(HtmlController controller, HtmlView view,HtmlEditorApp app) {
        this.controller = controller;
        this.view = view;
        this.app=app;
        this.scanner=new Scanner(System.in);
        commandMap=new LinkedHashMap<>();
        initCommandMap();
    }

    public void analyzeCommand(String commandLine){
        String[] parts = commandLine.split(" ");
        String command = parts[0];
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
            case "load":
                handleLoad(commandLine, parts);
                break;
            case "save":
                handleSave(commandLine, parts);
                break;
            case "close":
                handleClose(commandLine, parts);
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
            case "showid":
                handleShowId(commandLine, parts);
                break;
            case "help":
                handleHelp(commandLine, parts);
                break;
            case "editor-list":
                handleEditorList();
                break;
            case "dir-tree":
                handleDirTree(commandLine, parts);
                break;
            case "dir-indent":
                handleDirIndent(commandLine, parts);
                break;
            case "edit":
                handleSwitchEditor(commandLine, parts);
                break;
            case "exit":
                handleExit(commandLine, parts);
                break;
            default:
                handleUnknownCommand(commandLine, parts);
                break;
        }
    }

    // 解析命令行输入，并调用对应的 Controller 方法
    public void parseCommand() {
        view.displayMessageInOneLine("");
        String commandLine = scanner.nextLine();
        analyzeCommand(commandLine);
        
    }

    public boolean confirmCommand(){
        boolean flag=true;
        while(flag){
            String input=scanner.nextLine().trim();
            if(input.equals("yes")){
                return true;
            }else if(input.equals("no")){
                return false;
            }else{
                continue;
            }
        }
        return false;
        
    }

    private void handleUnknownCommand(String commandLine,String[] parts){
        controller.handleUnknownCommand(commandLine,commandMap.keySet());
    }

    private void handleShowId(String commandLine,String[] parts){
        if(parts.length>=2){
            parts = commandLine.split(" ", 2);
            String flag=parts[1];
            if(flag.equals("true")){
                boolean showId=true;
                controller.showIdCommand(showId);
            }else if(flag.equals("false")){
                boolean showId=false;
                controller.showIdCommand(showId);
            }else{
                view.displayErrorMessage("Invalid flag "+parts[1]);
            }
        }else{
            view.displayErrorMessage("flag not found");
        }
            
        
    }

    private void handleClose(String commandLine,String[] parts){
        controller.closeActiveEditor();
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
            view.displayErrorMessage("Invalid append command format.");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
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
            view.displayErrorMessage("Invalid insert command format.");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleDelete(String commandLine,String[] parts){
        if (parts.length >= 2) {
            String targetId = parts[1];
            controller.deleteElement(targetId);
        } else {
            view.displayErrorMessage("Invalid delete command format.");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleEditId(String commandLine,String[] parts){
        if (parts.length >= 3) {
            String oldId = parts[1];
            String newId = parts[2];
            controller.editElementId(oldId, newId);
        } else {
            view.displayErrorMessage("Invalid edit-id command format.");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
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
            view.displayErrorMessage("Ivalid edit-content format");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleUndo(String commandLine,String[] parts){
        controller.undoLastCommand();
    }

    private void handleRedo(String commandLine,String[] parts){
        controller.redoLastCommand();
    }



    private void handleLoad(String commandLine,String[] parts){
        if (parts.length >= 2) {
            parts = commandLine.split(" ", 2);
            String path = parts[1];
            controller.loadFile(path);

        } else {
            view.displayErrorMessage("Invalid read command");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
        }
    }

    private void handleSave(String commandLine,String[] parts){
        if (parts.length >= 1) {
            controller.saveFile();
        } else {
            view.displayErrorMessage("Invalid save command");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
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
        view.displayInfo("Available Commands: ");
        for(var cmd:commandMap.values()){
            view.displayMessage(cmd);
        }
        view.displaySplitLine();
    }

    private void handleEditorList(){
        controller.listEditors();
    }

    void handleDirTree(String commandLine,String[] parts){
        controller.printDirTree();
    }

    void handleDirIndent(String commandLine,String[] parts){
        int indent=2;
        if(parts.length>=2){
            String indentStr=parts[1];
            try{
                indent=Integer.parseInt(indentStr);
            }catch(Exception e){
                view.displayErrorMessage("Invalid save command");
                view.displayInfo("Correct format: "+commandMap.get(parts[0]));
            }
        }
        controller.printDirIndent(indent);
    }

    void handleSwitchEditor(String commandLine,String[] parts){
        String path="";
        if(parts.length>=2){
            path=parts[1];
            controller.switchEditor(path);
        }else{
            view.displayErrorMessage("Invalid edit format");
            view.displayInfo("Correct format: "+commandMap.get(parts[0]));
        }
        
           
    }

    void handleExit(String commandLine,String[] parts){
        controller.recordAndExit();
        scanner.close();
        app.setIsRunning(false);
    }

    private void initCommandMap(){
        
        commandMap.put("append", "append tagName idValue parentId [textContent]");
        commandMap.put("delete","delete id");
        commandMap.put("insert", "insert tagName idValue brotherId [textContent]");
        commandMap.put("edit-id","edit-id oldId newId");
        commandMap.put("edit-text","edit-text id [newTextContent]");
        commandMap.put("undo","undo");
        commandMap.put("redo","redo");
        commandMap.put("load","load [path to file (relative or absolute(must in workspace))] [title]");
        commandMap.put("save", "save ");
        commandMap.put("edit", "edit filepath(relative or absolute)");
        commandMap.put("close", "close");
        commandMap.put("print-indent", "print-indent [indent]");
        commandMap.put("print-tree", "print-tree");
        commandMap.put("showid", "showid [true/false]");
        commandMap.put("help", "help");
        commandMap.put("editor-list", "editor-list");
        commandMap.put("dir-tree", "dir-tree");
        commandMap.put("dir-indent", "dir-indent");
        commandMap.put("spell-check", "spell-check");
        commandMap.put("exit", "exit");
    }


}


