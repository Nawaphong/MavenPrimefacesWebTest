package com.sample.editor;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.sample.entity.User;
import com.sample.jpa.UserLocal;

@SuppressWarnings("restriction")
@ManagedBean(name = "dtEditView")
@ViewScoped
public class UserEditView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7907960160940793265L;

	private List<User> users;
	private User userSeletedBean = new User();

	@ManagedProperty(value = "#{userVM}")
	private UserVM userVM;

	@EJB
	private UserLocal service;

	@PostConstruct
	public void init() {
		reloadTable();
		userVM.setUser(new User());
	}

	public void reloadTable() {
		users = service.getUsers();
	};

	public void onRowEdit(RowEditEvent event) {
		service.updateUser((User) event.getObject());
		FacesMessage msg = new FacesMessage("Car Edited", ((User) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((User) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onRowSelect(SelectEvent event) {
		User userSeletedBean = (User) event.getObject();
		userVM.setUser(userSeletedBean);
		FacesMessage msg = new FacesMessage("User Selected", userSeletedBean.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("User Unselected", ((User) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void fwAdd() {

		service.createUser(userVM.getUser());
		FacesMessage msg = new FacesMessage("Add User Complete...");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		reloadTable();
	}

	public void fwUpdate() {
		service.updateUser(userVM.getUser());
		FacesMessage msg = new FacesMessage("Update User Complete...");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		reloadTable();
	}

	public void fwRemove() {
		service.removeUser(userVM.getUser());
		FacesMessage msg = new FacesMessage("Remove User Complete...");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		reloadTable();
		fwCancel();
	}

	public void fwCancel() {
		userSeletedBean = null;
		userVM.setUser(new User());
		reloadTable();
		// FacesMessage msg = new FacesMessage("Cancel Operation");
		// FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserLocal getService() {
		return service;
	}

	public void setService(UserLocal service) {
		this.service = service;
	}

	public User getUserSeletedBean() {
		return userSeletedBean;
	}

	public void setUserSeletedBean(User userSeletedBean) {
		this.userSeletedBean = userSeletedBean;
	}

	public UserVM getUserVM() {
		return userVM;
	}

	public void setUserVM(UserVM userVM) {
		this.userVM = userVM;
	}

}
