package com.lab.html_editor.controller;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleCommandManager;

import java.util.*;

public class DocumentSession {
    private final HtmlDocument document;
    private final ConsoleCommandManager commandManager;
    private final Queue<Event> events;

    public DocumentSession(HtmlDocument document) {
        this.document = document;
        this.commandManager = new ConsoleCommandManager();
        this.events = new LinkedList<>();
    }

        /*可以对历史的event进行处理 */

    public Queue<Event> getAllEvents(){
        return events;
    }

    public Event getLastEvent(){
        return events.peek();
    }

    public void clearEvents(){
        events.clear();
    }

    public StatusEvent getLastStatusEvent(){
        Event lastStatusEvent = null;
        for (Event event : events) {
            if (event instanceof StatusEvent) {
                lastStatusEvent = (StatusEvent) event;
            }
        }
        return (StatusEvent) lastStatusEvent;
    }

    public void execute(ConsoleCommand command){
        commandManager.executeCommand(command);
    }

    public ConsoleCommandManager getCommandManager(){
        return commandManager;
    }

    public HtmlDocument getDocument(){
        return this.document;
    }
}
