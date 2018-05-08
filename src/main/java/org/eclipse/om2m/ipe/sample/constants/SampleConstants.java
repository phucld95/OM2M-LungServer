package org.eclipse.om2m.ipe.sample.constants;

import org.eclipse.om2m.commons.constants.Constants;

public class SampleConstants {
	
	private SampleConstants(){}
	public static final String LUNG_SERVER_URL ="http://192.168.1.121:8001";
	public static final String DB_NAME ="YEUHIEN";
	public static final String TB_USER ="User";
	public static final String TB_RECORD ="Record";
	public static final String DB_SERVER = "mongodb://localhost:12345";
	public static final String FFT_BASE_URL = "http://192.168.1.121:8002";
	public static final String POLI_URL = LUNG_SERVER_URL + "/lung-function/api/get-values/";
	public static final String FFT_URL = FFT_BASE_URL + "/lung-audio-data/api/get-values/";
	public static final String POA = "sample";
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "AE_IPE_SAMPLE";
	public static final String QUERY_STRING_OP = "op";
	
	public static String CSE_ID = "/" + Constants.CSE_ID;
	public static String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
}
