package com.jebms.ald.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jebms.comm.core.BaseEntity;


@Table(name="apps.xyg_pub_data_upload_tmp")
public class PubDataUploadTmp extends BaseEntity  {
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "SELECT apps.XYG_PUB_DATA_UPLOAD_TMP_S.NEXTVAL FROM DUAL")
	@Id
	private Long uploadTmpId;
	
	private Long batchId;
	
	private String attributeCategory;
	
	private Integer rowNum;
	
	private String attribute1;
	
	private String attribute2;
	
	private String attribute3;
	
	private String attribute4;
	
	private String attribute5;
	
	private String attribute6;
	
	private String attribute7;
	
	private String attribute8;
	
	private String attribute9;
	
	private String attribute10;
	
	private String attribute11;
	
	private String attribute12;
	
	private String attribute13;
	
	private String attribute14;
	
	private String attribute15;
	
	private String attribute16;
	
	private String attribute17;
	
	private String attribute18;
	
	private String attribute19;
	
	private String attribute20;
	
	private String attribute21;
	
	private String attribute22;
	
	private String attribute23;
	
	private String attribute24;
	
	private String attribute25;
	
	private String attribute26;
	
	private String attribute27;
	
	private String attribute28;
	
	private String attribute29;
	
	private String attribute30;
	
	private Integer processFlag;
	
	private String processMessage;

	public Long getUploadTmpId() {
		return uploadTmpId;
	}

	public void setUploadTmpId(Long uploadTmpId) {
		this.uploadTmpId = uploadTmpId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	public String getAttribute15() {
		return attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	public String getAttribute16() {
		return attribute16;
	}

	public void setAttribute16(String attribute16) {
		this.attribute16 = attribute16;
	}

	public String getAttribute17() {
		return attribute17;
	}

	public void setAttribute17(String attribute17) {
		this.attribute17 = attribute17;
	}

	public String getAttribute18() {
		return attribute18;
	}

	public void setAttribute18(String attribute18) {
		this.attribute18 = attribute18;
	}

	public String getAttribute19() {
		return attribute19;
	}

	public void setAttribute19(String attribute19) {
		this.attribute19 = attribute19;
	}

	public String getAttribute20() {
		return attribute20;
	}

	public void setAttribute20(String attribute20) {
		this.attribute20 = attribute20;
	}

	public String getAttribute21() {
		return attribute21;
	}

	public void setAttribute21(String attribute21) {
		this.attribute21 = attribute21;
	}

	public String getAttribute22() {
		return attribute22;
	}

	public void setAttribute22(String attribute22) {
		this.attribute22 = attribute22;
	}

	public String getAttribute23() {
		return attribute23;
	}

	public void setAttribute23(String attribute23) {
		this.attribute23 = attribute23;
	}

	public String getAttribute24() {
		return attribute24;
	}

	public void setAttribute24(String attribute24) {
		this.attribute24 = attribute24;
	}

	public String getAttribute25() {
		return attribute25;
	}

	public void setAttribute25(String attribute25) {
		this.attribute25 = attribute25;
	}

	public String getAttribute26() {
		return attribute26;
	}

	public void setAttribute26(String attribute26) {
		this.attribute26 = attribute26;
	}

	public String getAttribute27() {
		return attribute27;
	}

	public void setAttribute27(String attribute27) {
		this.attribute27 = attribute27;
	}

	public String getAttribute28() {
		return attribute28;
	}

	public void setAttribute28(String attribute28) {
		this.attribute28 = attribute28;
	}

	public String getAttribute29() {
		return attribute29;
	}

	public void setAttribute29(String attribute29) {
		this.attribute29 = attribute29;
	}

	public String getAttribute30() {
		return attribute30;
	}

	public void setAttribute30(String attribute30) {
		this.attribute30 = attribute30;
	}

	public Integer getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(Integer processFlag) {
		this.processFlag = processFlag;
	}

	public String getProcessMessage() {
		return processMessage;
	}

	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}

}
