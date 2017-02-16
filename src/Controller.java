import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by csclass on 10/24/16.
 */


public class Controller extends Thread {

    //Data Variables
    public View view ;
    public Model model;

    public PrintWriter toServer;
    public BufferedReader fromServer;
    public Socket socket;

    public Controller (){
        model = new Model();
        view = new View(this);


        // NETWORKING
        String serverId = "localhost";
        int port = 9999;

        try {
            socket = new Socket(serverId , port);

            toServer = new PrintWriter(socket.getOutputStream() , true);

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            fromServer = new BufferedReader(isr);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    //Method to send DATA to the SERVER
    public void sendToServer(String msg){

        toServer.println(msg);

    }

    //Method to Read from the SERVER
    public String readFromServer() {
        String msg = "";
        try {
            msg =  fromServer.readLine();
        } catch (IOException e){
            e.printStackTrace();;
        }
        return msg;
    }


    //Method to setup Game Panel
    public void startGame(){
        view.mainWindow();
    }


    public void setUserToken(String userToken){
        model.setUserToken(userToken);
    }

    public String getUserToken() {
        return model.getUsertoken();
    }

    public void setUsername(String username){
        model.setUsername(username);
    }

    public String getUsername(){
        return model.getUsername();
    }

    public void setGametoken(String gametoken) {
        model.setGametoken(gametoken);
    }

    public String getGametoken(){
        return model.getGametoken();
    }


    // METHODS FOR GET USER LOGIN PANEL (2.1 / 2.2)

    public void loginButtonPressed(String username, String password) {

        sendToServer("LOGIN--"+username+"--"+password);
        String serverMessage = readFromServer();

        if ((serverMessage.contains("UNKNOWNUSER")))
            view.information.setText("Invalid Username");
        else if ((serverMessage.contains("INVALIDUSERPASSWORD")))
            view.information.setText("Invalid Password " + "\n"+ "(User not authenticated)");
        else if ((serverMessage.contains("USERALREADYLOGGEDIN")))
            view.information.setText("User already logged in");
        else if ((serverMessage.contains("SUCCESS"))) {
            view.information.setText("User logged into the  " + "\n" + "system successfully");
            serverMessage = serverMessage.replace("RESPONSE--LOGIN--SUCCESS--" , "");

            setUserToken(serverMessage);

            setUsername(username);

            view.layout.show(view.mainPanel,"2");

            //SETTING PLAYER NAME
            String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                    "</font></html>";
            view.playerNameLabel.setText(playerName2);

        }

        else if (serverMessage.contains("INVALIDMESSAGEFORMAT"))
            view.information.setText("Invalid Username" + "\n" + "or Password");
    }

    public void registerButtonPressed(String username, String password){

        sendToServer("CREATENEWUSER--"+username+"--"+password);
        String serverMessage = readFromServer();

        if ((serverMessage.contains("INVALIDUSERNAME")))
            view.information.setText("Username Empty");
        else if ((serverMessage.contains("INVALIDPASSWORD")))
            view.information.setText("Password Empty");
        else if ((serverMessage.contains("USERALREADYEXISTS")))
            view.information.setText("User already exists" + "\n" + " in the user store");
        else if ((serverMessage.contains("SUCCESS")))
            view.information.setText("User created in the" + "\n" + "user store successfully");
        else if (serverMessage.contains("INVALIDMESSAGEFORMAT"))
            view.information.setText("Invalid Username or" + "\n" + "Invalid Password");

    }

    //METHODS FOR 2.3 - PLAY GAME GUI

    public void startNewGameButtonPressed(){


        sendToServer("STARTNEWGAME--"+getUserToken());
        String serverMessage = readFromServer();

        if ((serverMessage.contains("USERNOTLOGGEDIN")))
            view.informationPlayGamePanel.setText("Invalid UserToken");
        else if ((serverMessage.contains("FAILURE")))
            view.informationPlayGamePanel.setText("User Already Playing");
        else if ((serverMessage.contains("SUCCESS"))) {

            String gameKEY = serverMessage.replace("RESPONSE--STARTNEWGAME--SUCCESS--", "");
            setGametoken(gameKEY);

            view.layout.show(view.mainPanel, "3");

            String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                    "</font></html>";

            view.playerNameLabel2.setText(playerName2);
            view.gameKey.setText(getGametoken());
            view.informationStartGamePanel.setText("Game Created. You " + "\n" + "are the leader");


            //Getting the names of the participants - I Have hardcoded to 1 participant
            start();


        }


    }

    public void joinGameButtonPressed(){


        sendToServer("JOINGAME--" + getUserToken()+ "--"+ view.gameKeyEntered.getText());
        String serverMessage = readFromServer();

        if ((serverMessage.contains("USERNOTLOGGEDIN")))
            view.informationJoinGamePanel.setText("Invalid UserToken");
        else if ((serverMessage.contains("GAMEKEYNOTFOUND")))
            view.informationJoinGamePanel.setText("Invalid Game Token");
        else if ((serverMessage.contains("FAILURE")))
            view.informationJoinGamePanel.setText("User Already Playing");
        else if ((serverMessage.contains("SUCCESS"))) {
            model.setGametoken(view.gameKeyEntered.getText());
            view.layout.show(view.mainPanel, "5");

            String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                    "</font></html>";

            view.playerNameLabel4.setText(playerName2);

            new Thread() {

                public void run(){

                    String output = null;
                    do {

                        output = readFromServer();
                        System.out.println(output);

                    } while(output == null);


                    question = output;
                    view.layout.show(view.mainPanel, "7");

                    String playerName3 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                            "</font></html>";

                    view.playerNameLabel6.setText(playerName3);

                    //FORMATTING THE QUESTION RECEIVED FROM SERVER
                    question = question.replace("NEWGAMEWORD--", "");
                    String receivedQuestion = question.replace("--", "*");
                    int indexAsterix = receivedQuestion.indexOf("*");

                    question = receivedQuestion.substring(0,indexAsterix);
                    correctAnswer = receivedQuestion.substring(indexAsterix+1);
                    view.question2.setText(question);

                    model.setQuestion(question);
                    model.setCorrectAnswer(correctAnswer);

                    String question1 = "<html><b><font face = Candara size = 6 color = white>"+  model.getQuestion()
                            +"</font></b></html>";

                    view.question2.setText(question1);




                }
            }.start();







        }


    }

    public void exitGameButtonPressed(){
        sendToServer("LOGOUT--");
        String message = readFromServer();
        if(message.equals("RESPONSE--LOGOUT--SUCCESS--LOGOUT")) {
            view.dispose();

        } else if (message.equals("RESPONSE--LOGOUT--USERNOTLOGGEDIN"))
            view.informationResults.setText("User not Logged In");
    }




    public String question;
    public String correctAnswer;
    public void startGameButtonPressed() {

        sendToServer("ALLPARTICIPANTSHAVEJOINED--"+model.getUsertoken()+"--"+ model.getGametoken());
        String serverMessage = readFromServer();

        question = serverMessage;

        if ((serverMessage.contains("USERNOTLOGGEDIN")))
            view.informationStartGamePanel.setText("Invalid user token");
        else if ((serverMessage.contains("INVALIDGAMETOKEN")))
            view.informationStartGamePanel.setText("Invalid game token");
        else if ((serverMessage.contains("USERNOTGAMELEADER")))
            view.informationStartGamePanel.setText("User already playing the game");
        else {


            view.layout.show(view.mainPanel, "7");

            String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                    "</font></html>";

            view.playerNameLabel6.setText(playerName2);

            //FORMATTING THE QUESTION RECEIVED FROM SERVER
            question = question.replace("NEWGAMEWORD--", "");
            String receivedQuestion = question.replace("--", "*");
            int indexAsterix = receivedQuestion.indexOf("*");

            question = receivedQuestion.substring(0,indexAsterix);
            correctAnswer = receivedQuestion.substring(indexAsterix+1);
            view.question2.setText(question);

            model.setQuestion(question);
            model.setCorrectAnswer(correctAnswer);

            String question1 = "<html><b><font face = Candara size = 6 color = white>"+  model.getQuestion()
                    +"</font></b></html>";

            view.question2.setText(question1);



        }

    }


    public String clientAnswer;
    public void sendSuggestionButtonPressed() {

        clientAnswer = view.suggestion.getText();

        new Thread() {

            public void run() {
                while (clientAnswer.equalsIgnoreCase(model.getCorrectAnswer()))

                {
                    view.informationSubmitSuggestion.setText("You are smart! Change your answer");
                }
            }
        }.start();




        //CREATING A NEW THREAD TO GET ANSWERS
        new Thread() {
            public void run() {

                if (!clientAnswer.equalsIgnoreCase(model.getCorrectAnswer())) {

                    sendToServer("PLAYERSUGGESTION--"+model.getUsertoken()+"--"+model.getGametoken()+"--"+view.suggestion.getText());
                    view.informationSubmitSuggestion.setText("Your suggestion has been sent");
                    view.submitSuggestion.setEnabled(false);


                    String output = null;

                    do {
                        output = readFromServer();

                    } while (output == null);

                    view.layout.show(view.mainPanel, "8");
                    String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                            "</font></html>";
                    view.playerNameLabel7.setText(playerName2);

                    String serverMessage = output;

                    if (serverMessage.contains("USERNOTLOGGEDIN"))
                        view.informationSubmitSuggestion.setText("Invalid user token");
                    else if ((serverMessage.contains("INVALIDGAMETOKEN")))
                        view.informationSubmitSuggestion.setText("Invalid game token");
                    else if ((serverMessage.contains("UNEXPECTEDMESSAGETYPE")))
                        view.informationSubmitSuggestion.setText("A suggestion was sent when a different message was expected by the server");
                    else if ((serverMessage.contains("INVALIDMESSAGEFORMAT")))
                        view.informationSubmitSuggestion.setText("Message format is not according to what is given above");
                    else {


                        String a = output;

                        a = a.replace("ROUNDOPTIONS--", "");
                        a = a.replaceAll("--", "*");
                        a = a.substring(0, a.length());
                        String a1 = a.substring(0, a.indexOf('*'));
                        a = a.substring(a.indexOf('*') + 1, a.length());
                        String a2 = a.substring(0, a.indexOf('*'));
                        String a3 = a.substring(a.indexOf('*') + 1, a.length());

                        view.o1.setText(a1);
                        view.o2.setText(a2);
                        view.o3.setText(a3);

                    }
                }
            }


        }.start();



    }

    public void nextRoundPressed(){
        resetGamePanel();
        view.layout.show(view.mainPanel, "7");

        String playerName3 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                "</font></html>";

        view.playerNameLabel6.setText(playerName3);

        //FORMATTING THE QUESTION RECEIVED FROM SERVER
        question = question.replace("NEWGAMEWORD--", "");
        String receivedQuestion = question.replace("--", "*");
        int indexAsterix = receivedQuestion.indexOf("*");

        question = receivedQuestion.substring(0,indexAsterix);
        correctAnswer = receivedQuestion.substring(indexAsterix+1);
        view.question2.setText(question);

        model.setQuestion(question);
        model.setCorrectAnswer(correctAnswer);

        String question1 = "<html><b><font face = Candara size = 6 color = white>"+  model.getQuestion()
                +"</font></b></html>";

        view.question2.setText(question1);
    }

    public void resetGamePanel(){
        view.suggestion.setText("");
        view.submitSuggestion.setEnabled(true);
        view.submitOption.setEnabled(true);
        view.informationResults.setText("");
        view.informationSubmitChoice.setText("");
        view.informationResults.setText("");
    }

    public String userschoice;
    public void submitOptionButtonPressed() {



        userschoice = "";

        if(view.o1.isSelected())
            userschoice = view.o1.getText();
        else if(view.o2.isSelected())
            userschoice = view.o2.getText();
        else if(view.o3.isSelected())
            userschoice = view.o3.getText();




        new Thread() {

            public void run() {
                while (userschoice.equals(clientAnswer))

                {
                    view.informationSubmitChoice.setText("You cannot pick your own answer!");
                }
            }
        }.start();



        //WAITING FOR SERVER TO RESPOND

        new Thread(){
            public void run(){

                if(!userschoice.equals(clientAnswer)) {

                    sendToServer("PLAYERCHOICE--" + model.getUsertoken() + "--" + model.getGametoken() + "--" + userschoice);
                    view.informationSubmitChoice.setText("Your suggestion has been sent");

                    view.submitOption.setEnabled(false);

                    String output = null;

                    do {

                        output = readFromServer();

                    } while (output == null);

                    String serverMessage = output;


                    if (serverMessage.contains("USERNOTLOGGEDIN"))
                        view.informationSubmitChoice.setText("Invalid user token");
                    else if ((serverMessage.contains("INVALIDGAMETOKEN")))
                        view.informationSubmitChoice.setText("Invalid game token");
                    else if ((serverMessage.contains("UNEXPECTEDMESSAGETYPE")))
                        view.informationSubmitChoice.setText("A suggestion was sent when a different message was " +
                                "expected by the server");
                    else if ((serverMessage.contains("INVALIDMESSAGEFORMAT")))
                        view.informationSubmitChoice.setText("Message format is not according to what is given above");

                    else {

                        view.layout.show(view.mainPanel, "9");
                        String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + getUsername() +
                                "</font></html>";
                        view.playerNameLabel8.setText(playerName2);

                        String results = serverMessage;
                        results = results.replace("ROUNDRESULT--", "");
                        results = results.replaceAll("--", "*");

                        String player1, player2, timesFooled1, timesFooled2, playersFooled1, playersFooled2, cumulative1, cumulative2;

                        if(!userschoice.equals(clientAnswer)) {

                            //Player 1
                            player1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            String message1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            cumulative1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            playersFooled1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            timesFooled1 = results.substring(0, results.indexOf('*'));

                            results = results.substring(results.indexOf('*') + 1, results.length());

                            //Player 2
                            player2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            String message2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            cumulative2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            playersFooled2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            timesFooled2 = results.substring(0, results.length());

                            if (model.getUsername().equals(player1))
                                view.round.setText(message1);
                            else view.round.setText(message2);

                        }

                        else {

                            //Player 1
                            player1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 2, results.length());
                            cumulative1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            playersFooled1 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            timesFooled1 = results.substring(0, results.indexOf('*'));

                            results = results.substring(results.indexOf('*') + 1, results.length());

                            //Player 2
                            player2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 2, results.length());
                            cumulative2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            playersFooled2 = results.substring(0, results.indexOf('*'));
                            results = results.substring(results.indexOf('*') + 1, results.length());
                            timesFooled2 = results.substring(0, results.indexOf('*'));

                            view.round.setText("You picked the right answer but didn't fool anyone");   //You can change the text if you want

                        }

                        String s = player1 + " => Score: " + cumulative1 + " | Fooled: " +
                                timesFooled1 + "player(s)  | Fooled by: " + playersFooled1 + " player(s)" +
                                "\n" + player2  + " => Score: " + cumulative2 + " | Fooled: " +
                                timesFooled2 + " player(s) | Fooled by: " + playersFooled2 + " player(s)";


                        view.overall.setText(s);



                        //FOR NEXT ROUND
                        String nextRound  = readFromServer();
                        if(nextRound.equals("GAMEOVER--")){
                            view.nextRound.setEnabled(false);
                            view.informationResults.setText("Game Over");
                        }
                        else {
                            question = nextRound;
                            view.informationResults.setText("Click Next to go to the Next Word");
                        }



                    }

                }



            }

        }.start();




    }












    //THREAD METHOD FOR LEADER

    public void run(){

        int numberOfParticipants = 0;

        do {

            String msg = readFromServer();

            if(msg.contains("NEWPARTICIPANT--")){

                numberOfParticipants++;
                msg = msg.replace("NEWPARTICIPANT--","");
                msg = msg.replace("--", "*");

                String name = msg.substring(0,msg.indexOf("*"));
                int score = Integer.parseInt(msg.substring(msg.indexOf("*") +1));

                model.participantName = name;
                model.participantScore = score;
                numberOfParticipants++;

                view.participantsField.setText("Participant Added: " + model.participantName);
            }

        } while (numberOfParticipants == 1);

        view.startGame.setEnabled(true);



    }

    public static void main(String[] args) {
        Controller controller;
        controller = new Controller();

        controller.startGame();

    }
}

