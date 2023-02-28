/**
 * This class is used to create a player object that contains the player's name, wins, and losses.
 */
package boardgame.game;

public class Player implements Saveable{
    // Declaring the variables that will be used in the class.
    private String userStr = "";
    private int wins = 0;
    private int loses = 0;
    
    /**
     * This function sets the userStr variable to the name parameter
     * 
     * @param name The name of the user.
     */
    public void setUser(String name){
        userStr = name;
    }
    
    /**
     * This function returns the userStr variable
     * 
     * @return The userStr variable is being returned.
     */
    public String getUser(){
        return userStr;
    }
    
    /**
     * This function sets the number of wins for the player
     * 
     * @param winNum The number of wins the player has.
     */
    public void setWins(int winNum){
        wins = winNum;
    }
    
    /**
     * This function returns the number of wins the player has
     * 
     * @return The number of wins.
     */
    public int getWins(){
        return wins;
    }
    
    /**
     * This function adds one to the variable wins
     */
    public void addWins(){
        wins += 1;
    }

    /**
     * This function sets the number of losses to the value of the parameter loseNum
     * 
     * @param loseNum The number of losses the player has.
     */
    public void setLosses(int loseNum){
        loses = loseNum;
    }
    
    /**
     * This function returns the number of losses the player has
     * 
     * @return The number of losses.
     */
    public int getLosses(){
        return loses;
    }
    
    /**
     * This function adds one to the loses variable
     */
    public void addLosses(){
        loses += 1;
    }
    
    /**
     * It returns a string that contains the user's name, wins, and losses
     * 
     * @return The user's name, the number of losses, and the number of wins.
     */
    public String getStringToSave(){
        return getUser() + " " + getWins() + " " + getLosses();
    }
    
    /**
     * This function takes a string, splits it into an array of strings, and then sets the user's name,
     * wins, and losses based on the values in the array
     * 
     * @param toLoad String
     */
    public void loadSavedString(String toLoad){
        String[] strArr = toLoad.split(" ");
        if (strArr.length == 3){
            String name = strArr[0];
            setUser(name);
            wins = Integer.parseInt(strArr[1]);
            setWins(wins);
            loses = Integer.parseInt(strArr[2]);
            setLosses(loses);
        }
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The userStr, wins, and loses.
     */
    @Override
    public String toString() {
        return "Player [userStr=" + userStr + ", wins=" + wins + ", loses=" + loses + "]";
    }
}
