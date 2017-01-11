#变化情况绘图
options(stringsAsFactors=F,scipen=99)
rm(list=ls());gc()
library(sqldf)
require(data.table)
library(recharts)
library('htmlwidgets') ##保持echarts文件为html
da<- fread("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/非空all/result_all.csv",header = TRUE)
#总的用户变化情况--20160730-20161028活跃用户变化情况 %>% setYAxis(min=0)
d_huo=sqldf("select start,end,count(mail) as n1 from da where mails_num>0 group by start,end ")
pic=echartr(d_huo,as.character(d_huo$start), n1,type = 'line') %>% setSymbols('emptycircle') %>% addMarkLine(data=data.frame(type='average', name1='Avg')) %>% addMarkPoint(series=1, data=data.frame(type='max', name='Max')) %>% setTitle('20160730-20161028活跃用户变化情况',paste0(''),textStyle=list(color='red')) %>% setYAxis(min=800)
saveWidget(pic,"/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/图结果/活跃用户变化情况.html")
#总的邮件变化情况
d_huo=sqldf("select start,end,sum(mails_num) as n1 from da where mails_num>0 group by start,end ")
pic=echartr(d_huo,as.character(d_huo$start), n1,type = 'line') %>% setSymbols('emptycircle') %>% addMarkLine(data=data.frame(type='average', name1='Avg')) %>% addMarkPoint(series=1, data=data.frame(type='max', name='Max')) %>% setTitle('20160730-20161028活跃用户变化情况',paste0(''),textStyle=list(color='red')) %>% setYAxis(min=0)
saveWidget(pic,"/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/图结果/总邮件数变化情况.html")
##最大节点情况 每个时间段 最大节点变化 分布
timeall=unique(da$start)
for (i in 1:length(timeall)) {
    da1=da[which(da$start==timeall[i])]
    # pic=echartr(da1, mails_count,type = 'bar')
    # saveWidget(pic,'/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/图结果/t1.html')
    # x=c(1:length(da1$mails_count)
    y=sqldf("select mail,mails_count from da1 order by mails_count desc")
    # pic=echartr(y, mail,mails_count,type = 'bar')
    name=paste0(timeall[i],"-邮件数分布",seq="")
    pic=echartr(y, mail,mails_count,type = 'line')%>% addMarkPoint(series=1, data=data.frame(type='max', name='Max')) %>% setTitle(name)
    file=paste0("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/图结果/每个时间段分布结果/",name,".html",seq="")
    saveWidget(pic,file)
    # echartr(da1, c(1:length(da1$mails_count)), mails_count,type = 'line')
}

##新增用户情况
##减少用户情况
#校内节点变化  校外 result_outer
da<- fread("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/非空all/result_school.csv",header = TRUE)
##内部-外部最大节点
da<- fread("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/非空all/result_school.csv",header = TRUE)
da<- fread("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/非空all/result_outer.csv",header = TRUE)

tmp=sqldf("select mail,sum(mails_count) as n1 from da group by mail order by n1 desc")
name=paste0("校内节点","-分布",seq="")
pic=echartr(tmp, mail,n1,type = 'line')%>% addMarkPoint(series=1, data=data.frame(type='max', name='Max')) %>% setTitle(name)
file=paste0("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份01/20160730_20161028备份/2016-7-30-2016-10-28/图结果/",name,".html",seq="")
saveWidget(pic,file)
