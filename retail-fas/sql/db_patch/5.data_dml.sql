
#   FAS 初始化数据字典
#   version:FAS-0.1.1
#   MODIFIED				(MM/DD/YY)
#   yang.h    			10/30/14
#   
#   
#   ********************* FAS 初始化基础数据*******************
#   ###############################


set autocommit=0;

INSERT INTO `param_main` (`id`, `param_code`, `param_name`, `lookup_level`, `control_level`, `remark`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1600002451', 'MS_Check_duringNetQty', '财务结账日之前是否有新业务发生', NULL, '2', '', 'fas_dev', '2016-11-18 13:51:10', NULL, '2016-11-18 13:51:10');

INSERT INTO `param_dtl` (`id`, `param_code`, `dtl_name`, `dtl_value`, `isvalid`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1600002453', 'MS_Check_duringNetQty', '财务结账日之前有新业务产生', '1', '1', 'fas_dev', '2016-11-18 13:51:11', NULL, '2016-11-18 13:51:10');

commit;