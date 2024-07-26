package com.illumio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.illumio.LogProcessorFactory.CloudProvider;

public class Main {
	static Logger log = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		 LogProcessor awsLogProcessor = LogProcessorFactory.create(CloudProvider.AWS);
		 try {
			awsLogProcessor.processLogs("src/resources/AWSFlowLog.txt", "src/resources/LookUpFile.csv");
		} catch (FileNotFoundException e) {
			log.log(Level.WARNING, "Error : file not found.");
		    e.printStackTrace(); 
		} catch (IOException e) {
			log.log(Level.WARNING, "Error reading file.");
			e.printStackTrace();
		}
		 log.info("Thank you for checking out our Log Parser, Output file can be found at : src/output.txt "
		 		+ "(or use the command -> cat src/output.txt)");
	}
}