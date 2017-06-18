package com.vinay.hapanalyser.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

public class HAPLogsParser extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<String> filesToBeAnalysed;
	private String urlToBeAnalsyed;
	private String expectedResponseTime;
	private ConcurrentHashMap<String, ServerParsedData> serverToServerDataMap;

	public HAPLogsParser(LinkedList<String> listOfFilesToBeAnalysed, String urlToBeAnalsyed,
			String expectedResponseTime, ConcurrentHashMap<String, ServerParsedData> serverToServerDataMap) {
		this.filesToBeAnalysed = listOfFilesToBeAnalysed;
		this.urlToBeAnalsyed = urlToBeAnalsyed;
		this.expectedResponseTime = expectedResponseTime;
		this.serverToServerDataMap=serverToServerDataMap;
	}

	public LinkedList<String> getFilesToBeAnalysed() {
		return filesToBeAnalysed;
	}

	public String getUrlToBeAnalsyed() {
		return urlToBeAnalsyed;
	}

	public String getExpectedResponseTime() {
		return expectedResponseTime;
	}

	// public void startProcessingAndPrintData() throws IOException {
	// System.out.println(ServerParsedData.headings());
	// for (String fileName : filesToBeAnalysed) {
	// BufferedReader fileReader = new BufferedReader(new FileReader(new
	// File(fileName)));
	// try {
	// String hapLog;
	// while ((hapLog = fileReader.readLine()) != null) {
	// String[] hapSplittedLog = hapLog.split(" ");
	//
	// if (hapSplittedLog.length < 23) {
	// continue;
	// }
	// if (!hapSplittedLog[21].contains(getUrlToBeAnalsyed())) {
	// continue;
	// }
	//
	// String serverName = hapSplittedLog[9];
	// String[] responseTimeData = hapSplittedLog[10].split("/");
	// String requestDate = new
	// StringBuilder(hapSplittedLog[2]).append(hapSplittedLog[1]).append("-")
	// .append(hapSplittedLog[3]).toString();
	// String httpMethodName = hapSplittedLog[20];
	//
	// ServerParsedData serverParsedData;
	// if (serverToServedDataMap.containsKey(serverName)) {
	// serverParsedData = serverToServedDataMap.get(serverName);
	// } else {
	// serverParsedData = new ServerParsedData();
	// serverParsedData.setServerName(serverName);
	// serverToServedDataMap.put(serverName, serverParsedData);
	// }
	// serverParsedData.setTotalRequests(serverParsedData.getTotalRequests() +
	// 1);
	// if (Long.valueOf(responseTimeData[4]) <=
	// Long.valueOf(getExpectedResponseTime())) {
	// serverParsedData.setTotRequestsWitinExpectedTime(
	// serverParsedData.getTotRequestsWitinExpectedTime() + 1);
	// }
	// serverParsedData.getDates().add(requestDate);
	// serverParsedData.getTimeTakenFromClientToHAP().add(Long.valueOf(responseTimeData[0]));
	// serverParsedData.getTimeTakenInHAPQueues().add(Long.valueOf(responseTimeData[1]));
	// serverParsedData.getTimeTakenForConnectionToServer().add(Long.valueOf(responseTimeData[2]));
	// serverParsedData.getTimeTakenFromServerToHAP().add(Long.valueOf(responseTimeData[3]));
	// serverParsedData.getTotalResponseTime().add(Long.valueOf(responseTimeData[4]));
	// if (httpMethodName.contains("PUT")) {
	// serverParsedData.setTotPUT(serverParsedData.getTotPUT() + 1);
	// }
	// if (httpMethodName.contains("GET")) {
	// serverParsedData.setTotGET(serverParsedData.getTotGET() + 1);
	// }
	// if (httpMethodName.contains("POST")) {
	// serverParsedData.setTotPOST(serverParsedData.getTotPOST() + 1);
	// }
	// if (httpMethodName.contains("DELETE")) {
	// serverParsedData.setTotDELETE(serverParsedData.getTotDELETE() + 1);
	// }
	// }
	//
	// } finally {
	// fileReader.close();
	// }
	// }
	//
	// List<ServerParsedData> values = new
	// ArrayList<ServerParsedData>(serverToServedDataMap.values());
	// Collections.sort(values);
	// for (ServerParsedData serverParsedData : values) {
	// serverParsedData.setP95ClientToHAP(calculate95Percentile(serverParsedData.getTimeTakenFromClientToHAP()));
	// serverParsedData.setP95HAPQueue((calculate95Percentile(serverParsedData.getTimeTakenInHAPQueues())));
	// serverParsedData
	// .setP95HAPToServer(calculate95Percentile(serverParsedData.getTimeTakenForConnectionToServer()));
	// serverParsedData
	// .setP95ServerResponse(calculate95Percentile(serverParsedData.getTimeTakenFromServerToHAP()));
	// serverParsedData.setP95TotResponse(calculate95Percentile(serverParsedData.getTotalResponseTime()));
	// List<String> dates = serverParsedData.getDates();
	// Collections.sort(dates);
	// serverParsedData.setStartTime(dates.get(0));
	// serverParsedData.setEndTime(dates.get(dates.size() - 1));
	// System.out.println(serverParsedData);
	// }
	//
	// }

	// private long calculate95Percentile(List<Long> timeTakenList) {
	// Collections.sort(timeTakenList);
	// int size = timeTakenList.size();
	// int index = (int) (size * 0.95);
	// timeTakenList.get(index);
	// return timeTakenList.get(index);
	// }

	@Override
	protected void compute() {

		String fileToBeAnalysed = getFilesToBeAnalysed().poll();
		if (getFilesToBeAnalysed().size() >= 1) {
			HAPLogsParser hapLogsParser = new HAPLogsParser(getFilesToBeAnalysed(), getUrlToBeAnalsyed(),
					getExpectedResponseTime(),getServerToServedDataMap());
			hapLogsParser.fork();
			//hapLogsParser.join();
		}
		try {
			startProcessingData(fileToBeAnalysed);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ConcurrentHashMap<String, ServerParsedData> getServerToServerDataMap() {
		return serverToServerDataMap;
	}

	private void startProcessingData(String fileToBeAnalysed) throws NumberFormatException, IOException {
		BufferedReader fileReader = new BufferedReader(new FileReader(new File(fileToBeAnalysed)));
		try {
			String hapLog;
			while ((hapLog = fileReader.readLine()) != null) {
				String[] hapSplittedLog = hapLog.split(" ");

				if (hapSplittedLog.length < 23) {
					continue;
				}
				if (!hapSplittedLog[21].contains(getUrlToBeAnalsyed())) {
					continue;
				}

				String serverName = hapSplittedLog[9];
				String[] responseTimeData = hapSplittedLog[10].split("/");
				String requestDate = new StringBuilder(hapSplittedLog[2]).append(hapSplittedLog[1]).append("-")
						.append(hapSplittedLog[3]).toString();
				String httpMethodName = hapSplittedLog[20];

				ServerParsedData serverParsedData;
				if (serverToServerDataMap.containsKey(serverName)) {
					serverParsedData = serverToServerDataMap.get(serverName);
				} else {
					serverParsedData = new ServerParsedData();
					serverParsedData.setServerName(serverName);
					serverToServerDataMap.put(serverName, serverParsedData);
				}
				serverParsedData.setTotalRequests(serverParsedData.getTotalRequests() + 1);
				if (Long.valueOf(responseTimeData[4]) <= Long.valueOf(getExpectedResponseTime())) {
					serverParsedData
							.setTotRequestsWitinExpectedTime(serverParsedData.getTotRequestsWitinExpectedTime() + 1);
				}
				serverParsedData.getDates().add(requestDate);
				serverParsedData.getTimeTakenFromClientToHAP().add(Long.valueOf(responseTimeData[0]));
				serverParsedData.getTimeTakenInHAPQueues().add(Long.valueOf(responseTimeData[1]));
				serverParsedData.getTimeTakenForConnectionToServer().add(Long.valueOf(responseTimeData[2]));
				serverParsedData.getTimeTakenFromServerToHAP().add(Long.valueOf(responseTimeData[3]));
				serverParsedData.getTotalResponseTime().add(Long.valueOf(responseTimeData[4]));
				if (httpMethodName.contains("PUT")) {
					serverParsedData.setTotPUT(serverParsedData.getTotPUT() + 1);
				}
				if (httpMethodName.contains("GET")) {
					serverParsedData.setTotGET(serverParsedData.getTotGET() + 1);
				}
				if (httpMethodName.contains("POST")) {
					serverParsedData.setTotPOST(serverParsedData.getTotPOST() + 1);
				}
				if (httpMethodName.contains("DELETE")) {
					serverParsedData.setTotDELETE(serverParsedData.getTotDELETE() + 1);
				}
			}

		} finally {
			fileReader.close();
		}

	}

	public ConcurrentHashMap<String, ServerParsedData> getServerToServedDataMap() {
		return this.serverToServerDataMap;
	}

}
