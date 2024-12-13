package com.lab.html_editor.utils.command;

public abstract class ConsoleWorkspaceCommand implements ConsoleCommand{
    @Override
    public boolean supportsUndo() {
        return false;
    }
}
