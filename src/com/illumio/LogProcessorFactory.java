package com.illumio;

public class LogProcessorFactory {

	/**
	 * The supported cloud providers for which logs can be processed.s
	 */
	public enum CloudProvider {
	    AWS,
	}
	
	/**
	 * Factory to build desired log processor.
	 */
	public static LogProcessor create(CloudProvider type) {
		switch(type) {
			case AWS:
				return new AWSLogProcessor();
			default:
				throw new IllegalArgumentException("Unsupported Log type");
		}
	}

}