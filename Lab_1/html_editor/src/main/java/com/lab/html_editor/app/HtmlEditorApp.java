package com.lab.html_editor.app;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.view.HtmlView;

import java.util.Scanner;

public class HtmlEditorApp {
    private final HtmlView view;
    private final HtmlController controller;
    private final HtmlEditorCommandParser parser;
    private final SpellCheckService spellCheckService;
    private final ConsoleCommandManager consoleCommandManager;
    private final Scanner scanner;

    private boolean isRunning=true;

    
    
 
    /**
     * APP层
     * 顶层组装核心组件的部分
     */
    public HtmlEditorApp(){
        this.view=new HtmlView();
        this.consoleCommandManager=new ConsoleCommandManager();
        this.spellCheckService=new SpellCheckService();
        this.controller=new HtmlController(view,consoleCommandManager,spellCheckService);
        this.parser=new HtmlEditorCommandParser(controller,view);
        this.scanner=new Scanner(System.in);
    }

    /**
     * 主循环
     */
    public void start(){
        view.displayWelComeStage();
        while (isRunning) {
            view.displayMessageInOneLine("");
            String commandLine = scanner.nextLine();
            if (commandLine.equalsIgnoreCase("exit")) {
                isRunning = false;
                view.displayMessage("Exiting the HTML Editor.");
            } else {
                try{
                    parser.parseCommand(commandLine); 
                }catch(Exception e){
                    view.displayMessage("A serious exception occurred: ");
                    view.displayMessage(e.getMessage());
                }
                
            }
        }
        scanner.close();
    }


    public HtmlView getView(){
        return view;
    }

    public HtmlController getController(){
        return controller;
    }

    public HtmlEditorCommandParser getParser(){
        return parser;
    }


    /**
     * 测试用函数，模拟用户输入
     * @param input
     */
    public void simulateInput(String input){
        try{
            parser.parseCommand(input); 
        }catch(Exception e){
            view.displayMessage("A serious exception occurred: ");
            view.displayMessage(e.getMessage());
        }
    }

    
}
