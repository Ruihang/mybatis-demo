# MyBatis Maven 示例
## 一、创建Maven项目
## 二、依赖包
### 1. 依赖Mybatis包
### 2. 依赖Mysql驱动包
### 3. 依赖Junit单元测试包
## 三、编写Mybatis核心配置文件
### 1.创建数据库连接属性
db.properties
```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://192.168.13.130:3306/my_test?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false
username=root
password=123456
```
### 2.创建MyBatis数据库配置文件
mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 添加属性配置文件 -->
    <properties resource="db.properties"></properties>

    <settings>
        <!-- 自动映射等级 NONE, PARTIAL, FULL，默认PARTIAL，如果强制开启自动映射 autoMapper="true"则此设置无效 -->
        <setting name="autoMappingBehavior" value="PARTIAL"/>
    </settings>

    <typeAliases>
        <package name="top.ruix.entity"/>
    </typeAliases>

    <environments default="development"> <!-- 配置多个环境 default默认环境的id -->
        <environment id="development"> <!-- 环境 -->
            <transactionManager type="JDBC"/> <!-- 事务管理 type有JDBC、MANAGED -->
            <dataSource type="POOLED"> <!-- 数据源 type有UNPOOLED、POOLED、JNDI -->
                <!-- JDBC驱动 -->
                <property name="driver" value="${driver}"/>
                <!-- 连接字符串 -->
                <property name="url" value="${url}"/>
                <!-- 数据库用户名 -->
                <property name="username" value="${username}"/>
                <!-- 数据库密码 -->
                <property name="password" value="${password}"/>
                <!-- 在任意时间可以存在的活动（也就是正在使用）连接数量，默认值：10 -->
                <property name="poolMaximumActiveConnections" value="10"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="top\ruix\dao\UserDaoMapper.xml"/>
    </mappers>
</configuration>
```
## 四、创建实体类
```java
package top.ruix.entity;

public class UserEntity {

    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```
## 五、创建DAO接口
### 1.创建Mybatis查询映射接口
```java
package top.ruix.dao;

import top.ruix.entity.UserEntity;

public interface IUserDao {

    UserEntity findById(int id);
}
```
## 六、编写Mapper映射配置文件
### 1.创建Mapper配置文件初始化格式
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="top.ruix.dao.IUserDao">
</mapper>
```
其中`namespace`是命名空间，同时也是Mybatis映射接口类
### 2.添加ResultMap
`ResultMap`是数据表接口映射到对象的一个配置
```xml
<resultMap type="UserEntity" id="userMap">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <result property="password" column="password"/>
</resultMap>
```
### 3.添加select查询SQL语句
在xml中添加`select`元素，`id`需要与映射接口的方法名一致，参数也需要一致
```xml
<select id="findById" resultMap="userMap" parameterType="int">
    select * from `user` where id= #{id}
</select>
```