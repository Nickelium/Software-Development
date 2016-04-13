package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportMemento;
import Model.Memento.Memento;

public class SubSystemMemento extends Memento<SubSystem>
{
	private List<SubSystem> subsystems;
	private List<SubSystemMemento> subsystemMementos = new ArrayList<>();
	
	private List<BugReport> bugreports;
	private List<BugReportMemento> bugreportMementos = new ArrayList<>();
	
	
	public SubSystemMemento(SubSystem originator)
	{
		super(originator);
		this.subsystems = new ArrayList<>(originator.getSubSystems());
		for(SubSystem subsystem : subsystems)
			subsystemMementos.add(subsystem.createMemento());
		
		this.bugreports = new ArrayList<>(originator.getBugReports());
		for(BugReport bugreport : bugreports)
			bugreportMementos.add(bugreport.createMemento());
		
	}
	
	List<SubSystem> getSubSystems()
	{
		return subsystems;
	}
	
	List<SubSystemMemento> getSubSystemMementos()
	{
		return subsystemMementos;
	}
	
	List<BugReport> getBugReports()
	{
		return bugreports;
	}
	
	List<BugReportMemento> getBugReportMementos()
	{
		return bugreportMementos;
	}

}