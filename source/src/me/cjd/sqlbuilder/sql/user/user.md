fromUser
===
from t_user where enable = 1 

findUser
===
select * sql('fromUser') sql('test.orderUser');