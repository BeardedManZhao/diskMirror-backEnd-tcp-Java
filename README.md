![logo](https://github.com/user-attachments/assets/0b05e34b-96e3-44ec-8a69-bb9b82059a07)

# diskMirror-backEnd-tcp-Java

DiskMirror 的 TCP 服务器版本，TCP
协议的文件IO服务器，其具相较于[DiskMirror Http 服务器](https://github.com/BeardedManZhao/diskMirror-backEnd-spring-boot.git)
而言，具有更好的性能，以及更小的内存占用。

## 如何使用？

diskMirror 的 TCP 服务器版本是一个单独的 Java 程序，依赖等信息完全不需要您担心，且具有很好的性能，启动方式非常简单！

### 自动配置（推荐）

#### 启动脚本或按顺序输入命令

您只需要将这里的脚本的下载链接更换为 [项目存储库](https://github.com/BeardedManZhao/diskMirror-backEnd-tcp-Java/releases)
中 您期望的版本，然后直接在一个空目录运行即可！

```
wget <这里就是您的 URL>
echo "export DiskMirror_CONF=$(pwd)/conf/config.json" | sudo tee -a /etc/profile
mkdir ./conf
source /etc/profile
```

#### 直接进行启动

输入命令的操作，您可以前往 [直接进行启动](#直接进行启动) 章节中进行查阅！

### 手动配置（不推荐）

#### 下载 jar 包

首先我们前往[项目存储库](https://github.com/BeardedManZhao/diskMirror-backEnd-tcp-Java/releases)
中下载您需要的对应的版本，然后将其放到一个单独的目录中。

#### 启动项目

在这里我们将jar 包放到了目录 `/opt/app/diskMirror-Tcp-Java` 中，下面是查看目录中文件列表的日志展示！

```
root@armbian:/opt/app/diskMirror-Tcp-Java# ll
total 10748
drwxrwxrwx 2 root root     4096 Nov  5 19:54 ./
drwxr-xr-x 8 root root     4096 Nov  4 16:43 ../
-rwxrw-rw- 1 root root 10997488 Nov  5 19:44 DiskMirrorTcpServer.jar*

```

#### 配置 DiskMirror_CONF 变量

此变量指向的是一个 json 的配置文件，其中存储的就是 DiskMirror 后段服务的一些配置项目

```shell
export DiskMirror_CONF=/opt/app/diskMirror-Tcp-Java/config/config.json
```

#### 为配置文件做准备

当我们写好了环境变量之后，DiskMirror Tcp 服务器就知道了自己要从哪里读取配置文件，它如果读取不到会自己创建一个默认配置文件的。

而我们要做的就是确保 DiskMirror Tcp 服务器 可以顺利的创建出默认的配置文件，因此在这里我们要做些操作

##### 创建配置文件存储目录

我们设置的 DiskMirror_CONF 对应的路径是 `/opt/app/diskMirror-Tcp-Java/config/config.json`
其所在的目录就是 `/opt/app/diskMirror-Tcp-Java/config/` 我们在这里只需要创建 `/opt/app/diskMirror-Tcp-Java/config` 即可。

```
mkdir /opt/app/diskMirror-Tcp-Java/config
chmod -R 777 /opt/app/diskMirror-Tcp-Java/config
```

#### 直接进行启动

您可以按照下面的命令启动

```
# ThreadNumber 代表本服务器中可以同时处理多少请求，每个请求处理器需要单独占用一个线程
# tcpMetaPort,fileMetaPort 代表本服务器需要的两个端口，分别用来接收请求元数据，以及文件数据IO，注意这里不要有空格，两个端口号之间是使用逗号分割
java -jar DiskMirrorTcpServer.jar <ThreadNumber> <tcpMetaPort,fileMetaPort>
```

##### 一切正常下的启动引导

在下面是一个启动的示例，案例中，我们启动了一个 10 个线程的服务器，其打开了两个端口，10001用于接收请求元数据，10002是文件数据的IO通道。

```

root@armbian:/opt/app/diskMirror-Tcp-Java# java -jar ./DiskMirrorTcpServer.jar 10 10001,10002
WARNING: sun.reflect.Reflection.getCallerClass is not supported. This will impact performance.
[INFO][DiskMirrorBackEnd][24-11-05:08]] : threadNumber = 10
[INFO][DiskMirrorBackEnd][24-11-05:08]] : 加载配置文件：/opt/app/diskMirror-Tcp-Java/conf/config.json
[top.lingyuzhao.utils.ConfigureConstantArea][Tue Nov 05 20:48:55 CST 2024][WARNING]     The configuration file [/opt/app/diskMirror-Tcp-Java/./conf/conf.properties] does not exist. Use the default configuration.
[INFO][DiskMirrorBackEnd][24-11-05:08]] : public static void loadConf(webConf) run!!!
[INFO][DiskMirrorBackEnd][24-11-05:08]] : diskMirror 构建适配器:LocalFSAdapter:1.3.2
             'WWWKXXXXNWWNk,     ,kkd7               KWWb,                     
             ;WWN3.....,lNWWk.                       KWWb,                     
             ;WWNl        XWWk.  :XXk,   oKNNWNKo    KWWb,   dXXO:             
             ;WWNl  ^  ^  3WWX7  7WWO7  0WWo:,:O0d,  KWWb, lNWKb:              
             ;WWNl  -__-  :WWNl  7WWO7  0WWO,.       KWWbbXWKb:.               
             ;WWNl        kWW03  7WWO7   lXWWWN0o.   KWWNWWW0;                 
             ;WWNl       lWWNo,  7WWO7     .,7dWWN;  KWWOolWWN7                
             'WWNo,..,'oXWWKo'   7WWO7 .lb:    XWNl. KWWb, .KWWk.              
             ;WWWWWWWWWNKOo:.    7WWO7  oNWX0KWWKb:  KWWb,   bWWX'             
              ,'''''''',,.        ,'',    ,;777;,.    '''.    .''',            
KWWNWK,        ,WWNWWd.   ;33:                                                 
KWWbWWO.       XWXkWWd.   ...    ...  .,,   ...  ,,.      .,,,,        ...  .,,
KWWodWWd      OWNlOWWd.  .WWN7   KWW3OWNWl.:WWOlNWNO:  3KWWXXNWWXo.   ,WWX3XWNK
KWWo.OWWo    oWWb;xWWd.  .WWXl   0WWXkl',, ;WWNKb:,,, XWWkl,..,oWWN'  ,WWNKd7,,
KWWo  XWN7  ;WWx3 dWWd.  .WWXl   0WWO3     ;WWWl,    bWW03      OWWk, ,WWWo'   
KWWo  ,NWK',NW0l  dWWd.  .WWXl   0WWd,     ;WWX3     kWWO:      dWMO: ,WWNl    
KWWo   ;WWkKWXl.  dWWd.  .WWXl   0WWd.     ;WWK7     7WWX7      XWWd; ,WWN3    
KWWo    lWWWNo,   dWWd.  .WWXl   0WWd.     ;WWK7      oWWX3,.,7XWWk3  ,WWN3    
kXXo     dXXd:    oXXb.  .KX0l   xXXb.     'KXO7       .o0XNNNXKkl'   .KXKl    

```

##### 没有配置文件做准备的情况下启动引导

如果您没有配置文件，您会遇到两种情况，下面日志展示的是第一种情况，这说明您的 [为配置文件做准备](#为配置文件做准备)
章节出现了问题！您需要按照教程操作。

```
root@armbian:/opt/app/diskMirror-Tcp-Java# java -jar ./DiskMirrorTcpServer.jar 10 10001,10002
WARNING: sun.reflect.Reflection.getCallerClass is not supported. This will impact performance.
[INFO][DiskMirrorBackEnd][24-11-05:08]] : threadNumber = 10
[ERROR][DiskMirrorBackEnd][24-11-05:08]] : 我们无法为您生成配置文件，可能是您指定的路径有目录不存在，或我们无权限创建文件，但我们使用了默认配置文件来运行 diskMirror
java.io.FileNotFoundException: /opt/app/diskMirror-Tcp-Java/config/config.json (No such file or directory)
        at java.io.FileOutputStream.open0(Native Method) ~[?:?]
        at java.io.FileOutputStream.open(FileOutputStream.java:293) ~[?:?]
        at java.io.FileOutputStream.<init>(FileOutputStream.java:235) ~[?:?]
        at java.io.FileOutputStream.<init>(FileOutputStream.java:184) ~[?:?]
        at top.lingyuzhao.diskMirror.backEnd.conf.DiskMirrorConfig.<clinit>(DiskMirrorConfig.java:93) [DiskMirrorTcpServer.jar:?]
        at top.lingyuzhao.diskMirrorTcpServer.Main.main(Main.java:35) [DiskMirrorTcpServer.jar:?]
[INFO][DiskMirrorBackEnd][24-11-05:08]] : top.lingyuzhao.diskMirror.core.TcpAdapter@452e19ca → top.lingyuzhao.diskMirror.core.LocalFSAdapter@6b0d80ed:V1.3.2

```

第二种情况就是属于正常情况，其会自动的为您创建好默认的配置文件，然后按照默认配置文件启动！

```
root@armbian:/opt/app/diskMirror-Tcp-Java# java -jar ./DiskMirrorTcpServer.jar 10 10001,10002
WARNING: sun.reflect.Reflection.getCallerClass is not supported. This will impact performance.
[INFO][DiskMirrorBackEnd][24-11-05:08]] : threadNumber = 10
[INFO][DiskMirrorBackEnd][24-11-05:08]] : 您设置了配置文件目录为：/opt/app/diskMirror-Tcp-Java/conf/config.json，但是您的配置文件不存在，我们为您创建了默认配置文件！
```

## 更新记录

### 2024-12-05

- 使用了 `1.3.5` 版本的 diskMirror，这将更加稳定！同时有新功能！
- 使用了 `2024.12.05` 版本的 diskMirror 后端库！解决日志冲突