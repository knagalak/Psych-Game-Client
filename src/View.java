
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View extends JFrame implements ActionListener{

    protected Controller controller;



    protected CardLayout layout = new CardLayout();
    protected JPanel mainPanel = new JPanel();

    //Data variables for 2.1 and 2.2
    protected JButton login;
    protected JButton register;
    protected JTextField userIdField ;
    protected JPasswordField passwordField;
    protected JTextArea information;

    //Data variables for getPlayGamePanel (2.3)
    protected JButton startNewGame;
    protected JButton joinAGame;
    protected JTextArea informationPlayGamePanel;
    protected JLabel playerNameLabel;

    //Data variables for getStartANewGame (2.4)
    protected JLabel gameKey;
    protected JTextArea participantsField;
    protected JButton startGame;
    protected JTextArea informationStartGamePanel;
    protected JLabel playerNameLabel2;

    //Data Variables for Join A Game (2.5)
    protected JTextField gameKeyEntered;
    protected JButton joinGame;
    protected JTextArea informationJoinGamePanel;
    protected JLabel playerNameLabel3;

    //Data Variables for Waiting for Leader
    protected JLabel playerNameLabel4;







    // SHREYAS CODE


    //Data Variables for Submit Suggestion (2.7)
    protected JTextField suggestion;
    protected JTextArea informationSubmitSuggestion;
    protected JLabel playerNameLabel6;
    protected JLabel question2;
    protected JButton submitSuggestion;

    //Data Variables for Submit Choice (2.8)
    protected JTextArea informationSubmitChoice;
    protected JLabel playerNameLabel7;
    protected JButton submitOption;
    protected String usersChoice;

    protected JRadioButton o1;
    protected JRadioButton o2;
    protected JRadioButton o3;

    //Data Variables for Receive Results (2.9)
    protected JTextArea informationResults;
    protected JLabel playerNameLabel8;
    protected JLabel round;
    protected JTextArea overall;

    protected JButton nextRound;
    protected JButton exitButton;

    //SHREYAS CODE






    //CONSTRUCTOR
    public View(Controller controller){
        this.controller = controller;
    }


    public void actionPerformed(ActionEvent e) {
        //Method Overridden in all Cases - This is a dummy Method
        Object source = e.getSource();

    }

    public void mainWindow () {

        Container contentPane = this.getContentPane();

        //Creating the Main Window (on which the panels change)

        mainPanel.setLayout(layout);
        mainPanel.setSize(400,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setBackground(new Color(102, 0, 51));
        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //Add the USER LOGIN PANEL on MAIN PANEL initially (2.1 & 2.2)
        mainPanel.add(getUserLoginPanel(),"1");

        //Add the Play Game Panel (2.3)
        mainPanel.add(getPlayGamePanel(),"2");
        //Add the Start A New Game Panel (2.4)
        mainPanel.add(getStartANewGamePanel(), "3");
        //Add the Join a Game panel (2.5)
        mainPanel.add(getJoinAGamePanel(),"4");
        //Add Waiting for the Leader Panel (2.5)
        mainPanel.add(getWaitingForTheLeaderPanel(), "5");


        //Add Send Player's Suggestion Panel
        mainPanel.add(getPlayersSuggestionPanel(), "7");

        //Add Send User's Choice Panel
        mainPanel.add(getUsersChoicePanel(), "8");

        //Add Recieve Results Panel
        mainPanel.add(getResultsPanel(), "9");



        contentPane.add(mainPanel);
        layout.show(mainPanel,"1");
        setVisible(true);


    }

    public JPanel getUserLoginPanel() {


        JPanel userLogin = new JPanel (new GridLayout(4,0));
        //Creating Title panel
        JPanel titlePanel = new JPanel(new GridLayout(1,1));
        titlePanel.setVisible(true);

        String titleName = "<html><font color = yellow>FoilMaker!!</font></html>";
        JLabel title = new JLabel();
        title.setFont(new Font("Impact" , Font.ITALIC , 60));

        title.setText(titleName);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);

        titlePanel.add(title);

        titlePanel.setBackground(new Color(102,0,51));
        userLogin.add(titlePanel);


        //Creating a User Id login panel
        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.setSize(600,50);
        loginPanel.setBackground(new Color(102,0,51));
        userLogin.add(loginPanel);
        //Grid Object 1
        String usernameText = "<html><b><font face = Candara size = 6 color = white>Username:</font></b></html>";
        JLabel username = new JLabel(usernameText);
        username.setHorizontalAlignment(JLabel.CENTER);
        username.setVerticalAlignment(JLabel.CENTER);
        loginPanel.add(username);
        //Grid Object 2
        userIdField = new JTextField(10);
        loginPanel.add(userIdField);

        //Grid Object 3
        String passwordText = "<html><b><font face = Candara size = 6 color = white>Password:</font></b></html>";
        JLabel password = new JLabel(passwordText);
        password.setHorizontalAlignment(JLabel.CENTER);
        password.setVerticalAlignment(JLabel.CENTER);
        loginPanel.add(password);
        //Grid Object 4
        passwordField = new JPasswordField(10);
        loginPanel.add(passwordField);


        //Creating Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(102,0,51));
        userLogin.add(buttonPanel);
        //Login Button
        login = new JButton("Login");
        register = new JButton("Register");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = "";
                username = userIdField.getText();
                char[] passwordArray = passwordField.getPassword();
                String password = "";
                for(int i = 0 ; i <passwordArray.length ; i ++) {
                    password = password + passwordArray[i];
                }

                controller.loginButtonPressed(username, password);


            }
        } );

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = "";
                username = userIdField.getText();
                char[] passwordArray = passwordField.getPassword();

                String password = "";
                for(int i = 0 ; i <passwordArray.length ; i ++) {
                    password = password + passwordArray[i];
                }

               controller.registerButtonPressed(username , password);

            }
        });


        buttonPanel.add(login);
        buttonPanel.add(register);

        //Creating info area
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300,200);
        textInfo.setBackground(new Color (102,0,51));
        information = new JTextArea();
        information.setEditable(false);
        information.setFont(new Font("Calibri" , Font.BOLD , 16));
        information.setBackground(new Color (102, 0 , 51));
        information.setForeground(Color.pink);
        information.setAlignmentX(150);
        information.setVisible(true);
        textInfo.add(information);
        textInfo.setVisible(true);
        userLogin.add(textInfo);

        //Visibilities
        titlePanel.setVisible(true);
        userLogin.setVisible(true);



        return userLogin;

    }


    public JPanel getPlayGamePanel(){


        //PLAY GAME PANE

        JPanel playGame = new JPanel(new GridLayout(3,0));
        playGame.setSize(300,600);
        playGame.setVisible(true);
        playGame.setBackground(new Color(102, 0, 51));

        playerNameLabel = new JLabel();
        playerNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playGame.add(playerNameLabel);


        // Creating the two Buttons (Start New Game and Join Game)
        JPanel buttonPanel = new JPanel(new FlowLayout());

        startNewGame = new JButton();
        startNewGame.setText("Start New Game");
        startNewGame.setVisible(true);
        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startNewGameButtonPressed();
            }
        });


        joinAGame = new JButton();
        joinAGame.setText("Join A Game");
        joinAGame.setVisible(true);
        joinAGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(mainPanel,"4");

                String playerName2 = "<html><font face = Impact size = 60 color = yellow>" + controller.getUsername() +
                        "</font></html>";
                playerNameLabel3.setText(playerName2);

            }
        });



        buttonPanel.add(startNewGame);
        buttonPanel.add(joinAGame);
        buttonPanel.setVisible(true);
        buttonPanel.setBackground(new Color(102, 0, 51));

        playGame.add(buttonPanel);


        //Creating information panel
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 200);
        textInfo.setBackground(new Color(102, 0, 51));
        informationPlayGamePanel= new JTextArea();
        informationPlayGamePanel.setEditable(false);
        informationPlayGamePanel.setFont(new Font("Calibri", Font.BOLD, 16));
        informationPlayGamePanel.setBackground(new Color(102, 0, 51));
        informationPlayGamePanel.setForeground(Color.pink);
        informationPlayGamePanel.setAlignmentX(150);
        informationPlayGamePanel.setText("User Logged in " + "\n" + "Successfully");
        informationPlayGamePanel.setVisible(true);
        textInfo.add(informationPlayGamePanel);
        textInfo.setVisible(true);

        playGame.add(textInfo);


        return playGame;

    }

    public JPanel getStartANewGamePanel(){

        //Main Start A New Panel
        JPanel startANewGame = new JPanel(new GridLayout(6,0));
        startANewGame.setSize(300,600);
        startANewGame.setVisible(true);
        startANewGame.setBackground(new Color(102, 0, 51));

        //Name of Player

        playerNameLabel2 = new JLabel();
        playerNameLabel2.setFont(new Font("Impact" , Font.ITALIC , 32));

        playerNameLabel2.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel2.setHorizontalAlignment(JLabel.CENTER);
        startANewGame.add(playerNameLabel2);

        //Creating a JLabel that says Others should use this key to join your game
        JLabel textJoinYourGame = new JLabel();
        String joinYourGame = "<html> <font color = yellow> Others should use this key to" + "\n" +" join your " +
                "\n" + "game " +
                "</font></html>";
        textJoinYourGame.setText(joinYourGame);
        textJoinYourGame.setFont(new Font("Calibri", Font.BOLD, 16));
        textJoinYourGame.setVerticalAlignment(JLabel.CENTER);
        textJoinYourGame.setHorizontalAlignment(JLabel.CENTER);
        startANewGame.add(textJoinYourGame);

        //Creating a JLabel that has the KEY
        JPanel gameKeyPanel = new JPanel(new BorderLayout());
        gameKeyPanel.setVisible(true);
        gameKeyPanel.setSize(300,50);
        gameKey = new JLabel();
        gameKey.setVerticalAlignment(JLabel.CENTER);
        gameKey.setHorizontalAlignment(JLabel.CENTER);
        gameKey.setFont(new Font("Calibri", Font.BOLD, 16));
        gameKey.setForeground(Color.pink);
        gameKeyPanel.setBackground(new Color(102, 0, 51));
        gameKeyPanel.add(gameKey,BorderLayout.CENTER);
        startANewGame.add(gameKeyPanel);

        //Creating a Participants Panel
        //I have created a data member of JTextArea that lets you change the text by calling methods
        JPanel participants = new JPanel();
        participants.setBorder(BorderFactory.createTitledBorder("Participant"));
        participantsField = new JTextArea();
        participantsField.setVisible(true);
        participantsField.setEditable(false);
        participantsField.setSize(300,300);
        participantsField.setAlignmentX(10);
        participantsField.setBackground(Color.yellow);
        participants.add(participantsField);
        startANewGame.add(participants);

        //Creating start a game button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setSize(300,50);
        buttonPanel.setBackground(new Color(102, 0, 51));
        startGame = new JButton();
        startGame.setEnabled(false); //INITIALLY SET DISABLED
        startGame.setText("Start Game");
        buttonPanel.add(startGame);
        startANewGame.add(buttonPanel);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGameButtonPressed();

            }
        });

        //Creating information Panel
        //Creating information panel
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 50);
        textInfo.setBackground(new Color(102, 0, 51));
        informationStartGamePanel= new JTextArea();
        informationStartGamePanel.setEditable(false);
        informationStartGamePanel.setFont(new Font("Calibri", Font.BOLD, 16));
        informationStartGamePanel.setBackground(new Color(102, 0, 51));
        informationStartGamePanel.setForeground(Color.pink);
        informationStartGamePanel.setAlignmentX(150);
        informationStartGamePanel.setText("DEMO");
        informationStartGamePanel.setVisible(true);
        textInfo.add(informationStartGamePanel);
        textInfo.setVisible(true);

        startANewGame.add(textInfo);


        return startANewGame;
    }

    public JPanel getJoinAGamePanel(){
        JPanel joinAGame = new JPanel(new GridLayout(5,0));
        joinAGame.setSize(300,600);
        joinAGame.setVisible(true);
        joinAGame.setBackground(new Color(102, 0, 51));

        //Players Name


        playerNameLabel3 = new JLabel();
        playerNameLabel3.setFont(new Font("Impact" , Font.ITALIC , 32));


        playerNameLabel3.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel3.setHorizontalAlignment(JLabel.CENTER);
        joinAGame.add(playerNameLabel3);

        //TEXT TO ENTER KEY
        JLabel textJoinYourGame = new JLabel();
        String joinYourGame = "<html> <font color = yellow> Enter Game Key " + "\n" +"to join a game " +
                "</font></html>";
        textJoinYourGame.setText(joinYourGame);
        textJoinYourGame.setFont(new Font("Calibri", Font.BOLD, 16));
        textJoinYourGame.setVerticalAlignment(JLabel.CENTER);
        textJoinYourGame.setHorizontalAlignment(JLabel.CENTER);
        joinAGame.add(textJoinYourGame);

        //Place HOLDER FOR KEY
        JPanel gameKeyPanel = new JPanel(new FlowLayout());
        gameKeyPanel.setVisible(true);
        gameKeyPanel.setSize(300,50);
        gameKeyEntered = new JTextField(10);
        gameKeyPanel.setBackground(new Color(102, 0, 51));
        gameKeyPanel.add(gameKeyEntered);
        joinAGame.add(gameKeyPanel);


        //Button Join a Game
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setSize(300,50);
        buttonPanel.setBackground(new Color(102, 0, 51));
        joinGame = new JButton();
        joinGame.setText("Join Game");
        buttonPanel.add(joinGame);
        joinAGame.add(buttonPanel);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.joinGameButtonPressed();
            }
        });

        //TextArea
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 50);
        textInfo.setBackground(new Color(102, 0, 51));
        informationJoinGamePanel= new JTextArea();
        informationJoinGamePanel.setEditable(false);
        informationJoinGamePanel.setFont(new Font("Calibri", Font.BOLD, 16));
        informationJoinGamePanel.setBackground(new Color(102, 0, 51));
        informationJoinGamePanel.setForeground(Color.pink);
        informationJoinGamePanel.setAlignmentX(150);
        informationJoinGamePanel.setText(" Welcome! ");
        informationJoinGamePanel.setVisible(true);
        textInfo.add(informationJoinGamePanel);
        textInfo.setVisible(true);
        joinAGame.add(textInfo);



        return joinAGame;

    }

    public JPanel getWaitingForTheLeaderPanel(){
        JPanel waitingLeader = new JPanel(new GridLayout(3,0));
        waitingLeader.setSize(300,600);
        waitingLeader.setVisible(true);
        waitingLeader.setBackground(new Color(102, 0, 51));

        //Player Name

        playerNameLabel4 = new JLabel();
        playerNameLabel4.setFont(new Font("Impact" , Font.ITALIC , 32));

        playerNameLabel4.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel4.setHorizontalAlignment(JLabel.CENTER);
        waitingLeader.add(playerNameLabel4);



        //WAITING FOR THE LEADER TEXT
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 200);
        textInfo.setBackground(new Color(102, 0, 51));
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Calibri", Font.BOLD, 16));
        info.setBackground(new Color(102, 0, 51));
        info.setForeground(Color.pink);
        info.setAlignmentX(150);
        info.setText("Waiting for " + "\n" + "the Leader...");
        info.setVisible(true);
        textInfo.add(info);
        textInfo.setVisible(true);
        textInfo.add(info);
        waitingLeader.add(textInfo);




        return waitingLeader;
    }


    public JPanel getPlayersSuggestionPanel() {

        JPanel QAPanel = new JPanel (new GridLayout(5,0));
        QAPanel.setSize(300,600);
        QAPanel.setBackground(new Color(102, 0, 51));

        //Creating  Name Label
        playerNameLabel6 = new JLabel();
        playerNameLabel6.setFont(new Font("Impact" , Font.ITALIC , 32));

        playerNameLabel6.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel6.setHorizontalAlignment(JLabel.CENTER);
        QAPanel.add(playerNameLabel6);


        //Creating Question Panel
        JPanel questionPanel = new JPanel(new GridLayout(2,0));
        questionPanel.setSize(600,50);
        questionPanel.setBackground(new Color(102,0,51));
        QAPanel.add(questionPanel);
        //Grid Object 1
        String word1 = "<html><b><font face = Candara size = 6 color = white>What is the word for</font></b></html>";
        JLabel word2 = new JLabel(word1);
        word2.setHorizontalAlignment(JLabel.CENTER);
        word2.setVerticalAlignment(JLabel.CENTER);
        questionPanel.add(word2);
        //Grid Object 2

        question2 = new JLabel();
        question2.setHorizontalAlignment(JLabel.CENTER);
        question2.setVerticalAlignment(JLabel.CENTER);
        questionPanel.add(question2);


        //Creating Suggestion Panel
        JPanel suggestionPanel = new JPanel(new FlowLayout());
        suggestionPanel.setSize(600,50);
        suggestionPanel.setBackground(Color.PINK);
        QAPanel.add(suggestionPanel);
        suggestionPanel.setBorder(BorderFactory.createTitledBorder("Your Suggestion"));
        suggestion = new JTextField(10);
        suggestionPanel.add(suggestion);

        //Creating Submit button
        submitSuggestion = new JButton();
        submitSuggestion.setText(("Submit Suggestion"));
        suggestionPanel.add(submitSuggestion);

        submitSuggestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendSuggestionButtonPressed();
            }
        });

        //TextArea
        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 50);
        textInfo.setBackground(new Color(102, 0, 51));
        informationSubmitSuggestion = new JTextArea();
        informationSubmitSuggestion.setEditable(false);
        informationSubmitSuggestion.setFont(new Font("Calibri", Font.BOLD, 16));
        informationSubmitSuggestion.setBackground(new Color(102, 0, 51));
        informationSubmitSuggestion.setForeground(Color.pink);
        informationSubmitSuggestion.setAlignmentX(150);
        informationSubmitSuggestion.setVisible(true);
        informationSubmitSuggestion.setText("Enter your Suggestion");
        textInfo.add(informationSubmitSuggestion);
        textInfo.setVisible(true);
        QAPanel.add(textInfo);
        QAPanel.setVisible(true);


        return QAPanel;
    }



    public JPanel getUsersChoicePanel() {
        JPanel choicesPanel = new JPanel (new GridLayout(5,0));
        choicesPanel.setSize(300,600);
        choicesPanel.setVisible(true);
        choicesPanel.setBackground(new Color(102, 0, 51));

        //Player Name
        playerNameLabel7 = new JLabel();
        playerNameLabel7.setFont(new Font("Impact" , Font.ITALIC , 32));

        playerNameLabel7.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel7.setHorizontalAlignment(JLabel.CENTER);

        //Adding 1
        choicesPanel.add(playerNameLabel7);



        JLabel options = new JLabel();
        String o = "<html> <font color = yellow> Pick your option below </font></html>";
        options.setText(o);
        options.setBackground(Color.pink);
        options.setFont(new Font("Calibri", Font.BOLD, 16));
        options.setVerticalAlignment(JLabel.CENTER);
        options.setHorizontalAlignment(JLabel.CENTER);

        //Adding 2
        choicesPanel.add(options);

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBackground(new Color(102, 0, 51));
        o1 = new JRadioButton();
        o1.setFont(new Font("Calibri", Font.BOLD, 16));
        o1.setForeground((Color.pink));
        o1.setSelected(true);
        o2 = new JRadioButton();
        o2.setFont(new Font("Calibri", Font.BOLD, 16));
        o2.setForeground((Color.pink));
        o3 = new JRadioButton();
        o3.setFont(new Font("Calibri", Font.BOLD, 16));
        o3.setForeground((Color.pink));

        ButtonGroup group = new ButtonGroup();
        group.add(o1);
        group.add(o2);
        group.add(o3);

        optionsPanel.add(o1, BorderLayout.NORTH);
        optionsPanel.add(o2, BorderLayout.CENTER);
        optionsPanel.add(o3, BorderLayout.SOUTH);

        //Adding 4
        choicesPanel.add(optionsPanel);

        JPanel submit = new JPanel(new FlowLayout());
        submit.setBackground(new Color(102, 0, 51));
        submitOption = new JButton(" Submit Option");

        submitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.submitOptionButtonPressed();
            }
        });

        submit.add(submitOption);
        choicesPanel.add(submit);

        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 50);
        textInfo.setBackground(new Color(102, 0, 51));
        informationSubmitChoice = new JTextArea();
        informationSubmitChoice.setEditable(false);
        informationSubmitChoice.setFont(new Font("Calibri", Font.BOLD, 16));
        informationSubmitChoice.setBackground(new Color(102, 0, 51));
        informationSubmitChoice.setForeground(Color.pink);
        informationSubmitChoice.setAlignmentX(150);
        informationSubmitChoice.setVisible(true);
        textInfo.add(informationSubmitChoice);
        textInfo.setVisible(true);
        choicesPanel.add(textInfo);

        return choicesPanel;

    }

    public JPanel getResultsPanel() {

        //Main results Panel
        JPanel resultsPanel = new JPanel (new GridLayout(5,0));
        resultsPanel.setSize(300,600);
        resultsPanel.setVisible(true);
        resultsPanel.setBackground(new Color(102, 0, 51));

        //Player Name
        playerNameLabel8 = new JLabel();
        playerNameLabel8.setFont(new Font("Impact" , Font.ITALIC , 32));

        playerNameLabel8.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel8.setHorizontalAlignment(JLabel.CENTER);
        resultsPanel.add(playerNameLabel8);


        //Round Results Panel
        JPanel roundResults = new JPanel(new FlowLayout());
        roundResults.setBackground(Color.lightGray);
        roundResults.setVisible(true);
        roundResults.setBorder(BorderFactory.createTitledBorder("Round Results"));


        round = new JLabel();
        round.setFont(new Font("Calibri", Font.BOLD, 14));

        round.setVerticalAlignment(JLabel.CENTER);
        round.setHorizontalAlignment(JLabel.CENTER);
        roundResults.add(round);


        JPanel overallResults = new JPanel(new FlowLayout());
        overallResults.setBackground(Color.gray);
        overallResults.setVisible(true);
        overallResults.setBorder(BorderFactory.createTitledBorder("Overall Results"));

        overall = new JTextArea();
        overall.setFont(new Font("Calibri", Font.PLAIN, 14));
        overall.setBackground(Color.gray);

        overallResults.add(overall);

        resultsPanel.add(roundResults);
        resultsPanel.add(overallResults);


        //Adding Next Round Button
        JPanel next = new JPanel(new FlowLayout());
        next.setBackground(new Color(102, 0, 51));
        nextRound = new JButton();
        nextRound.setText("Next Round");
        next.add(nextRound);
        nextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.nextRoundPressed();
            }
        });

        exitButton = new JButton();
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exitGameButtonPressed();
            }
        });
        exitButton.setText("Exit Game");
        exitButton.setEnabled(true);
        next.add(exitButton);
        resultsPanel.add(next);




        JPanel textInfo = new JPanel(new FlowLayout());
        textInfo.setSize(300, 50);
        textInfo.setBackground(new Color(102, 0, 51));
        informationResults = new JTextArea();
        informationResults.setEditable(false);
        informationResults.setFont(new Font("Calibri", Font.BOLD, 16));
        informationResults.setBackground(new Color(102, 0, 51));
        informationResults.setForeground(Color.pink);
        informationResults.setAlignmentX(150);
        informationResults.setVisible(true);
        textInfo.add(informationResults);
        textInfo.setVisible(true);
        resultsPanel.add(informationResults);





        return resultsPanel;

    }








}






