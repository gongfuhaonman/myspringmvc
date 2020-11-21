package domain;

public class  Student {
public String id;
private String name;
private int age;
private String gender;
public Student(){}
public Student(String id,String name,int age,String gender){
	this.id=id;
	this.name=name;
	this.age=age;
	this.gender=gender;
}
public String getId() {
	return this.id;
}
public void setId(String id) {
	this.id=id;
}
public String getName() {
	return this.name;
}
public void setName(String name) {
	this.name=name;
}
public int getAge() {
	return this.age;
}
public void setAge(int age) {
	this.age=age;
}
public String getGender() {
	return this.gender;
}
public void setGender(String gender) {
	this.gender=gender;
}
}
