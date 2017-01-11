# -*- coding: utf-8 -*-
'''
# 3 输出该段时间的关系
# 输入(文件夹下邮箱命名的记录txt)，输出(目标邮箱---->内部交流邮箱---->外部交流邮箱)
'''
import pandas as pd
from pandasql import sqldf
import datetime
import os
import time

##获取文件夹下所有文件的名字
def getfilenames(path):
    filenames=[]
    for file in os.listdir(path):
        filenames.append(file)
    return filenames
filenames=getfilenames("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/data/")
for i in range(len(filenames)):
    if open("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/data/"+filenames[i]).read().find('---->') != -1 :
        file = pd.read_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/data/"+filenames[i],sep='---->',header=None)
        t1=[]
        for j in range(file.shape[0]):
          t1.append(filenames[i])
        t1=pd.DataFrame({'t1':t1})
        file=pd.concat([t1,file],axis=1)
        file.to_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/alldata.csv",header=False,mode='a',index=False,encoding="utf-8") #a追加
del filenames
# del file
del t1
##读取所有文件
alldata = pd.read_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/alldata.csv",header=None)
# alldata =file
alldata.columns = ['mail','title','send','receive','time','state']
#处理日期  print 自动转码显示中文
tmp=[]
tmp1=[]
for i in range(alldata.shape[0]):
    t1=alldata.iloc[i,4][0:len(alldata.iloc[i,4])-11] #几月几日
    t2=alldata.iloc[i,4][(len(alldata.iloc[i,4])-11):len(alldata.iloc[i,4])] #时刻
    timeArray = time.strptime(t1, "%m月%d日")
    tmp.append("2016"+time.strftime("%m%d", timeArray))
    if '晚上' in t2:
      tmp1.append(str(12+int(t2[6:8]))+t2[8:11])
    elif '下午' in t2:
      tmp1.append(str(12+int(t2[6:8]))+t2[8:11])
    else :
      tmp1.append(t2[6:11])
t1=pd.DataFrame({'t1':tmp})
alldata=pd.concat([alldata,t1],axis=1)
t1=pd.DataFrame({'t2':tmp1})
alldata=pd.concat([alldata,t1],axis=1)
alldata.to_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/alldata2.csv",header=False,mode='a',index=False,encoding="utf-8")
