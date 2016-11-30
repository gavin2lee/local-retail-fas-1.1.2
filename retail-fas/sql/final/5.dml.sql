#   FAS 初始化数据
#   version:GMS-0.1.1
#   MODIFIED				(MM/DD/YY)
#   yang.h						08/28/28
#   
#   
#   *********************FAS系统初始化*******************
#  1.FAS基础数据    Insert		lookup,lookup_entry,lookup_rel等
#--------------------------------------------------------------


tee 5.dml.log

select
'FAS 初始化数据'		as name,
'FAS-0.1.1'			as version \G

source 5.dml/5.1.fas_data.sql

notee