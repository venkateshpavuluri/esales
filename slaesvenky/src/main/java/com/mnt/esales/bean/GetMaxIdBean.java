/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

/**
 * @author naresh
 * 
 */
public class GetMaxIdBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

}
