package com.metafour.multitenancy;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-21
 * @see org.springframework.tests.sample.beans.ITestBean
 */
public interface ITestBean {

	int getAge();

	void setAge(int age);

	String getName();

	void setName(String name);

	ITestBean getSpouse();

	void setSpouse(ITestBean spouse);

	ITestBean[] getSpouses();

	/**
	 * Increment the age by one.
	 * @return the previous age
	 */
	int haveBirthday();
}
