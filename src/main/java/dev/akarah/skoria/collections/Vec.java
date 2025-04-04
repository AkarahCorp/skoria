package dev.akarah.skoria.collections;

import dev.akarah.skoria.util.Nullable;

public class Vec<T> {
    @SuppressWarnings("unchecked")
    T[] inner = (T[]) new Object[] {};
    int len = 0;

    public static <T> Vec<T> empty() {
        return new Vec<>();
    }

    @SuppressWarnings("unchecked")
    public static <T> Vec<T> withCapacity(int capacity) {
        var vect = new Vec<T>();
        vect.inner = (T[]) new Object[capacity];
        return vect;
    }

    @SuppressWarnings("unchecked")
    public void grow() {
        if (this.len == 0) {
            this.len = 1;
        }
        if (this.len >= this.inner.length) {
            var newArray = (T[]) new Object[this.len * 2];

            System.arraycopy(this.inner, 0, newArray, 0, this.inner.length);
            this.inner = newArray;
        }
    }

    public void push(T value) {
        this.len += 1;
        this.grow();

        this.inner[len - 1] = value;
    }

    public void set(int index, T value) {
        this.grow();

        if (index < 0 || index >= this.len - 1) {
            return;
        }
        this.inner[index] = value;
    }

    public Nullable<T> get(int index) {
        if (index < 0 || index >= this.len) {
            return Nullable.ofNull();
        }
        return Nullable.of(this.inner[index]);
    }

    public void insert(int index, T value) {
        if (index < 0 || index >= this.len) {
            return;
        }

        this.len++;
        this.grow();
        System.arraycopy(this.inner, index, this.inner, index + 1, this.len - index);
        this.inner[index] = value;
    }

    public Nullable<T> remove(int index) {
        if (index < 0 || index >= this.len) {
            return Nullable.ofNull();
        }

        this.len -= 1;
        var tmp = this.inner[index];
        System.arraycopy(this.inner, index, this.inner, index - 1, this.len - index);
        return Nullable.of(tmp);
    }
}
