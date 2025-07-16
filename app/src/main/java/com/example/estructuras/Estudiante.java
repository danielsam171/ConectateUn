public class Estudiante {
    private int id;
    private String name;
    private String apellidos;
    private String [] deportes_practicados;
    private String [] deportes_interesados;


    public Estudiante (String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String[] getDeportesPracticados() {
        return deportes_practicados;
    }
    public void setDeportesPracticados(String[] deportes_practicados) {
        this.deportes_practicados = deportes_practicados;
    }
    public String[] getDeportesInteresados() {
        return deportes_interesados;
    }
    public void setDeportesInteresados(String[] deportes_interesados) {
        this.deportes_interesados = deportes_interesados;
    }

    public boolean equals(Estudiante otro) {
        return this.id == otro.id;
    }

}


