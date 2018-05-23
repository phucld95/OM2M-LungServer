package org.eclipse.om2m.ipe.sample.constants;

import org.eclipse.om2m.commons.constants.Constants;

public class SampleConstants {
	
	private SampleConstants(){}
	public static final String SERVER_URL ="http://localhost";
	public static final String POLI_SERVER_URL = SERVER_URL + ":8001";
	public static final String FFT_SERVER_URL = SERVER_URL + ":8002";
	public static final String DB_NAME ="YEUHIEN";
	public static final String TB_USER ="User";
	public static final String TB_RECORD ="Record";
	public static final String TB_FOLLOW ="Follow";
	public static final String DB_SERVER = "mongodb://localhost:12345";
	public static final String FFT_BASE_URL = FFT_SERVER_URL + "/lung-audio-data/api/get-values/";
	public static final String POLI_BASE_URL = POLI_SERVER_URL + "/lung-function/api/get-values/";
	public static final String POA = "sample";
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "AE_IPE_SAMPLE";
	public static final String QUERY_STRING_OP = "op";
	
	public static String CSE_ID = "/" + Constants.CSE_ID;
	public static String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
}
