package com.vinay.hapanalyser.main;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import com.vinay.hapanalyser.core.HAPLogsParser;
import com.vinay.hapanalyser.core.ServerParsedData;

public class HAPLogAnalyser {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println(
					"File(s) to be analysed-In case of multiple file please provide the comma separated file name");
			String[] filesToBeAnalysed = sc.next().split(",");
			LinkedList<String> listOfFilesToBeAnalysed = new LinkedList<String>(asList(filesToBeAnalysed));
			System.out.println("URL to be analysed");
			String urlToBeAnalsyed = sc.next();
			System.out.println(
					"Expected response time - This will provide the results of how many request were withing this reponse time");
			String expectedResponseTime = sc.next();
			ForkJoinPool forkJoinPool = new ForkJoinPool();
			HAPLogsParser hapLogsParser = new HAPLogsParser(listOfFilesToBeAnalysed, urlToBeAnalsyed,
					expectedResponseTime, new ConcurrentHashMap<String,ServerParsedData>());
			forkJoinPool.invoke(hapLogsParser);
			printData(hapLogsParser);
			forkJoinPool.shutdown();
		} finally {
			sc.close();
		}

	}

	private static void printData(HAPLogsParser hapLogsParser) {
		ConcurrentHashMap<String, ServerParsedData> serverToServedDataMap = hapLogsParser.getServerToServedDataMap();
		List<ServerParsedData> values = new ArrayList<ServerParsedData>(serverToServedDataMap.values());
		Collections.sort(values);
		System.out.println(ServerParsedData.headings());
		for (ServerParsedData serverParsedData : values) {
			serverParsedData.setP95ClientToHAP(calculate95Percentile(serverParsedData.getTimeTakenFromClientToHAP()));
			serverParsedData.setP95HAPQueue((calculate95Percentile(serverParsedData.getTimeTakenInHAPQueues())));
			serverParsedData
					.setP95HAPToServer(calculate95Percentile(serverParsedData.getTimeTakenForConnectionToServer()));
			serverParsedData
					.setP95ServerResponse(calculate95Percentile(serverParsedData.getTimeTakenFromServerToHAP()));
			serverParsedData.setP95TotResponse(calculate95Percentile(serverParsedData.getTotalResponseTime()));
			List<String> dates = serverParsedData.getDates();
			Collections.sort(dates);
			serverParsedData.setStartTime(dates.get(0));
			serverParsedData.setEndTime(dates.get(dates.size() - 1));
			System.out.println(serverParsedData);

		}

	}

	private static long calculate95Percentile(List<Long> timeTakenList) {
		Collections.sort(timeTakenList);
		int size = timeTakenList.size();
		int index = (int) (size * 0.95);
		timeTakenList.get(index);
		return timeTakenList.get(index);
	}

}
