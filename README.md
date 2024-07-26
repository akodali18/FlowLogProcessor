# Build and Run:


#### Install JDK or JRM

1. To install JDK using Homebrew:

```
brew install openjdk
```

2. Verify javac and java are installed 

```
javac -version
java -version
```

#### Compile and Run the program
```
javac -d out -sourcepath src src/com/illumio/*.java
java -cp out com.illumio.Main
```

#### Compile and Run the test
```
javac -d out -sourcepath src src/com/illumio/*.java src/test/*.java
java -cp out test/AWSLogProcessorTest
```

#### Project structure
src/com/illumio/ -> Main application files
src/test -> Unit test files
src/resources/* -> input flow log and look up table files.
src/output.txt -> output file

#### Description
This is a program that can parse a file containing flow log data and maps each row to a tag based on a lookup table. The lookup table is defined as a csv file, and it has 3 columns, dstport,protocol,tag.   The dstport and protocol combination decide what tag can be applied.   



#### Thoughts and Assumptions
* I chose to have a simple Java project for this task instead of creating it as a Maven project.
* For the rest of the project, I have tried to incorporate comments in the code to improve readability and to serve as the source of truth.
* Test Matrix can be found in the test file at src/test/AWSLogProcessorTest.java and I have limited the unit testing to the main log processor task.
* Based on the given constraints of flow log file and look up files, they seem small enough to be read in memory BufferedReader.
* Flow Log File:
The flow log file is a plain text file containing flow log records. Each record is a comma-separated value (CSV) line with at least dstport and protocol fields, found at index 6 and 7 respectively.
* Lookup Table File:
The lookup table file is a plain text CSV with columns: dstport, protocol, tag. So dstport and protocol fields are found at index 0 and 1 respectively.
* Output file location is "src/output.txt"
* I have initiated the look up inside processLogs for ease of task. Usually it would be passed in as a parameter to AWSLogProcessor during instantiation or via dependency injection.
* To polish the output i have right padded spaces to some columns while writing to file.
* The AWSLogProcessor is a singleton class assuming that look up file is the same for a given cloud provider.

Note: you will see commits from "MS". That is just my home's macbook name. As i set up git with an ssh key (ms@MSs-MacBook-Pro.local) from my home macbook and git seems to use that for author name.






