package com.yqh.boot.entity;

import java.util.Date;


public class PhoneLogin {
	
    private Long phoneLoginId;
    private String phone;
    private Long mappingUserId;
    private Integer isDelete;
    private Date createTime;
    private Date lastModifyTime;
    
	public Long getPhoneLoginId() {
		return phoneLoginId;
	}
	public void setPhoneLoginId(Long phoneLoginId) {
		this.phoneLoginId = phoneLoginId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getMappingUserId() {
		return mappingUserId;
	}
	public void setMappingUserId(Long mappingUserId) {
		this.mappingUserId = mappingUserId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
    
    

}