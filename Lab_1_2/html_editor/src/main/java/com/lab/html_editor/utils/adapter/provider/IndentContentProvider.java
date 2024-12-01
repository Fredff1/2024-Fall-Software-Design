package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeNode;

public interface IndentContentProvider<T> extends FeatureProvider<T>,TextProvider<T>,
PrefixProvider<T>,SuffixProvider<T>{
    
}
