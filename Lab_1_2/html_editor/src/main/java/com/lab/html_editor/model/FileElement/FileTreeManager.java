package com.lab.html_editor.model.FileElement;
import java.io.File;
import java.util.*;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.factory.file_node_factory.FileNodeFactory;

public class FileTreeManager {
    private DirectoryNode root;
    private final String rootPath; 
    private final FileNodeFactory factory=new FileNodeFactory();
    private final Map<String, AbstractFileNode> nodeIndex = new HashMap<>();

    public FileTreeManager(String directoryPath) {
        rootPath=directoryPath;
        root = buildTree();
    }

    public FileTreeManager() {
        String defaultWorkspacePath = System.getProperty("user.dir") + File.separator + "workspace";
        File workspaceDir = new File(defaultWorkspacePath);

        // 如果目录不存在，则创建
        if (!workspaceDir.exists()) {
            boolean created = workspaceDir.mkdirs();
            if (!created) {
                throw new IllegalStateException("Failed to create workspace directory at: " + defaultWorkspacePath);
            }
        }

        // 初始化 rootPath 和 root 节点
        rootPath = defaultWorkspacePath;
        root = buildTree();
        
    }

    public DirectoryNode getRoot() {
        return root;
    }

    public AbstractFileNode getNodeById(String id) {
        String path=resolvePath(id);
        return nodeIndex.get(path);
    }

    public DirectoryNode buildTree() {
        File rootFile = new File(rootPath);
        if (!rootFile.exists() || !rootFile.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + rootPath);
        }
        DirectoryNode rootNode=(DirectoryNode)factory.createComponent("directory",rootFile.getName(),rootFile.getPath());
        nodeIndex.put(rootPath, rootNode);
        buildTreeRecursive(rootFile, rootNode);
        return rootNode;
    }

    public String resolvePath(String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(rootPath, path); // 将相对路径拼接到根目录
        }
        return file.getAbsolutePath();
    }

   

    private void buildTreeRecursive(File currentFile, DirectoryNode parentNode) {
        File[] files = currentFile.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String absolutePath = file.getAbsolutePath(); // 使用绝对路径作为 ID
            if (file.isDirectory()) {
                DirectoryNode dirNode=(DirectoryNode)factory.createComponent("directory",file.getName(),file.getAbsolutePath());
                parentNode.addChild(dirNode);
                nodeIndex.put(absolutePath, dirNode); // 添加到索引
                buildTreeRecursive(file, dirNode);
            } else {
                FileNode fileNode = (FileNode)factory.createComponent("file",file.getName(),file.getAbsolutePath());
                parentNode.addChild(fileNode);
                nodeIndex.put(absolutePath, fileNode); // 添加到索引
            }
        }
    }

    public void addNode(String path) {
        String absolutePath = resolvePath(path); // 解析路径为绝对路径
        File file = new File(absolutePath);
        if (nodeIndex.containsKey(absolutePath)) {
            throw new IllegalArgumentException("Node already exists: " + absolutePath);
        }
    
        // 获取父节点
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            throw new IllegalArgumentException("Cannot add root node dynamically.");
        }
        var parentFilePath=parentFile.getAbsolutePath();
        DirectoryNode parentNode = (DirectoryNode) nodeIndex.get(parentFilePath);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent node not found: " + parentFile.getAbsolutePath());
        }
    
        // 添加新节点
        if (file.isDirectory()) {
            DirectoryNode dirNode=(DirectoryNode)factory.createComponent("directory",file.getName(),file.getAbsolutePath());
            parentNode.addChild(dirNode);
            nodeIndex.put(absolutePath, dirNode);
        } else {
            FileNode fileNode = (FileNode)factory.createComponent("file",file.getName(),file.getAbsolutePath());
            parentNode.addChild(fileNode);
            nodeIndex.put(absolutePath, fileNode);
        }
    }

    public void removeNode(String path) {
        String absolutePath = resolvePath(path); // 解析路径为绝对路径
        AbstractFileNode node = nodeIndex.get(absolutePath);
        if (node == null) {
            throw new IllegalArgumentException("Node not found: " + absolutePath);
        }
    
        // 从父节点中移除
        DirectoryNode parentNode = (DirectoryNode)node.getFather();
        if (parentNode != null) {
            parentNode.removeChild(node);
        }
    
        // 递归删除所有子节点
        removeNodeRecursive(node);
    
        // 从索引中移除
        nodeIndex.remove(absolutePath);
    }
    
    private void removeNodeRecursive(AbstractFileNode node) {
        if (node instanceof DirectoryNode) {
            DirectoryNode dirNode = (DirectoryNode) node;
            for (TreeNode child : dirNode.getChildren()) {
                removeNodeRecursive((AbstractFileNode)child);
                nodeIndex.remove(((AbstractFileNode)child).getAbsolutePath());
            }
        }
    }
    
    

    
    

    public static String getBasename(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        path = path.replaceAll("[/\\\\]+$", ""); // 移除末尾的斜杠或反斜杠
        int lastSeparatorIndex = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return (lastSeparatorIndex == -1) ? path : path.substring(lastSeparatorIndex + 1);
    }
}
