import javax.swing.*;

public class hw1 extends JFrame {

    private final JTextField accountField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public hw1() {
        setTitle("登入");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

        setVisible(true);
    }

    private void handleLogin() {
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (account.equals("admin") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "登入成功");
        } else {
            JOptionPane.showMessageDialog(this, "帳號或密碼錯誤",
                    "登入失敗", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hw1::new);
    }
}
