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

#### Thoughts and Assumptions
* I chose to have a simple Java project for this task instead of creating it as a Maven project.
* For the rest of the project, I have tried to incorporate comments in the code to improve readability and to serve as the source of truth.
* Test Matrix can be found in the test file at src/test/AWSLogProcessorTest.java and I have limited the unit testing to the main log processor task.
* Based on the given constraints of flow log file and look up files, they seem small enough to be read in memory BufferedReader.






