# C/C++ VSCodek开发环境搭建

1. 下载mingw-w64-install.exe

2. 添加环境变量：

    将mingw-w64安装目录下的bin文件夹路径加入Path中

    新建C_INCLUDE_PATH环境变量， 内容为mingw-w64安装目录下include文件夹的路径

3. vsCode安装C/C++插件， 该插件会自动根据设置的环境变量寻找相应
    的编译器。

## 相关配置文件

1. launch.json

```
{
    // 使用 IntelliSense 了解相关属性。 
    // 悬停以查看现有属性的描述。
    // 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "name": "(gdb) Launch",
            "type": "cppdbg",
            "request": "launch",
            "program": "${workspaceFolder}/${fileBasenameNoExtension}.exe",
            "args": [],
            "stopAtEntry": false,
            "cwd": "${workspaceFolder}",
            "environment": [],
            "externalConsole": true,
            "MIMode": "gdb",
            "miDebuggerPath": "C:/Program Files (x86)/mingw-w64/i686-8.1.0-posix-dwarf-rt_v6-rev0/mingw32/bin/gdb.exe",
            "setupCommands": [
                {
                    "description": "Enable pretty-printing for gdb",
                    "text": "-enable-pretty-printing",
                    "ignoreFailures": true
                }
            ],
            "preLaunchTask": "build"
        }
    ]
}
```

2. task.json

```
{
    // See https://go.microsoft.com/fwlink/?LinkId=733558
    // for the documentation about the tasks.json format
    "version": "2.0.0",
    "tasks": [
        {
            "label": "build",
            "type": "shell",
            "group": {
                "kind": "build",
                "isDefault": true
            },
            "presentation": {
                "echo": true,
                "reveal": "always",
                "focus": false,
                "panel": "shared"
            },
            "windows": {
                "command": "gcc",
                "args": [
                    // "-ggdb",
                    "\"${file}\"",
                    "--std=c11",
                    "-o",
                    "\"${fileDirname}\\${fileBasenameNoExtension}.exe\"",
                    "-g",
                ]
            }
        }
    ]
}
```

## 基本编译与调试方法

### 常用命令：

#### gcc
gcc 文件1.c 文件2.c -o 可执行程序.exe -g, 在生成可执行文件的命令后加-g 可进行调试。

#### gdb



