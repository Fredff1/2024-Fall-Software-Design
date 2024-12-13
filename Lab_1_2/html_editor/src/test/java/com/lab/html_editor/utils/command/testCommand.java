package com.lab.html_editor.utils.command;

import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.model.exceptions.HtmlAppendExcption;
import com.lab.html_editor.model.exceptions.HtmlDeleteException;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlAppendCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlDeleteCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlEditContentCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlEditIdCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlInsertCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlPrintIndentCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlPrintTreeCommand;
import com.lab.html_editor.utils.command.editor_command.ConsoleHtmlSpellCheckCommand;
import com.lab.html_editor.view.HtmlView;

public class testCommand {
    private ConsoleCommandManager manager = new ConsoleCommandManager();
    private HtmlDocument document = null;
    private HtmlView view = new HtmlView();
    private HtmlController controller = new HtmlController(view,new SpellCheckService());

    @Before
    public void setUp() {
        controller.loadFile("111.txt");
        document=controller.getActiveDocument();
    }

    @Test
    public void testBasicCommands() {
        ConsoleHtmlAppendCommand appendCommand = new ConsoleHtmlAppendCommand(controller, "li", "li_1", "body", "li 1");
        ConsoleHtmlInsertCommand insertCommand = new ConsoleHtmlInsertCommand(controller, "li", "li_2", "li_1", "li 2");
        ConsoleHtmlDeleteCommand deleteCommand = new ConsoleHtmlDeleteCommand(document, "li_1");
        manager.executeCommand(appendCommand);
        assert (document.search("li_1").getText().equals("li 1"));
        manager.executeCommand(insertCommand);
        assert (document.search("li_2").getText().equals("li 2"));
        manager.executeCommand(deleteCommand);
        try {
            document.search("li_1");
        } catch (Exception e) {
            assert (e instanceof HtmlServiceSearchException);
        }
        ConsoleHtmlEditIdCommand editIdCommand = new ConsoleHtmlEditIdCommand(document, "li_2", "li_3");
        manager.executeCommand(editIdCommand);
        assert (document.search("li_3").getText().equals("li 2"));
        ConsoleHtmlEditContentCommand editContentCommand = new ConsoleHtmlEditContentCommand(document, "li_3", "li 3",new SpellCheckService());
        assert (manager.executeCommand(editContentCommand));
        assert (document.search("li_3").getText().equals("li 3"));
        // 打印命令和拼写检查不做assert，只判断是否执行到对应路径
        ConsoleHtmlPrintIndentCommand printIndentCommand = new ConsoleHtmlPrintIndentCommand(document, view, 2);
        assert (manager.executeCommand(printIndentCommand));
        ConsoleHtmlPrintTreeCommand printTreeCommand = new ConsoleHtmlPrintTreeCommand(document);
        assert (manager.executeCommand(printTreeCommand));
        
        ConsoleHtmlSpellCheckCommand appCheckCommand = new ConsoleHtmlSpellCheckCommand(document,
                new SpellCheckService());
        assert (manager.executeCommand(appCheckCommand));

    }

    @Test
    public void testUndoRedo() {
        ConsoleHtmlAppendCommand appendCommand = new ConsoleHtmlAppendCommand(controller, "li", "li_1", "body", "li 1");
        ConsoleHtmlEditContentCommand editContentCommand = new ConsoleHtmlEditContentCommand(document, "li_1",
                "Updated li 1",new SpellCheckService());

        // 执行添加命令并检查
        manager.executeCommand(appendCommand);
        assert (document.search("li_1").getText().equals("li 1"));

        // 执行编辑内容命令并检查
        manager.executeCommand(editContentCommand);
        assert (document.search("li_1").getText().equals("Updated li 1"));

        // 测试撤销编辑命令
        manager.undo();
        assert (document.search("li_1").getText().equals("li 1")); // 内容应回到原始值

        // 测试撤销添加命令
        manager.undo();
        try {
            document.search("li_1");
        } catch (Exception e) {
            assert (e instanceof HtmlServiceSearchException); // 验证删除后的搜索异常
        }

        // 测试重做添加命令
        manager.redo();
        assert (document.search("li_1").getText().equals("li 1")); // 重做后内容应恢复

        // 测试重做编辑命令
        manager.redo();
        assert (document.search("li_1").getText().equals("Updated li 1")); // 重做后应恢复到编辑后的内容

        // 测试多次撤销和边界情况
        manager.undo(); // 撤销编辑
        manager.undo(); // 撤销添加
        manager.undo(); // 应不会再撤销成功
        assert (!manager.undo()); // 撤销栈应为空

        // 测试多次重做和边界情况
        manager.redo(); // 重做添加
        manager.redo(); // 重做编辑
        manager.redo(); // 应不会再重做成功
        assert (!manager.redo()); // 重做栈应为空
    }

    @Test
    public void testDuplicateIdAppend() {
       

        // 第一次 append 正常
        document.append("p", "p1", "body", "First paragraph");

        // 重复 ID append 
        assertThrows(HtmlAppendExcption.class, () -> {
            document.append("p", "p1", "body", "Duplicate paragraph");
        });
    }

    @Test
    public void testNonExistentIdDelete() {
        

        // 不存在的元素 ID 
        assertThrows(HtmlDeleteException.class, () -> {
            document.delete("nonexistentId");
        });
    }

    @Test
    public void testInvalidInsertOperation() {
        

        // 先添加一个叶子元素，再尝试在其下插入元素
        document.append("p", "p1", "body", "Leaf element");

        // 试图在叶子元素下插入新元素
        assertThrows(HtmlAppendExcption.class, () -> {
            document.append("p", "p2", "p1", "Invalid insert into leaf");
        });
    }

    @Test
    public void testEditIdWithNonExistentElement() {
        

        // 尝试修改不存在的 ID 
        
        ConsoleHtmlEditIdCommand editIdCommand = new ConsoleHtmlEditIdCommand(document, "li_2", "li_3");
        assert(!manager.executeCommand(editIdCommand));
        
    }

    @Test
    public void testEditIdWithDuplicateId() {
       

        // 正常添加两个元素
        document.append("p", "p1", "body", "Paragraph 1");
        document.append("p", "p2", "body", "Paragraph 2");

        // 尝试将 p2 的 ID 修改为 p1
        
        ConsoleHtmlEditIdCommand editIdCommand = new ConsoleHtmlEditIdCommand(document, "p2", "p1");
        assert(!manager.executeCommand(editIdCommand));
        
    }

    @Test
    public void testEditContentWithNonExistentElement() {
        // 尝试编辑不存在元素的内容
        assertThrows(HtmlEditFailException.class, () -> {
            document.editContent("nonexistentId", "New content");
        });
    }

}
