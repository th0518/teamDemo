package com.example.vo;

import java.io.Serializable;

public class News implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id,newsurl,newstitle,newscontent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewsurl() {
		return newsurl;
	}

	public void setNewsurl(String newsurl) {
		this.newsurl = newsurl;
	}

	public String getNewstitle() {
		return newstitle;
	}

	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}

	public String getNewscontent() {
		return newscontent;
	}

	public void setNewscontent(String newscontent) {
		this.newscontent = newscontent;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", newsurl=" + newsurl + ", newstitle="
				+ newstitle + ", newscontent=" + newscontent + "]";
	}

	public News() {
		super();
	}
	
	
}
