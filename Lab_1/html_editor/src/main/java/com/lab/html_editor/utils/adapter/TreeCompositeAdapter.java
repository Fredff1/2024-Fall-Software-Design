package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeNode;

import java.util.List;

public interface TreeCompositeAdapter {
    public String getFeature();
    public List<TreeNodeAdapter> getChildren();
    
} 