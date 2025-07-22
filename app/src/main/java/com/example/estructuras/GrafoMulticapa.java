import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;


// TODO: Representa una lista multicapa de grafo 
// Una conexión con un vecino y la lista de deportes que comparten
class Conexion {

    int vecinoId; // Id del estudiante destino de la conexión
    ArrayList<String> deportes = new ArrayList<>();  // ArrayList de todos lso deportes que comparten que jusifican la conexión

    Conexion(int vecinoId, String deporte) {
        this.vecinoId = vecinoId;
        if (deporte != null) deportes.add(deporte);
    }
}

// TODO!: Grafo multicapa representado por una lista de adyacencia
// Grafo multicapa: adjacency list
public class GrafoMulticapa {

    // La lista de adyacencia es un ArrayList de ArrayLists 
    // Cada ArrayList interno contiene objetos de tipo Conexion
    // Cada objeto Conexion contiene el id del vecino y un ArrayList de deportes compartidos 
    // Nota: adj.get(i) = lista de conexiones del estudiante con índice i
    private ArrayList<ArrayList<Conexion>> adj = new ArrayList<>(); // matirz de adyacencia

    //TODO!: Ver si utilizo la clase HashMap que creamos nosotros o la de java.util
    // Mapea id de estudiante (entero arbitrario) → índice en la lista adj (entero consecutivo de 0 a dimensión del ArrayList externo)
    private java.util.HashMap<Integer,Integer> idToIndex = new java.util.HashMap<>(); //TODO: Lo dejo como java.util.HashMap, para diferenciarlo del HashMap que creamos en el proyecto nosotros ya creamos
    
    // Mapea índice de la lista adj → id
    private ArrayList<Integer> indexToId = new ArrayList<>();

    // El grafo se inicializa con los HashMaps de estudiantes y deportes, que son las otras dos estructuras de datos que se usan en la aplicación
    public GrafoMulticapa(HashMap<Integer,Estudiante> hashEst,
                          HashMap<String, ArrayList<Estudiante>> hashDep) {

        // 1. Crear los índices para todos los estudiantes
        ArrayList<Estudiante> todos = hashEst.values(); // ArrayList de valores (i.e. de todos los estudiantes)
        for (int i=0;i<todos.size();i++){
            Estudiante e = todos.get(i);
            idToIndex.put(e.getId(), i); // Mapea el id del estudiante al índice en la lista adj. Se agrega a "idToIndex"
            indexToId.add(e.getId());    // Agrega el id del estudiante a "indexToId"
            adj.add(new ArrayList<>());  // Agrega una nueva lista de conexiones vacía para el estudiante
        }

        // 2. Recorrer cada deporte y conectar todos los pares de estudiantes que estén conectados por ese deporte
        // Se forma un clique de estudiantes que comparten un deporte
        // Se recorre el HashMap de deportes, que tiene como clave el nombre del deporte
        // y como valor un ArrayList de estudiantes que lo practican
        // Se conecta cada par de estudiantes que comparten el deporte
        hashDep.forEach((deporte, listaEst) -> {
            for (int i=0;i<listaEst.size();i++){
                for (int j=i+1;j<listaEst.size();j++){
                    int idA = listaEst.get(i).getId();
                    int idB = listaEst.get(j).getId();
                    agregarArista(idA, idB, deporte);
                }
            }
        });
    }

    // --- Método para agregar un nuevo nodo (Estudiante) al grafo ---
    /**
     * Agrega un estudiante al grafo, asigna un nuevo índice y crea conexiones
     * con los nodos existentes basadas en hashDep (deportes practicados).
     * Se asume que hashDep ya contiene al estudiante.
     */
    public void agregarNodo(Estudiante nuevo,
                            HashMap<String, ArrayList<Estudiante>> hashDep) {
        // 1. Asignar nuevo índice y listas
        int newIdx = adj.size();
        idToIndex.put(nuevo.getId(), newIdx);
        indexToId.add(nuevo.getId());
        adj.add(new ArrayList<>());

        // 2. Conectar con cada estudiante que comparta deporte
        for (String deporte : nuevo.getDeportesPractica()) {
            ArrayList<Estudiante> lista = hashDep.get(deporte); // Lista de todos los estudiantes preexistentes que practican el deporte
            if (lista == null) continue;
            for (Estudiante otro : lista) {
                if (otro.getId() != nuevo.getId()) {
                    agregarArista(nuevo.getId(), otro.getId(), deporte);
                }
            }
        }
    }

    // --- Método para eliminar un nodo (Estudiante) del grafo ---
    /**
     * Elimina todas las conexiones del nodo y elimina el nodo.
     * Actualiza índices de los nodos restantes.
     */
    public void eliminarNodo(int id) {
        Integer idx = idToIndex.remove(id);
        if (idx == null) return;  // no existe el estudiante con Id "id"

        // 1. Remover lista de adyacencia del nodo
        adj.remove((int) idx);
        // 2. Remover id de indexToId
        indexToId.remove((int) idx);

        // 3. Eliminar conexiones entrantes en otros nodos
        for (ArrayList<Conexion> conexiones : adj) {
            conexiones.removeIf(c -> c.vecinoId == id);
        }

        // 4. Reajustar índices para todos los nodos con índice > idx
        // Se está reajustando el ArrayList indexToId 
        for (int i = idx; i < indexToId.size(); i++) {
            int movedId = indexToId.get(i);
            idToIndex.put(movedId, i);
        }
    }

    // Reconstruye completamente el grafo a partir de los mapas dados
    public void actualizar(HashMap<Integer, Estudiante> hashEst,
                           HashMap<String, ArrayList<Estudiante>> hashDep) {
        // 1. Limpiar estructuras internas
        adj.clear();
        idToIndex.clear();
        indexToId.clear();
        // 2. Reconstruir índices y listas vacías
        ArrayList<Estudiante> todos = hashEst.values();
        for (int i = 0; i < todos.size(); i++) {
            Estudiante e = todos.get(i);
            idToIndex.put(e.getId(), i);
            indexToId.add(e.getId());
            adj.add(new ArrayList<>());
        }
        // 3. Reconstruir aristas (cliques por deporte)
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

    // Agrega (o actualiza) arista no dirigida entre idA e idB con el deporte dado
    private void agregarArista(int idA, int idB, String deporte){
        int a = idToIndex.get(idA);
        int b = idToIndex.get(idB);
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

    // Método que me retorna la conexión entre "idxOrigen" y "vecinoId"
    private Conexion buscarConexion(int idxOrigen, int vecinoId){
        // Recorro una lista de conexiones (ArrayList) del estudiante con índice "idxOrigen"
        for (Conexion c : adj.get(idxOrigen)){
            if (c.vecinoId == vecinoId) return c;
        }
        return null;
    }

    // Vecinos (ignorando capas)
    public ArrayList<Integer> vecinos(int id){
        ArrayList<Integer> res = new ArrayList<>();
        int idx = idToIndex.get(id);
        for (Conexion c : adj.get(idx)) res.add(c.vecinoId);
        return res;
    }

    //TODO!: Métodos de búsqueda en el grafo basados en BFS

    // Determina si dos nodos están conectados (BFS)
    public boolean estanConectados(int origenId, int destinoId) {
        
        // Verifica si ambos nodos existen en el grafo  
        // Si alguno de los dos no existe, retorna false
        if (!idToIndex.containsKey(origenId) || !idToIndex.containsKey(destinoId)) {
            return false;
        }
        
        // Implementación de BFS para verificar si hay un camino entre origenId y destinoId

        // Usamos un HashSet para marcar los nodos visitados y una cola para el BFS
        java.util.Set<Integer> visitados = new java.util.HashSet<>(); // Se usa para optimizar la búsqueda en el BFS
        Queue<Integer> cola = new LinkedList<>();

        // Se incializa la cola con el nodo de origen y se marca como visitado para poder iniciar el BFS
        visitados.add(origenId);
        cola.add(origenId);

        // Busqueda por BFS
        while (!cola.isEmpty()) {
            int actual = cola.poll();
            if (actual == destinoId) {
                return true;
            }
            for (Integer vecino : vecinos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return false;
    }

    // Permite saber si un estudiante está conectado (directa o indirectamente)
    // con alguien que practique uno de los deportes que le interesan.
    // Retorna true si existe conexión; false en caso contrario.
    public boolean existeConexionDeInteres(int origenId,
                                           HashMap<Integer, Estudiante> hashEst) {
        Estudiante origen = hashEst.get(origenId);
        if (origen == null) return false;
        List<String> intereses = origen.getDeportesInteres();
        if (intereses == null || intereses.isEmpty()) return false;
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();
        visitados.add(origenId);
        cola.add(origenId);
        while (!cola.isEmpty()) {
            int actual = cola.poll();
            for (Integer vecinoId : vecinos(actual)) {
                if (visitados.contains(vecinoId)) continue;
                Estudiante vecino = hashEst.get(vecinoId);
                // Si el vecino practica alguno de los deportes de interés
                for (String dep : intereses) {
                    if (vecino.getDeportesPractica().contains(dep)) {
                        return true;
                    }
                }
                visitados.add(vecinoId);
                cola.add(vecinoId);
            }
        }
        return false;
    }

    // Retorna lista de tuplas (estudianteId, deporteInteres) para todos los estudiantes conectados
    // que practican al menos uno de los deportes de interés de origen.
    public ArrayList<Map.Entry<Integer,String>> obtenerEstudiantesConDeporteInteres(
            int origenId, HashMap<Integer, Estudiante> hashEst) {
        Estudiante origen = hashEst.get(origenId);
        ArrayList<Map.Entry<Integer,String>> resultados = new ArrayList<>();
        if (origen == null) return resultados;
        List<String> intereses = origen.getDeportesInteres();
        if (intereses == null || intereses.isEmpty()) return resultados;
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
                        if (vecino.getDeportesPractica().contains(dep)) {
                            resultados.add(new AbstractMap.SimpleEntry<>(vecinoId, dep));
                        }
                    }
                }
                visitados.add(vecinoId);
                cola.add(vecinoId);
            }
        }
        return resultados;
    }

    // Imprimir el grafo con capas
    public void imprimir() {
        for (int i = 0; i < adj.size(); i++) {
            int id = indexToId.get(i);
            System.out.print("Estudiante " + id + " -> ");
            for (Conexion c : adj.get(i)) {
                // Convertir primero a String para evitar error de int + ArrayList
                String deportesStr = c.deportes.toString();
                System.out.print(c.vecinoId + ":" + deportesStr + "  ");
            }
            System.out.println();
        }
    }
}

