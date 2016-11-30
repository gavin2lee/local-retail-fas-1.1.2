/**  
*   
* 项目名称：retail-fas-web  
* 类名称：InvoiceConfirmController  
* 类描述：地区到票确认
* 创建人：ouyang.zm  
* 创建时间：2014-12-3 下午2:48:02  
* @version       
*/
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("invoice_confirm/area/")
@ModuleVerify("30180001")
public class AreaInvoiceConfirmController extends InvoiceConfirmController {

}
