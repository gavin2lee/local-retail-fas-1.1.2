package cn.wonhigh.retail.fas.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.cache.RedisStorage;
import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.utils.Helper;
import cn.wonhigh.retail.fas.common.constans.ErrorCodeConstant;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ReflectUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.ResultVo;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.mdm.api.util.CacheContext;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseController<ModelType extends FasBaseModel> extends BaseCrudController<ModelType> {

	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	protected abstract CrudInfo init();

	private CrudInfo getCrudInfo() {
		return init();
	}

	/**
	 * 处理异常的方法
	 * 
	 * @param ex
	 *            异常
	 * @param request
	 *            HttpServletRequest
	 * @return 异常返回结构实体类
	 */
	@ExceptionHandler(ManagerException.class)
	@ResponseBody
	public ResultVo handleServiceException(ManagerException ex, HttpServletRequest request) {
		String errorDetail = getStackTrace(ex);
		logger.info("业务系统内部异常>>>>>>>>>" + request.getRequestURI());
		logger.error(ex.getMessage());
		logger.error(errorDetail);
		ResultVo resultVo = new ResultVo();
		resultVo.setErrorCode(ErrorCodeConstant.E_0001);
		resultVo.setErrorMessage("业务系统内部异常"); // ex.getMessage()
		resultVo.setErrorDetail(errorDetail);
		return resultVo;
	}

	protected String getShardingFlag() {
		SystemUser currUser = Authorization.getUser();
		String organTypeNo = currUser.getOrganTypeNo();
		String currZone = Authorization.getCurrentZone();
		String shardingFlag = organTypeNo.concat("_").concat(currZone);
		return shardingFlag;
	}

	/**
	 * 新增
	 * 
	 * @param model
	 *            待新增的实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> insert(@ModelAttribute("model") ModelType model, HttpServletRequest request)
			throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			getCrudInfo().getManager().add(model);
			map.put("success", true);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("数据新增报错", e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 修改
	 * 
	 * @param model
	 *            待修改的实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> update(@ModelAttribute("model") ModelType model, HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			getCrudInfo().getManager().modifyById(model);
			map.put("success", true);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("数据更新报错", e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 给实体对象的字段设置默认的值
	 * 
	 * @param model
	 *            实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return 设置默认值之后的实体对象
	 * @throws Exception
	 */
	protected ModelType setDefaulValues(ModelType model, HttpServletRequest request) throws Exception {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		if (StringUtils.isEmpty(model.getId())) {
			model.setId(null);
			model.setCreateUser(loginUser.getUsername());
			model.setCreateTime(DateUtil.getCurrentDateTime());
			// model.setUpdateUser(loginUser.getLoginName());
			model.setUpdateTime(DateUtil.getCurrentDateTime());
			model.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
			if (model.getStatus() == null) {
				model.setStatus(FasLogoutStatusEnum.UNABLE_STATUS.getValue());
			}
		} else {
			model.setUpdateUser(loginUser.getUsername());
			model.setUpdateTime(DateUtil.getCurrentDateTime());
		}
		return model;
	}

	/**
	 * 导出
	 * 
	 * @param modelType
	 *            实体对象
	 * @param req
	 *            HttpServletRequest
	 * @param model
	 *            Model
	 * @param response
	 *            HttpServletResponse
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/do_fas_export")
	public void doFasExport(ModelType modelType, HttpServletRequest request, Model model, HttpServletResponse response)
			throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			try {
				// 字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});

				List<Map> subColumnsList = new ArrayList<Map>();
				if (StringUtils.isNotEmpty(subExportColumns)) {
					subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {
					});

					// 如果是混合表头，则将subColumnsList加入columnsList集合
					if (ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
						columnsList.addAll(subColumnsList);
						subColumnsList = new ArrayList<Map>(1);
					}
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);

				if (StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
					});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}

				List<ModelType> list = this.queryExportData(params);
				List<Map> listArrayList = new ArrayList<Map>();
				if (list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for (Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<ModelType> format = null;
					for (ModelType vo : list) {
						Map map = null;
						if (flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if (formatAnnotation != null) {
							format = (AbstractExportFormat<ModelType>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap();
							ReflectUtil.object2MapWithoutNull(vo, map);
						}
						if (subColumnsList != null && subColumnsList.size() > 0) {
							List<Map> subExportData = this.findComplexHeaderData(vo);
							map.put("subExportData", subExportData);
						} else {
							map.put("subExportData", new ArrayList<Map>(1));
						}
						listArrayList.add(map);
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(listArrayList);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}

	/**
	 * 导出
	 * 
	 * @param modelType
	 *            实体对象
	 * @param req
	 *            HttpServletRequest
	 * @param model
	 *            Model
	 * @param response
	 *            HttpServletResponse
	 * @throws ManagerException
	 *             异常
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "/do_fas_exports")
	public void doFasExports(ModelType modelType, HttpServletRequest request, Model model, HttpServletResponse response)
			throws ManagerException, JsonParseException, JsonMappingException, IOException {

		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");

		setCommonsParams(params);

		if (!StringUtils.isNotEmpty(exportColumns))
			return;
		ObjectMapper mapper = new ObjectMapper();
		ExportComplexVo exportVo = null;
		exportVo = new ExportComplexVo();
		exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
		Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
		exportVo.setRowAccessWindowSize(rowAccessWindowSize);
		List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
		});

		// 对手动隐藏的列进行处理，把手动隐藏的列过滤掉，不导出来
		List<Map> tempColumnsList = new ArrayList<Map>();
		for (Map map : columnsList) {
			Object hid = map.get("hidden");
			if (null != hid) {
				if (hid instanceof Boolean && !hid.equals(true)) {
					tempColumnsList.add(map);
				} else if (hid instanceof String && !hid.equals("true")) {
					tempColumnsList.add(map);
				}
			} else {
				tempColumnsList.add(map);
			}
		}

		tempColumnsList = this.sortExportColumns(tempColumnsList);
		exportVo.setColumnsMapList(tempColumnsList);
		if (StringUtils.isNotEmpty(firstHeaderColumns)) {
			List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
			});
			exportVo.setHeaderColumnsMapList(headerColumnsList);
		}

		final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);

		try {
			Function<Object, Boolean> handler = new Function<Object, Boolean>() {
				@Override
				public Boolean apply(Object input) {
					Map vals = (Map) input;
					queryResultMap(vals);
					exportExcel.write(vals);
					return true;
				}

			};
			SimplePage page = new SimplePage();
			page.setPageSize(Integer.MAX_VALUE);
			this.selectManagerMenthod(page, params, handler);
			exportExcel.flush(response);
		} catch (Exception e) {
			throw new ManagerException(e);
		} finally {
			try {
				exportExcel.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private ExportComplexVo buildExportVOAndParams(HttpServletRequest request, Model model, Map<String, Object> params)
			throws ManagerException {

		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");

		setCommonsParams(params);
		if (!params.containsKey("shardingFlag")) {
			params.put("shardingFlag", getShardingFlag());
		}
		if (!StringUtils.isNotEmpty(exportColumns))
			return null;
		ObjectMapper mapper = new ObjectMapper();
		ExportComplexVo exportVo = null;
		exportVo = new ExportComplexVo();
		exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
		Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
		exportVo.setRowAccessWindowSize(rowAccessWindowSize);
		try {
			List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {

			});

			if (StringUtils.isNotEmpty(firstHeaderColumns)) {
				List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
				});
				exportVo.setHeaderColumnsMapList(headerColumnsList);
			}

			// 对手动隐藏的列进行处理，把手动隐藏的列过滤掉，不导出来
			List<Map> tempColumnsList = new ArrayList<Map>();
			for (Map map : columnsList) {
				Object hid = map.get("hidden");
				if (null != hid) {
					if (hid instanceof Boolean && !hid.equals(true)) {
						tempColumnsList.add(map);
					} else if (hid instanceof String && !hid.equals("true")) {
						tempColumnsList.add(map);
					}
				} else {
					tempColumnsList.add(map);
				}
			}

			tempColumnsList = this.sortExportColumns(tempColumnsList);
			exportVo.setColumnsMapList(tempColumnsList);

		} catch (Exception e) {
			throw new ManagerException(e);
		}
		return exportVo;
	}

	@RequestMapping(value = "/async_exports")
	@ResponseBody
	public String exportAsync(ModelType modelType, HttpServletRequest request, Model model, HttpServletResponse response)
			throws ManagerException, UnsupportedEncodingException {
		Map<String, Object> params = builderParams(request, model);

		ExportComplexVo exportVo = buildExportVOAndParams(request, model, params);

		// String count = request.getParameter("count");
		if (exportVo == null)
			return "";

		String ticket = getTicket(params);
		doExport(exportVo, params, ticket);
		return ticket;
	}

	protected String getTicket(Map<String, Object> params) {
		return UUIDGenerator.getUUID();
		// String val = "";
		// for (String key : params.keySet()) {
		// val += "&" + params.get(key);
		// }
		// if( StringUtils.isEmpty(val))
		// return UUIDGenerator.getUUID();
		// else
		// return "fas_" + (val + this.getClass().getName()).hashCode();
	}

	static long TIME_OUT = 1 * 60 * 1000;

	protected void doExport(final ExportComplexVo exportVo, final Map<String, Object> params, final String ticket)
			throws UnsupportedEncodingException {
		String fileName = exportVo.getFileName();
		final String tempFolder = System.getProperty("java.io.tmpdir");
		final String separator = System.getProperty("file.separator");
		final String sf = "{\"index\":%d,\"status\":%d,\"ticket\":\"" + ticket + "\",\"name\":\"" + fileName + "\"}";
		final String tmpFileName = tempFolder + separator + "export" + separator + ticket + ".xlsx";
		RedisStorage.getCurrent().set(ticket, String.format(sf, 0, 1), TIME_OUT);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				FileOutputStream stream = null;
				File tmplFile;
				final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);
				try (final CacheContext context = new CacheContext()) {
					RedisStorage.getCurrent().set(ticket + ":action", 0, TIME_OUT);
					Function<Object, Boolean> handler = new Function<Object, Boolean>() {
						@Override
						public Boolean apply(Object input) {
							Map vals = (Map) input;
							queryResultMap(vals);
							exportExcel.write(vals);
							if (exportExcel.getRowIndex() % 500 == 1) {
								Integer flag = RedisStorage.getCurrent().get(ticket + ":action");
								if (flag == null || flag == 1) {
									logger.info("stop export file " + ticket);
									throw new NullPointerException("cancel export.");
								}
								// 刷新超时时间
								RedisStorage.getCurrent().set(ticket + ":action", 0, TIME_OUT);
								RedisStorage.getCurrent().set(ticket, String.format(sf, exportExcel.getRowIndex(), 1),
										TIME_OUT);
							}
							return true;
						}
					};

					Helper.createTemplateFolder("export");
					tmplFile = new File(tmpFileName);
					if (!tmplFile.exists())
						tmplFile.createNewFile();
					stream = new FileOutputStream(tmplFile);
					logger.info("export file to " + tmplFile.getAbsolutePath());
					SimplePage page = new SimplePage();
					page.setPageSize(Integer.MAX_VALUE);
					selectManagerMenthod(page, params, handler);
					exportExcel.flush(stream);
					RedisStorage.getCurrent().set(ticket, String.format(sf, exportExcel.getRowIndex(), 2), TIME_OUT);

				} catch (Exception e) {
					if (stream != null) {
						try {
							stream.close();
							clearn(tmpFileName);
						} catch (IOException e1) {

						}
					}
					RedisStorage.getCurrent().set(ticket, String.format(sf, exportExcel.getRowIndex(), -1), TIME_OUT);
					logger.error("export file error " + ticket, e);
				} finally {
					try {
						exportExcel.close();
					} catch (Exception e) {

					}
				}

			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}

	private void clearn(String fileName) {
		new File(fileName).delete();
	}

	public void setCommonsParams(Map<String, Object> params) {

	}

	/**
	 * 对导出字段进行排序
	 * 
	 * @param columns
	 *            原导出字段集合
	 * @return 排序后的导出字段
	 */
	public List<Map> sortExportColumns(List<Map> columns) {
		List<Map> results = new ArrayList<Map>();
		for (int i = 0; i < columns.size(); i++) {
			Map map = null;
			if (columns.get(i).get("seq") != null && !"".equals(columns.get(i).get("seq").toString().trim())) {
				for (Map entry : columns) {
					Integer seq = Integer.parseInt(entry.get("seq").toString());
					if (seq.intValue() == i + 1) {
						map = entry;
						break;
					}
				}
			} else {
				map = columns.get(i);
			}
			results.add(map);
		}
		return results;
	}

	/**
	 * 查询导出数据的方法
	 * 
	 * @param params
	 *            查询参数
	 * @return List<ModelType>
	 * @throws ManagerException
	 *             异常
	 * @throws Exception
	 */
	protected List<ModelType> queryExportData(Map<String, Object> params) throws ManagerException, Exception {
		int total = this.getCrudInfo().getManager().findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		List<ModelType> list = this.getCrudInfo().getManager().findByPage(page, orderByField, orderBy, params);
		return list;
	}

	protected void queryResultMap(Map vals) {

	}

	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {

	}

	/**
	 * 查询符合表头的数据
	 * 
	 * @param vo
	 *            查询参数
	 * @return List<Map>
	 * @throws ManagerException
	 */
	protected List<Map> findComplexHeaderData(ModelType vo) throws ManagerException {
		return new ArrayList<Map>();
	}

	/**
	 * 导出类型为Map的数据
	 * 
	 * @param params
	 * @param response
	 * @param dataList
	 *            需要导出的数据
	 * @throws ManagerException
	 */
	protected void exportData(Map<String, Object> params, HttpServletResponse response, List<?> dataList)
			throws ManagerException {
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			try {
				// 字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});

				List<Map> subColumnsList = new ArrayList<Map>();
				if (StringUtils.isNotEmpty(subExportColumns)) {
					subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {
					});

					// 如果是混合表头，则将subColumnsList加入columnsList集合
					if (ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
						columnsList.addAll(subColumnsList);
						subColumnsList = new ArrayList<Map>(1);
					}
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);

				if (StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
					});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}

				List<Map> list = (List<Map>) dataList;
				if (list != null && list.size() > 0) {
					for (Map map : list) {
						map.put("subExportData", new ArrayList<Map>(1));
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(list);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}

	/**
	 * 审核
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping("/do_audit")
	@ResponseBody
	public Boolean doAduit(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, request);
			int auditVal = StringUtils.isEmpty(request.getParameter("auditVal")) ? FasAduitStatusEnum.ADUIT_STATUS
					.getValue() : Integer.parseInt(request.getParameter("auditVal"));
			if (oList != null && oList.size() > 0) {
				for (ModelType model : oList) {
					model.setAuditStatus(auditVal);
					model.setAuditor(model.getUpdateUser());
					model.setAuditTime(model.getUpdateTime());
					this.getCrudInfo().getManager().modifyById(model);
				}
			}
			return true;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 启用
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping("/do_enable")
	@ResponseBody
	public Boolean doEnable(HttpServletRequest request) throws ManagerException {
		return this.updateStatus(request, FasLogoutStatusEnum.ENABLE_STATUS.getValue());
	}

	/**
	 * 停用
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping("/do_unable")
	@ResponseBody
	public Boolean doUnable(HttpServletRequest request) throws ManagerException {
		return this.updateStatus(request, FasLogoutStatusEnum.UNABLE_STATUS.getValue());
	}

	/**
	 * 修改单据状态
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping("/do_status")
	@ResponseBody
	public Boolean doStatus(HttpServletRequest request) throws ManagerException {
		String status = request.getParameter("status");
		if (StringUtils.isEmpty(status)) {
			return false;
		}
		return this.updateStatus(request, Integer.parseInt(status));
	}

	/**
	 * 启用/停用公用的修改方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param enableVal
	 *            参数值
	 * @return Boolean
	 * @throws ManagerException
	 *             异常
	 */
	private Boolean updateStatus(HttpServletRequest request, int enableVal) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, request);
			if (oList != null && oList.size() > 0) {
				String fieldName = request.getParameter("fieldName");
				Method setMethod = null;
				for (ModelType model : oList) {
					if (StringUtils.isEmpty(fieldName)) {
						model.setStatus(enableVal);
					} else {
						setMethod = model.getClass()
								.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1),
										Integer.class);
						setMethod.invoke(model, enableVal);
					}
					this.setDefaulValues(model, request);
					this.getCrudInfo().getManager().modifyById(model);
				}
			}
			return true;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 批量修改的方法
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return ResponseEntity<Map<String, Boolean>>
	 * @throws JsonParseException
	 *             异常
	 * @throws JsonMappingException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/save")
	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) throws JsonParseException,
			JsonMappingException, IOException, ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<ModelType>> params = null;
		try {
			params = this.convertToMap(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		if(this.validateSaveData(params)){
			if (params.size() > 0) {
				this.getCrudInfo().getManager().save(params);
			}
			flag.put("success", true);
		}else{
			flag.put("success", false);//TODO
		}
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
	
	protected Boolean validateSaveData(Map<CommonOperatorEnum, List<ModelType>> params) {
		return true;
	}

	protected Map<CommonOperatorEnum, List<ModelType>> convertToMap(HttpServletRequest req) throws Exception {
		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		Map<CommonOperatorEnum, List<ModelType>> params = new HashMap<CommonOperatorEnum, List<ModelType>>();
		if (StringUtils.isNotEmpty(deletedList)) {
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req);
			params.put(CommonOperatorEnum.DELETED, oList);
		}
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req);
			params.put(CommonOperatorEnum.UPDATED, oList);
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req);
			params.put(CommonOperatorEnum.INSERTED, oList);
		}
		return params;
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param mapper
	 *            ObjectMapper
	 * @param list
	 *            List<Map>
	 * @return List<ModelType>
	 * @throws Exception
	 */
	protected List<ModelType> convertListWithTypeReference(ObjectMapper mapper, List<Map> list,
			HttpServletRequest request) throws Exception {
		Class<ModelType> entityClass = (Class<ModelType>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<ModelType> tl = new ArrayList<ModelType>(list.size());
		for (int i = 0; i < list.size(); i++) {
			ModelType type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			this.setDefaulValues(type, request);
			tl.add(type);
		}
		return tl;
	}

	/**
	 * 转换成整型
	 * 
	 * @param rowAccessWindowSizeStr
	 *            String
	 * @return Integer
	 */
	public Integer getRowAccessWindowSizeValue(String rowAccessWindowSizeStr) {
		Integer rowAccessWindowSize = 1;
		if (!StringUtils.isEmpty(rowAccessWindowSizeStr)) {
			try {
				rowAccessWindowSize = Integer.parseInt(rowAccessWindowSizeStr);
			} catch (NumberFormatException e) {
				rowAccessWindowSize = 1;
			}
		}
		return rowAccessWindowSize;
	}

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 *            Throwable
	 * @return String
	 */
	private static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
}
