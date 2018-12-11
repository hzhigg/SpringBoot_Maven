package com.demo.user.entity;

import com.demo.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//生成toString()方法,让其调用父类的toString()方法
@ToString(callSuper=true)
//生成hashCode()和equals()方法，默认情况下，它将使用所有非静态，非transient字段。但可以通过在可选的exclude参数中来排除更多字段。或者，通过在of参数中命名它们来准确指定希望使用哪些字段
@EqualsAndHashCode(callSuper=true,of={"userName","id"})
@Data
public class User extends BaseEntity<User>{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -5007266232032037049L;

	private String email;

    private String nickName;

    private String password;

    private String userName;

   
}