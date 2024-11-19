package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/VerificadorSenhasDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    public static void inserirSenhaVerificada(String senha, String resultado) {
        String insertSQL = "INSERT INTO senhas_verificadas (senha, resultado) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, senha);
            pstmt.setString(2, resultado);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Linhas inseridas: " + rowsAffected); // Verifique se linhas estão sendo inseridas
            System.out.println("Senha: " + senha + " | Resultado: " + resultado);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir senha verificada: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("Conexão com o banco de dados bem-sucedida!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
