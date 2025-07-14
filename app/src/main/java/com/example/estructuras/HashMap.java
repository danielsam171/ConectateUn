package com.example.estructuras;

public class HashMap<K, V> {
    private int size;
    private Nodo<K, V>[] table;

    public HashMap(int capacity) {
        this.size = 0;
        this.table = new Nodo[capacity];
    }

    public void put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (table[index] == null) {
            table[index] = new Nodo<>(key, value);
        } else {
            Nodo<K, V> current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Nodo<>(key, value);
        }
        size++;
    }

    public V get(K key) {
        int index = key.hashCode() % table.length;
        Nodo<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private static class Nodo<K, V> {
        K key;
        V value;
        Nodo<K, V> next;

        Nodo(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}





