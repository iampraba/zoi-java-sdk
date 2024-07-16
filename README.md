# Zoho Office Integrator Java SDK Examples

## Table Of Contents

* [Overview](#overview)
* [Environmental Setup](#environmental-setup)
* [Including the SDK in your project](#including-the-sdk-in-your-project)
* [How to run the sample code?](#how-to-run-the-sample-code)

## Overview

This code repository showcases examples that illustrate the utilization of the Java SDK for Zoho Office Integrator in order to engage with Office Suite Editors.

## Environmental Setup

JAVA SDK requires Java (version 8 and above) to be set up in your development environment.

## Including the SDK in your project

Java SDK is included in this project through Maven distribution. You can include the SDK to your project using:

1. **Maven**

    - Configure Office Integrator SDK using following snippet in your project pom.xml file.

        ```xml
        <repositories>
            <repository>
                <id>zoi-java-sdk</id>
                <url>https://maven.zohodl.com</url>
            </repository>
        </repositories>
        <dependencies>
            <dependency>
                <groupId>com.zoho.officeintegrator</groupId>
                <artifactId>zoi-java-sdk</artifactId>
                <version>1.0.2</version>
            </dependency>
        </dependencies>
        ```

2. **Gradle**
    
    - Configure Office Integrator SDK using following snippet in your project gradle configuration file.

        ```gradle
        repositories{
            maven { url "https://maven.zohodl.com" }
        }
        dependencies{
            implementation 'com.zoho.officeintegrator:zoi-java-sdk:1.0.2'
        }
        ```

3. **Download and Bundle SDK Jar in your project**
   
   * [zoi-java-sdk-1.0.2.jar](https://maven.zohodl.com/com/zoho/officeintegrator/zoi-java-sdk/1.0.2/zoi-java-sdk-1.0.2.jar) - Please download this jar and include along with the following dependency jars in your project to run Office Integrator java sdk on your application.

        ### -  Dependency JARs

        * [commons-lang3-3.9.jar](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.9)

        * [commons-logging-1.1.3.jar](https://mvnrepository.com/artifact/commons-logging/commons-logging/1.1.3)

        * [commons-fileupload-1.4.jar](https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload/1.4)

        * [commons-io-1.3.2.jar](https://mvnrepository.com/artifact/commons-io/commons-io/1.3.2)

        * [httpclient-4.4.1.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.4.1)

        * [httpcore-4.4.4.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore/4.4.4)

        * [httpmime-4.5.3.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime/4.5.3)

        * [mysql-connector-java-8.0.27.jar](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.27)

        * [json-20170516.jar](https://mvnrepository.com/artifact/org.json/json/20170516)

        * [opencsv-5.9.jar](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.9)

        * [javax.servlet-api-4.0.1.jar](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api/4.0.1)

        * [servlet-api-2.5.jar](https://mvnrepository.com/artifact/javax.servlet/servlet-api/2.5)

        * [junit.jar](https://mvnrepository.com/artifact/junit/junit)

## **How to run the sample code?**

### To run from Mac Terminal
   - Clone this project using following command.
   ```sh
   git clone https://github.com/iampraba/zoi-java-sdk-examples.git
   ```
   - Go to project folder

   ```sh
   cd zoi-java-sdk-example
   ```
   - Run following following command to download dependencies and complete the sample code

   ```sh
   mvn compile
   ```
   - Run your sample code

   ```sh
   mvn exec:java -Dexec.mainClass=<Main class name with package name>
   ```

   e.g to run create document sample code

   ```sh
   mvn exec:java -Dexec.mainClass=com.zoho.officeintegrator.v1.examples.writer.CreateDocument
   ```
 
### To run from your favourite java supported IDE

- Clone this project using following command.

   ```sh
   git clone https://github.com/iampraba/zoi-java-sdk-examples.git
   ```

- Open above cloned project in your IDE, download dependencies mentioned in maven pom.xml and run the sample java code
