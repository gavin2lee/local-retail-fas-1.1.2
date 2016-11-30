package cn.wonhigh.retail.fas.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wonhigh.retail.gms.common.constans.PublicConstans;


/**
 * 
 * 公共跳转控制
 * 
 * @author dong.j
 * @date 2013-10-30 上午10:54:45
 * @version 0.1.0
 * @copyright yougou.com
 */
@Controller
public class CommonRedirectController {

	/**
	 * 公共查询跳转
	 * 
	 * @param path
	 *            跳转页面的名称
	 * @return
	 */
	@RequestMapping("/search_dialog/{path}")
	public String toPage(@PathVariable String path) {
		return "search_dialog/" + path;
	}

	@RequestMapping("/print/preview")
	public String index(Model model, HttpServletRequest req, HttpServletResponse response) throws IOException {
		String viewName = (String) req.getParameter("viewName");
		return "/print/" + viewName;
	}

	@RequestMapping("/to/import")
	public String index() throws IOException {
		return "/print/import";
	}
	
	/**
	 * 文件下载
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/download",produces = "text/plain;charset=UTF-8")
	public ResponseEntity<byte[]> down(@RequestParam(required = true, value = "fileName") String fileName,
			HttpServletRequest request) throws Exception {
		String ctxPath = request.getSession().getServletContext().getRealPath(PublicConstans.DOWNLOAD_DIR);
		String path = ctxPath + File.separator + fileName;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		File file = new File(path);
		byte[] data = null;
		if (file.exists()) {
			headers.setContentLength(file.length());
			headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
			data = FileUtils.readFileToByteArray(file);
		} else {
			headers.setContentDispositionFormData("attachment", "error.txt");
			data = "模板下载错误,请稍后再试!".getBytes();
		}
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}
}

