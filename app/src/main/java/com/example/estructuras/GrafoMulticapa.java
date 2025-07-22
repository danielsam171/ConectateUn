package com.example.estructuras;//package com.example.estructuras;

// GrafoMulticapa.java
// Usa HashMap propio: com.example.estructuras.HashMap

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

// Conexión no dirigida con lista de deportes (capas)
class Conexion {
    int vecinoId;
    ArrayList<String> deportes = new ArrayList<>();

    Conexion(int vecinoId, String deporte) {
        this.vecinoId = vecinoId;
        if (deporte != null) deportes.add(deporte);
    }
}

public class GrafoMulticapa {

    // Lista de adyacencia multicapa
    private ArrayList<ArrayList<Conexion>> adj = new ArrayList<>();

    // Mapea idEstudiante -> índice en adj
    private HashMap<Integer,Integer> idToIndex;

    // Mapea índice -> idEstudiante
    private ArrayList<Integer> indexToId = new ArrayList<>();

    // --- CONSTRUCTOR ---
    public GrafoMulticapa(HashMap<Integer, Estudiante> hashEst,
                          HashMap<String, ArrayList<Estudiante>> hashDep) {

        // capacidad base para idToIndex (cualquier número >0)
        idToIndex = new HashMap<>(Math.max(16, hashEst.Getsize()*2));

        // 1. Crear índices
        ArrayList<Estudiante> todos = hashEst.values();
        for (int i = 0; i < todos.size(); i++) {
            Estudiante e = todos.get(i);
            idToIndex.put(e.getId(), i);
            indexToId.add(e.getId());
            adj.add(new ArrayList<>());
        }

        // 2. Conectar cliques por deporte
        hashDep.forEach((deporte, listaEst) -> {
            for (int i = 0; i < listaEst.size(); i++) {
                for (int j = i + 1; j < listaEst.size(); j++) {
                    int idA = listaEst.get(i).getId();
                    int idB = listaEst.get(j).getId();
                    agregarArista(idA, idB, deporte);
                }
            }
        });
    }

    // --- Agregar nodo ---
    public void agregarNodo(Estudiante nuevo,
                            HashMap<String, ArrayList<Estudiante>> hashDep) {

        int newIdx = adj.size();
        idToIndex.put(nuevo.getId(), newIdx);
        indexToId.add(nuevo.getId());
        adj.add(new ArrayList<>());

        // Conectar con estudiantes que compartan deporte
        for (String deporte : nuevo.getDeportesPracticados()) {
            ArrayList<Estudiante> lista = hashDep.get(deporte);
            if (lista == null) continue;
            for (Estudiante otro : lista) {
                if (otro.getId() != nuevo.getId()) {
                    agregarArista(nuevo.getId(), otro.getId(), deporte);
                }
            }
        }
    }

    // --- Eliminar nodo ---
    public void eliminarNodo(int id) {
        Integer idx = idToIndex.get(id);
        if (idx == null) return;

        adj.remove((int) idx);
        indexToId.remove((int) idx);

        for (ArrayList<Conexion> conexiones : adj) {
            conexiones.removeIf(c -> c.vecinoId == id);
        }

        // Reajustar índices
        for (int i = idx; i < indexToId.size(); i++) {
            int movedId = indexToId.get(i);
            idToIndex.update(movedId, i);
        }
        // También eliminamos la entrada vieja del map (por si quedó)
        idToIndex.remove(id);
    }

    // --- Reconstruir completamente ---
    public void actualizar(HashMap<Integer, Estudiante> hashEst,
                           HashMap<String, ArrayList<Estudiante>> hashDep) {
        adj.clear();
        indexToId.clear();
        idToIndex = new HashMap<>(Math.max(16, hashEst.Getsize()*2));

        ArrayList<Estudiante> todos = hashEst.values();
        for (int i = 0; i < todos.size(); i++) {
            Estudiante e = todos.get(i);
            idToIndex.put(e.getId(), i);
            indexToId.add(e.getId());
            adj.add(new ArrayList<>());
        }

        hashDep.forEach((deporte, listaEst) -> {
            for (int i = 0; i < listaEst.size(); i++) {
                for (int j = i + 1; j < listaEst.size(); j++) {
                    int idA = listaEst.get(i).getId();
                    int idB = listaEst.get(j).getId();
                    agregarArista(idA, idB, deporte);
                }
            }
        });
    }

    // --- Vecinos (ignorando capas) ---
    public ArrayList<Integer> vecinos(int id){
        ArrayList<Integer> res = new ArrayList<>();
        Integer idx = idToIndex.get(id);
        if (idx == null) return res;
        for (Conexion c : adj.get(idx)) res.add(c.vecinoId);
        return res;
    }

    // --- BFS: están conectados ---
    public boolean estanConectados(int origenId, int destinoId) {
        if (idToIndex.get(origenId) == null || idToIndex.get(destinoId) == null) {
            return false;
        }
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();
        visitados.add(origenId);
        cola.add(origenId);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            if (actual == destinoId) return true;
            for (Integer vecino : vecinos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return false;
    }

    // --- Existe conexión con alguien que practique deporte de interés ---
    public boolean existeConexionDeInteres(int origenId,
                                           HashMap<Integer, Estudiante> hashEst) {
        Estudiante origen = hashEst.get(origenId);
        if (origen == null) return false;

        String[] intereses = origen.getDeportesInteresados();
        if (intereses == null || intereses.length == 0) return false;

        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();
        visitados.add(origenId);
        cola.add(origenId);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            for (Integer vecinoId : vecinos(actual)) {
                if (visitados.contains(vecinoId)) continue;
                Estudiante vecino = hashEst.get(vecinoId);
                if (vecino != null) {
                    for (String dep : intereses) {
                        if (dep == null) continue;
                        // comparar contra array de practicados
                        for (String dPrac : vecino.getDeportesPracticados()) {
                            if (dep.equals(dPrac)) return true;
                        }
                    }
                }
                visitados.add(vecinoId);
                cola.add(vecinoId);
            }
        }
        return false;
    }

    // --- Lista de pares (idVecino, deporteInteres) ---
    public ArrayList<Map.Entry<Integer,String>> obtenerEstudiantesConDeporteInteres(
            int origenId, HashMap<Integer, Estudiante> hashEst) {

        ArrayList<Map.Entry<Integer,String>> resultados = new ArrayList<>();
        Estudiante origen = hashEst.get(origenId);
        if (origen == null) return resultados;

        String[] intereses = origen.getDeportesInteresados();
        if (intereses == null || intereses.length == 0) return resultados;

        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();
        visitados.add(origenId);
        cola.add(origenId);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            for (Integer vecinoId : vecinos(actual)) {
                if (visitados.contains(vecinoId)) continue;
                Estudiante vecino = hashEst.get(vecinoId);
                if (vecino != null) {
                    for (String dep : intereses) {
                        if (dep == null) continue;
                        for (String dPrac : vecino.getDeportesPracticados()) {
                            if (dep.equals(dPrac)) {
                                resultados.add(new AbstractMap.SimpleEntry<>(vecinoId, dep));
                            }
                        }
                    }
                }
                visitados.add(vecinoId);
                cola.add(vecinoId);
            }
        }
        return resultados;
    }

    // --- Imprimir ---
    public void imprimir() {
        for (int i = 0; i < adj.size(); i++) {
            int id = indexToId.get(i);
            System.out.print("Estudiante " + id + " -> ");
            for (Conexion c : adj.get(i)) {
                String deportesStr = c.deportes.toString();
                System.out.print(c.vecinoId + ":" + deportesStr + "  ");
            }
            System.out.println();
        }
    }

    // --- Métodos privados ---

    private void agregarArista(int idA, int idB, String deporte){
        Integer a = idToIndex.get(idA);
        Integer b = idToIndex.get(idB);
        if (a == null || b == null) return;

        Conexion ab = buscarConexion(a, idB);
        if (ab == null){
            adj.get(a).add(new Conexion(idB, deporte));
        } else if (!ab.deportes.contains(deporte)){
            ab.deportes.add(deporte);
        }

        Conexion ba = buscarConexion(b, idA);
        if (ba == null){
            adj.get(b).add(new Conexion(idA, deporte));
        } else if (!ba.deportes.contains(deporte)){
            ba.deportes.add(deporte);
        }
    }

    private Conexion buscarConexion(int idxOrigen, int vecinoId){
        for (Conexion c : adj.get(idxOrigen)){
            if (c.vecinoId == vecinoId) return c;
        }
        return null;
    }
}