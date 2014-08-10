/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Naresh
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class BranchBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String branchId;
	private String branchName;
	private String city;
	private String state;
	private String pinNo;
	private String phone;
	private String branchCode;
	private String contactPerson;
	private String contactNo;
	private String address1;
	private String address2;
	private String clientId;
	private String clientName;
	private String regDate;
	private String landmark;
	private String branchTheme;
	private String status;
	private String msg;
	@XmlTransient
	private MultipartFile clientlogo;
	private String logoPath;
	private String editPath;
	private String emailId;

	/**
	 * @return the editPath
	 */
	public String getEditPath() {
		return editPath;
	}

	/**
	 * @param editPath
	 *            the editPath to set
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

	public String getBranchId() {
		return branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPinNo() {
		return pinNo;
	}

	public String getPhone() {
		return phone;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getClientId() {
		return clientId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getClientName() {
		return clientName;
	}

	public String getLandmark() {
		return landmark;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getBranchTheme() {
		return branchTheme;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public void setBranchTheme(String branchTheme) {
		this.branchTheme = branchTheme;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
