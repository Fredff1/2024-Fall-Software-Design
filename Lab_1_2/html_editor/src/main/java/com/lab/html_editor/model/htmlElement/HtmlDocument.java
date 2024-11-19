package com.lab.html_editor.model.htmlElement;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.exceptions.HtmlAppendExcption;
import com.lab.html_editor.model.exceptions.HtmlCreateException;
import com.lab.html_editor.model.exceptions.HtmlDeleteException;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.exceptions.HtmlInsertException;
import com.lab.html_editor.model.exceptions.HtmlOperationFailException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.exceptions.HtmlSetChildException;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlBody;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlHead;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlTitle;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.utils.observer.Observable;
import com.lab.html_editor.utils.observer.Observer;
import com.lab.html_editor.utils.strategy.HtmlRepresentationStrategy;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementRenderVisitor;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementTextVisitor;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;

import java.util.*;

/**
 * 一个完整的HtmlDocument,包含HtmlElementService用于提供服务
 */
public class HtmlDocument implements Observable {
    private final HtmlComposite root;
    private final HtmlService htmlElementService;
    private final List<Observer> observers;
    private final Map<String,String[]> invalidDestination=new HashMap<>();

    private String documentname;
    private int indent=2;
    

    public HtmlDocument(String documentName, String title,HtmlService service) {
        this.htmlElementService = service;
        this.observers = new ArrayList<>();
        initValidateMap();
        this.documentname = documentName;
        // 初始化四个必定有的标签
        this.root = (HtmlComposite) htmlElementService.createElement("html", "html");

        HtmlHead head = (HtmlHead) htmlElementService.createElement("head", "head");
        HtmlTitle docu_title = (HtmlTitle) htmlElementService.createElement("title", "title", title);
        HtmlBody body = (HtmlBody) htmlElementService.createElement("body", "body");
        head.addChild(docu_title);
        this.root.addChild(head);
        this.root.addChild(body);

    }

    public HtmlComposite getRoot() {
        return this.root;
    }

    public HtmlService getService(){
        return this.htmlElementService;
    }

    public void setDocumentName(String documentName) {
        this.documentname = documentName;
    }

    public String getDocumentName() {
        return this.documentname;
    }

    /**
     * 通用的寻找对应id节点方法
     * @param id
     * @return
     * @throws HtmlServiceSearchException
     */
    public HtmlElement search(String id) throws HtmlServiceSearchException{
        HtmlElement target = null;
        target = htmlElementService.getElementById(id);
        return target;
    }

    /**
     * append命令
     * @param sourcetagNameString 新建的tag
     * @param sourceId 新建的id
     * @param parentId 目标父亲的id
     * @param sourceTextContent text
     * @return
     */
    public boolean append(String sourcetagNameString, String sourceId, String parentId, String sourceTextContent) {
        validateDestination("append",parentId);
        TreeNode parentNode = null;
        
        parentNode = root.findChild(parentId);
        if(parentNode==null){;
            throw new HtmlAppendExcption("parent of id: " + parentId + " does not exist");
        }
            
        

        if (!(parentNode instanceof HtmlComposite)) {
            throw new HtmlAppendExcption("cannot append elements to non-composite node");
        }

        HtmlElement sourceHtmlElement = null;
        try {
            sourceHtmlElement = htmlElementService.createElement(sourcetagNameString, sourceId, sourceTextContent);
        } catch (HtmlCreateException e) {
           throw new HtmlAppendExcption(e.getMessage());
        }

        try {
            htmlElementService.setElementAsChild(parentId, sourceHtmlElement, -1);
        } catch (HtmlSetChildException e) {
            htmlElementService.reverseCreate(sourceId);
            throw new HtmlAppendExcption(e.getMessage());
        }
       
        return true;

    }

    /**
     * insert命令
     * @param sourcetagNameString 新建tag
     * @param sourceId 新建id
     * @param brotherId 兄弟的id
     * @param sourceTextContent 文本
     * @return
     */
    public boolean insert(String sourcetagNameString, String sourceId, String brotherId, String sourceTextContent) {
        validateDestination("insert",brotherId);
        TreeNode brotherNode = null;
        
        brotherNode = root.findChild(brotherId);
        if(brotherNode==null){
            throw new HtmlInsertException("cannot find brother node of id: "+brotherId);
        }
            
        var parentNode = brotherNode.getFather();

        HtmlElement sourceHtmlElement = null;
        try {
            sourceHtmlElement = htmlElementService.createElement(sourcetagNameString, sourceId, sourceTextContent);
        } catch (HtmlCreateException e) {
            throw new HtmlInsertException(e.getMessage());
        }

        try {
            int index_of_brother = ((HtmlComposite) parentNode).getChildIndex(brotherNode);
            htmlElementService.setElementAsChild(((HtmlComposite) parentNode).getId(), sourceHtmlElement,
                    index_of_brother);

        } catch (HtmlSetChildException e) {
            htmlElementService.reverseCreate(sourceId);
            throw new HtmlInsertException(e.getMessage());
        }
       
        return true;
    }

    /**
     * 将child设为parent对应id的child，并加入到对应index
     * @param parentId
     * @param child
     * @param addIndex
     */
    public void setElementAsChild(String parentId, HtmlElement child, int addIndex) {
        htmlElementService.setElementAsChild(parentId, child, addIndex);
    }

    /**
     * 修改id
     * @param oldId
     * @param newId
     * @return
     * @throws HtmlServiceSearchException
     */
    public boolean editID(String oldId, String newId) throws HtmlServiceSearchException{
        try{
            validateDestination("edit-id", oldId);
            htmlElementService.updateElementId(oldId, newId);
            return true;
        }catch(HtmlServiceSearchException e){
            throw new HtmlEditFailException(e.getMessage());
        }
        
    }

    /**
     * 修改文本
     * @param id
     * @param newContent
     * @return
     * @throws HtmlServiceSearchException
     */
    public boolean editContent(String id, String newContent) throws HtmlServiceSearchException{
        try{
            htmlElementService.updateElementContent(id, newContent);
            return true;
        }catch(HtmlServiceSearchException e){
            throw new HtmlEditFailException(e.getMessage());
        }
    }

    /**
     * 删除节点
     * @param targetId
     * @return
     * @throws HtmlDeleteException
     */
    public boolean delete(String targetId) throws HtmlDeleteException {
        validateDestination("delete", targetId);
        try{
            htmlElementService.removeElement(targetId);
        }catch(HtmlServiceSearchException e){
            throw new HtmlDeleteException(e.getMessage());
        }
        
        return true;
    }

    /**
     * 使用对应访问者得到整体的打印信息
     * @return
     */
    public HtmlElementRenderVisitor getRenderInfo() {
        HtmlElementRenderVisitor visitor = new HtmlElementRenderVisitor(indent);
        try {
            htmlElementService.visitElement("html", visitor);
        } catch (Exception e) {

        }
        return visitor;
    }

    public void setIndent(int indent){
        this.indent=indent;
    }

    public int getIndent(){
        return indent;
    }

    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        root.convertAllRepresentationStrategies(strategy);
    }

    

    public HtmlRepresentationStrategy getRepresentationStrategy(){
        var strategy=root.getRepresentationStrategy();
        return strategy;
    }

    
    /**
     * 得到所有节点的文本内容
     * @return
     */
    public HtmlElementTextVisitor getAllText(){
        HtmlElementTextVisitor textVisitor=new HtmlElementTextVisitor();
        try{
            htmlElementService.visitElement("html", textVisitor);
        }catch(Exception e){

        }
        return textVisitor;
    }

    public int getElementIndex(String id) {
        return htmlElementService.getIndexOfChild(id);
    }


    

    // 生成整个文档的 HTML 表示
    public String toHtmlString(int indent) {
        return root.toStringRepresentation(indent);
    }

    public void visitRoot(HtmlVisitor visitor){
        htmlElementService.visitElement("html", visitor);
    }

    public void visitElement(HtmlVisitor visitor,String id){
        htmlElementService.visitElement(id, visitor);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Event event) {
        for (var observer : observers) {
            observer.update(event);
        }
    }

    /**
     * 不允许修改特定节点
     * @param operationType
     * @param operationTargetId
     * @return
     */
    private boolean validateDestination(String operationType,String operationTargetId){
        if(invalidDestination.containsKey(operationType)){
            String[] targets=invalidDestination.get(operationType);
            for(var dest:targets){
                if(operationTargetId.equals(dest)){
                    throw new HtmlOperationFailException("cannot "+operationType +" because "+operationTargetId+" do not support "+operationType);
                }
            }
        }
        
        return true;
        
    }

    private void initValidateMap(){
        invalidDestination.put("append", new String[]{"html","head"});
        invalidDestination.put("delete", new String[]{"html","title","body","head"});
        invalidDestination.put("insert",new String[]{"body","title"});
        invalidDestination.put("edit-id", new String[]{"html","title","body","head"});
    }
}
