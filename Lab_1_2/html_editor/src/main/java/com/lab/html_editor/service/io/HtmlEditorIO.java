package com.lab.html_editor.service.io;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.HtmlDocumentManager;
import com.lab.html_editor.controller.HtmlEditor;
import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.utils.command.workspace_command.ConsoleLoadFileCommand;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;



public class HtmlEditorIO {
     private static final String CONFIG_FOLDER = ".html_editor";
    private static final String STATE_FILE = CONFIG_FOLDER + "/editors_state.txt";

    /**
     * 将所有编辑器的信息保存到文件
     *
     * @param editors      编辑器集合
     * @param activeEditor 活动编辑器
     * @throws IOException 写入文件时发生错误
     */
    public static void saveEditors(HtmlDocumentManager manager, HtmlEditor activeEditor) throws IOException {
        // 创建文件夹
        Path folderPath = Paths.get(CONFIG_FOLDER);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath); // 自动创建所需的父目录
        }

        // 创建文件（如果不存在）
        Path filePath = Paths.get(STATE_FILE);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        // 写入编辑器状态信息
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATE_FILE))) {
            // 保存活动编辑器
            if (activeEditor != null) {
                writer.write("active_editor:" + activeEditor.getFileNode().getAbsolutePath());
                writer.newLine();
            }

            // 保存每个编辑器的信息
            for (var editor:manager) {
                if(editor.isFileExist()==false){
                    continue;
                }
                writer.write("editor:" + editor.getFileNode().getAbsolutePath());
                writer.newLine();
                writer.write("showid:" + editor.isShowId());
                writer.newLine();
            }
        }
    }

    /**
     * 从文件加载编辑器的信息并创建对应的 HtmlEditor
     *
     * @param controller 文档管理器，用于添加新的 HtmlEditor
     * @throws IOException 读取文件时发生错误
     */
    public static void loadEditors(HtmlController controller) throws IOException {
        File file = new File(STATE_FILE);
        var documentManager=controller.getDocumentManager();
        var fileManager=controller.getFileTreeManager();
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String activeEditorPath = null;
            HtmlEditor lastEditor = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(":", 2);
                if (parts.length < 2) continue;

                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "active_editor":
                        activeEditorPath = value;
                        break;
                    case "editor":
                        AbstractFileNode node=fileManager.getNodeById(value);
                        HtmlDocument document=controller.getIOManager().read(fileManager.resolvePath(value),new HtmlService());
                        HtmlElementSpellCheckVisitor visitor=new HtmlElementSpellCheckVisitor(controller.getSpellCheckService());
                        document.visitRoot(visitor);
                        var editor=controller.getDocumentManager().addEditor(document,(FileNode)node);
                        lastEditor=editor;
                        break;
                    case "showid":
                        if (lastEditor != null) {
                            lastEditor.setShowId(Boolean.parseBoolean(value));
                        }
                        break;
                }
            }

            // 设置活动编辑器
            if (activeEditorPath != null) {
                documentManager.setActiveEditor(activeEditorPath);
            }
        }
    }
}
