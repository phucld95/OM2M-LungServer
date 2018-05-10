package org.eclipse.om2m.ipe.sample;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.net.URI;
import java.util.logging.Logger;

public class HadoopConnecter {

	public void saveData() throws Exception  {
		Logger logger = Logger.getLogger("io.saagie.example.hdfs.Main");
		String hdfsuri = "hdfs://localhost:9000";
		String path="/hello";
		String fileName="chaydima";
		String fileContent="hello;world";
		 
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsuri);
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		System.setProperty("hadoop.home.dir", "/");
		FileSystem fs;
		fs = FileSystem.get(URI.create(hdfsuri), conf);
		Path workingDir=fs.getWorkingDirectory();
	    Path newFolderPath= new Path(path);
	    if(!fs.exists(newFolderPath)) {
	    	// Create new Directory
	    	fs.mkdirs(newFolderPath);
	    }
	    logger.info("Begin Write file into hdfs");
	    Path hdfswritepath = new Path(newFolderPath + "/" + fileName);
	    FSDataOutputStream outputStream=fs.create(hdfswritepath);
	    outputStream.writeBytes(fileContent);
	    outputStream.close();
	    logger.info("End Write file into hdfs");

	    logger.info("Read file into hdfs");
	    Path hdfsreadpath = new Path(newFolderPath + "/" + fileName);
	    FSDataInputStream inputStream = fs.open(hdfsreadpath);
	    String out= IOUtils.toString(inputStream, "UTF-8");
	    logger.info(out);
	    inputStream.close();
	    fs.close();
	}
}
