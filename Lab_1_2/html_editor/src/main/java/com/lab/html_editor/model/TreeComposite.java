package com.lab.html_editor.model;

import java.util.*;

public interface TreeComposite extends TreeNode{
   

    
    public int getChildrenSize();

    public TreeNode getChild(int child_index) throws IllegalArgumentException;

    public boolean addChild(TreeNode node);

    public boolean addChild(TreeNode node,int index);

    public boolean removeChild(TreeNode node);

    public List<TreeNode> getChildren();

    //public boolean removeChild(String child_id);


}
