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
        //this.rel = new HashMap<>(10);
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


    public List<Nodo<Integer, Estudiante>> encontrarRutaPorDeporte(Nodo<Integer, Estudiante> origen, String deporteBuscado) {

        //Map<Nodo<Integer, Estudiante>, Nodo<Integer, Estudiante>> predecesor = new HashMap<>();
        Set<Nodo<Integer, Estudiante>> visitados = new HashSet<>();
        Queue<Nodo<Integer, Estudiante>> cola = new LinkedList<>();

        visitados.add(origen);
        cola.add(origen);

        Nodo<Integer, Estudiante> destinoFinal = null;

        while (!cola.isEmpty()) {
            Nodo<Integer, Estudiante> actual = cola.poll();

            if (!actual.equals(origen)) {
                for (String d : actual.value.getDeportesPracticados()) {
                    if (d.equalsIgnoreCase(deporteBuscado)) {
                        destinoFinal = actual;
                        break;
                    }
                }
                if (destinoFinal != null) break;
            }

            for (Arista e : rel.getOrDefault(actual, List.of())) {
                Nodo<Integer, Estudiante> vecino = e.destino;
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    //predecesor.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        // Si no se encuentra a nadie
        if (destinoFinal == null) return List.of();

        // Reconstruir el camino desde destinoFinal hasta origen
        List<Nodo<Integer, Estudiante>> ruta = new LinkedList<>();
        Nodo<Integer, Estudiante> nodo = destinoFinal;

        while (nodo != null) {
            ruta.add(0, nodo); // insertar al inicio
            //nodo = predecesor.get(nodo);
        }
        return ruta; // la lista de nodos visitados
    }

    
    public void eliminarNodo(Nodo<Integer, Estudiante> nodoPorEliminar) {
        // eliminar nodo de la lista y del map
        nodos.remove(nodoPorEliminar);
        rel.remove(nodoPorEliminar);

        // borrar las referencias al nodo en los demas
        for (Nodo<Integer, Estudiante> nodo : rel.keySet()) {
            List<Arista> aristas = rel.get(nodo);
            List<Arista> aristasAEliminar = new ArrayList<>();

            // añadir las aristas que se quieren eliminar a una lista
            for (Arista arista : aristas) {
                if (arista.destino.equals(nodoPorEliminar)) {
                    aristasAEliminar.add(arista);
                }
            }
            // eliminarlas
            aristas.removeAll(aristasAEliminar);
        }
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

