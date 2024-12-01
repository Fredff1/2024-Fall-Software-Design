package com.lab.html_editor.service;


import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.exceptions.HtmlChildOperationFailException;
import com.lab.html_editor.model.exceptions.HtmlCreateException;
import com.lab.html_editor.model.exceptions.HtmlDeplicatedIdException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.exceptions.HtmlSetChildException;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;
import com.lab.html_editor.utils.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementIdUpdateVisitor;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementTextUpdateVisitor;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;


import java.util.*;

/**
 * 统一的Html元素管理
 * 确保元素id唯一，同时管理各种元素操作以及储存
 */
public class HtmlService { 
    private final Map<String,HtmlElement> elementMap;
    private final BasicHtmlFactory htmlFactory;
    

    public HtmlService(){
        this.elementMap=new HashMap<>();
        this.htmlFactory=new BasicHtmlFactory();

    }

    // 创建元素并存储
    public HtmlElement createElement(String tagName, String id, String... content)
    throws HtmlCreateException {
        if (elementMap.containsKey(id)) {
            throw new HtmlCreateException("Id: "+id+" already exists",id);
        }
        HtmlElement element;
        try{
            if(content.length>0){
                element = (HtmlElement)htmlFactory.createComponent(id, tagName, content[0]);
            }else{ 
                element=(HtmlElement)(HtmlElement)htmlFactory.createComponent(id, tagName);
            }
        }catch(Exception e){
            throw new HtmlCreateException(e.getMessage());
        }
        
        
        // 使用工厂创建元素
        
        
        // 将元素注册到IDManager并存储到elementMap
        
        elementMap.put(id, element);
        element.addDecorator(new HtmlSpellCheckDecorator(element));
        element.addDecorator(new HtmlShowIdDecorator(element, true));
        return element;
    }

    public void reverseCreate(String id){
        elementMap.remove(id);
    }

    /**
     * 将对应element设置为parentId的child
     * 用于append insert命令
     * @param parentId
     * @param element
     * @param addIndex
     * @return
     * @throws HtmlSetChildException
     */
    public boolean setElementAsChild(String parentId,HtmlElement element,int addIndex)
    throws HtmlSetChildException{
        if(elementMap.containsKey(parentId)==false){
            throw new HtmlSetChildException("parent id: "+parentId+" does not exist");
        }

        if(element==null){
            throw new HtmlSetChildException("child not valid");
        }
        
        var parent=elementMap.get(parentId);
        
        if(parent instanceof HtmlComposite==false){
            throw new HtmlSetChildException("cannot add child to a leaf node");
        }

        boolean containChild=elementMap.containsValue(element);
        

        //element 不在map里，id也没有被注册，则注册并加入
        if(!containChild&&!elementMap.containsKey(element.getId())){
            elementMap.put(element.getId(), element);
        }else if(!containChild&&elementMap.containsKey(element.getId())){
            //element不在map但id被注册，不允许插入
            throw new HtmlSetChildException("id of child already exsits");
        }else{
            var father=(HtmlComposite)((TreeNode)element).getFather();
            if(father!=null){
                father.removeChild((TreeNode)element);
            }
        }
        
        var compositeParent=(HtmlComposite)parent;
        if(addIndex==0&&compositeParent.getTextElement()!=null){
            throw new HtmlChildOperationFailException("Cannot replace text node",element);
        }
        if(addIndex<0){
            compositeParent.addChild((TreeNode)element);
        }else{
            compositeParent.addChild((TreeNode)element, addIndex);
        }
        return true;
    }

    /**
     * 查找元素
     * @param id
     * @return
     * @throws HtmlServiceSearchException
     */
    public HtmlElement getElementById(String id) throws HtmlServiceSearchException{
        if(elementMap.containsKey(id)){
            return elementMap.get(id);
        }
        throw new HtmlServiceSearchException("element of Id: "+id+" does not exist");
    }

    /**
     * 得到id对应元素在其父节点的index
     * 
     * @param id 目标子节点的id
     * @return 如果是html根节点，返回0，否则返回在父节点的index
     */
    public int getIndexOfChild(String id){
        TreeNode element=(TreeNode)elementMap.get(id);
        HtmlComposite parent=(HtmlComposite)element.getFather();
        if(parent==null){
            return 0;
        }
        return parent.getChildIndex(element);
    }

    /**
     * 更新id
     * @param oldId
     * @param newId
     * @return
     * @throws HtmlServiceSearchException
     */
    public boolean updateElementId(String oldId, String newId)throws HtmlServiceSearchException {
       
        HtmlElement element = elementMap.get(oldId);
        if(element==null){
            throw new HtmlServiceSearchException("element of Id: "+oldId+" does not exist");
        }
        boolean newIdExist=elementMap.containsKey(newId);
        if(newIdExist){
            throw new HtmlDeplicatedIdException("Id "+newId+" already exists" );
        }
        HtmlElementIdUpdateVisitor idUpdateVisitor = new HtmlElementIdUpdateVisitor(newId,this);
        element.accept(idUpdateVisitor);
        return true;
    }


    /**
     * 因为操作比较多用,从一般的visitor种分开专门提供了一个方法
     * @param id
     * @param newContent
     * @return
     */
    public boolean updateElementContent(String id, String newContent) throws HtmlServiceSearchException{
        HtmlElement element = elementMap.get(id);
        if (element == null) {
            throw new HtmlServiceSearchException("element of Id: "+id+" does not exist");
        }
        HtmlElementTextUpdateVisitor updateVisitor=new HtmlElementTextUpdateVisitor(htmlFactory.createText(newContent));
        element.accept(updateVisitor);
        return true;
    }

    /**
     * 通用的visitor方法 
     * */
    public void visitElement(String id,HtmlVisitor visitor) throws HtmlServiceSearchException{
        var target=getElementById(id);
        if(target!=null){
            target.accept(visitor);
        }else{
            throw new HtmlServiceSearchException("element of Id: "+id+" does not exist");
        }
    }

    // 删除元素
    public boolean removeElement(String id) throws HtmlServiceSearchException {
        if (!elementMap.containsKey(id)) {
            throw new HtmlServiceSearchException("element of Id: "+id+" does not exist");
        }
        HtmlElement element = elementMap.remove(id); // 从Map中移除
        
        var father=(HtmlComposite)(element).getFather();
        if(father!=null){
            father.removeChild((TreeNode)element);
        }
        return true; // 从IDManager中移除
    }

    // 获取所有元素
    public Collection<HtmlElement> getAllElements() {
        return elementMap.values(); // 返回所有存储的元素
    }

    public Map<String,HtmlElement> getElementMap(){
        return elementMap;
    }

    

    

    

    
}
