package com.metafour.multitenancy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-21
 * @see org.springframework.tests.sample.beans.TestBean
 */
@Data @NoArgsConstructor
public class TestBean implements BeanNameAware, BeanFactoryAware, ITestBean{//, DisposableBean, Comparable<TestBean> {

	protected String beanName;
	protected BeanFactory beanFactory;
	protected boolean postProcessed;
	protected boolean initialized;
	protected boolean destroyed;

	protected String name;
	protected String sex;
	protected int age;

	protected ITestBean spouse;
	protected ITestBean[] spouses;

	public TestBean(String name) {
		this.name = name;
	}

	// @Override
	// public boolean equals(Object other) {
	// if (this == other) return true;
	// if (other == null || !(other instanceof TestBean)) return false;
	// TestBean tb2 = (TestBean) other;
	// return ObjectUtils.nullSafeEquals(this.name, tb2.name) && this.age == tb2.age;
	// }
	//
	// @Override
	// public int hashCode() {
	// return this.age;
	// }

//	@Override
//	public int compareTo(TestBean other) {
//		if (this.name != null && other instanceof TestBean) return this.name.compareTo(other.getName());
//		return 1;
//	}

	@Override
	public String toString() {
		return this.name + "-bean";
	}

	public void initialize() {
		this.initialized = true;
	}

//	@Override
//	public void destroy() throws Exception {
//		this.destroyed = true;
//		System.out.println("Bean is destroyed");
//	}

	@Override
	public int haveBirthday() {
		return age++;
	}

}
