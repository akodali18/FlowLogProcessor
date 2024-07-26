package com.illumio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AWSLogProcessor implements LogProcessor{
	

	/**
	 * 
	 * Processes flow log data from AWS cloud provider.
	 * 
	 * Constraints:
	 * flow log file size is < 10MB
	 * lookUpFile size is  < 10k lines (approximately 10k * 20 char/bytes = 200k bytes ~= 0.2 MB)
	 * 
	 * The size of these files should fit in memory. Note that this won't handle large log files.
	 * 
	 * @throws IOException 
	 */
	@Override
	public void processLogs(String flowLogFilePath, String lookUpTableFilePath) throws IOException {
		Map<LookUpPair, String> lookUpMap = Utils.processLookUpMap(lookUpTableFilePath);
		
		Map<String, Integer> tagCountMap = new HashMap<>();
		Map<LookUpPair, Integer> lookUpPairCountMap = new HashMap<>();
		
		// Read logs from aws flow log file.
		try (BufferedReader br = new BufferedReader(new FileReader(flowLogFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
			    String[] columns = line.split(",");
			    String dstPort = columns[6]; 
			    String protocol = columns[7]; 
			    // Convert protocol to lowercase to do a case insensitive comparison. 
			    LookUpPair logInputPair = new LookUpPair(dstPort, protocol.toLowerCase());			   
			    String tag = lookUpMap.getOrDefault(logInputPair, "Untagged");;
			    // Add to output TagCount map
			    tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
			    
			    // Add to output port+protocol combination count
			    lookUpPairCountMap.put(logInputPair, lookUpPairCountMap.getOrDefault(logInputPair, 0) + 1);
			}
		}
		
		exportToFile(tagCountMap, lookUpPairCountMap);
		return;
	}

	private void exportToFile(Map<String, Integer> tagCountMap, Map<LookUpPair, Integer> lookUpPairCountMap) throws IOException {
		// Create a new file and write to it.
		File file = new File("src/output.txt");
		file.createNewFile();
		 try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			 writer.write("Tag     Count " + "\n");
			 for(Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
				StringBuilder rightPadSpaces = new StringBuilder();
				//append 2 spaces by default.
				for(int i=0; i< 10 - entry.getKey().length(); i++) {
					rightPadSpaces.append(" ");
				}
				 writer.write(entry.getKey() + rightPadSpaces.toString() + entry.getValue()+ "\n");
			 }
			 writer.write("\n\n" + "Port Protocol Count " + "\n");
			 for(Map.Entry<LookUpPair, Integer> entry : lookUpPairCountMap.entrySet()) {
				 writer.write(entry.getKey() + "  " + entry.getValue()+ "\n");
			 }
		 }
	}

}
