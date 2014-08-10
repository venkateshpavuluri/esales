/**
 * 
 */
package com.mnt.esales.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author venkateshp
 *
 */
public class DatabaseBackupFileUpload {
	private MultipartFile imageFile;

	/**
	 * @return the imageFile
	 */
	public MultipartFile getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

}
