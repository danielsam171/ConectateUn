package com.example.estructuras;

//clase nodo , con Key , Value  pensado para almacenar ID , Estudiante
public class Nodo <K, V> {
    K key;
    V value;
    Nodo<K, V> next;
    //nodo que apunta al siguiente, pensado en lista enlazadas al haber colisiones

    Nodo(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

}
