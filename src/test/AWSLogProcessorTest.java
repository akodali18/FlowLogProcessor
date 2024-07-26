package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.illumio.LogProcessor;
import com.illumio.LogProcessorFactory;
import com.illumio.LogProcessorFactory.CloudProvider;

public class AWSLogProcessorTest {

	private LogProcessor awsLogProcessor;
	
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Assertion failed: Please check the error stack trace for more details ");
        }
    }
    
    public static void main(String[] args) {
        AWSLogProcessorTest test = new AWSLogProcessorTest();
        
        test.setUp();
        
        try {
        	test.testProcessLogs();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        try {
        	test.testInvalidInputFile();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        System.out.println("All tests completed.");
    }

    /*
     * Tests for invalid file input. Other validations of input file can be similarly tested.
     * 
     */
	private void testInvalidInputFile() throws IOException {
		try {
			awsLogProcessor.processLogs("NonExistentAWSFlowLog.txt", "src/resources/LookUpFile.csv");
		} catch (FileNotFoundException e) {
			 System.out.println("testInvalidInputFile PASS.");
		}
	}

	private void setUp() {
    	awsLogProcessor = LogProcessorFactory.create(CloudProvider.AWS);
    }
    
	/*
	 * Test successful execution. Does the below tests as well: 
	 * 1. Test tag with no mapping i.e. "Untagged"
	 * 2. Test Case Insensitive comparison between protocol in flow log and protocol in look up table.
	 * Ideally these would be all separate tests, combining for the sake of time.
	 * 
	 * 3. Test when any input file doesn't exist.
	 */
    private void testProcessLogs() throws IOException {
    	awsLogProcessor.processLogs("src/resources/AWSFlowLog.txt", "src/resources/LookUpFile.csv");

        // Check the output file
        Path outputPath = Paths.get("src/output.txt");
        assertTrue(Files.exists(outputPath));

        // Validate contents
        String outputContent = new String(Files.readAllBytes(outputPath));
        assertTrue(outputContent.contains("Tag     Count"));
        assertTrue(outputContent.contains("sv_P1     2"));
        assertTrue(outputContent.contains("sv_P2     3"));
        assertTrue(outputContent.contains("SV_P3     1"));
        assertTrue(outputContent.contains("Port Protocol Count"));
        assertTrue(outputContent.contains("22     tcp      1"));
        assertTrue(outputContent.contains("68     udp      1"));
        assertTrue(outputContent.contains("23     tcp      1"));
        assertTrue(outputContent.contains("443    tcp      2"));
        assertTrue(outputContent.contains("31     udp      1"));
        assertTrue(outputContent.contains("53     udp      1"));
        assertTrue(outputContent.contains("80     tcp      1"));
        assertTrue(outputContent.contains("123    udp      1"));
        
        //Tests "Untagged" tag
        assertTrue(outputContent.contains("Untagged  4"));
        
        
        // Note:: that input was 25, TCP, however assuming here that converting
        //  and persisting in lower case is good for functional purposes. 
        assertTrue(outputContent.contains("25     tcp      1"));
        
        System.out.println("testProcessLogs PASS.");
    }
    
}
