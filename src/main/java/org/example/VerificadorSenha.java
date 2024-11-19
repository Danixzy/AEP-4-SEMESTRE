package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class VerificadorSenha extends JFrame {

    private JTextField senhaInput;
    private JLabel resultadoLabel;

    public VerificadorSenha() {
        // Configurações da Janela
        setTitle("Verificador de Força de Senha");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Elementos da Interface
        senhaInput = new JTextField();
        resultadoLabel = new JLabel("Digite uma senha e clique em verificar", SwingConstants.CENTER);
        JButton verificarButton = new JButton("Verificar");

        // Adicionando elementos à janela
        add(senhaInput);
        add(verificarButton);
        add(resultadoLabel);

        // Ação do botão de verificação
        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senha = senhaInput.getText();
                String resultado = verificarForcaSenha(senha);
                resultadoLabel.setText("Resultado: " + resultado);

                // Chamar o método para inserir a senha e o resultado no banco de dados
                try {
                    DatabaseHandler.inserirSenhaVerificada(senha, resultado);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao salvar no banco de dados: " + ex.getMessage());
                }
            }
        });
    }

    // Método para verificar a força da senha
    private String verificarForcaSenha(String senha) {
        if (senha.length() < 8) {
            return "Fraca";
        }
        if (!Pattern.compile("[A-Z]").matcher(senha).find()) {
            return "Média";
        }
        if (!Pattern.compile("[0-9]").matcher(senha).find()) {
            return "Média";
        }
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(senha).find()) {
            return "Forte";
        }
        return "Muito Forte";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerificadorSenha frame = new VerificadorSenha();
            frame.setVisible(true);
        });
    }
}
