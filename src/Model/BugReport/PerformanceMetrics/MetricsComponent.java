package Model.BugReport.PerformanceMetrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Karina on 06.05.2016.
 */
public class MetricsComponent {

    private String title;
    private ArrayList<InformationHolder> information = new ArrayList<>();

    MetricsComponent(String title) {
        setTitle(title);
    }

    //region getters & setters

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public List<InformationHolder> getInformation() {
        return Collections.unmodifiableList(information);
    }

    //endregion

    //add one information holder
    void addInformation(String description, Double value) {
        information.add(new InformationHolder(description, value));
    }

}
