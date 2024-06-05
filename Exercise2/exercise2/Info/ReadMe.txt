How to use
    1. Extract zip archive or use git clone https://github.com/prasevic-alen/Java_Exercises.git
    2. enter the folder Exercise2/exercise2
    3. mariadb -h 127.0.0.1 -u javaee -p javaee < Info/database_dump.sql
    3. Makes sure that Wildfly server is running and than execute
       mvn clean package wildfly:deploy
    4. Access page on http://wildflyServerIP:8080/Exercise2
       In my case used: http://localhost:8080/Exercise2 


Information about the Bean and content
  - I organized the Java classes into three categories: 
      repository classes handle interacting with the database, 
      bean classes manage the application logic, 
      and entity classes represent the data structures.
  - I reused styles from the previous Exercise1
  - Pages are did structured based on the functions, 
      and I think that design and idea should be intuitive
      so user can understand the all pages flow
  - pom.xml is included 
    