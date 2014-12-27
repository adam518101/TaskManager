package com.coal.black.bc.socket.dto;

import java.util.Date;

import com.coal.black.bc.socket.IDtoBase;

public class TaskDto extends IDtoBase {
	private static final long serialVersionUID = 672584624580279518L;
	private Integer id = 0;
	private String bank;
	private String caseID;
	private String name;
	private String identityCard;
	private String bankCard;
	private double caseAmount = 0;
	private double hasPayed = 0;
	private String noticeStatement;
	private String address;
	private String region;
	private Integer visitTimes = 0;
	private String visitReason;
	private String requirement;
	private String memo;
	private String contactInfo;
	private String companyName;
	private String visitReport;
	private Date estimateVisitDate;
	private Date realVisitDate;
	private Date returnTime;
	private Integer realVisitUser = 0;
	private boolean isValid = false;
	private Integer taskStatus = 0;
	private Integer userTaskStatus = 0;
	private Long grantTime = 0L;// 任务授予的时间
	private Long operateTime = 0L;// 任务操作的时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public double getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(double caseAmount) {
		this.caseAmount = caseAmount;
	}

	public double getHasPayed() {
		return hasPayed;
	}

	public void setHasPayed(double hasPayed) {
		this.hasPayed = hasPayed;
	}

	public String getNoticeStatement() {
		return noticeStatement;
	}

	public void setNoticeStatement(String noticeStatement) {
		this.noticeStatement = noticeStatement;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getVisitTimes() {
		return visitTimes;
	}

	public String getVisitReason() {
		return visitReason;
	}

	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVisitReport() {
		return visitReport;
	}

	public void setVisitReport(String visitReport) {
		this.visitReport = visitReport;
	}

	public Date getEstimateVisitDate() {
		return estimateVisitDate;
	}

	public void setEstimateVisitDate(Date estimateVisitDate) {
		this.estimateVisitDate = estimateVisitDate;
	}

	public Date getRealVisitDate() {
		return realVisitDate;
	}

	public void setRealVisitDate(Date realVisitDate) {
		this.realVisitDate = realVisitDate;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getRealVisitUser() {
		return realVisitUser;
	}

	public void setRealVisitUser(Integer realVisitUser) {
		this.realVisitUser = realVisitUser;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getUserTaskStatus() {
		return userTaskStatus;
	}

	public void setUserTaskStatus(Integer userTaskStatus) {
		this.userTaskStatus = userTaskStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Long grantTime) {
		this.grantTime = grantTime;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	public void setVisitTimes(Integer visitTimes) {
		this.visitTimes = visitTimes;
	}

	@Override
	public String toString() {
		return "TaskDto [id=" + id + ", bank=" + bank + ", caseID=" + caseID + ", name=" + name + ", identityCard=" + identityCard + ", bankCard=" + bankCard
				+ ", caseAmount=" + caseAmount + ", hasPayed=" + hasPayed + ", noticeStatement=" + noticeStatement + ", address=" + address + ", region="
				+ region + ", visitTimes=" + visitTimes + ", visitReason=" + visitReason + ", requirement=" + requirement + ", memo=" + memo + ", contactInfo="
				+ contactInfo + ", companyName=" + companyName + ", visitReport=" + visitReport + ", estimateVisitDate=" + estimateVisitDate
				+ ", realVisitDate=" + realVisitDate + ", returnTime=" + returnTime + ", realVisitUser=" + realVisitUser + ", isValid=" + isValid
				+ ", taskStatus=" + taskStatus + ", userTaskStatus=" + userTaskStatus + ", grantTime=" + grantTime + ", operateTime=" + operateTime + "]";
	}
}
