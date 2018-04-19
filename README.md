# xygerp-api
xygerp api微服务架构

基于Oracle EBS系统的一个API微服务架构系统，以扩展EBS的各种服务！

系统用途说明：https://juejin.im/post/5ad0bc2cf265da23766bb5e8

# 系统源码使用说明

#### 1 必须要安装的软件。

首先要说明的是，如果要测试本源码，一些必须的软件还是必须要安装的。

 - Java JDK。
 > 建议1.8版本的。配置环境变量什么的是必须的。
 - Redis数据库。
 > 记得安装好之后再开启Redis服务。
 - Maven项目管理工具。
 > 配置环境变量什么的是必须的。

#### 2 修改链接数据库的配置文件。

接着，这个微服务系统是基于EBS的，所以，也就是要链接EBS的Oracle数据库。

修改：

```
D:\JSP_MyEclipse\xygerp-api-demo\xygerp-ald\src\main\resources\application-dev.yml
D:\JSP_MyEclipse\xygerp-api-demo\xygerp-albc\src\main\resources\application-dev.yml
```

将下面的信息对应修改：

```
url: <!--你的数据库链接-->
username: <!--数据库用户，测试环境可以直接用apps-->
password: <!--数据库密码-->
```

> 备注：如果您不是用EBS系统？还可以用这个微服务么？
> 答案是：那暂时不可以用该微服务架构，但是你可以用别的微服务架构系统。说白了这个只是基于Spring Cloud的一个基于EBS的微服务系统！
> 因为，如果不是EBS系统，那Spring Security的认证方式需要调整，另外，还有一大堆相关的表格实际上是没有的，功能无法使用，那用这个系统也没意义了。
> * 换句话说，该微服务系统是定制用于EBS系统的。也是这个系统相对于别的微服务系统的一个特点！！ *

#### 3 将待引入的jar包(xygerp-comm-1.0-SNAPSHOT.jar)安装到本地repository

（这是由于comm部分涉及一些公司的信息，所以comm并没有开源，请谅解。）

命令行CD到lib目录，再执行命令。

```
cd D:\JSP_MyEclipse\xygerp-api-demo\xygerp-comm
D:\JSP_MyEclipse\xygerp-api-demo\xygerp-comm>mvn install:install-file -Dfile=xygerp-comm-1.0-SNAPSHOT.jar -DgroupId=com.xygerp -DartifactId=xygerp-comm -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```

出现下面的提示说明编译成功：

```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-install-plugin:2.4:install-file (default-cli) @ standalone-pom
---
[INFO] Installing D:\JSP_MyEclipse\xygerp-api-demo\xygerp-comm\xygerp-comm-1.0-S
NAPSHOT.jar to D:\Program Files\apache-maven-repository\com\xygerp\xygerp-comm\1
.0-SNAPSHOT\xygerp-comm-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.835 s
[INFO] Finished at: 2018-04-14T16:05:57+08:00
[INFO] Final Memory: 5M/123M
[INFO] ------------------------------------------------------------------------
```

#### 4 再到主目录，执行mvn打包命令：

```
cd D:\JSP_MyEclipse\xygerp-api-demo
D:\JSP_MyEclipse\xygerp-api-demo>mvn clean install -Dmaven.test.skip=true -P dev
```

出现下面的提示说明编译成功：

```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO]
[INFO] xygerp-basic-support ............................... SUCCESS [  4.729 s]
[INFO] xygerp-server-eureka ............................... SUCCESS [  7.178 s]
[INFO] xygerp-server-zuul ................................. SUCCESS [  3.850 s]
[INFO] Java for xygerp api system ......................... SUCCESS [  0.342 s]
[INFO] xygerp-ald ......................................... SUCCESS [ 15.026 s]
[INFO] xygerp-albc ........................................ SUCCESS [  9.508 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 41.949 s
[INFO] Finished at: 2018-04-14T14:21:42+08:00
[INFO] Final Memory: 75M/219M
[INFO] ------------------------------------------------------------------------
```

#### 5 再分别到对应的4个项目下，执行命令开启服务：

```
D:\JSP_MyEclipse\xygerp-api\xygerp-server-eureka\target>java -jar xygerp-server-eureka-1.0-SNAPSHOT.war
D:\JSP_MyEclipse\xygerp-api\xygerp-server-zuul\target>java -jar xygerp-server-zuul-1.0-SNAPSHOT.war
D:\JSP_MyEclipse\xygerp-api\xygerp-ald\target>java -jar xygerp-ald-1.0-SNAPSHOT.war
D:\JSP_MyEclipse\xygerp-api\xygerp-albc\target>java -jar xygerp-albc-1.0-SNAPSHOT.war
```

#### 6 测试对应的服务是否可以正确运行：

```
eureka服务：http://127.0.0.1:8101
xygerp-ald服务接口测试： http://127.0.0.1:8102/xygerp/ald/swagger-ui.html  
xygerp-albc服务接口测试： http://127.0.0.1:8102/xygerp/albc/swagger-ui.html  
```

# 微服务系统架构图

该系统的架构图如下所示。
![这里写图片描述](https://user-gold-cdn.xitu.io/2018/4/13/162bf5f3308a4c3c?w=844&h=995&f=png&s=127037)
