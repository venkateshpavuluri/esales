/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.bean;

import java.io.Serializable;

/**
 * @author Naresh
 * 
 */
public class DepartmentBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deptId;
	private String deptName;
	private String display;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
