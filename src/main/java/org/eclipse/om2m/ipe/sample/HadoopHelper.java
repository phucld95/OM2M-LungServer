package org.eclipse.om2m.ipe.sample;

import java.io.File;

public class HadoopHelper {

	public void run(String filename, String content) throws Exception {
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "writeHdfs-0.0.1-SNAPSHOT-jar-with-dependencies.jar", filename, content);
		pb.directory(new File("/home/lephuc/eclipse-workspace/writeHdfs/target"));
		Process p = pb.start();
		System.out.println("Save file success!");
	  }
}
