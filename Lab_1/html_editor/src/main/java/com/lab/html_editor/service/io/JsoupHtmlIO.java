package com.lab.html_editor.service.io;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lab.html_editor.model.htmlElement.*;
import com.lab.html_editor.service.HtmlService;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

/**
 * 使用jsoup进行的IO，并转化为我的HtmlDocument
 */
public class JsoupHtmlIO implements HtmlIO {

    private HtmlDocument htmlDocument;

    
    

    @Override
    public HtmlDocument read(String filePath,HtmlService service) throws IOException {
        File file=new File(filePath);
        if (!file.exists()) {
            throw new IOException("File path "+filePath+" does not exist");
        }
        Document jsoupDocument = Jsoup.parse(file, "UTF-8");
        String title=jsoupDocument.title();
        HtmlDocument htmlDocument = new HtmlDocument(file.getName(), title,service);
        this.htmlDocument=htmlDocument;
        // 将 jsoup 解析的内容映射到自定义 HtmlDocument
        Element body = jsoupDocument.body();
        String body_text=body.ownText();
        Elements children = body.children();
        if (body != null) {
            if(body_text!=null&&body_text!=""){
                htmlDocument.editContent("body", body_text);
            }
            HtmlComposite htmlBody=(HtmlComposite)htmlDocument.getRoot().findChild("body");
            for(var child:children){
                htmlBody.addChild(mapElementToHtmlComposite(child));
            }
        }

        return htmlDocument;
    }

    private HtmlElement mapElementToHtmlComposite(Element jsoupElement) {
        HtmlElement element = (HtmlElement) convertSingle(jsoupElement);
        HtmlComposite composite=null;
        if(element instanceof HtmlComposite){
            composite=(HtmlComposite)element;
            for (Element child : jsoupElement.children()) {
                if (child.children().isEmpty()) {
                    composite.addChild(convertSingle(child));
                } else {
                    composite.addChild(mapElementToHtmlComposite(child));
                }
            }
            return composite;
        }else if(element instanceof HtmlLeaf){
            return element;
        }else{
            throw new RuntimeException("Error occured when reading document");
        }
    }

    private HtmlElement convertSingle(Element jsoupElement) {
        String tagName = jsoupElement.tagName();
        String id = jsoupElement.id();
        String text = jsoupElement.ownText();
        HtmlElement element = (HtmlElement) htmlDocument.getService().createElement(tagName, id, text);
        return element;
    }

    @Override
    public void write(HtmlDocument document, String filePath) throws IOException {
        // 将自定义结构转换回 HTML 格式并写入文件
        String htmlContent = document.getRoot().toStringRepresentation(2);
        File outputFile = new File(filePath);

        // 使用 FileWriter 将内容写入文件
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(htmlContent);
        } catch (IOException e) {
            throw new IOException("Failed to write HTML content to file: " + filePath, e);
        }
    }
}
