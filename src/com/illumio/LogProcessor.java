package com.illumio;

import java.io.IOException;

/**
 * The LogProcessor interface defines contracts for all Log Processors.
 */
public interface LogProcessor {
    /**
     * Processes the log file and generates log summaries and statistics.
     * @throws IOException 
     * 
     * @param flowLogFilePath the file path of the log file to process.
     * @param lookUpTableFilePath the file path of the log file to process.
     */
	void processLogs(String flowLogFilePath, String lookUpTableFilePath) throws IOException;
	
}
