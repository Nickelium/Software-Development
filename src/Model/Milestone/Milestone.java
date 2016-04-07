package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;

import java.util.Objects;

import static java.lang.Character.*;

/**
 * Created by Laurens on 27/03/2016.
 */
public class Milestone {

    private String milestoneID;

    public Milestone(String milestoneID) throws ReportErrorToUserException {
        setMilestoneID(milestoneID);
    }

    public Milestone(){
        this.milestoneID = "M0";
    }

    public String getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(String milestoneID) throws ReportErrorToUserException {
        if(isValidMilestoneID(milestoneID))
            this.milestoneID = milestoneID;
        else
            throw new ReportErrorToUserException("Invalid milestoneID has been supplied");
    }

    public boolean isValidMilestoneID(String milestoneID){
        if(!(Objects.equals(milestoneID.substring(0, 1), "M")))
            return false;
        char[] chars = new char[milestoneID.length()-1];
        milestoneID.getChars(1,milestoneID.length(),chars, 0);
        for(char c : chars){
            if(!(isDigit(c) || c == '.'))
                return false;
        }

        // Er moeten tussen twee punten minstens één digit staan
        if(!hasDigitBetweenEveryCoupleDots(chars))
            return false;

        // Einde string moet een digit zijn
        if(chars[chars.length-1] == '.')
            return false;

        // Check if max versionnumber < 100
        if(!isLimitedTo100(chars))
            return false;

        // Alle testen doorstaan --> valid ID
        return true;
    }

    private boolean isLimitedTo100(char[] chars){
        for(int i=0; i<=chars.length-3; i++){
            if(isDigit(chars[i]) && isDigit(chars[i+1]) && isDigit(chars[i + 2])){
                return false;
            }
        }
        return true;
    }

    private boolean hasDigitBetweenEveryCoupleDots(char[] chars){
        for(int i=0; i<=chars.length-2; i++){
            if(chars[i] == '.' && chars[i+1] == '.'){
                return false;
            }
        }
        return true;
    }

    public double getIDvalue(){
        char[] chars = new char[milestoneID.length()-1];
        milestoneID.getChars(1,milestoneID.length(),chars, 0);

        double idValue = 0.0;
        String stringValue = "";

        int i = 0;

        // First Iteration
        if(isDigit(chars[i]) && chars.length == 1) {
            stringValue = stringValue + chars[i]  + ".0";
            i++;
        }

        else if(isDigit(chars[i]) && isDigit(chars[i+1]) && chars.length == 2) {
            stringValue = stringValue + chars[i] + chars[i + 1] + ".0";
            i = i+2;
        }

        else if(isDigit(chars[i]) && isDigit(chars[i+1])) {
            stringValue = stringValue + chars[i] + chars[i + 1] + ".";
            i = i+2;
        }

        else{
            stringValue = stringValue + chars[i] + ".";
            i++;
        }

        // Loop for other part of string
        while(i<=chars.length-1){

            if(chars[i] == '.') {
                i = i+1;
            }

            else if(isDigit((chars[i])) && i == chars.length-1){
                stringValue = stringValue + "0" + chars[i];
                i = i+1;
            }

            else if(isDigit((chars[i])) && chars[i+1] == '.'){
                stringValue = stringValue + "0" + chars[i];
                i = i+2;
            }

            // isDigit(chars[i]) && isDigit(chars[i+1]
            else {
                stringValue = stringValue + chars[i] + chars[i+1];
                i = i+2;
            }
        }

        idValue = Double.parseDouble(stringValue);
        return idValue;
    }
}
