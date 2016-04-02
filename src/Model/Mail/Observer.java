package Model.Mail;

public interface Observer<T> {
	
	public void update(Subject s, Object aspect);

}
