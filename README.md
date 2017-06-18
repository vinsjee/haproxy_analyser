# haproxy_analyser
Simple java utility to analyse HAP logs

<b>Build</b>

This project uses maven for build.

mvn clean install

<b>Execution</b>

java -jar haproxy_analyser-1.0-jar-with-dependencies.jar

<b>Input</b>

1) comma separated list of HAP logs file.
2) URL pattern to be analysed.
3) Expected repsonse time.


<b>Output</b>

1) serverName - Name of the server which served request.
2) totalRequests - Total number of requests for the given URL pattern.
3) totRequestsWithinExpectedTime - Total number of requests within expected response time.
4) p95ClientToHAP - 95th percentile of total time in milliseconds spent waiting for a full HTTP request from the client (not counting body) after the first byte was received. It can be "-1" if the connection was aborted before a complete request could be received or the a bad request was received. It should always be very small because a request generally fits in one single packet. Large times here generally indicate network issues between the client and haproxy or requests being typed by hand. See "Timers" below for more details.
5) p95HAPQueue - 95th percentile of is the total time in milliseconds spent waiting in the various queues.
6) p95HAPToServer - 95th percentile of the total time in milliseconds spent waiting for the connection to establish to the final server, including retries. 
7) p95ServerResponse - 95th percentile of the total time in milliseconds spent waiting for the server to send a full HTTP response, not counting data.
8) p95TotResponse - 95th percentile of the time the request remained active in haproxy, which is the total time in milliseconds elapsed between the first byte of the request was received and the last byte of response was sent.
9) totGET - total number of GETs.
10) totPUT - total number of PUTs.
11) totPOST - total number of POSTs.
12) totDELETE - total numbers of DELETEs.
13) startTime - start time.
14) endTime - end time.