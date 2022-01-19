package com.sxau.cms.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxau.cms.util.DateJsonSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Entity
@Table(name="cms_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 编号（主键）
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 生日
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;

	/**
	 * 注册时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date registerTime;

	/**
	 * 状态（正常0、禁用1）
	 */
	private String status;

	/**
	 * 头像（存储的是头像的url地址）
	 */
	private String image;

	/**
	 * 用户所属角色
	 */

	@ManyToOne
	@JoinColumn(name="role_id")
	@JsonIgnoreProperties
	private Role role;


	@OneToMany(mappedBy="user",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Article> articleList=new ArrayList<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonSerialize(using = DateJsonSerializer.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@JsonSerialize(using = DateJsonSerializer.class)
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public List<Article> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", realname=" + realname + ", gender=" + gender + ", birthday=" + birthday + ", registerTime="
				+ registerTime + ", status=" + status + ", image=" + image + ", role=" + role + ", articleList="
				+ articleList + "]";
	}
}