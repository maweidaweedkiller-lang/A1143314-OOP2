import javax.swing.*;
import java.awt.*;

public class A1143314_OOP2_HW1_1 extends JFrame {

    private final JTextField accountField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public A1143314_OOP2_HW1_1() {
        setTitle("登入");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);   // 視窗置中
        setLayout(null);

        JLabel accountLabel = new JLabel("帳號：");
        accountLabel.setBounds(30, 25, 60, 25);
        accountField.setBounds(100, 25, 150, 25);

        JLabel passwordLabel = new JLabel("密碼：");
        passwordLabel.setBounds(30, 65, 60, 25);
        passwordField.setBounds(100, 65, 150, 25);

        JButton loginButton = new JButton("登入");
        loginButton.setBounds(100, 110, 80, 30);

        add(accountLabel);
        add(accountField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        loginButton.addActionListener(e -> handleLogin());

        setVisible(true);   // 所有元件加完之後才顯示
    }

    private void handleLogin() {
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword());

        // 練習用；實務上不要把帳密寫死在程式裡
        if (account.equals("admin") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "登入成功");
        } else {
            JOptionPane.showMessageDialog(this, "帳號或密碼錯誤",
                    "登入失敗", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(A1143314_OOP2_HW1_1::new);
    }
}