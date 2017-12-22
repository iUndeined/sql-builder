不再为Java代码中难以维护的Sql语句而烦恼，SqlBuilder帮助您！
====

## 工具优势

整个工具大小仅36.6KB  
读取外部sql.md文件并缓存  
开放SqlRenderEngine接口让您可以自己渲染原生语句  
内部已集成只要引入jar包即可直接使用的模板引擎（Beetl、Freemarker）  

## 1分钟快速配置

1. 将 sqlbuilder-config-default.properties 拷入您的项目src文件夹下  
2. 并将其更名为 sqlbuilder-config.properties  
3. sqlFolders 填入sql文件存放目录，多目录用逗号（,）分隔  
4. sqlMode 选择运行模式，run模式为产品模式读取缓存速度快，没有实时性；debug为开发模式，实时监测sql文件变化改动sql无需重启  
5. 引入 sql-builder-v1.3.5.jar 包  
6. Java 代码内调用    

```java

/**
 * 如果您需要使用模板引擎来渲染那么
 * 在这里设置一个全局引擎
 * 比如我使用beetl引擎  
 */
SqlBuilder.setEngine(new SqlBeetlEngine());  
  
// 获取sql语句，test为文件名称，findSqlOfBeetl为语句唯一id，重名则获取第一个  
String sql0 = SqlBuilder.render("test.findSqlOfBeetl");  

// 该传参构造在 v1.3.5版本已不推荐使用
String sql1 = SqlBuilder.render("test.findSqlOfBeetl", new SqlBuilderPara("name", "颖"), ...); 

// 推荐使用
String sql2 = SqlBuilder.sql("test.findSqlOfBeetl")
// 指定模板渲染引擎，仅作用于该条语句，后设置会覆盖前设置
// 使用 freemarker 引擎
.engine(SqlFreemarkerEngine.class)
// 使用 beetl 引擎
.engine(SqlBeetlEngine.class)
// 设置参数
.para("name", "颖")
.para(..., ...)
// 渲染语句
.render();

```

## 测试用例
## test.md 文件

```

findSqlOfFreemarker
===
-- 外部sql一样可以写注释
select * from T_TEST AS i <#if order ??>ORDER BY i.date ${order} </#if>

findSqlOfBeetl
===
-- 注释2
select * from T_TEST AS i <%if (!isEmpty(name)) {%>where i.name = '${name}' <%}%>

fromUserId
===
from user where id = ? 

findUserName
===
-- 语句引入函数
-- 引入本文件直接写语句名称
-- 引入它文件如普通调用 test.yourSqlName 即可
select username sql('fromUserId') 

```

## Tester.java 测试类

```java

// 设置 全局模板引擎
SqlBuilder.setEngine(new SqlBeetlEngine());

// Beetl
String sql1 = SqlBuilder.render("test.findSqlOfBeetl", new SqlBuilderPara("name", "颖"));

System.out.println("Beetl渲染：");
System.out.println(sql1);

// Freemarker
// 设置 单独模板引擎
String sql2 = SqlBuilder.render(new SqlFreemarkerEngine(), "test.findSqlOfFreemarker", new SqlBuilderPara("order", "DESC"));

System.out.println("Freemarker渲染：");
System.out.println(sql2);

// 语句引入函数用例
String sql3 = SqlBuilder.render("test.findUserName");

System.out.println("sql('...') 函数渲染：");
System.out.println(sql3);

```

## 输出结果

```

Beetl渲染：  
select * from T_TEST AS i where i.name = '颖'  

Freemarker渲染：  
select * from T_TEST AS i ORDER BY i.date DESC  

sql('...') 函数渲染：  
select * from user where id = ?  

```

## 更新日志
v1.3.5  
[新增] 增加新的构造方式 SqlBuilder.sql('fileName.sqlId').para(key, value).para(key, value).render();  

v1.3.4  
[修复] SqlBuilderPara 传 null 值会发生错误的问题  

v1.3.3  
[修复] SqlBuilderModel 无法toJSONString的问题  

v1.3.2  
[新增] SqlBuilderModel 类支持jfinal的快速使用  
[新增] 配置文件 sqlDebug=true 调试模式，输出渲染前后语句以及参数  

v1.3.1  
[优化] 达梦数据库SQL语句渲染代码  

V1.3  
[新增] sql('...') 语句引入函数支持  
[新增] 中间件接口，提升扩展性  

V1.2  
[新增] log4j 日志  
[新增] 国产达梦数据库sql语句渲染支持  
[优化] 使用Sonarqube对代码质量进行优化  
[修复] 修复了一些小bug  
[测试] 根据在正式商业项目中上线使用进行的优化调整  
  
v1.1  
[优化] 移除了对 apache-commons-lange3工具包的依赖  
  
v1.0  
[出生] 项目首次登录 git.  

------

未完待续
感谢您花费时间阅读这份说明稿。

今天是我生日，Happy birthday to me。

作者 cjd   
2017 年 12月22日    
