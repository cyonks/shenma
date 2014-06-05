##准备工作
###工具准备
* hibernate3.5.3 
* Eclipse Java EE IDE for Web Developers.Version: Helios
* MySQL5.5

###环境配置搭建
* 安装并配置好Eclipse、JRE等环境。**配置好系统环境变量**
* 下载hibernate版本

###创建简单的数据库
MySQL创建数据库表

```java
create database testssh;
use testssh;
create table user(
	u_id int not null auto_increment,
	u_name varchar(30),
	primary key(u_id)
);
```

##创建hibernate项目流程
1.File->New->Dynamic Web Project->填写项目信息(hibernatedemo)->finish
本例项目名称为hibernatedemo
![](http://images.cnitblog.com/blog/427864/201406/052145153955054.jpg)


2.复制hibernate jar包到项目hibernatedemo/WebContent/WEB-INF/lib下

![](http://images.cnitblog.com/blog/427864/201406/052145393333409.jpg)


3.在src目录下编写hibernate.cfg.xml,配置数据库信息

```java
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
	<session-factory >
		<property name="connection.url">jdbc:mysql://localhost:3300/testssh</property>
		<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
		<property name="connection.username">root</property>
		<property name="connection.password">123456</property>
		<property name="dialect">org.hibernate.dialect.MySQLDialect</property>
		<property name="show_sql">true</property>
		<property name="format_sql">true</property>
		<mapping resource="com/entity/User.hbm.xml" />
	</session-factory>
</hibernate-configuration>
```

4.src下新建包com.entity用于实体对象，编写类User，在同一目录下编写User.hbm.xml映射文件。这里使用XML配置映射，亦可以使用注解方式映射数据库表。

User.java

```java
package com.entity;
public class User {
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
```

编写好User.hbm.xml,并将mapping配置到hibernate.cfg.xml中
```java
<!DOCTYPE hibernate-mapping PUBLIC 
"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping 
          package="com.entity">
	<class name="User" table="user" >
		<id name="id" column="u_id">
		<generator class="native"/>
		</id>
		<property name="name" column="u_name"/>
	</class>
</hibernate-mapping>
```

5.测试。com.test包中编写测试类

```java
public class Testhibernate {
	public static void main(String[] args) {
		Configuration cfg=new Configuration().configure();
		SessionFactory sf=cfg.buildSessionFactory();
		Session session =sf.openSession();
		Transaction tx=session.beginTransaction();
		User user=(User)session.get(User.class, 1);
		System.out.println(user.getId());
		System.out.println(user.getName());
		session.close();
		sf.close();
	}
}
```

6.成功输出，整个hibernate项目创建配置流程finished！
![](http://images.cnitblog.com/blog/427864/201406/052146578951407.jpg)


