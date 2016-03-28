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
        if(isValidMilestoneID(milestoneID))
            this.milestoneID = milestoneID;
        else
            throw new ReportErrorToUserException("Invalid milestoneID has been supplied");
    }

    public Milestone(){
        this.milestoneID = "M0";
    }

    public String getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(String milestoneID) {
        this.milestoneID = milestoneID;
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

        // Volledige string is OK!
        return true;
    }

    public double getIDvalue(){
        char[] chars = new char[milestoneID.length()-1];
        milestoneID.getChars(1,milestoneID.length(),chars, 0);
        int level = 0;
        double idValue = 0.0;

        for(char c: chars){
            if(isDigit(c)) {
                idValue = idValue + (c * Math.pow(10, level));
                level = level - 1;
            }
        }
        return idValue;
    }
}
