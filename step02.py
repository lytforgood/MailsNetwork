# -*- coding: utf-8 -*-
'''
# 2 输出绘图参数
# 输入(目标邮箱---->内部交流邮箱---->外部交流邮箱)
# 输出(1：所有有内部记录的内部邮箱 2：内部邮箱的连边)
'''
import pandas as pd
from pandasql import sqldf

mails = pd.read_csv('/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/alldata.txt',header=None)
mails.columns = ['mail','no_school_mails','school_mails']
school_mails=mails[(mails.school_mails !="无")].reset_index()


source=[]
target=[]
for i in xrange(school_mails.shape[0]):
    one_mail=school_mails['mail'][i]
    school_mail=school_mails['school_mails'][i]
    if school_mail.find("-->") !=-1 :
      targets=school_mail.split("-->")
      for t in targets :
           source.append(one_mail)
           target.append(t)
    else :
      source.append(one_mail)
      target.append(school_mail)

alldata1=pd.concat([pd.DataFrame({'source':source}),pd.DataFrame({'target':target})],axis=1)
# alldata2=pd.concat([pd.DataFrame({'source':target}),pd.DataFrame({'target':source})],axis=1)
# re=pd.concat([alldata1,alldata2],axis=0).reset_index()
# re=re.drop_duplicates(['source'])['target']
alldata1.to_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/edges.txt",header=False,index=False)
#合并数组
source.extend(target)
t=pd.DataFrame({'mail':source})
#去重
t.drop_duplicates(inplace=True)
#node,id,label
t.to_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/notes.txt",header=False,index=False)
