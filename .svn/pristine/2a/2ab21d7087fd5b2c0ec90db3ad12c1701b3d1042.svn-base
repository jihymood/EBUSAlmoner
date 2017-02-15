package com.xpro.ebusalmoner.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "person")
public class Person {

	@Column(name = "id", isId = true)
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private int age;
	@Column(name = "address")
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PersonBean [id=" + id + ", name=" + name + ", age=" + age
				+ ", address=" + address + "]";
	}

}
