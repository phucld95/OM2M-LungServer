package org.eclipse.om2m.ipe.sample.model;

import java.time.LocalDate;
import java.time.Period;

import com.google.gson.JsonElement;

public class Record {
	private String id;
	private String userId;
	private String engCurve;
	private String frmTimes;
	private String PEF;
	private String FVC;
	private String FEV1;
	private String flowCurve;
	private String volumes;
	private String time;
	private String pred_PEF;
	private String pred_FEV1;
	private String pred_FVC;
	private String record;
	
	public Record() {
		
	}
	
	
	public Record(String id, String userId, String engCurve, String frmTimes, 
			String PEF, String FVC, String FEV1, 
			String flowCurve, String volumes, String time,
			String pred_PEF, String pred_FEV1, String pred_FVC,
			String record){
    	this.id = id;
    	this.userId = userId;
    	this.engCurve = engCurve;
    	this.frmTimes = frmTimes;
    	this.PEF = PEF;
    	this.FVC = FVC;
    	this.FEV1 = FEV1;
    	this.flowCurve = flowCurve;
    	this.volumes = volumes;
    	this.time = time;
    	this.pred_PEF = pred_PEF;
    	this.pred_FEV1 = pred_FEV1;
    	this.pred_FVC = pred_FVC;
    	this.record = record;
	}
	
	public static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, today).getYears();
        } else {
            return 0;
        }
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getEngCurve() {
		return engCurve;
	}


	public void setEngCurve(String engCurve) {
		this.engCurve = engCurve;
	}


	public String getFrmTimes() {
		return frmTimes;
	}


	public void setFrmTimes(String frmTimes) {
		this.frmTimes = frmTimes;
	}


	public String getPEF() {
		return PEF;
	}


	public void setPEF(String pEF) {
		PEF = pEF;
	}


	public String getFVC() {
		return FVC;
	}


	public void setFVC(String fVC) {
		FVC = fVC;
	}


	public String getFEV1() {
		return FEV1;
	}


	public void setFEV1(String fEV1) {
		FEV1 = fEV1;
	}


	public String getFlowCurve() {
		return flowCurve;
	}


	public void setFlowCurve(String flowCurve) {
		this.flowCurve = flowCurve;
	}


	public String getVolumes() {
		return volumes;
	}


	public void setVolumes(String volumes) {
		this.volumes = volumes;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getPred_PEF() {
		return pred_PEF;
	}


	public void setPred_PEF(String pred_PEF) {
		this.pred_PEF = pred_PEF;
	}


	public String getPred_FEV1() {
		return pred_FEV1;
	}


	public void setPred_FEV1(String pred_FEV1) {
		this.pred_FEV1 = pred_FEV1;
	}


	public String getPred_FVC() {
		return pred_FVC;
	}


	public void setPred_FVC(String pred_FVC) {
		this.pred_FVC = pred_FVC;
	}


	public String getRecord() {
		return record;
	}


	public void setRecord(String record) {
		this.record = record;
	}
}
