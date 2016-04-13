package Model.Mail;

import Model.Memento.Memento;

public class ObserverAspectMemento extends Memento<ObserverAspect>
{
	private Subject structure;
	
	
	public ObserverAspectMemento(ObserverAspect originator) 
	{
		super(originator);
		this.structure = originator.structure;
	}
	
	
	Subject getStructure()
	{
		return structure;
	}

}
