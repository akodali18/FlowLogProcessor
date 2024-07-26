package com.illumio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Helper Class
 * 
 */
public class Utils {

	/**
	 * Helper method to process the input look up map.
	 * 
	 * @param lookUpTableFilePath the file that contains look up info.
	 * 
	 * @return A Map that contains Pair<port, protocol> -> tag info.
	 */
	public static Map<LookUpPair, String> processLookUpMap(String lookUpTableFilePath) throws IOException {
		Map<LookUpPair, String> lookUpMap = new HashMap<>();
		
		// Read look up file.
		try (BufferedReader br = new BufferedReader(new FileReader(lookUpTableFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				
				// Example format: 24,udp,sv_p1  (port, protocol, tag) 
			    String[] columns = line.trim().split(",");
			    String dstPort = columns[0];
			    String protocol = columns[1];
			    String tag = columns[2];
			    LookUpPair pair = new LookUpPair(dstPort, protocol);
			    lookUpMap.put(pair, tag);
			}
		}
        return lookUpMap;
	}
}
