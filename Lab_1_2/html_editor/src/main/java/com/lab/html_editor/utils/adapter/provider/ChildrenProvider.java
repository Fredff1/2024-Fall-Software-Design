package com.lab.html_editor.utils.adapter.provider;

import java.util.List;

import com.lab.html_editor.model.TreeNode;


public interface ChildrenProvider<T> {
    public List<TreeNode> getChildren();
}
