package com.illumio;

import java.util.Objects;

public class LookUpPair {
	String dstPort;
	String protocol;
	
	public LookUpPair(String dstPort, String protocol) {
		this.dstPort = dstPort;
		this.protocol = protocol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dstPort, protocol);
	}

	@Override
	public boolean equals(Object object) {
		LookUpPair otherPair = (LookUpPair) object;
		return this.dstPort.equalsIgnoreCase(otherPair.dstPort) && this.protocol.equalsIgnoreCase(otherPair.protocol);
	}

	@Override
	public String toString() {
		StringBuilder rightPadSpaces = new StringBuilder();
		//append 2 spaces by default.
		rightPadSpaces.append("  ");
		if (this.dstPort.length() < 5) {
			int length = 5 - this.dstPort.length();
			for(int i=0; i < length; i++) {
				rightPadSpaces.append(" ");
			}
		}
		//Right pad spaces to align data in text file.
		return this.dstPort + rightPadSpaces.toString() + this.protocol + "    ";
	}
}
