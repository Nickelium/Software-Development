package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;

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
public class Milestone implements Comparable<Milestone> {

    /**
     * The milestone ID has no limits in terms of layering (number of dot separated numbers),
     * but numbers it contains must be in the range of the int data structure.
     */
    private int[] layeredMilestone;

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
     *
     * @throws ReportErrorToUserException is thrown if the milestoneID is invalid
     */
    public Milestone() throws ReportErrorToUserException {
        this.setMilestoneID("M0");
    }

    /**
     * Method to return the value of the milestone ID as a string.
     * @return the value of the milestone ID as a string
     */
    public String getMilestoneID() {
        return this.toString();
    }

    /**
     * Method to return the layered representation of the milestone ID.
     * @return the layered representation of the milestone ID
     */
    public int[] getLayeredMilestone() {
        return this.layeredMilestone;
    }

    /**
     * Method to set the layered milestone of the milestone object.
     * Also saves a string representation of the object.
     *
     * @param milestoneID the milestone ID that needs to be set.
     * @throws ReportErrorToUserException is thrown if the given milestone ID is not valid.
     */
    public void setMilestoneID(String milestoneID) throws ReportErrorToUserException {
        if (isValidMilestoneID(milestoneID)) {
            this.layeredMilestone = milestoneStringToArray(milestoneID);
        } else {
            throw new ReportErrorToUserException("Invalid milestoneID has been supplied");
        }
    }

    /**
     * Method to create the layered milestone representation of a given milestone.
     * Each layer represents a level in the dot separated structure of the milestone ID.
     *
     * @param milestoneID the milestone ID of which the layered milestone has to be made
     * @return the layered milestone ID
     */
    private int[] milestoneStringToArray(String milestoneID) {
        if (milestoneID == null) throw new IllegalArgumentException("milestoneId is null");
        String milestoneNumbers = milestoneID.substring(1);
        String[] stringArray = milestoneNumbers.split("\\.");
        int[] intArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }

    /**
     * Method to determine whether a given milestone ID is a valid milestone ID.
     *
     * Requirements for a valid milestone ID:
     * the milestone ID has to start with a capital letter M.
     * all non-numerical characters have to be dots.
     * between each couple of dots there has to be at least one digit.
     * the last character of the milestone ID has to be a digit.
     *
     * @param milestoneID the milestone ID that has to be checked.
     * @return true if the milestone ID is valid, false if not.
     */
    public boolean isValidMilestoneID(String milestoneID) {
        if (milestoneID == null) return false;
        // the milestone ID has to start with a capital letter M.
        if (!(Objects.equals(milestoneID.substring(0, 1), "M")))
            return false;

        // all non-numerical characters have to be dots.
        char[] chars = new char[milestoneID.length() - 1];
        milestoneID.getChars(1, milestoneID.length(), chars, 0);
        for (char c : chars) {
            if (!(isDigit(c) || c == '.'))
                return false;
        }

        // between each couple of dots there has to be at least one digit.
        if (!hasDigitBetweenEveryCoupleDots(chars))
            return false;

        // the last character of the milestone ID has to be a digit.
        if (chars[chars.length - 1] == '.')
            return false;

        // All tests passed: string is valid.
        return true;
    }

    /**
     * Method to check if there is a digit between every couple of dots
     *
     * @param chars a list of characters representing the milestone ID
     * @return true if there is a digit between every couple of dots, false if not
     */
    private boolean hasDigitBetweenEveryCoupleDots(char[] chars) {
        if (chars == null) return false;
        for (int i = 0; i <= chars.length - 2; i++) {
            if (chars[i] == '.' && chars[i + 1] == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to compare milestone to each other. The values of the milestones are
     * evaluated at each level of the layered milestone. When a difference is found,
     * the comparison is finished.
     *
     * @param m1 the first milestone
     * @param m2 the second milestone
     * @return 1 if the first milestone ID is greater than the second milestone ID
     *         0 if both milestone IDs are equal to each other
     *         -1 if the first milestone ID is smaller than the second milestone ID
     */
    private int compareMilestones(Milestone m1, Milestone m2) {
        if (m1 == null) throw new IllegalArgumentException("Milestone 1 is null");
        if (m2 == null) throw new IllegalArgumentException("Milestone 2 is null");
        int[] layeredMilestone1 = m1.getLayeredMilestone();
        int[] layeredMilestone2 = m2.getLayeredMilestone();
        int lengthL1 = layeredMilestone1.length;
        int lengthL2 = layeredMilestone2.length;
        int index = 0;

        while (index < lengthL1 && index < lengthL2) {
            if (layeredMilestone1[index] > layeredMilestone2[index])
                return 1;
            else if (layeredMilestone1[index] < layeredMilestone2[index])
                return -1;

            // if both layers are equal, do nothing

            // Checks needed for milestones with different number of layers
            if (index == lengthL1 - 1 && index < lengthL2 - 1)
                return -1; // milestone2 has more layers, is therefor bigger than milestone1
            else if (index < lengthL1 - 1 && index == lengthL2 - 1)
                return 1; // milestone1 has more layers, is therefor bigger than milestone2
            else if (index == lengthL1 - 1 && index == lengthL2 - 1)
                return 0;

            // No changes in current layer, continue with next layer
            index++;
        }

        // Program should never be able to reach this; consistency checks while creating
        // Milestone object (setMilestoneID) prevents this.
        return 0;
    }

    /**
     * Returns a string representation of the layered milestone ID object.
     * @return a string representation of the milestone ID
     */
    @Override
    public String toString() {
        String milestone = "M";
        for (int layer : this.getLayeredMilestone()) {
            milestone = milestone + "." + layer;
        }
        return milestone.substring(0, 1) + milestone.substring(2);
    }

    /**
     * The value 0 if the argument is a Milestone lexicographically equal to this Milestone;
     * a value of 1 if the argument is a Milestone lexicographically less than this Milestone.
     * a value of -1 if the argument is a Milestone lexicographically greater than this Milestone;
     *
     * @param o the other milestone to compare this milestone to
     * @return 1 if this milestone > the value of o, 0 if this milestone = the value of o, -1 if this milestone < the value of o.
     */
    @Override
    public int compareTo(Milestone o) {
        return compareMilestones(this, o);
    }
    
    /**
     * Method to check if the given object equals this milestone
     * 
     * @param obj The object to compare
     * 
     * @return Return whether the object equals this milestone or not
     * 
     */
    @Override
    public boolean equals(Object obj)
    {
    	if(obj == null) return false;
    	if(!(obj instanceof Milestone)) return false;
    	
    	try
    	{
    		Milestone milestone = (Milestone)obj;
    		return compareTo(milestone) == 0;
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    	
    }
}
