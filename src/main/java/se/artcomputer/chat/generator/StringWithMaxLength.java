package se.artcomputer.chat.generator;

import java.util.Objects;

public abstract class StringWithMaxLength {
    private String value;

    protected StringWithMaxLength(String value) {
        String trim = value.trim();
        this.value = trim.substring(0, Math.min(trim.length(), getMaxLength()));
    }

    protected StringWithMaxLength() {

    }

    protected abstract int getMaxLength();

    protected void setValue(String value) {
        this.value = value;
    }

    protected String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringWithMaxLength)) return false;
        StringWithMaxLength that = (StringWithMaxLength) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
