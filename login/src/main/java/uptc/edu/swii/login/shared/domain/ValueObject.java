package uptc.edu.swii.login.shared.domain;

public abstract class ValueObject {
    // Value objects are immutable and compared by value
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}