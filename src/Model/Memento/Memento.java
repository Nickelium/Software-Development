package Model.Memento;

public abstract class Memento<T>
{
	private T originator;
	
	public Memento(T originator)
	{
		this.originator = originator;
	}
	
	public T getOriginator()
	{
		return originator;
	}
}
