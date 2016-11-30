package cn.wonhigh.retail.fas.common;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import junit.framework.TestCase;

public class BigDecimalUtilTest extends TestCase {
	
	 
    public void testmutiAll()
    {
    	BigDecimal[] values = {new BigDecimal(1.51),new BigDecimal(1.56),new BigDecimal(3.443333)};
    	BigDecimal result = BigDecimalUtil.mutiAll(values);
    	Boolean equals = result.doubleValue()==8.11;
        assertTrue( equals);
        
        result = BigDecimalUtil.mutiAll(new BigDecimal(1.51),new BigDecimal(1.56),new BigDecimal(3.443333));
    	equals = result.doubleValue()==8.11;
        assertTrue( equals);
    }
    
    public void testmutiAllZero()
    {
    	BigDecimal[] values = {null,new BigDecimal(1.56),new BigDecimal(3.443333)};
    	BigDecimal result = BigDecimalUtil.mutiAll(values);
        assertTrue( result.doubleValue() - BigDecimal.ZERO.doubleValue() == 0);
        
//        result = BigDecimalUtil.mutiAll(null,new BigDecimal(1.56),new BigDecimal(3.443333));
//        assertTrue( result.doubleValue() - BigDecimal.ZERO.doubleValue() == 0);
    }
}
