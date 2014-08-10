/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

/**
 * @author Naresh
 * 
 */
public class PopulateBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String Id;
	private String name;
	
	public String getId() {
		return Id;
	}
	public String getName() {
		return name;
	}
	public void setId(String id) {
		Id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
}
