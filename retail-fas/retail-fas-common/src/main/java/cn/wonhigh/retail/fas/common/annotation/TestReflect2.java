package cn.wonhigh.retail.fas.common.annotation;

// 将Test2对象的数据设置到Test1对象中
@ClassReflect(reflectClass = TestReflect1.class)
public class TestReflect2 {

	@FieldReflect(name = "name1")
	private String name2;

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
}
