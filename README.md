# haproxy_analyser
Simple java utility to analyse HAP logs

Build

This project uses maven for build.

mvn clean install

Execution

java -jar haproxy_analyser-1.0-jar-with-dependencies.jar

Input

1) comma separated list of HAP logs file.
2) URL pattern to be analysed.
3) Expected repsonse time.


Output

serverName - Name of the server which served request.
totalRequests - Total number of requests for the given URL pattern.
totRequestsWithinExpectedTime - Total number of requests within expected response time.
p95ClientToHAP - 95th percentile , total time in milliseconds spent waiting for a full HTTP
    			 request from the client (not counting body) after the first byte was
    			 received. It can be "-1" if the connection was aborted before a complete
    			 request could be received or the a bad request was received. It should
    			 always be very small because a request generally fits in one single packet.
      			 Large times here generally indicate network issues between the client and
    			 haproxy or requests being typed by hand. See "Timers" below for more details.
p95HAPQueue -
p95HAPToServer -
p95ServerResponse -
p95TotResponse -
totGET -
totPUT -
totPOST -
totDELETE -
startTime -
endTime