package Model.BugReport.PerformanceMetrics;

// VALUE CLASS

/**
 * Value class representing an information holder object.
 * The information holder object contains a description about a certain way
 * that a part of a performance metric is measured. The associated value
 * for that metric is also contained within the object.
 */
public interface IInformationHolder<T> {

    //region getters & setters

    /**
     * Returns the description of an information holder
     *
     * @return the description field of the information holder object
     */
    String getDescription();

    /**
     * Returns the value of an information holder
     *
     * @return the value field of the information holder object
     */
    T getValue();

    //endregion

}
