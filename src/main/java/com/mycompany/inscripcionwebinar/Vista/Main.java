package com.mycompany.inscripcionwebinar.Vista;

import com.mycompany.inscripcionwebinar.Controlador.ControladorParticipante;
import com.mycompany.inscripcionwebinar.Modelo.Clases.Participante;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("===== INSCRIPCION WEBINAR CORPORATIVO =====");
            System.out.println("1. Inscribir participante");
            System.out.println("2. Listar participantes");
            System.out.println("3. Buscar participantes por empresa");
            System.out.println("4. Contar participantes inscritos");
            System.out.println("5. Eliminar inscripcion por id");
            System.out.println("6. Salir");
            System.out.print("Elige una opcion: ");

            String opcion = sc.nextLine().trim();

            try {
                switch (opcion) {
                    case "1":
                        inscribir(sc);
                        break;
                    case "2":
                        listar();
                        break;
                    case "3":
                        buscarPorEmpresa(sc);
                        break;
                    case "4":
                        contar();
                        break;
                    case "5":
                        eliminar(sc);
                        break;
                    case "6":
                        salir = true;
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opcion invalida, intenta de nuevo.");
                }
            } catch (SQLException ex) {
                System.out.println("Ocurrio un error de base de datos: " + ex.getMessage());
            }
        }

        sc.close();
    }

    private static void inscribir(Scanner sc) throws SQLException {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Correo: ");
        String correo = sc.nextLine().trim();
        System.out.print("Empresa: ");
        String empresa = sc.nextLine().trim();

        boolean exito = ControladorParticipante.inscribirParticipante(nombre, correo, empresa);
        if (exito) {
            System.out.println("Participante inscrito correctamente.");
        } else {
            System.out.println("No se pudo inscribir al participante.");
        }
    }

    private static void listar() throws SQLException {
        List<Participante> lista = ControladorParticipante.listarParticipantes();
        if (lista.isEmpty()) {
            System.out.println("No hay participantes inscritos.");
        } else {
            for (Participante p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void buscarPorEmpresa(Scanner sc) throws SQLException {
        System.out.print("Empresa a buscar: ");
        String empresa = sc.nextLine().trim();

        List<Participante> lista = ControladorParticipante.buscarPorEmpresa(empresa);
        if (lista.isEmpty()) {
            System.out.println("No hay participantes inscritos de esa empresa.");
        } else {
            for (Participante p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void contar() throws SQLException {
        int total = ControladorParticipante.contarParticipantes();
        System.out.println("Total de participantes inscritos: " + total);
    }

    private static void eliminar(Scanner sc) throws SQLException {
        System.out.print("Id del participante a eliminar: ");
        String texto = sc.nextLine().trim();

        try {
            int id = Integer.parseInt(texto);
            boolean eliminado = ControladorParticipante.eliminarParticipante(id);
            if (eliminado) {
                System.out.println("Participante eliminado correctamente.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Debes ingresar un numero de id valido.");
        }
    }

}
