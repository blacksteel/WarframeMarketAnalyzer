package enums.comparable.interfaces;

public interface ComparableByValue<T>{
	public abstract T getValue();
	public abstract boolean valueEquals(T object);
}
