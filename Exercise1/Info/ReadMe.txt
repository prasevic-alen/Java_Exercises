How to use
    1. Extract zip archive or use git clone https://github.com/prasevic-alen/Java_Exercises.git
    2. enter the folder Exercise1
    3. Makes sure that Wildfly server is running and than execute
    mvn clean package wildfly:deploy
    4. Access page on http://wildflyServerIP:8080/Exercise1
       In my case used: http://localhost:8080/Exercise1 

Information about the Bean and content
    - NumberleBean is using Guess class to store guessed digits
    - In the constructor I decided to start the game
      so someone can say that they have 7 guesses as it is started with initial 0 as Guess
      ( this can be changed, if it would be requirement )
    - index page will have possibility to start new game or to guess number
      form is separated to use single digit 
      ( I made it that if user add other character than digit, 
      it will use to guess previous number, without returning any error )
    - When Guessed correct number we will open success page
      success page will have return to index button 
    - When we did not guess correct number we will open failure page
      failure page will have return to index button  
    