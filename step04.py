# -*- coding: utf-8 -*-
'''
数据预处理
---------联系过的还联系吗？没联系的有可能联系吗?根据邮件往来预测QQ好友关系
两周汇报-写总结 录音标重点
社交网络原始数据进行统计
20160730-20161028
1、活跃人数(收件数/收件人、发件数/发件人、总数/总人数--校内/校外连接度)
2、统计所有每个时间段，谁是最大节点，外/内部最多，所有人节点数分布状况
3、每个时间段-新增/减少的联系人个数
'''
import pandas as pd
from pandasql import sqldf
import datetime
alldata = pd.read_csv("./非空all/alldata2.csv",header=None)
alldata.columns=['c0','c1','c2','c3','c4','c5','c6','c7']
peoples=alldata.drop_duplicates(['c0'])['c0']
#peoples.index=[j for j in range(peoples.shape[0])]
peoples.index=range(peoples.shape[0])

###全部节点计算
mail=[]
start=[]
end=[]
sender=[]
sender_num=[]
recipient=[]
recipient_num=[]
mails_count=[]
mails_num=[]
#xrange做循环的性能比range   range(1,100)生成1-100的list  xrange(1,100)迭代器
for x in xrange(peoples.shape[0]):
    people=peoples[x]
    people_data=alldata[alldata.iloc[:,0]==people]
    ##做时间窗口
    start_time= datetime.datetime.strptime('20161028','%Y%m%d')
    for i in xrange(1,14):
        d1=start_time+ datetime.timedelta(days=7*(i-1))
        d2=start_time+ datetime.timedelta(days=7*i)
        d1=d1.strftime('%Y%m%d')
        d2=d2.strftime('%Y%m%d')
        tmp=people_data[(people_data.iloc[:,6].astype(int)>=int(d1))&(people_data.iloc[:,6].astype(int)<=int(d2))]
        mail.append(people)
        start.append(d1)
        end.append(d2)
        sender.append(tmp.drop_duplicates(['c2'])['c2'].shape[0])
        sender_num.append(tmp['c2'].shape[0])
        recipient.append(tmp.drop_duplicates(['c3'])['c3'].shape[0])
        recipient_num.append(tmp['c3'].shape[0])
        mails_count.append(tmp.drop_duplicates(['c2'])['c2'].shape[0]+tmp.drop_duplicates(['c3'])['c3'].shape[0])
        mails_num.append(tmp['c2'].shape[0]+tmp['c3'].shape[0])
mail=pd.DataFrame({'mail':mail})
start=pd.DataFrame({'start':start})
end=pd.DataFrame({'end':end})
sender=pd.DataFrame({'sender':sender})
sender_num=pd.DataFrame({'sender_num':sender_num})
recipient=pd.DataFrame({'recipient':recipient})
recipient_num=pd.DataFrame({'recipient_num':recipient_num})
mails_count=pd.DataFrame({'mails_count':mails_count})
mails_num=pd.DataFrame({'mails_num':mails_num})
result=pd.concat([mail,start,end,sender,sender_num,recipient,recipient_num,mails_count,mails_num],axis=1)
result.to_csv("./非空all/result_all.csv",mode='a',index=False)







################################################################################################
import pandas as pd
import datetime
alldata = pd.read_csv("./非空all/alldata2.csv",header=None)
alldata.columns=['c0','c1','c2','c3','c4','c5','c6','c7']
alldata=alldata[(alldata['c2'].str.contains('@nuc',na=False))&(alldata['c3'].str.contains('@nuc',na=False))]
peoples=alldata.drop_duplicates(['c0'])['c0']
#peoples.index=[j for j in range(peoples.shape[0])]
peoples.index=range(peoples.shape[0])
##校内数据计算
mail=[]
start=[]
end=[]
sender=[]
sender_num=[]
recipient=[]
recipient_num=[]
mails_count=[]
mails_num=[]
#xrange做循环的性能比range   range(1,100)生成1-100的list  xrange(1,100)迭代器
for x in xrange(peoples.shape[0]):
    people=peoples[x]
    people_data=alldata[alldata.iloc[:,0]==people]
    ##做时间窗口
    start_time= datetime.datetime.strptime('20161028','%Y%m%d')
    for i in xrange(1,14):
        d1=start_time+ datetime.timedelta(days=7*(i-1))
        d2=start_time+ datetime.timedelta(days=7*i)
        d1=d1.strftime('%Y%m%d')
        d2=d2.strftime('%Y%m%d')
        tmp=people_data[(people_data.iloc[:,6].astype(int)>=int(d1))&(people_data.iloc[:,6].astype(int)<=int(d2))]
        mail.append(people)
        start.append(d1)
        end.append(d2)
        sender.append(tmp.drop_duplicates(['c2'])['c2'].shape[0])
        sender_num.append(tmp['c2'].shape[0])
        recipient.append(tmp.drop_duplicates(['c3'])['c3'].shape[0])
        recipient_num.append(tmp['c3'].shape[0])
        mails_count.append(tmp.drop_duplicates(['c2'])['c2'].shape[0]+tmp.drop_duplicates(['c3'])['c3'].shape[0])
        mails_num.append(tmp['c2'].shape[0]+tmp['c3'].shape[0])
mail=pd.DataFrame({'mail':mail})
start=pd.DataFrame({'start':start})
end=pd.DataFrame({'end':end})
sender=pd.DataFrame({'sender':sender})
sender_num=pd.DataFrame({'sender_num':sender_num})
recipient=pd.DataFrame({'recipient':recipient})
recipient_num=pd.DataFrame({'recipient_num':recipient_num})
mails_count=pd.DataFrame({'mails_count':mails_count})
mails_num=pd.DataFrame({'mails_num':mails_num})
result=pd.concat([mail,start,end,sender,sender_num,recipient,recipient_num,mails_count,mails_num],axis=1)
result.to_csv("./非空all/result_school.csv",mode='a',index=False)


################################################################################################
import pandas as pd
import datetime
alldata = pd.read_csv("./非空all/alldata2.csv",header=None)
alldata.columns=['c0','c1','c2','c3','c4','c5','c6','c7']
alldata=alldata[((alldata['c2'].str.contains('@nuc',na=False))&(-alldata['c3'].str.contains('@nuc',na=False)))|((-alldata['c2'].str.contains('@nuc',na=False))&(alldata['c3'].str.contains('@nuc',na=False)))]
peoples=alldata.drop_duplicates(['c0'])['c0']
#peoples.index=[j for j in range(peoples.shape[0])]
peoples.index=range(peoples.shape[0])
##校外数据计算
mail=[]
start=[]
end=[]
sender=[]
sender_num=[]
recipient=[]
recipient_num=[]
mails_count=[]
mails_num=[]
#xrange做循环的性能比range   range(1,100)生成1-100的list  xrange(1,100)迭代器
for x in xrange(peoples.shape[0]):
    people=peoples[x]
    people_data=alldata[alldata.iloc[:,0]==people]
    ##做时间窗口
    start_time= datetime.datetime.strptime('20161028','%Y%m%d')
    for i in xrange(1,14):
        d1=start_time+ datetime.timedelta(days=7*(i-1))
        d2=start_time+ datetime.timedelta(days=7*i)
        d1=d1.strftime('%Y%m%d')
        d2=d2.strftime('%Y%m%d')
        tmp=people_data[(people_data.iloc[:,6].astype(int)>=int(d1))&(people_data.iloc[:,6].astype(int)<=int(d2))]
        mail.append(people)
        start.append(d1)
        end.append(d2)
        sender.append(tmp.drop_duplicates(['c2'])['c2'].shape[0])
        sender_num.append(tmp['c2'].shape[0])
        recipient.append(tmp.drop_duplicates(['c3'])['c3'].shape[0])
        recipient_num.append(tmp['c3'].shape[0])
        mails_count.append(tmp.drop_duplicates(['c2'])['c2'].shape[0]+tmp.drop_duplicates(['c3'])['c3'].shape[0])
        mails_num.append(tmp['c2'].shape[0]+tmp['c3'].shape[0])
mail=pd.DataFrame({'mail':mail})
start=pd.DataFrame({'start':start})
end=pd.DataFrame({'end':end})
sender=pd.DataFrame({'sender':sender})
sender_num=pd.DataFrame({'sender_num':sender_num})
recipient=pd.DataFrame({'recipient':recipient})
recipient_num=pd.DataFrame({'recipient_num':recipient_num})
mails_count=pd.DataFrame({'mails_count':mails_count})
mails_num=pd.DataFrame({'mails_num':mails_num})
result=pd.concat([mail,start,end,sender,sender_num,recipient,recipient_num,mails_count,mails_num],axis=1)
result.to_csv("./非空all/result_outer.csv",mode='a',index=False)



##简单绘图
import matplotlib.pyplot as plt
import numpy as np
# 20160730-20161028
# 1、活跃人数(收件数/收件人、发件数/发件人、总数/总人数--校内/校外连接度)
# 2、统计所有每个时间段，谁是最大节点，外/内部最多，所有人节点数分布状况
# 3、每个时间段-新增/减少的联系人个数
tmp=result[result['mail'].str.contains('13934603474@nuc',na=False)]
x=range(tmp.shape[0])
# plt.plot(x,tmp.iloc[:,3])
plt.figure(figsize=(8,4)) #设置画布大小
plt.plot(x,tmp['mails_count'])  #设置x y 无参数默认折线图
plt.xlabel("每周情况")
plt.ylabel("变化个数")
plt.xlim(0, 10)
plt.ylim(0, 15)
plt.title('单节点动态演变')  #设置图表的标题
plt.legend() #显示图示 右上角的小图
plt.show()  #显示
