package org.eclipse.om2m.ipe.sample.model;

public class Device {
    public final static String LOCATION = "Home";
    public final static String TYPE = "ANDROID_APP";
    private boolean state = false;
    private String deviceId;
    
    public Device(String deviceId, boolean initState){
    	this.deviceId = deviceId;
    	this.state = initState;
    }
    
	/**
	 * @return the state
	 */
	public boolean getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the lampId to set
	 */
	public void setLampId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
