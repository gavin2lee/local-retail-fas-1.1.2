/**
 * title:HqInvoiceConfirmController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部到票确认
 * auther:user
 * date:2015-4-10 上午9:38:03
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("invoice_confirm/hq/")
@ModuleVerify("30180002")
public class HqInvoiceConfirmController extends InvoiceConfirmController {
}
