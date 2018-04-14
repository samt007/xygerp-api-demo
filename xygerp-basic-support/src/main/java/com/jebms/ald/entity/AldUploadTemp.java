package com.jebms.ald.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jebms.comm.core.BaseEntity;


@Table(name="apps.xyg_ald_upload_temp")
public class AldUploadTemp extends BaseEntity  {
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "SELECT apps.XYG_ALD_UPLOAD_TEMP_S.NEXTVAL FROM DUAL")
	@Id
	private Long uploadTempId;
	
	private Long batchId;
	
	private String docType;
	
	private String docCode;
	
	private Integer docRow;
	
	private String col1;
	
	private String col2;
	
	private String col3;
	
	private String col4;
	
	private String col5;
	
	private String col6;
	
	private String col7;
	
	private String col8;
	
	private String col9;
	
	private String col10;
	
	private String col11;
	
	private String col12;
	
	private String col13;
	
	private String col14;
	
	private String col15;
	
	private String col16;
	
	private String col17;
	
	private String col18;
	
	private String col19;
	
	private String col20;
	
	private String col21;
	
	private String col22;
	
	private String col23;
	
	private String col24;
	
	private String col25;
	
	private String col26;
	
	private String col27;
	
	private String col28;
	
	private String col29;
	
	private String col30;
	
	private String col31;
	
	private String col32;
	
	private String col33;
	
	private String col34;
	
	private String col35;
	
	private String col36;
	
	private String col37;
	
	private String col38;
	
	private String col39;
	
	private String col40;
	
	private String col41;
	
	private String col42;
	
	private String col43;
	
	private String col44;
	
	private String col45;
	
	private String col46;
	
	private String col47;
	
	private String col48;
	
	private String col49;
	
	private String col50;
	
	private String col51;
	
	private String col52;
	
	private String col53;
	
	private String col54;
	
	private String col55;
	
	private Integer processFlag;
	
	private String processMessage;

	public Long getUploadTempId() {
		return uploadTempId;
	}

	public void setUploadTempId(Long uploadTempId) {
		this.uploadTempId = uploadTempId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public Integer getDocRow() {
		return docRow;
	}

	public void setDocRow(Integer docRow) {
		this.docRow = docRow;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21;
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22;
	}

	public String getCol23() {
		return col23;
	}

	public void setCol23(String col23) {
		this.col23 = col23;
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24;
	}

	public String getCol25() {
		return col25;
	}

	public void setCol25(String col25) {
		this.col25 = col25;
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26;
	}

	public String getCol27() {
		return col27;
	}

	public void setCol27(String col27) {
		this.col27 = col27;
	}

	public String getCol28() {
		return col28;
	}

	public void setCol28(String col28) {
		this.col28 = col28;
	}

	public String getCol29() {
		return col29;
	}

	public void setCol29(String col29) {
		this.col29 = col29;
	}

	public String getCol30() {
		return col30;
	}

	public void setCol30(String col30) {
		this.col30 = col30;
	}

	public String getCol31() {
		return col31;
	}

	public void setCol31(String col31) {
		this.col31 = col31;
	}

	public String getCol32() {
		return col32;
	}

	public void setCol32(String col32) {
		this.col32 = col32;
	}

	public String getCol33() {
		return col33;
	}

	public void setCol33(String col33) {
		this.col33 = col33;
	}

	public String getCol34() {
		return col34;
	}

	public void setCol34(String col34) {
		this.col34 = col34;
	}

	public String getCol35() {
		return col35;
	}

	public void setCol35(String col35) {
		this.col35 = col35;
	}

	public String getCol36() {
		return col36;
	}

	public void setCol36(String col36) {
		this.col36 = col36;
	}

	public String getCol37() {
		return col37;
	}

	public void setCol37(String col37) {
		this.col37 = col37;
	}

	public String getCol38() {
		return col38;
	}

	public void setCol38(String col38) {
		this.col38 = col38;
	}

	public String getCol39() {
		return col39;
	}

	public void setCol39(String col39) {
		this.col39 = col39;
	}

	public String getCol40() {
		return col40;
	}

	public void setCol40(String col40) {
		this.col40 = col40;
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

	public String getCol41() {
		return col41;
	}

	public void setCol41(String col41) {
		this.col41 = col41;
	}

	public String getCol42() {
		return col42;
	}

	public void setCol42(String col42) {
		this.col42 = col42;
	}

	public String getCol43() {
		return col43;
	}

	public void setCol43(String col43) {
		this.col43 = col43;
	}

	public String getCol44() {
		return col44;
	}

	public void setCol44(String col44) {
		this.col44 = col44;
	}

	public String getCol45() {
		return col45;
	}

	public void setCol45(String col45) {
		this.col45 = col45;
	}

	public String getCol46() {
		return col46;
	}

	public void setCol46(String col46) {
		this.col46 = col46;
	}

	public String getCol47() {
		return col47;
	}

	public void setCol47(String col47) {
		this.col47 = col47;
	}

	public String getCol48() {
		return col48;
	}

	public void setCol48(String col48) {
		this.col48 = col48;
	}

	public String getCol49() {
		return col49;
	}

	public void setCol49(String col49) {
		this.col49 = col49;
	}

	public String getCol50() {
		return col50;
	}

	public void setCol50(String col50) {
		this.col50 = col50;
	}

	public String getCol51() {
		return col51;
	}

	public void setCol51(String col51) {
		this.col51 = col51;
	}

	public String getCol52() {
		return col52;
	}

	public void setCol52(String col52) {
		this.col52 = col52;
	}

	public String getCol53() {
		return col53;
	}

	public void setCol53(String col53) {
		this.col53 = col53;
	}

	public String getCol54() {
		return col54;
	}

	public void setCol54(String col54) {
		this.col54 = col54;
	}

	public String getCol55() {
		return col55;
	}

	public void setCol55(String col55) {
		this.col55 = col55;
	}
	
	
}
