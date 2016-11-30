package cn.wonhigh.retail.fas.common.annotation;

import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;

public class TestReflect {

	public static void main(String[] args) throws Exception {
		TestReflect2 test2 = new TestReflect2();
		test2.setName2("test2");

		TestReflect1 test1 = new TestReflect1();

		AnnotationReflectUtil.copyProperties(test1, test2);
		System.out.println(test1.getName1());
	}
}
