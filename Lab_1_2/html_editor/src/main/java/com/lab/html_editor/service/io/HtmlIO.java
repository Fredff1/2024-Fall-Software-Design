package com.lab.html_editor.service.io;

import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;

import java.io.*;

public interface  HtmlIO {
    HtmlDocument read(String filePath,HtmlService service) throws IOException;
    void write(HtmlDocument document, String filePath) throws IOException;
}
