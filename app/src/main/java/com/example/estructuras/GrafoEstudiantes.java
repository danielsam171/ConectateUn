package com.example.estructuras;

import java.util.*;

public class GrafoEstudiantes {

    private static class Arista {
        private Nodo<Integer, Estudiante> destino;
        private String deporte;

        Arista(Nodo<Integer, Estudiante> destino, String deporte) {
            this.destino = destino;
            this.deporte = deporte;
        }
    }

    private List<Nodo<Integer, Estudiante>> nodos;
    private Map<Nodo<Integer, Estudiante>, List<Arista>> rel;

    public GrafoEstudiantes() {
        this.nodos = new ArrayList<>();
        this.rel = new HashMap<>();
    }

    public void addNodo(Nodo<Integer, Estudiante> nuevo) {
        nodos.add(nuevo);
        rel.putIfAbsent(nuevo, new ArrayList<>());    // añadir nodo a lista si no estaba

        for (int i = 0; i < nodos.size(); i++) { // recorrer lista
            Nodo<Integer, Estudiante> otro = nodos.get(i);
            if (nuevo.value.equals(otro.value)) continue; // saltar al siguiente si es el mismo

            Set<String> deportes = deportesCompartidos(nuevo.value, otro.value);
            if (!deportes.isEmpty()) {
                for (String d : deportes) {
                    rel.get(nuevo).add(new Arista(otro, d));
                    rel.get(otro).add(new Arista(nuevo, d)); // modificar ambos nodos conectados
                }
            }
        }
    }

    private Set<String> deportesCompartidos(Estudiante e1, Estudiante e2) {
        if (e1.getDeportesPracticados() == null || e2.getDeportesPracticados() == null) {
            return new HashSet<>(); // vacio
        }
        Set<String> set1 = new HashSet<>(Arrays.asList(e1.getDeportesPracticados()));
        Set<String> set2 = new HashSet<>(Arrays.asList(e2.getDeportesPracticados()));
        set1.retainAll(set2); // la interseccion
        return set1;
    }

    /*public void mostrarGrafoEstudiantes() {
        for (Nodo<Integer, Estudiante> nodo : rel.keySet()) {
            System.out.print(nodo.value.nombre + ": ");
            for (Arista arista : rel.get(nodo)) {
                System.out.print("{" + arista.destino.value.nombre + ", " + arista.deporte + "} ");
            }
            System.out.println();
        }
    }*/
}

