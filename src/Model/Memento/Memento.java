package Model.Memento;

/**
 * Interface of a memento.
 * 
 *
 * @param <T> The type of the memento.
 */
public abstract class Memento<T>
{
	private T originator;
	
	/**
	 * Constructor of this memento
	 * 
	 * @param originator The originator to build a memento from
	 * 
	 * @throws IllegalArgumentException the originator is null
	 */
	public Memento(T originator)
	{
		if(originator == null) throw new IllegalArgumentException("The originator cannot be null");
		this.originator = originator;
	}
	
	/**
	 * Getter to get the originator of this memento
	 * 
	 * @return The originator
	 */
	public T getOriginator()
	{
		return originator;
	}
}
