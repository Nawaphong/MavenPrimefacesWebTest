package com.sample.editor;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sample.entity.User;

@ManagedBean(name = "userVM")
@ViewScoped
public class UserVM implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6938747368852779906L;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
