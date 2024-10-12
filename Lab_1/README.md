html-editor/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── lab/
│   │   │           └── htmleditor/
│   │   │               ├── model/          # HTML模型层 实现基本的TreeNode HtmlElement 和具体的html元素
|   |   |               ├── stategy/         # 策略模式 实现不同的html打印策略
│   │   │               ├── command/        # 命令层 实现命令模式
│   │   │               ├── controller/     # 控制器层 管理用户命令交互 输入输出 
│   │   │               ├── io/             # 输入输出层 管理读写文件和打印
│   │   │               └── utils/          # 工具层 一些通用的utility
│   ├── test/                                # 测试代码
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── lab/
│   │   │           └── htmleditor/
│   │   │               ├── model/          # HTML模型测试类
│   │   │               ├── command/        # 命令类测试
│   │   │               ├── controller/     # 控制器类测试
│   │   │               ├── io/             # 输入输出类测试
│   │   │               └── utils/          # 工具类测试


## TreeNode 类
所有树节点的基类

## HtmlElement 类 继承 TreeNode 代表了所欲html元素

## HtmlRepresentationStrategy 接口 html的表示方法
