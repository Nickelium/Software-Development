package Model.BugReport.PerformanceMetrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a metrics component object.
 * A performance metric usually consists of a number of metrics components,
 * each denoting a certain skill in the title, and an array list
 * with information holders about a certain skill.
 */
public class MetricsComponent {

    /**
     * A metrics component contains a title, denoting which skill is
     * being checked in the information component.
     */
    private String title;

    /**
     * The information array list contains information holders for each property
     * that needs to be checked for the performance metric.
     */
    private ArrayList<InformationHolder> information = new ArrayList<>();

    /**
     * Package visible constructor to create a new metrics component object.
     *
     * @param title the skill for which the component will hold information.
     */
    MetricsComponent(String title) {
        setTitle(title);
    }

    //region getters & setters

    /**
     * Returns the title of the metrics component.
     * @return the title of the metrics component.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set a new title for the metrics component.
     * @param title the new title to be set.
     */
    private void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the list with information holders about a certain skill.
     * @return an unmodifiable list of all informations about a skill.
     */
    public List<InformationHolder> getInformation() {
        return Collections.unmodifiableList(information);
    }

    //endregion

    //add one information holder

    /**
     * Package visible method to add a new information holder to this metrics component.
     *
     * @param description the description to what kind of information is contained in
     *                    the information holder object.
     * @param value the value of the new information holder.
     */
    void addInformation(String description, Double value) {
        information.add(new InformationHolder(description, value));
    }

}
