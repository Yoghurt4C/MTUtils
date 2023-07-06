package Zeno410Utils;


/**
 * @author Zeno410
 * <p>
 * IMPORTANT: Use of this interface implies a contract in which the implementing object will
 * remember its trackers and update them whenever it's changed. This is not an enforceable contract,
 * and if you don't follow it you get a obnoxious and hard-to-find fictitious implementation bug.
 */

public interface Trackable<Type> {
    void informOnChange(Acceptor<Type> target);

    void stopInforming(Acceptor<Type> target);
}
