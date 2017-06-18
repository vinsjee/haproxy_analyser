package com.vinay.hapanalyser.main;

import java.io.IOException;
import java.util.Scanner;

import com.vinay.hapanalyser.core.HAPLogsParser;

public class HAPLogAnalyser {
	
	
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
	    try{ 
	     System.out.println("File(s) to be analysed-In case of multiple file please provide the comma separated file name");
	     String[] filesToBeAnalysed = sc.next().split(",");
	     System.out.println("URL to be analysed");
	     String urlToBeAnalsyed = sc.next();
	     System.out.println("Expected response time - This will provide the results of how many request were withing this reponse time");
	     String expectedResponseTime = sc.next();
	     HAPLogsParser hapLogsParser = new HAPLogsParser(filesToBeAnalysed, urlToBeAnalsyed, expectedResponseTime);
	     hapLogsParser.startProcessingAndPrintData();
	    }finally{
	    	sc.close();
	    }
	
	}

}
