package com.example.estructuras;

public class Nodo <K, V> {

    K key;
    V value;
    Nodo<K, V> next;

    Nodo(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    

}
