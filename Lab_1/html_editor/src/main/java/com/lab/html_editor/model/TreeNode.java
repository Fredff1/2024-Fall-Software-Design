package com.lab.html_editor.model;

import java.util.ArrayList;
import java.util.List;



public abstract class TreeNode {
    private String name;
    private String id;
    private List<TreeNode> children;
    /**
     * 通用的文本树节点
     */
    public TreeNode(String name,String id){
        this.name=name;
        this.id=id;
        this.children=new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }
    
    public String getId(){
        return this.id;
    }

    public List<TreeNode> getChildren(){
        return this.children;
    }

    public TreeNode getChild(int child_index) throws IllegalArgumentException{
        if (child_index>=children.size()){
            throw new IllegalArgumentException("Child index out of range for Node "+this.id);
        }
        return children.get(child_index);
    }

    public boolean addChild(TreeNode node){
        try{
            this.children.add(node);
            return true;
        }catch(Exception e){
            System.out.println("Failed to add a child node for Node "+this.id);
            return false;
        }
    }

    public boolean removeChild(TreeNode node){
        try{
            this.children.remove(node);
            return true;
        }catch(Exception e){
            System.out.println("Filed to remove child node "+node.getId()+" for father node "+this.id);
            return false;
        }
    }

    public boolean removeChild(String child_id){
        TreeNode child_to_remove=null;
        for(var child:this.children){
            if (child.getId().equals(child_id)){
                child_to_remove=child;
            }
        }
        if (child_to_remove!=null){
            this.children.remove(child_to_remove);
            return true;
        }else{
            System.out.println("Filed to remove child node "+child_id+" for father node "+this.id+"because father node has no child with this id");
            return false;
        }
    }

    // 抽象方法：生成节点的字符串表示（例如，生成 HTML 或文件树的表示）
    public abstract String toStringRepresentation();

    

    
}
