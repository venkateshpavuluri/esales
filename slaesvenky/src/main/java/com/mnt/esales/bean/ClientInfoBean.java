/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Naresh
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientName;
	private String city;
	private String state;
	private String pin;
	private String phone;
	private String contactPerson;
	private String contactNo;
	private String theme;
	private String regDate;
	private String address;
	private String addr;
	private String landmark;
	private String email;
	private String themeName;
	private String deptId;
	private String msg;
	@XmlTransient
	private MultipartFile clientlogo;
	private String logoPath;
	private String editPath;

	/**
	 * @return the editPath
	 */
	public String getEditPath() {
		return editPath;
	}

	/**
	 * @param editPath the editPath to set
	 */
	public void setEditPath(String editPath) {
		this.editPath = editPath;
	}

	/**
	 * @return the logoPath
	 */
	public String getLogoPath() {
		return logoPath;
	}

	/**
	 * @param logoPath
	 *            the logoPath to set
	 */
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	/**
	 * @return the clientlogo
	 */
	public MultipartFile getClientlogo() {
		return clientlogo;
	}

	/**
	 * @param clientlogo
	 *            the clientlogo to set
	 */
	public void setClientlogo(MultipartFile clientlogo) {
		this.clientlogo = clientlogo;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPin() {
		return pin;
	}

	public String getPhone() {
		return phone;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getTheme() {
		return theme;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getAddress() {
		return address;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
