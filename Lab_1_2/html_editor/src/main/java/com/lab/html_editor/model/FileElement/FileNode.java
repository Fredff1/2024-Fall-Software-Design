package com.lab.html_editor.model.FileElement;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeLeaf;

public class FileNode extends AbstractFileNode implements TreeLeaf{


    public FileNode(String name,String absolutePath,TreeComposite father) {
        super(name, absolutePath, father);

    }

    public FileNode(String name,String absolutePath){
        super(name, absolutePath, null);

    }

    @Override
    public String getName() {
        return name;
    }

    
}
