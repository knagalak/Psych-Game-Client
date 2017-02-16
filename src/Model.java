/**
 * Created by csclass on 10/24/16.
 */
public class Model {

    public String usertoken;
    public String username;
    public String gametoken;
    public String participantName;
    public int participantScore;


    public String question;
    public String correctAnswer;


    public void setUserToken(String userToken) {
        this.usertoken = userToken;
    }

    public String getUsertoken(){
        return usertoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setGametoken(String gametoken){
        this.gametoken = gametoken;
    }

    public String getGametoken(){
        return gametoken;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }

    public void setCorrectAnswer(String answer){
        this.correctAnswer = answer;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }


















}
