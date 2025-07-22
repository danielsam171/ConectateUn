package com.example.estructuras;

import java.util.ArrayList;
import java.util.function.BiConsumer; 

public class HashMap<K, V> {
    private int size;
    private static final double LOAD_FACTOR = 0.75;
    private Nodo<K, V>[] table;

    public HashMap(int capacity) {
        this.size = 0;
        this.table = new Nodo[capacity];
    }

    //Insertar valor al Hashmap
    public void put(K key, V value) {
        if ((double) (size + 1) / table.length > LOAD_FACTOR) {
            rehash(); // duplica la tabla y reubica todo
        }
        int index = Math.abs(key.hashCode()) % table.length;
        Nodo<K, V> current = table[index];

        // Si la lista está vacía, se inserta ahí
        if (current == null) {
            table[index] = new Nodo<>(key, value);
            size++;
            return;
        }

        // Recorre la lista para ver si la clave ya existe
        while (current != null) {
            if (current.key.equals(key)) {
                // Ya hay un estudiante con este ID
                System.out.println("ID Estudiante ya existe;");
                return;
            }
            if (current.next == null) break; // llegamos al final de la lista
            current = current.next;
        }
        // Si no estaba, agregamos al final
        current.next = new Nodo<>(key, value);
        size++;
    }

    //Obtener nodo del hash con su Key
    public V get(K key) {
        int index = Math.abs(key.hashCode()) % table.length;
        Nodo<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    // Actualiza el valor de una clave solo si ya existe
    public boolean update(K key, V value) {
        int index = Math.abs(key.hashCode()) % table.length;
        Nodo<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value; // cambia el valor
                return true;
            }
            current = current.next;
        }
        return false; // no existe la clave
    }

    // Elimina una clave (y su valor) si existe
    public void remove(K key) {
        int index = Math.abs(key.hashCode()) % table.length;
        Nodo<K, V> current = table[index];
        Nodo<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    // Es el primer nodo del bucket
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Duplica el tamaño de la tabla y reinserta todo (rehash)
    private void rehash() {
        Nodo<K, V>[] oldTable = table;
        table = new Nodo[oldTable.length * 2];
        size = 0;
        // Recorremos todos los buckets
        for (Nodo<K, V> head : oldTable) {
            Nodo<K, V> current = head;
            while (current != null) {
                // Volvemos a insertar cada clave/valor en el nuevo array
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    // Imprime el contenido interno del HashMap
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print("Espacio " + i + ": ");
            Nodo<K, V> current = table[i];
            if (current == null) {
                System.out.println("vacío");
            } else {
                while (current != null) {
                    System.out.print("[" + current.key + " -> " + current.value + "]");
                    if (current.next != null) System.out.print(" -> ");
                    current = current.next;
                }
                System.out.println();
            }
        }
    }

    // Devuelve el número de elementos almacenados
    public int Getsize() {
        return size;
    }

    //TODO!: Implementar métodos para poder iterar en la clase 
    //TODO!: Se requieren para construir el grafo multicapa

    //TODO: Retorna un ArrayList con todos los valores almacenados en los nodos del HashMap
    public ArrayList<V> values() {
        ArrayList<V> list = new ArrayList<>();
        for (Nodo<K,V> head : table) {
            Nodo<K,V> cur = head;
            while (cur != null) {
                list.add(cur.value);
                cur = cur.next;
            }
        }
        return list;
    }
    
    // TODO: Retorna un ArrayList con todos los keys almacenados en los nodos del HashMap
    public ArrayList<K> keys() {
        ArrayList<K> list = new ArrayList<>();
        for (Nodo<K,V> head : table) {
            Nodo<K,V> cur = head;
            while (cur != null) {
                list.add(cur.key);
                cur = cur.next;
            }
        }
        return list;
    }
    
    // TODO: Permite realizar una acción para cada par (key, value) del HashMap. Se están recorriendo todos los todos los pares (key,value)
    // TODO: BiConsumer es una interfaz funcional que recibe dos parámetros y no devuelve nada. Permite hacer programación funcional.
    // TODO: Se emplea para construir el grafo multicapa
    public void forEach(BiConsumer<K,V> action){
        for (Nodo<K,V> head : table) {
            Nodo<K,V> cur = head;
            while (cur != null) {
                action.accept(cur.key, cur.value);
                cur = cur.next;
            }
        }
    }
    
    
}





