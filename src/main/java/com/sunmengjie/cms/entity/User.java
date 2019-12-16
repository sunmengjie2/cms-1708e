package com.sunmengjie.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sunmengjie.cms.common.Gender;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 547983065118815310L;

	private Integer id;

	@NotBlank(message = "用户名不能为空")
	@Size( min = 3, max = 16,message = "用户名称应该大于等于3且小于等于16")
	private String username;

	@NotBlank(message = "密码不能为空")
	@Size(min = 6, max = 10, message = "密码应6-10位")
	private String password;

	private String nickname;
	private Date birthday;

	private Gender gender;
	private int locked;
	private Date createTime;
	private Date updateTime;
	private String url;// 头像的位置
	private int score;// 积分
	private int role; // 角色
	
	public User() {
		super();
	
	}

	public User(Integer id,
			@NotBlank(message = "用户名不能为空") @Size(min = 3, max = 16, message = "用户名称应该大于等于3且小于等于16") String username,
			@NotBlank(message = "用户名不能为空") @Size(min = 6, max = 10, message = "密码应6-10位") String password,
			String nickname, Date birthday, Gender gender, int locked, Date createTime, Date updateTime, String url,
			int score, int role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.birthday = birthday;
		this.gender = gender;
		this.locked = locked;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.url = url;
		this.score = score;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", birthday=" + birthday + ", gender=" + gender + ", locked=" + locked + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", url=" + url + ", score=" + score + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
	
}
