package cn.wonhigh.retail.fas.jms;

import com.yougou.logistics.base.common.vo.BaseDto;
import com.yougou.logistics.base.jms.AbstractConsumer;

/**
 * @author yuesheng.yin
 * 
 */
public class TestConsumer extends AbstractConsumer {

	private static final long serialVersionUID = 7254684091989419155L;

	@Override
	public void messageListener(BaseDto dto) {
		log.info("=======================================");
		log.info("TestConsumer  consumed...");
		try {
			log.info(Thread.currentThread().getId() + " :::: " + Thread.currentThread().getName());
		} catch (Exception e) {
			log.error("Log update  faily,exception is:"
					+ e.getMessage(), e);
		}

	}

}

