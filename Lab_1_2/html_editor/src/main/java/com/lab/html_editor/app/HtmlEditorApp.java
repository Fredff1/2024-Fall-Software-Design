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


    private boolean isRunning=true;

    
    
 
    /**
     * APP层
     * 顶层组装核心组件的部分
     */
    public HtmlEditorApp(){
        this.view=new HtmlView();
        this.spellCheckService=new SpellCheckService();
        this.controller=new HtmlController(view,spellCheckService);
        this.parser=new HtmlEditorCommandParser(controller,view,this);
        controller.setParser(parser);
    }

    /**
     * 主循环
     */
    public void start(){
        view.displayWelComeStage();
        controller.restoreHistory();
        while (isRunning) {
            try{
                parser.parseCommand(); 
            }catch(Exception e){
                view.displayErrorMessage(e.getMessage());
            }
        }
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

    public void setIsRunning(boolean isRunning){
        this.isRunning=isRunning;
    }


    /**
     * 测试用函数，模拟用户输入
     * @param input
     */
    public void simulateInput(String input){
        try{
            parser.analyzeCommand(input); 
        }catch(Exception e){
            view.displayMessage("A serious exception occurred: ");
            e.printStackTrace();
        }
    }

    
}
