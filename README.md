# WebChat
A simple chat application written in Java with a simple username password based authentication and multiple chatrooms.
## Pre-requisites
1. Java 8
2. Maven
3. Apache Tomcat
## Installation
1. Clone this Repository
2. Install a DB server of your choice
3. Write a DB Client under `src/main/java/com/webchat/db/impl` as an implementation of `com.webchat.db.DBService`. A Maria DB Client already exists.
4. Modify `src/main/resources/db.properties` with your DB server's details
5. Run `sql\ddl.sql` on DB
6. Run a maven clean and install 
7. Install the target war file on the tomcat server
### Running the application
1. Run the tomcat server
2. Navigate to `hostURL\index.jsp` where hostURL is the URL on which the tomcat server is running
3. Register a few users
4. Assign 'team's to the registered users by inserting to the `TEAM` table
5. Log in as a user and chat
 