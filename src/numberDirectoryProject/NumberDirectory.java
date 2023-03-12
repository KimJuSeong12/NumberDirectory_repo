package numberDirectoryProject;

import java.util.Objects;

public class NumberDirectory {
	private int id;
	private String name;
	private int age;
	private String gender;
	private String num;
	private String address;

	public NumberDirectory() {

	}

	public NumberDirectory(String name) {
		this(0, name, 0, null, null, null);
	}

	public NumberDirectory(String name, int age) {
		this(0, name, age, null, null, null);
	}

	public NumberDirectory(String name, int age, String gender, String num, String address) {
		this(0, name, age, gender, num, address);
	}

	public NumberDirectory(int id, String name, int age, String gender, String num, String address) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.num = num;
		this.address = address;
	}

	public int getId() {
		return id;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NumberDirectory) {
			NumberDirectory nd = (NumberDirectory) obj;
			return (this.name.equals(nd.name) && this.age == nd.age);
		}
		return false;
	}

	@Override
	public String toString() {
		return id + "\t" + name + "\t" + age + "\t" + gender + "\t" + num + "\t" + address;
	}

}
