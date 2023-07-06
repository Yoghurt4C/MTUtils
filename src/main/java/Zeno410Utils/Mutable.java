package Zeno410Utils;

/**
 * @author Zeno410
 */
public interface Mutable<Type> extends Trackable<Type> {
    void set(Type newValue);

    Type value();
}
