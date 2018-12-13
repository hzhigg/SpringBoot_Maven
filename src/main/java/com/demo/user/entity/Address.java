package com.demo.user.entity;

import com.demo.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//生成toString()方法,让其调用父类的toString()方法
@ToString(callSuper=true)
//生成hashCode()和equals()方法，默认情况下，它将使用所有非静态，非transient字段。但可以通过在可选的exclude参数中来排除更多字段。或者，通过在of参数中命名它们来准确指定希望使用哪些字段
@EqualsAndHashCode(callSuper=true)
@Data
public class Address extends BaseEntity <Address>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3315811306356754547L;

	/**
     * 具体地址
     * 表字段 : address.address
     */
    private String address;

    /**
     * 城市
     * 表字段 : address.city
     */
    private String city;

    /**
     * 国家
     * 表字段 : address.country
     */
    private String country;

    /**
     * 地址标签（家、公司）
     * 表字段 : address.label
     */
    private String label;

    /**
     * 省份
     * 表字段 : address.province
     */
    private String province;

    /**
     * 无意义自增主键
     * 表字段 : address.web_user_id
     */
    private Long webUserId;

    /**
     * 获取 具体地址 字段:address.address
     *
     * @return address.address, 具体地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置 具体地址 字段:address.address
     *
     * @param address the value for address.address, 具体地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取 城市 字段:address.city
     *
     * @return address.city, 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置 城市 字段:address.city
     *
     * @param city the value for address.city, 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取 国家 字段:address.country
     *
     * @return address.country, 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置 国家 字段:address.country
     *
     * @param country the value for address.country, 国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 获取 地址标签（家、公司） 字段:address.label
     *
     * @return address.label, 地址标签（家、公司）
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置 地址标签（家、公司） 字段:address.label
     *
     * @param label the value for address.label, 地址标签（家、公司）
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 获取 省份 字段:address.province
     *
     * @return address.province, 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置 省份 字段:address.province
     *
     * @param province the value for address.province, 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取 无意义自增主键 字段:address.web_user_id
     *
     * @return address.web_user_id, 无意义自增主键
     */
    public Long getWebUserId() {
        return webUserId;
    }

    /**
     * 设置 无意义自增主键 字段:address.web_user_id
     *
     * @param webUserId the value for address.web_user_id, 无意义自增主键
     */
    public void setWebUserId(Long webUserId) {
        this.webUserId = webUserId;
    }
}