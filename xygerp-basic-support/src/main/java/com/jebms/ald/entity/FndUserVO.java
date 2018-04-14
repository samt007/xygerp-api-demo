package com.jebms.ald.entity;

public class FndUserVO extends FndUser{

    /**
     * 用户登录的语言
     */
    private String language;
    
    private String employeeNumber;
    
    private String fullName;
    
    /*
    private List<FndRespVO> respVOs = new ArrayList<>();
    
    private List<FndMenuVO> menuVOs = new ArrayList<>();

	public List<FndRespVO> getRespVOs() {
		return respVOs;
	}

	public void setRespVOs(List<FndRespVO> respVOs) {
		this.respVOs = respVOs;
	}

	public List<FndMenuVO> getMenuVOs() {
		return menuVOs;
	}

	public void setMenuVOs(List<FndMenuVO> menuVOs) {
		this.menuVOs = menuVOs;
	}
	
	*/

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

}