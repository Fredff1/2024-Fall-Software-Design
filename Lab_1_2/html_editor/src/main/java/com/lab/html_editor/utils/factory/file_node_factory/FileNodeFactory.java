package com.lab.html_editor.utils.factory.file_node_factory;

import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.factory.TreeNodeFactory;

public class FileNodeFactory implements TreeNodeFactory{


    public AbstractFileNode createComponent(String... features){
        if(features.length<3){
            throw new IllegalArgumentException("Invalid file Node creation");
        }

        String type=features[0];
        String name=features[1];
        String absolutePath=features[2];
        AbstractFileNode node;
        if(type.equals("directory")){
            node= new DirectoryNode(name, absolutePath);
        }else if(type.equals("file")){
            node= new FileNode(name, absolutePath);
        }else{
            return null;
        }
        node.addDecorator(new FileNodeUpdateStatusDecorator(node));
        return node;
    }
}
