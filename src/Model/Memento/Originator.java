package Model.Memento;

/**
 * Interface of an originator.
 * The originator will work together with a memento to build a memento and restore from that memento.
 *
 * @param <T>
 * @param <R>
 */
public interface Originator<T extends Memento<R>, R> 
{
	/**
	 * Method to create a memento.
	 * 
	 * @return The memento
	 */
	public Memento<R> createMemento();
	
	/**
	 * Method to restore the originator given the memento
	 * 
	 * @param memento The memento to restore from
	 */
	public void restoreMemento(T memento);
}
