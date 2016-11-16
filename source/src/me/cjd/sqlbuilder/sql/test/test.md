findTestForStatus
===

select * from T_TEST AS i where i.status = ? 

findSqlOfFreemarker
===

select * from T_TEST AS i <#if order ??>ORDER BY i.date ${order} </#if>

findSqlOfBeetl
===

select * from T_TEST AS i <%if (!isEmpty(name)) {%>where i.name = '${name}' <%}%>

findSqlOfVelocity
===

select * from T_TEST AS i #if(order != null) ORDER BY i.id ${order} #end

orderUser
===
order by ${orderName!} ${orderRule!}