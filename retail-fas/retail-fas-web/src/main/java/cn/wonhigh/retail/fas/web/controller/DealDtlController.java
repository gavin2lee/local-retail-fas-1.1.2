package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.FasBaseModel;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("/deal_dtl")
@ModuleVerify("30170029")
public class DealDtlController extends BaseController<FasBaseModel> {

	@Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/deal_dtl/",null);
    }

}
