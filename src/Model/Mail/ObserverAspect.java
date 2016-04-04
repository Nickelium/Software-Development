package Model.Mail;

public abstract class ObserverAspect implements Observer{

	protected Subject s;
	
	public ObserverAspect(Subject s)
	{
		if(s != null)
		{
			this.s = s;
			s.addObserver(this);
		}
	}
	
	@Override
	public abstract void update(Subject s, Object aspect);

	public void destructor()
	{
		if(s != null)
		{
			s.removeObserver(this);
			s = null;
		}
	}
	
	@Override
	public abstract String toString();

}
