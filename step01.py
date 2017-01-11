# -*- coding: utf-8 -*-
'''
# 1 输出该段时间的关系
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
paths="/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/data/"
filenames=getfilenames(paths)
##获取邮箱分类
def getschoolmails(x):
    s_mails=set()
    n_s_mails=set()
    x=str(x)
    if x.find("nuc.edu.cn") !=-1:
      s_mails.add(x)
    else :
      n_s_mails.add(x)
    return   s_mails,n_s_mails
# 1 输出该段时间的关系
# 输入(文件夹下邮箱命名的记录txt)，输出(目标邮箱---->内部交流邮箱---->外部交流邮箱)
for i in range(len(filenames)):
    path=paths+filenames[i]
    if open(path).read().find('---->') == -1 :
       # print "该文件记录为空"
       pass
    else :
       file = pd.read_csv(path,sep='---->',header=None)
       file.columns = ['title','send','receive','time','state']
       school_mails = set()
       no_school_mails = set()
       for x in file["send"] :
          s_mails,n_s_mails=getschoolmails(x)
          school_mails=school_mails | s_mails
          no_school_mails=no_school_mails | n_s_mails
       for x in file["receive"] :
          s_mails,n_s_mails=getschoolmails(x)
          school_mails=school_mails | s_mails
          no_school_mails=no_school_mails | n_s_mails
       ##删除自己本身 dir()查看方法
       mail=filenames[i][0:len(filenames[i])-4]
       if mail in school_mails :
          school_mails.remove(mail)
       c1=mail
       c2=""
       if len(school_mails) >0 :
         for x in school_mails :
            c2=c2+"-->"+x
       else :
            c2=c2+"-->无"
       c3=""
       if len(no_school_mails) >0 :
         for x in no_school_mails :
            c3=c3+"-->"+x
       else :
            c3=c3+"-->无"
       index=range(1)
       out=pd.DataFrame({'mail':c1,'school_mails':c2[3:len(c2)],'no_school_mails':c3[3:len(c3)]},index=index)
       # out=c1+ "---->" +c2[3:len(c2)]+ "---->" +c3[3:len(c3)]
       out.to_csv("/Users/yuyin/Downloads/笔记学习/邮箱数据/备份02/alldata.txt",header=False,mode='a',index=False) #a追加
