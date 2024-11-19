package com.lab.html_editor.utils.command;

public interface ConsoleCommand {
    
    boolean execute();  // 执行命令
    boolean undo();     // 撤销命令
    boolean redo();

    default boolean supportsUndo() {
        return true;
    }
}
