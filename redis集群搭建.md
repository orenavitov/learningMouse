# redis集群搭建

## 工具需求
1. redis 下载地址：https://github.com/MSOpenTech/redis/releases
2. ruby 下载地址：https://rubyinstaller.org/downloads/
3. ruby_gem 下载地址： https://rubygems.org/pages/download
4. redis集群启动脚本 下载地址： https://raw.githubusercontent.com/antirez/redis/unstable/src/redis-trib.rb （保存网页为redis-trib.rb）

## 搭建步骤

### 创建集群节点
redis 要求集群中必须至少有3个节点，如果对每个节点实施备份，需要至少6个redis节点；
1. 在下载的redis中修改redis.windows.conf文件：
port 修改，对应端口号
cluster-enabled yes
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
appendonly yes
2. 复制5分redis解压后的文件夹，由于在上一步中对原始解压后的文件夹已经做了部分修改，所以在复制的5分文件中只需要修改端口号
3. 在每一份复制的文件夹中创建一个start的bat文件

```` 
title redis-6380
redis-server.exe redis.windows.conf
````

4. 将redis集群的启动脚本redis-trib.rb保存至其中一个节点下，这里保存在端口号为6379的节点下

### redis集群启动
1. 首先启动每个节点的start.bat文件

2. 在端口为6379的几点下执行
```` 
redis-trib.rb create --replicas 1 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384
```` 
3. 每次重新启动redis集群是需要将节点下的dump.rdb,  appendonly.aof, 节点名.conf文件删除
