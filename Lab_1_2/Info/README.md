
# Lab 1 Html编辑器设计

## 1 项目架构
![](./pic/目录.png)


### 层级说明

#### model层
储存了树形结构的接口以及由组合模式组成的HtmlElement composite leaf 具体的各种Html元素以及document
#### io层 
使用jsoup读写html并转换为我的html模型
#### service层
包括管理id和html元素的服务，他们被用于HtmlDocument
包括拼写检查的服务模块
#### utils层
包含设计模式的内容
命令模式 用于各类具体命令
包含CommandManager用于执行命令以及一系列command
工厂模式 用于创建Html元素
分为抽象工厂（用于Treenode）以及具体工厂（用于HtmlElement）
观察者模式 用于同步model-controller-view之间的更新关系
策略模式 用于得到Html不同的转化格式(tree/indent)
访问者模式 用于访问HtmlDocument进行各种操作，如更新 读取文本用于检查等
#### view层
用于执行控制台打印
#### controller层
统合上述层
管理view、HtmlDocument(即model)以及拼写检查service、commandManager等服务
#### app层
创建主循环以及controller和CommandParser实例
由parser读取命令，交由controller进行所有操作

### 依赖关系

#### 依赖关系如图
![](./pic/架构.png)

#### 文字说明
1. Model 包
HtmlELement模块依赖于 HtmlRepresentationStrategy模块来转化为字符串
HtmlDocument模块依赖于 Model其他模块和HtmlService实现完整的文档功能
HtmlService层：
依赖于整个 Model，管理HtmlELement的各种操作
使用 HtmlFactory模块 和 HtmlVisitor模块使用工厂模式创建新元素，使用访问者模式进行访问
Observer模块：Model 依赖于 observer模块，实现Observable功能
2. Controller 包
Controller依赖于 HtmlDocument模块 和 View层，协调命令执行、数据管理与显示。
使用 command模块、observer模块、HtmlIO模块 和 SpellCheckService模块。
观察Model和View，同时具备IO和拼写检查的功能。
3. View 包
HtmlView间接使用 HtmlDocument模块（通过 Controller），通过 Controller 获取 HtmlDocument 的数据
使用 observer模块，实现Observable功能
4. App 包
App依赖 Controller模块 和 CommandParser模块，是程序的入口。

### 测试

使用Junit进行测试，针对所有模块都有一个或多个测试。
为了运行测试，输入以下命令
```bash
cd path/to/pom.xml
mvn test
```
特别注意：不要随意移动内部已经包含的文本文档

## 2 功能细节

### 文档要求的功能
#### init  
![](./pic/init.png)
#### append     
![](./pic/append.png)
#### insert   
![](./pic/insert.png)
#### delete  
![](./pic/delete.png)
#### edit-id  
![](./pic/edit_id.png)
#### edit-text  
![](./pic/edit_text.png)
#### print-tree  
![](./pic/print_tree.png)
#### print-indent  
![](./pic/print_indent.png)
#### spell-check  
![](./pic/spell_check.png)
#### undo  
以append为例
![](./pic/undo.png)
#### redo  
以append为例
![](./pic/redo.png)
#### read  
![](./pic/read1.png)
#### save  
![](./pic/save_1.png)
#### 未知命令识别
![](./pic/cmd_recommend.png)




## 3 运行

### 项目管理
本项目使用Maven进行构建并管理依赖
### jre版本: java 21
![](./pic/Java_ver.png)
### 运行方式
直接使用java运行打包好的jar文件，已包含所有依赖的库
![](./pic/start_run.png)
### 额外命令
除了要求的命令外，提供help命令打印所有可用的命令
![](./pic/help.png)