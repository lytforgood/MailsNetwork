#全部版本
#预测边
#!/usr/bin/Rscript --slave
options(stringsAsFactors=F,scipen=99)
rm(list=ls());gc()
library(sqldf)
require(data.table)
d<- fread("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/edges.txt",header = FALSE)
#zbdx@nuc.edu.cn认为是不合适点--去除
d1=sqldf("select * from d where V1 not like '%zbdx@nuc.edu.cn%'")
re=data.frame(tolower(d1$V1),tolower(d1$V2)) #转小写
names(re)=c("V1","V2")
re=sqldf("select * from re where V1!=V2") #去掉给自己写信的
e=unique(re$V1)
for(i in 1:length(e)){
    tmp=e[i]
    re1=re[which(re[,1]==tmp),]
    friend_all= re[(re[, 1] %in% re1[,2]),]
    tmp3 = friend_all[!(friend_all[, 2] %in% friend_all[friend_all[, 1] == tmp, 2]), ]
    #把连接的好友出现的次数统计出来
    listall = sort(table(tmp3[, 2]), dec = T)
    #找出前20个--修改阈值
    top = names(listall[1:5])
    # top = names(listall[1:20])
    #找出出现多的是不是也是20150049@nuc.edu.c的联系关系
    tmp4 = tmp3[tmp3[, 2] %in% top, 2]
    top20 = sort(table(tmp4), dec = T)
    top20=as.data.frame(top20)
    #好友--共同好友个数
    top20=top20[!(top20[,1] %in% re1[,2]),]
    if(length(top20)>1){
      top20=top20[which(top20[,1]!=tmp),]
      if(length(top20[,1])>0){
      out=data.frame(tmp,top20[,1])
      write.table (out, file ="/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/预测出的边关系.csv",sep =",",row.names = F,col.names=F,quote =F,append=TRUE)
      }
    }

}
