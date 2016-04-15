package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;

import java.util.Comparator;
import java.util.Objects;

import static java.lang.Character.isDigit;

/**
 * Class representing a Milestone object.
 *
 * A milestone is a label given to a bug report, project or subsystem.
 * For a project or subsystem such a milestone represents the progress achieved
 * so far.
 *
 * A milestone is identified by a String value: the milestone ID.
 *
 * Milestones are comparable: e.g. M0.5 smaller than M1.2.1 and M1.2.1 smaller than M1.3.0
 *
 * Initially all projects and subsystems have the default achieved milestone: M0.
 */
public class Milestone implements Comparator<Milestone>, Comparable<Milestone> {

    /**
     * The milestone ID takes the form of dot-separated numbers
     * prefixed with the letter M (e.g. M0.5 or M1.2.1).
     *
     * The milestone ID has no limits in terms of layering (number of dot separated numbers),
     * but the numbers between the dots have to be in the range [0,99].
     */
    private String milestoneID;

    /**
     * Constructor to create a milestone object with a given milestone ID.
     *
     * @param milestoneID the given milestone ID
     * @throws ReportErrorToUserException is thrown if the milestoneID is not valid.
     */
    public Milestone(String milestoneID) throws ReportErrorToUserException {
        setMilestoneID(milestoneID);
    }

    /**
     * Constructor to create a milestone object with the default milestone ID (M0).
     */
    public Milestone(){
        this.milestoneID = "M0";
    }

    /**
     * Returns the milestone ID of the milestone object.
     *
     * @return the milestone ID of the milestone object.
     */
    public String getMilestoneID() {
        return milestoneID;
    }

    /**
     * Method to set the milestone ID of the milestone object.
     *
     * @param milestoneID the milestone ID that needs to be set.
     * @throws ReportErrorToUserException is thrown if the given milestone ID is not valid.
     */
    public void setMilestoneID(String milestoneID) throws ReportErrorToUserException {
        if(isValidMilestoneID(milestoneID))
            this.milestoneID = milestoneID;
        else
            throw new ReportErrorToUserException("Invalid milestoneID has been supplied");
    }

    /**
     * Method to determine whether a given milestone ID is a valid milestone ID.
     *
     * Requirements for a valid milestone ID:
     *      the milestone ID has to start with a capital letter M.
     *      all non-numerical characters have to be dots.
     *      between each couple of dots there has to be at least one digit.
     *      the last character of the milestone ID has to be a digit.
     *      every part of the version number has to be a number in range [0,99].
     *
     * @param milestoneID the milestone ID that has to be checked.
     * @return true if the milestone ID is valid, false if not.
     */
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

    /**
     * Method to check whether every part of the version number is in range of [0,99]
     * @param chars a list of characters representing the milestone ID
     * @return true if every part is in range of [0,99], false if not.
     */
    private boolean isLimitedTo100(char[] chars){
        for(int i=0; i<=chars.length-3; i++){
            if(isDigit(chars[i]) && isDigit(chars[i+1]) && isDigit(chars[i + 2])){
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if there is a digit between every couple of dots
     * @param chars a list of characters representing the milestone ID
     * @return true if there is a digit between every couple of dots, false if not
     */
    private boolean hasDigitBetweenEveryCoupleDots(char[] chars){
        for(int i=0; i<=chars.length-2; i++){
            if(chars[i] == '.' && chars[i+1] == '.'){
                return false;
            }
        }
        return true;
    }

    /**
     * Method that converts a String value of milestoneID to a double value,
     * representing a numerical value.
     *
     * Layers in the milestone hierarchy are represented by negative powers of base 10,
     * starting at power zero. After every layer, the power count goes down by 2.
     * e.g: M1.0.5 = 1,0005. or M2.6.3 = 2,0603 or M16.43.1 = 16,4301.
     *
     * @return the double value of the milestone ID, based upon the string value.
     */
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

    /**
     * Method to compare two different milestone objects.
     * Milestones are lexicographically ordered (e.g. M0.5 smaller than M1.2.1 and M1.2.1 smaller than M1.3.0)
     *
     * @param m1 the first milestone object
     * @param m2 the second milestone object
     * @return 1 if the id value of m1 greater than the id value of m2
     *         0 if the id value of both objects is equal
     *         -1 if the id value of m1 greater than the id value of m2
     */
    @Override
    public int compare(Milestone m1, Milestone m2) {
        if(m1.getIDvalue() > m2.getIDvalue())
            return 1;
        else if(m1.getIDvalue() == m2.getIDvalue())
            return 0;
        else
            return -1;
    }

    @Override
    public String toString(){
        return this.getMilestoneID();
    }

    @Override
    public int compareTo(Milestone o) {
        return this.compare(this, o);
    }
}
