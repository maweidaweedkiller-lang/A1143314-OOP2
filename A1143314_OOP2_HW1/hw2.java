import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class hw2 extends JFrame {

    private final JLabel statsLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
    private final JLabel diceLabel = new JLabel("-", SwingConstants.CENTER);
    private final JButton rollButton = new JButton("擲骰子");
    private final Random random = new Random();

    private int count = 0;   // 已擲次數 N
    private int sum = 0;     // 點數總和 M

    public hw2() {
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        statsLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        diceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));

        JPanel bottom = new JPanel();
        bottom.add(rollButton);

        add(statsLabel, BorderLayout.NORTH);
        add(diceLabel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        rollButton.addActionListener(e -> roll());

        setVisible(true);
    }

    private void roll() {
        int value = random.nextInt(6) + 1;   // 1 ~ 6
        count++;
        sum += value;

        diceLabel.setText(String.valueOf(value));
        if (value == 6) {
            diceLabel.setForeground(new Color(0, 150, 0));   // 綠色
        } else if (value == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        double avg = (double) sum / count;
        statsLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", count, sum, avg));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hw2::new);
    }
}
