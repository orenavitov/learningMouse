# windows环境下git上传代码

1. 在git上创建新的repository

![](1.png)

2. 设置项目名称、项目描述与license

![](2.png)

3. 将git创建的空白项目clone到本地

![](3.png)

4. 将本地需要添加的文件复试到clone后的文件夹下

![](4.png)

5. 将clone文件夹下的文件add添加到git索引下

![](5.png)

6. git commit -m “提交信息”

![](6.png)

7. git push --all

![](7.png)

8. 如果是第一次上传需要全局配置git的用户名与密码

````
git config --global user.name "username"

git config --global user.email "email"
````

