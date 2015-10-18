/**
 * 
 */
package com.geospot.entities;

import org.springframework.data.annotation.Id;

/**
 * @author vincent.p.victor
 *
 */
public class MasterBizInfo {

	

	@Id
	private long masterBizId;
	private String masterBizName;
	private String isRegisteredBusiness;
	private String tin;
	private String strn;
	private String aadarNumber;
	private String pan;
	private String ownerName;
	private String gsBizPassword;
	

	public String getMasterBizName() {
		return masterBizName;
	}

	public void setMasterBizName(String masterBizName) {
		this.masterBizName = masterBizName;
	}

	public String getIsRegisteredBusiness() {
		return isRegisteredBusiness;
	}

	public void setIsRegisteredBusiness(String isRegisteredBusiness) {
		this.isRegisteredBusiness = isRegisteredBusiness;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getStrn() {
		return strn;
	}

	public void setStrn(String strn) {
		this.strn = strn;
	}

	public String getAadarNumber() {
		return aadarNumber;
	}

	public void setAadarNumber(String aadarNumber) {
		this.aadarNumber = aadarNumber;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getGsBizPassword() {
		return gsBizPassword;
	}

	public void setGsBizPassword(String gsBizPassword) {
		this.gsBizPassword = gsBizPassword;
	}

	public long getMasterBizId() {
		return masterBizId;
	}

	public void setMasterBizId(long masterBizId) {
		this.masterBizId = masterBizId;
	}
}
