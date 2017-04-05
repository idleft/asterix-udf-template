# User Defined Function template for AsterixDB
This is a minimum User Defined Function (UDF) template for AsterixDB. 

### Prerequisites
* Successfully build and install (via `mvn install`) AsterixDB locally.
 (See [AsterixDB Dependecies](#asterixdb-dependencies)
for details)

### How To Use
* Clone this repo onto your local machine.
* Build (`mvn install` or `mvn package`).
* The UDF package will be under `target/` directory.

### AsterixDB Dependencies
The dependency to AsterixDB is specified in pom.xml as follow:
```
<dependency>
    <groupId>org.apache.asterix</groupId>
    <artifactId>asterix-external-data</artifactId>
    <version>${asterixdb-verson}</version>
    <scope>provided</scope>
</dependency>
```
There are two ways to enable Maven to pick up AsterixDB depencies: 
  * Local Maven directory (Recommended): In this case, we will need to build AsterixDB locally
  and use `mvn install` to add all
  AsterixDB library into the local maven directory so it can be used by other projects.
  * Remote Maven Repo (Not Recommended): You can also try to add AsterixDB dependencies from remote Maven repo. Since 
  remote repo is not always up-to-date, it is recommended to use the local maven repo. If you want to use remote one,
  you will need to change the `asterixdb-verson` in pom.xml to match the latest version on remote repo.

### Sample UDFs
* Sum of two numbers: 
  * This example migrated from AsterxDB codebase calculates the sum of two given parameters. 
  It takes two integer as parameter and returns one integer as result.
* Simple sentiment analysis
  * This function appends a extra field `Sentiment` to the inputed record. An sample AQL to 
  test this function is as below:
 ```
 use dataverse test;

create type Tweet as open {
	"id" : string,
	"text": string
}

create type AnnotatedTweet as open {
	"id": string,
	"text": string,
	"Sentiment": string
}

let $t := {"id":"1", "text":"123123"}
return testlib#getSentiment($t)
```

* Text Encryption
  * This example shows how to use other libraries with UDF. It takes two string parameters, 
  text to be encrypted and encryption key, and it returns with the encrypted text. To run this
  example, you will need to configure the dependencies for UDF correctly. We will elaborate more
  on this in next section.

### Dependencies from UDFs
AsterxDB allows UDF to use external libraries. There are two ways to resolve the dependencies of the 
external libraries.
* **Option 1**: Single jar with dependencies
  * You can choose to include all the dependencies needed into your UDF jar, i.e. create a fat jar with
  all dependencies. In this way, when you install the UDF package, all dependencies will also
  be installed onto AsterixDB. The negative effect of this is, if the size of exteral library is big, this
  will cause the starting time becomes extremely long. Thus this is not recommended if you have many/large
  external libraries. To compile a fat jar, you need to add following configuration into the pom.xml, before
  the <execution> section.
  ```
  ...
  <execution>
    <id>jar-with-dependencies</id>
    <phase>package</phase>
    <goals>
        <goal>single</goal>
    </goals>
    <configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
  </execution>
  <execution>
  ...
  ```
* **Option 2**: Add dependencies to AsterixDB repo
  * This method utilized the class loading mechanism of AsterixDB. You will need to unzip the asterix-server-0.9.0-binary-assembly.zip
  under asterix/, and drop your librareis into asterix-server-0.9.0-binary-assembly/repo. Repack this directory into zip, and replace
  the old one under asterix/. **Note**, make sure the directory structure of the zip file is not nested. You should see following 
  structure when executing `unzip -l asterix-server-0.9.0-binary-assembly.zip`.
  
  ```
  Archive:  asterix-server-0.9.0-binary-assembly.zip
  Length      Date    Time    Name
  
        0  01-19-2017 14:15   bin/
     3765  01-18-2017 19:05   bin/asterixcc
     3327  01-18-2017 19:05   bin/asterixcc.bat
     4107  01-18-2017 19:04   bin/asterixhelper
     3331  01-18-2017 19:04   bin/asterixhelper.bat
     3765  01-18-2017 19:05   bin/asterixnc
     ....
  ```
  
  
  
  
  
