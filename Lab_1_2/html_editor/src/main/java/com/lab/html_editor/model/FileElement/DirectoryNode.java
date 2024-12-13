package com.lab.html_editor.model.FileElement;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;

public class DirectoryNode extends AbstractFileNode implements TreeComposite{
    
    private List<TreeNode> children;

    public DirectoryNode(String name,String absolutePath,TreeComposite father) {
        super(name, absolutePath, father);
        this.children = new ArrayList<>();
    }

    public DirectoryNode(String name,String absolutePath) {
        super(name, absolutePath, null);
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getChildrenSize() {
        return children.size();
    }

    @Override
    public TreeNode getChild(int childIndex) throws IllegalArgumentException {
        if (childIndex < 0 || childIndex >= children.size()) {
            throw new IllegalArgumentException("Invalid child index");
        }
        return children.get(childIndex);
    }

    public AbstractFileNode getChild(String name){
        for(var child:children){
            AbstractFileNode fileNode=(AbstractFileNode)child;
            if(name.equals(fileNode.getName())){
                return fileNode;
            }
        }
        return null;
    }

    @Override
    public boolean addChild(TreeNode node) {
        if (node instanceof AbstractFileNode) {
            ((AbstractFileNode) node).setFather(this);
        }
        return children.add(node);
    }

    @Override
    public boolean addChild(TreeNode node, int index) {
        if (index < 0 || index > children.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        if (node instanceof AbstractFileNode) {
            ((AbstractFileNode) node).setFather(this);
        }
        children.add(index, node);
        return true;
    }

    @Override
    public boolean removeChild(TreeNode node) {
        return children.remove(node);
    }

    @Override
    public List<TreeNode> getChildren() {
        return children;
    }

}
