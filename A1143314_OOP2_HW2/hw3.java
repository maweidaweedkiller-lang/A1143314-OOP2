import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class hw3 extends JFrame implements ActionListener {

    // 各類型的單位名稱
    private static final String[] LENGTH_UNITS = {"公尺", "公分", "英吋", "英尺"};
    private static final String[] WEIGHT_UNITS = {"公斤", "公克", "磅", "盎司"};
    private static final String[] TEMP_UNITS   = {"攝氏", "華氏", "克氏"};

    // 每個單位換算成基準單位要乘的倍數（長度以公尺為基準，重量以公斤為基準），順序和上面的名稱對應
    private static final double[] LENGTH_RATE = {1.0, 0.01, 0.0254, 0.3048};
    private static final double[] WEIGHT_RATE = {1.0, 0.001, 0.45359237, 0.028349523125};

    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
    private final JTextField inputField = new JTextField(12);
    private final JTextField outputField = new JTextField(12);
    private final JComboBox<String> fromBox = new JComboBox<>(LENGTH_UNITS);
    private final JComboBox<String> toBox = new JComboBox<>(LENGTH_UNITS);
    private final JButton convertButton = new JButton("換算");

    public hw3() {
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // NORTH：選換算類型
        JPanel north = new JPanel();                 // JPanel 預設是 FlowLayout
        north.add(new JLabel("換算類型："));
        north.add(typeBox);

        // CENTER：兩列，每列一個 JTextField + 一個 JComboBox
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        row1.add(inputField);
        row1.add(fromBox);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        outputField.setEditable(false);              // 結果欄只顯示、不讓使用者輸入
        row2.add(outputField);
        row2.add(toBox);

        JPanel center = new JPanel(new GridLayout(2, 1));
        center.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        center.add(row1);
        center.add(row2);

        // SOUTH：換算按鈕
        JPanel south = new JPanel();
        south.add(convertButton);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        toBox.setSelectedIndex(1);                   // 預設「公尺 → 公分」

        // 向事件來源者註冊，由這個視窗物件（this）擔任傾聽者
        typeBox.addActionListener(this);
        convertButton.addActionListener(this);

        setVisible(true);                            // 一定放最後
    }

    // 事件處理：用 getSource() 分辨是哪個元件觸發的
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typeBox) {
            changeUnits();
        } else if (e.getSource() == convertButton) {
            convert();
        }
    }

    // 切換換算類型時，兩個單位 JComboBox 的選項跟著換
    private void changeUnits() {
        String[] units;
        int type = typeBox.getSelectedIndex();
        if (type == 0) {
            units = LENGTH_UNITS;
        } else if (type == 1) {
            units = WEIGHT_UNITS;
        } else {
            units = TEMP_UNITS;
        }
        fromBox.setModel(new DefaultComboBoxModel<>(units));
        toBox.setModel(new DefaultComboBoxModel<>(units));
        toBox.setSelectedIndex(1);
        outputField.setText("");                     // 類型換了，舊的結果就清掉
    }

    private void convert() {
        double value;
        try {
            value = Double.parseDouble(inputField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請在第一個欄位輸入數字",
                    "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int type = typeBox.getSelectedIndex();
        int from = fromBox.getSelectedIndex();
        int to = toBox.getSelectedIndex();

        double result;
        if (type == 0) {
            result = value * LENGTH_RATE[from] / LENGTH_RATE[to];
        } else if (type == 1) {
            result = value * WEIGHT_RATE[from] / WEIGHT_RATE[to];
        } else {
            result = convertTemperature(value, from, to);
        }

        outputField.setText(format(result));
    }

    // 溫度不是乘一個倍數就好：先換成攝氏，再從攝氏換成目標單位（0 攝氏、1 華氏、2 克氏）
    private static double convertTemperature(double value, int from, int to) {
        double c;
        if (from == 1) {
            c = (value - 32) * 5 / 9;
        } else if (from == 2) {
            c = value - 273.15;
        } else {
            c = value;
        }

        if (to == 1) {
            return c * 9 / 5 + 32;
        } else if (to == 2) {
            return c + 273.15;
        } else {
            return c;
        }
    }

    // 最多顯示 4 位小數，並去掉多餘的 0（例如 2.5000 → 2.5）
    private static String format(double d) {
        String s = String.format("%.4f", d);
        s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        return s.equals("-0") ? "0" : s;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hw3::new);
    }
}
