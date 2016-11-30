package cn.wonhigh.retail.fas.api.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.starit.diamond.manager.ManagerListener;
import com.starit.diamond.manager.impl.DefaultDiamondManager;
import com.yougou.logistics.base.common.utils.DefaultDiamondConfChangeHandler;
import com.yougou.logistics.base.common.utils.DiamondConfChangeHandler;

/**
 * 配置文件加载器
 * @author wei.b
 * @date Jun 11, 2014 9:44:17 AM
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Configuration
public class DiamondLoaderContainer {

	private static final String DIAMOND_CONFIG_PROPERTIES = "diamond-config.properties";
	private static final int DIAMOND_INVOKE_TIME_OUT = 10000;
	public static final String CONF_FILE_PATH_FOR_WIN = "diamondConfigLocationForWindow";
	public static final String CONF_FILE_PATH_FOR_UNIX = "diamondConfigLocationForUnix";
	public static final String CONF_GROUP_NAME = "diamondGroupName";
	public static final String CONF_FILES = "diamondConfigFiles";
	public static final String COMMON_CONF_GROUP_NAME = "commonDiamondGroupName";
	public static final String COMMON_CONF_FILES = "commonDiamondConfigFiles";
	public static final String AFTER_HANDLER = "diamondAfterHandlerClass";
	public static final String USE_REALTIME_NOTIFICATION = "diamondUseRealTimeNotification";
	public static final String AUTO_CREATE_DIR = "diamondAutoCreateDicectory";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String WINDOWS = "Windows";
	public static final String OS_NAME = "os.name";
	public static final String BAK_DIR = "bak";

	private static boolean ran = false;

	private final int ZERO = 0;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, DefaultDiamondManager> mList = null;

	@PostConstruct
	public void contextInitialized() {
		//解决Junit执行diamond的问题,首次运行请执行Main生成配置
		if (ran) {
			return;
		}

		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(DIAMOND_CONFIG_PROPERTIES);
			Properties config = new Properties();
			try {
				config.load(is);
				if (config.size() == 0) {
					System.err.println("未加载到配置文件!!");
					logger.error("未加载到配置文件!!");
					return;
				}
			} catch (IOException e) {
				System.err.println("加载配置文件出错!!");
				logger.error("加载配置文件出错!!", e);
				return;
			}

			String confPath = null;
			String osName = System.getProperty(OS_NAME);
			if (osName.indexOf(WINDOWS) != -1) {
				confPath=System.getProperty(CONF_FILE_PATH_FOR_WIN);
				if(StringUtils.isEmpty(confPath)){
					confPath = config.getProperty(CONF_FILE_PATH_FOR_WIN);
				}
			} else {
				confPath=System.getProperty(CONF_FILE_PATH_FOR_UNIX);
				if(StringUtils.isEmpty(confPath)){
					config.getProperty(CONF_FILE_PATH_FOR_UNIX);
				}
			}

			String commonFiles = config.getProperty(COMMON_CONF_FILES);
			String[] commonPropList = builderFiles(commonFiles);
			String files = config.getProperty(CONF_FILES);
			String[] propList = builderFiles(files);
			if ((null == propList || propList.length == ZERO)
					&& (null == commonPropList || commonPropList.length == ZERO)) {
				return;
			}
			String afterHandler = config.getProperty(AFTER_HANDLER);

			boolean useRealTimeNotification = true;
			String useNotifyStr = config.getProperty(USE_REALTIME_NOTIFICATION);
			if (!(null == useNotifyStr || "".equals(useNotifyStr))) {
				useRealTimeNotification = new Boolean(useNotifyStr);
			}

			boolean autoCreateDir = false;
			String autoCreateDicectory = config.getProperty(AUTO_CREATE_DIR);
			if (!(null == autoCreateDicectory || "".equals(autoCreateDicectory))) {
				autoCreateDir = new Boolean(autoCreateDicectory);
			}

			//构建DefaultDiamondManager列表，去服务端拉配置文件到本地进行比较
			String commonGroupName = System.getProperty(COMMON_CONF_GROUP_NAME);
			String groupName = System.getProperty(CONF_GROUP_NAME);
			//增加从环境变量当中获得配置信息  解决多节点配置问题
			if(StringUtils.isEmpty(commonGroupName)){
				commonGroupName = config.getProperty(COMMON_CONF_GROUP_NAME);
			}
			if(StringUtils.isEmpty(groupName)){
				groupName=config.getProperty(CONF_GROUP_NAME);
			}	
			if (StringUtils.isBlank(commonGroupName) && StringUtils.isBlank(groupName)) {
				return;
			}
			mList = builderDiamondManagerList(confPath, commonGroupName, commonPropList, groupName, propList,
					afterHandler);
			handlerFileGenerator(confPath, groupName, mList, autoCreateDir);
			//只需要启动时生成文件,不需要实时修改通知生成
			if (!useRealTimeNotification) {
				closeDiamondListener();
			} else {
				//添加关闭容器监听
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						logger.debug("启动容器Shutdown监听,关闭diamond监听");
						closeDiamondListener();
					}
				});
			}
		} catch (Exception e) {
			logger.error("diamond启动异常,请检查配置", e);
		}

		ran = true;
	}

	/**
	 * 关闭监听
	 */
	private void closeDiamondListener() {
		if (null != mList && mList.size() > ZERO) {
			//移除diamond轮询
			for (DefaultDiamondManager d : mList.values()) {
				d.close();
			}
		}
	}

	/**
	 * 构造DiamondManager列表
	 * @param confPath 配置文件路径
	 * @param commonGroupName 公共的配置文件组
	 * @para commonPropList 公共的配置文件列表
	 * @param groupName 
	 * @param propList 
	 * @param afterHandlerClass 后继处理类
	 * @return 可用的Diamond列表 key:propertyName
	 */
	private Map<String, DefaultDiamondManager> builderDiamondManagerList(final String confPath, String commonGroupName,
			String[] commonPropList, String groupName, String[] propList, final String afterHandlerClass) {
		boolean commonPropListIsEmpty = null != commonPropList && commonPropList.length > ZERO ? false : true;
		boolean propListIsEmpty = null != propList && propList.length > ZERO ? false : true;

		//与common不是同一组生成两组DiamondManager,同一组就合并
		if (!groupName.equals(commonGroupName)) {
			int mapSize = (commonPropListIsEmpty ? ZERO : commonPropList.length)
					+ (propListIsEmpty ? ZERO : propList.length);
			Map<String, DefaultDiamondManager> map = new HashMap<String, DefaultDiamondManager>(mapSize);
			if (!commonPropListIsEmpty) {
				builderPropertiesList(confPath, commonGroupName, commonPropList, afterHandlerClass, map);
			}
			if (!propListIsEmpty) {
				builderPropertiesList(confPath, groupName, propList, afterHandlerClass, map);
			}
			return map;
		} else {
			String mergePropList[] = null;
			if (!commonPropListIsEmpty && !propListIsEmpty) {
				List<String> commonPropertyList = Arrays.asList(commonPropList);
				List<String> propertyList = Arrays.asList(propList);
				List<String> mergeList = new ArrayList<String>();
				for (String p : commonPropertyList) {
					if (!mergeList.contains(p)) {
						mergeList.add(p);
					}
				}
				for (String p : propertyList) {
					if (!mergeList.contains(p)) {
						mergeList.add(p);
					}
				}
				mergePropList = new String[mergeList.size()];
				mergeList.toArray(mergePropList);
			} else if (commonPropListIsEmpty) {
				mergePropList = propList;
			} else if (propListIsEmpty) {
				mergePropList = commonPropList;
			}

			Map<String, DefaultDiamondManager> map = new HashMap<String, DefaultDiamondManager>(mergePropList.length);
			builderPropertiesList(confPath, groupName, mergePropList, afterHandlerClass, map);
			return map;
		}
	}

	private void builderPropertiesList(final String confPath, String groupName, String[] propList,
			final String afterHandlerClass, Map<String, DefaultDiamondManager> map) {
		for (final String prop : propList) {
			DefaultDiamondManager m = new DefaultDiamondManager(groupName, prop, new ManagerListener() {
				/**
				 * 检测到服务端更新配置后继处理方法
				 * @param configInfo 配置内容
				 */
				public void receiveConfigInfo(String configInfo) {
					// 客户端处理数据的逻辑
					logger.debug("检测到配置文件{}更新", prop);
					if (StringUtils.isNotBlank(afterHandlerClass)) {
						try {
							//后继处理
							Object o = Class.forName(afterHandlerClass).newInstance();
							if (o instanceof DiamondConfChangeHandler) {
								DiamondConfChangeHandler h = (DiamondConfChangeHandler) o;
								if (StringUtils.isNotBlank(configInfo)) {
									h.handler(confPath, prop, configInfo);
								}
							}
						} catch (ClassNotFoundException e) {
							logger.error("后继处理类未找到出错:", e);
						} catch (InstantiationException e) {
							logger.error("后继处理类实例化出错:", e);
						} catch (IllegalAccessException e) {
							logger.error("后继处理类执行出错:", e);
						}
					}
				}

				@Override
				public Executor getExecutor() {
					return null;
				}
			}, true);
			if (m.exists(prop, groupName)) {
				map.put(prop, m);
			} else {
				m.close();
				m = null;
			}
		}
	}

	/**
	 * 检查有效的配置文件，无效的文件移动到配置目录下的bak目录中
	 * @param confPath 配置文件路径
	 * @param propList 配置文件列表
	 * @param autoCreateDir 自动创建目录
	 * @return 处理状态 true:成功 false:失败
	 */
	public boolean validateFiles(String confPath, Set<String> propList, boolean autoCreateDir) {
		File dir = new File(confPath);
		if (autoCreateDir) {
			dir.mkdir();
		}

		if (!(dir.exists() && dir.isDirectory())) {
			logger.error("配置目录文件不存在");
			return false;
		}

		File[] files = dir.listFiles();
		File bakDir = new File(dir, BAK_DIR);

		if (null == files || files.length == ZERO) {
			return false;
		}
		String oriFileName = null;
		String tempFileName = null;
		String curDate = null;
		File bakFile = null;
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			oriFileName = file.getName();
			tempFileName = oriFileName.indexOf(DOT) > -1 ? oriFileName.substring(ZERO, oriFileName.indexOf(DOT))
					: oriFileName;
			//无效配置文件移到bak目录
			if (!propList.contains(tempFileName)) {
				try {
					logger.debug("配置文件不在列表中,移动{}文件到{}目录", file.getName(), BAK_DIR);
					bakFile = new File(bakDir, oriFileName);
					if (bakFile.exists()) {
						if (null == curDate) {
							curDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
						}
						FileUtils.moveFile(file, new File(bakDir, oriFileName + "-" + curDate));
					} else {
						FileUtils.moveFileToDirectory(file, bakDir, true);
					}
				} catch (IOException e) {
					logger.error("配置文件不在列表中,移动{}文件到{}目录出错", file.getName(), BAK_DIR);
				}
			}
		}
		return true;
	}

	/**
	 * properties文件，去扩展名
	 * @param files 文件列表
	 * @return 文件名称列表
	 */
	private String[] builderFiles(String files) {
		String[] arr = StringUtils.split(files, COMMA);
		if (null != arr && arr.length > ZERO) {
			for (int i = ZERO; i < arr.length; i++) {
				int index = arr[i].indexOf(DOT);
				if (index > ZERO) {
					arr[i] = arr[i].substring(ZERO, index);
				}
			}
		}
		return arr;
	}

	/**
	 * 批量生成配置文件
	 * @param confPath 目录
	 * @param groupName 工程名称
	 * @param mList 可用的diamond对象列表
	 * @param autoCreateDir 自动创建目录
	 */
	private void handlerFileGenerator(String confPath, String groupName, Map<String, DefaultDiamondManager> mList,
			boolean autoCreateDir) {
		//不加载配置内容到环境变量中
		DefaultDiamondConfChangeHandler handler = new DefaultDiamondConfChangeHandler(false);
		//检查文件是否有效
		validateFiles(confPath, mList.keySet(), autoCreateDir);
		Iterator<String> keys = mList.keySet().iterator();
		String key = null;
		while (keys.hasNext()) {
			key = (String) keys.next();
			DefaultDiamondManager m = mList.get(key);
			/*判断是否是有效的配置
			if (!m.exists(key, groupName)) {
				//移除无效的配置
				m.close();
				keys.remove();
				continue;
			}*/
			String c = m.getAvailableConfigureInfomation(DIAMOND_INVOKE_TIME_OUT);
			if (StringUtils.isNotBlank(c)) {
				handler.handler(confPath, key, c);
			}
		}
	}
}