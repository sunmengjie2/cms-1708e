package com.sunmengjie.cms.entity;

//import java.util.Date;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;

/**
 * 
 * @author Administrator
 *
 */
public class Complain {

	
	private Integer id;
	
	private Integer articleId;
	
	private Integer userId;
	
	
	private Integer complaintype;
	

	private String urlip;
	
	private Articles article;
	
	
	private User user;


	
	
	public Articles getArticle() {
		return article;
	}


	public void setArticle(Articles article) {
		this.article = article;
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


	public Integer getComplaintype() {
		return complaintype;
	}


	public void setComplainType(Integer complaintype) {
		this.complaintype = complaintype;
	}


	public String getUrlip() {
		return urlip;
	}


	public void setUrlip(String urlip) {
		this.urlip = urlip;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Complain [id=" + id + ", articleId=" + articleId + ", userId=" + userId + ", complaintype="
				+ complaintype + ", urlip=" + urlip + ", user=" + user + "]";
	}
	
	

	
	
	
	
}
