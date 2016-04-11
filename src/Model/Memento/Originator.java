package Model.Memento;

public interface Originator<T extends Memento<R>, R> 
{
	public Memento<R> createMemento();
	
	public void restoreMemento(T memento);
}
