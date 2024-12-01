package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;

public interface IndentLeafProvider extends IndentContentProvider<TreeComposite>{
  
    public boolean isTextNode();
}
