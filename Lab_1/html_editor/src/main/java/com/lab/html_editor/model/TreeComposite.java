package com.lab.html_editor.model;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeComposite extends TreeNode{
    private List<TreeNode> children;
    

    public TreeComposite(String id){
        super(id);
        this.children=new ArrayList<>();
    }

    public List<TreeNode> getChildren(){
        return this.children;
    }



    public TreeNode getChild(int child_index) throws IllegalArgumentException{
        if (child_index>=children.size()){
            throw new IndexOutOfBoundsException("Child index out of range for Node "+this.getId());
        }
        return children.get(child_index);
    }

    public boolean addChild(TreeNode node){
        var flag=this.addChild(node, getChildren().size());
        return flag;
    }

    public boolean addChild(TreeNode node,int index){
        try{
            this.children.add(index,node);
            node.setFather(this);
            return true;
        }catch(Exception e){
            System.out.println("Failed to add a child node for Node "+this.getId()+" because of exception "+e.getMessage());
            return false;
        }
    }

    public boolean removeChild(TreeNode node){
        try{
            this.children.remove(node);
            node.setFather(null);
            return true;
        }catch(Exception e){
            System.out.println("Filed to remove child node "+node.getId()+" for father node "+this.getId());
            return false;
        }
    }

    public boolean removeChild(String child_id){
        if(child_id==null){
           throw new IllegalArgumentException("child_id to remove cannot be null"); 
        }
        TreeNode child_to_remove=null;
        for(var child:this.children){
            var target_id=child.getId();
            if(target_id==null){
                continue;
            }
            if (target_id.equals(child_id)){
                child_to_remove=child;
                break;
            }
        }
        if (child_to_remove!=null){
            this.children.remove(child_to_remove);
            return true;
        }else{
            throw new RuntimeException("Failed to remove child node "+child_id+" for father node "+this.getId()+"because father node has no child with this id");
        }
    }

    

    public abstract String toStringRepresentation(int indentLevel);

}
