package com.lab.html_editor.model;




public abstract class TreeNode {
    protected String id;
    protected TreeNode father;
    /**
     * 通用的文本树节点
     */
    public TreeNode(String id){
        this.id=id;
        this.father=null;
    }

    public TreeNode getFather(){
        return this.father;
    }

    public void setFather(TreeNode father){
        this.father=father;
    }


    public String getId(){
        return this.id;
    }

    public abstract void setId(String id);

    
    // 抽象方法：生成节点的字符串表示（例如，生成 HTML 或文件树的表示）
    public abstract String toStringRepresentation(int indentLevel);

    

    
}
