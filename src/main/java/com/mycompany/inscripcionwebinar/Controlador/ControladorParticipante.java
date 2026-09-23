package com.mycompany.inscripcionwebinar.Controlador;

import com.mycompany.inscripcionwebinar.Modelo.Clases.Participante;
import com.mycompany.inscripcionwebinar.Modelo.Persistencia.ConexionBD;
import com.mycompany.inscripcionwebinar.Modelo.Persistencia.Operaciones;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ControladorParticipante {

    public static boolean inscribirParticipante(String nombre, String correo, String empresa) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()
                || empresa == null || empresa.trim().isEmpty()) {
            System.out.println("Nombre, correo y empresa son obligatorios.");
            return false;
        }

        Operaciones.setConnection(ConexionBD.MysConnection());
        boolean resultado = false;

        try {
            if (!Operaciones.setAutoCommitBD(false)) {
                Operaciones.cerrarConexion();
                return false;
            }

            String sqlBuscar = "SELECT idparticipante FROM participantes WHERE correo = ?";
            PreparedStatement psBuscar = Operaciones.getConnection().prepareStatement(sqlBuscar);
            psBuscar.setString(1, correo);
            ResultSet rs = Operaciones.consultar_BD(psBuscar);

            if (rs != null && rs.next()) {
                System.out.println("Ya existe un participante inscrito con ese correo.");
                Operaciones.rollbackBD();
                resultado = false;
            } else {
                String sqlInsertar = "INSERT INTO participantes(nombre, correo, empresa) VALUES (?, ?, ?)";
                PreparedStatement psInsertar = Operaciones.getConnection().prepareStatement(sqlInsertar);
                psInsertar.setString(1, nombre);
                psInsertar.setString(2, correo);
                psInsertar.setString(3, empresa);

                if (Operaciones.insertar_actualizar_borrar_BD(psInsertar) > 0) {
                    Operaciones.commitBD();
                    resultado = true;
                } else {
                    Operaciones.rollbackBD();
                    resultado = false;
                }
            }
        } finally {
            Operaciones.cerrarConexion();
        }

        return resultado;
    }

    public static List<Participante> listarParticipantes() throws SQLException {
        List<Participante> lista = new ArrayList<>();
        Operaciones.setConnection(ConexionBD.MysConnection());
        String sentencia = "SELECT idparticipante, nombre, correo, empresa FROM participantes";
        PreparedStatement ps = Operaciones.getConnection().prepareStatement(sentencia);
        ResultSet rs = Operaciones.consultar_BD(ps);

        if (rs != null) {
            while (rs.next()) {
                lista.add(new Participante(
                        rs.getInt("idparticipante"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("empresa")));
            }
        }

        Operaciones.cerrarConexion();
        return lista;
    }

    public static List<Participante> buscarPorEmpresa(String empresa) throws SQLException {
        List<Participante> lista = new ArrayList<>();
        Operaciones.setConnection(ConexionBD.MysConnection());
        String sentencia = "SELECT idparticipante, nombre, correo, empresa FROM participantes WHERE empresa = ?";
        PreparedStatement ps = Operaciones.getConnection().prepareStatement(sentencia);
        ps.setString(1, empresa);
        ResultSet rs = Operaciones.consultar_BD(ps);

        if (rs != null) {
            while (rs.next()) {
                lista.add(new Participante(
                        rs.getInt("idparticipante"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("empresa")));
            }
        }

        Operaciones.cerrarConexion();
        return lista;
    }

    public static int contarParticipantes() throws SQLException {
        int total = 0;
        Operaciones.setConnection(ConexionBD.MysConnection());
        String sentencia = "SELECT COUNT(*) AS total FROM participantes";
        PreparedStatement ps = Operaciones.getConnection().prepareStatement(sentencia);
        ResultSet rs = Operaciones.consultar_BD(ps);

        if (rs != null && rs.next()) {
            total = rs.getInt("total");
        }

        Operaciones.cerrarConexion();
        return total;
    }

    public static boolean eliminarParticipante(int id) throws SQLException {
        Operaciones.setConnection(ConexionBD.MysConnection());
        String sentencia = "DELETE FROM participantes WHERE idparticipante = ?";
        PreparedStatement ps = Operaciones.getConnection().prepareStatement(sentencia);
        ps.setInt(1, id);

        boolean eliminado = Operaciones.insertar_actualizar_borrar_BD(ps) > 0;
        Operaciones.cerrarConexion();

        if (!eliminado) {
            System.out.println("No se encontro un participante con ese id.");
        }

        return eliminado;
    }

}
