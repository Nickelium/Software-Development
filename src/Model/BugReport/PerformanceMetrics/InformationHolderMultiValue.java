package Model.BugReport.PerformanceMetrics;

// VALUE CLASS

import java.util.ArrayList;
import java.util.List;

//TODO
public class InformationHolderMultiValue implements IInformationHolder<List<IInformationHolder<String>>> {

    //TODO
    private String description;

    //TODO
    private List<IInformationHolder<String>> value;

    //TODO
    InformationHolderMultiValue(String description) {
        setDescription(description);
        this.value = new ArrayList<IInformationHolder<String>>();
    }

    //region getters & setters

    //TODO
    public String getDescription() {
        return description;
    }

    //TODO
    private void setDescription(String description) {
        this.description = description;
    }

    //TODO
    public List<IInformationHolder<String>> getValue() {
        return value;
    }

    //TODO
    void addValue(IInformationHolder<String> informationHolder) {
        this.value.add(informationHolder);
    }

    //endregion

    @Override
    public String toString() {
        String str = description + ":";
        for (IInformationHolder<String> value : getValue()) {
            str += "  + " + value.toString() + "\n";
        }
        return str;
    }

}
