package com.lab.html_editor.utils.factory;




import com.lab.html_editor.model.TreeNode;



public interface TreeNodeFactory {
    TreeNode createComponent(String... args);
    
}
