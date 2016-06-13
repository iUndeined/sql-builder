在为Java Code里超长的Sql而烦恼吗？SqlBuilder帮助您！！！
====

## 工具优势

整个工具大小仅22KB  
读取外部sql.md文件并缓存  
开放SqlRenderEngine接口让您可以自己渲染原生语句  
内部已集成只要引入jar包即可直接使用的模板引擎（Beetl、Freemarker）  

## 1分钟快速配置

第一步、将sqlbuilder-builder.properties拷入您项目的src文件夹下  
第二步、sqlFolders 填入sql文件存放目录，多目录用逗号（,）分隔  
第三步、sqlMode 选择运行模式，run模式为产品模式读取缓存速度快，没有实时性；debug为开发模式，实时监测sql文件变化改动sql无需重启  
第四步、引入 sql-builder-1.1.jar 包  
第五步、Java 代码内调用    

```

// 如果您需要使用模板引擎来渲染那么  
// 比如我使用beetl引擎  
SqlBuilder.setEngine(new SqlBeetlEngine());  
  
// 获取sql语句，test为文件名称，findSqlOfBeetl为语句唯一id，重名则获取第一个  
String sql0 = SqlBuilder.render("test.findSqlOfBeetl");  
String sql1 = SqlBuilder.render("test.findSqlOfBeetl", new SqlBuilderPara("name", "颖"), ...);  

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


```

## Tester.java 测试类

```

// 设置 全局模板引擎
SqlBuilder.setEngine(new SqlBeetlEngine());

// Beetl
String sql1 = SqlBuilder.render("test.findSqlOfBeetl", new SqlBuilderPara("name", "颖"));

System.out.println("Beetl渲染：" + sql1);

// Freemarker
// 设置 单独模板引擎
String sql2 = SqlBuilder.render(new SqlFreemarkerEngine(), "test.findSqlOfFreemarker", new SqlBuilderPara("order", "DESC"));

System.out.println("Freemarker渲染：" + sql2);

```

## 输出结果

```

Beetl渲染：select * from T_TEST AS i where i.name = '颖' 

Freemarker渲染：select * from T_TEST AS i ORDER BY i.date DESC 

```
