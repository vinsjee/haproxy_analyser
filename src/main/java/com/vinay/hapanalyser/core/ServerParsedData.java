package com.vinay.hapanalyser.core;

import java.util.LinkedList;
import java.util.List;

public class ServerParsedData implements Comparable<ServerParsedData>{
	String serverName;
	List<Long> timeTakenFromClientToHAP = new LinkedList<Long>();
	List<Long> timeTakenInHAPQueues = new LinkedList<Long>();
	List<Long> timeTakenForConnectionToServer = new LinkedList<Long>();
	List<Long> timeTakenFromServerToHAP = new LinkedList<Long>();
	List<Long> totalResponseTime = new LinkedList<Long>();
	List<String> dates = new LinkedList<String>();
	long totalRequests;
	long p95ClientToHAP;
	long p95HAPQueue;
	long p95HAPToServer;
	long p95ServerResponse;
	long p95TotResponse;
	long totGET;
	long totPUT;
	long totPOST;
	long totDELETE;
	long totRequestsWitinExpectedTime;
	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	String startTime;
	String endTime;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public List<Long> getTimeTakenFromClientToHAP() {
		return timeTakenFromClientToHAP;
	}

	public void setTimeTakenFromClientToHAP(List<Long> timeTakenFromClientToHAP) {
		this.timeTakenFromClientToHAP = timeTakenFromClientToHAP;
	}

	public List<Long> getTimeTakenInHAPQueues() {
		return timeTakenInHAPQueues;
	}

	public void setTimeTakenInHAPQueues(List<Long> timeTakenInHAPQueues) {
		this.timeTakenInHAPQueues = timeTakenInHAPQueues;
	}

	public List<Long> getTimeTakenForConnectionToServer() {
		return timeTakenForConnectionToServer;
	}

	public void setTimeTakenForConnectionToServer(List<Long> timeTakenForConnectionToServer) {
		this.timeTakenForConnectionToServer = timeTakenForConnectionToServer;
	}

	public List<Long> getTimeTakenFromServerToHAP() {
		return timeTakenFromServerToHAP;
	}

	public void setTimeTakenFromServerToHAP(List<Long> timeTakenFromServerToHAP) {
		this.timeTakenFromServerToHAP = timeTakenFromServerToHAP;
	}

	public List<Long> getTotalResponseTime() {
		return totalResponseTime;
	}

	public void setTotalResponseTime(List<Long> totalResponseTime) {
		this.totalResponseTime = totalResponseTime;
	}

	public long getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public long getP95ClientToHAP() {
		return p95ClientToHAP;
	}

	public void setP95ClientToHAP(long p95ClientToHAP) {
		this.p95ClientToHAP = p95ClientToHAP;
	}

	public long getP95HAPQueue() {
		return p95HAPQueue;
	}

	public void setP95HAPQueue(long p95hapQueue) {
		p95HAPQueue = p95hapQueue;
	}

	public long getP95HAPToServer() {
		return p95HAPToServer;
	}

	public void setP95HAPToServer(long p95hapToServer) {
		p95HAPToServer = p95hapToServer;
	}

	public long getP95ServerResponse() {
		return p95ServerResponse;
	}

	public void setP95ServerResponse(long p95ServerResponse) {
		this.p95ServerResponse = p95ServerResponse;
	}

	public long getP95TotResponse() {
		return p95TotResponse;
	}

	public void setP95TotResponse(long p95TotResponse) {
		this.p95TotResponse = p95TotResponse;
	}

	public long getTotGET() {
		return totGET;
	}

	public void setTotGET(long totGET) {
		this.totGET = totGET;
	}

	public long getTotPUT() {
		return totPUT;
	}

	public void setTotPUT(long totPUT) {
		this.totPUT = totPUT;
	}

	public long getTotPOST() {
		return totPOST;
	}

	public void setTotPOST(long totPOST) {
		this.totPOST = totPOST;
	}

	public long getTotDELETE() {
		return totDELETE;
	}

	public void setTotDELETE(long totDELETE) {
		this.totDELETE = totDELETE;
	}

	public long getTotRequestsWitinExpectedTime() {
		return totRequestsWitinExpectedTime;
	}

	public void setTotRequestsWitinExpectedTime(long totRequestsWitinExpectedTime) {
		this.totRequestsWitinExpectedTime = totRequestsWitinExpectedTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static String headings() {
		return new StringBuilder()
				.append("serverName,totalRequests,totRequestsWithinExpectedTime,p95ClientToHAP,p95HAPQueue,p95HAPToServer,p95ServerResponse,p95TotResponse,totGET,totPUT,totPOST,totDELETE,startTime,endTime")
				.toString();

	}

	@Override
	public String toString() {
		return serverName + "," + totalRequests +","+ totRequestsWitinExpectedTime + "," +p95ClientToHAP+","+ p95HAPQueue + "," + p95HAPToServer + "," + p95ServerResponse
				+ "," + p95TotResponse + "," + totGET + "," + totPUT + "," + totPOST + "," + totDELETE + ","
				+ startTime + "," + endTime;
	}

	public int compareTo(ServerParsedData o) {
		if(this.getTotalRequests() > o.getTotalRequests()){
			return 1;
		}else if(this.getTotalRequests() == o.getTotalRequests()){
			return 0;
		}else{
			return -1;
		}
	}

}
