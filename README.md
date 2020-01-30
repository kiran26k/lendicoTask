# Repayment calculation 

Headers :  Content-Type: application/json
Method Type - Post : http://localhost:8080/repaymentPlan

Request Body : {
"loanAmount": "5000",
"nominalRate": "5.0",
"duration": 24,
"startDate": "2018-01-01T00:00:01Z"
}


Project developed using Spring Boot and Maven. Just export project and do maven clean install. 
One project is clean build , just run Application.java file and hit above url from any rest client to check result.
Spring embeded server used, no extra server configuration required.
