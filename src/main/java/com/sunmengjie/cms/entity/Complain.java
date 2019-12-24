package com.sunmengjie.cms.entity;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Administrator
 *
 */
public class Complain {

	
	private Integer id;
	
	private Integer articleId;
	
	private Integer userId;
	
	@NotNull
	private Integer complainType;
	
	@NotBlank
	private String compainOption;
	private String srcUrl;
	private String picture;
	private String content;
	
	@Email
	private String email;
	
	private String mobile;
	
	private Date created;
	
	private User user;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Complain() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getComplainType() {
		return complainType;
	}

	public void setComplainType(Integer complainType) {
		this.complainType = complainType;
	}

	public String getCompainOption() {
		return compainOption;
	}

	public void setCompainOption(String compainOption) {
		this.compainOption = compainOption;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Complain [id=" + id + ", articleId=" + articleId + ", userId=" + userId + ", complainType="
				+ complainType + ", compainOption=" + compainOption + ", srcUrl=" + srcUrl + ", picture=" + picture
				+ ", content=" + content + ", email=" + email + ", mobile=" + mobile + ", created=" + created + "]";
	}
	
	
	
}
