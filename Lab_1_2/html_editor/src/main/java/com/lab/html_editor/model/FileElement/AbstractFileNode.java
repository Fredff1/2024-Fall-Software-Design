package com.lab.html_editor.model.FileElement;


import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.utils.decorator.*;
import com.lab.html_editor.utils.strategy.FileIndentedRepresentation;
import com.lab.html_editor.utils.strategy.FileRepresentationStrategy;
import com.lab.html_editor.utils.strategy.FileTreeRepresentation;
import com.lab.html_editor.utils.strategy.RepresentationStrategy;

import java.util.*;

public abstract class AbstractFileNode implements TreeNode,Decorative{
    protected TreeComposite father;
    protected String name;
    protected String absolutePath;
    private final Map<DecoratorType,FileNodeDecorator> decorators=new HashMap<>();
    protected FileRepresentationStrategy strategy= new FileTreeRepresentation();//new FileTreeRepresentation();
   


    public AbstractFileNode(String name,String absolutePath,TreeComposite father){
        this.father=father;
        this.name=name;
        this.absolutePath=absolutePath;

        
    }

    public String getAbsolutePath(){
        return absolutePath;
    }

    public String getName(){
        return name;
    }

  
    @Override
    public TreeComposite getFather() {
        return father;
    }

    @Override
    public void setFather(TreeComposite father) {
        this.father = father;
    }

    

    public void addDecorator(Decorator decorator){
        
        decorators.put(decorator.getType(), (FileNodeDecorator)decorator);
    }

    public void removeDEcorator(DecoratorType type){
        decorators.remove(type);
    }

    public FileNodeDecorator getDecorator(DecoratorType type){
        return decorators.get(type);
    }

    public String toStringRepresentation(){
        return strategy.toStringRepresentation(this);
    }

    public void setRepresentationStrategy(FileRepresentationStrategy strategy){
        this.strategy=strategy;
    }
}
