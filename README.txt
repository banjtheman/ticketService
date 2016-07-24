======= Installing TicketService ======
1. git clone git@github.com:banjtheman/ticketService.git
2. cd ticketService
3. mvn clean compile

======= Running TicketService ======
1. mvn exec:java

======= Testing TicketService ======
1. mvn test

======= Assumptions =======
1. The term best seat means the lowest non taken seat i.e. row 0 seat 0 is best seat, row 0 seat 1 is next best seat, and so on. 
2. The venue depicted in the instructions will be used as the basis for the solution.
