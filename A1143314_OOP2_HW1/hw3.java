import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class hw3 extends JFrame {

    // 每個單位換算成「基準單位」要乘上的倍數（長度以公尺為基準，重量以公斤為基準）
    private static final Map<String, Double> LENGTH = new LinkedHashMap<>();
    private static final Map<String, Double> WEIGHT = new LinkedHashMap<>();
    private static final String[] TEMPERATURE = {"攝氏 °C", "華氏 °F", "克耳文 K"};

    static {
        LENGTH.put("公里 km", 1000.0);
        LENGTH.put("公尺 m", 1.0);
        LENGTH.put("公分 cm", 0.01);
        LENGTH.put("公釐 mm", 0.001);
        LENGTH.put("英里 mi", 1609.344);
        LENGTH.put("碼 yd", 0.9144);
        LENGTH.put("英尺 ft", 0.3048);
        LENGTH.put("英吋 in", 0.0254);

        WEIGHT.put("公噸 t", 1000.0);
        WEIGHT.put("公斤 kg", 1.0);
        WEIGHT.put("公克 g", 0.001);
        WEIGHT.put("台斤", 0.6);
        WEIGHT.put("磅 lb", 0.45359237);
        WEIGHT.put("盎司 oz", 0.028349523125);
    }

    private final JComboBox<String> categoryBox = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
    private final JTextField inputField = new JTextField("1");
    private final JComboBox<String> fromBox = new JComboBox<>();
    private final JComboBox<String> toBox = new JComboBox<>();
    private final JLabel resultLabel = new JLabel("請輸入數值後按「換算」", SwingConstants.CENTER);

    public hw3() {
        setTitle("單位換算器");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 中間：類別、數值、來源單位、目標單位
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));
        form.add(new JLabel("類別："));
        form.add(categoryBox);
        form.add(new JLabel("數值："));
        form.add(inputField);
        form.add(new JLabel("從："));
        form.add(fromBox);
        form.add(new JLabel("換成："));
        form.add(toBox);

        // 下方：按鈕與結果
        JButton convertButton = new JButton("換算");
        JButton swapButton = new JButton("⇄ 對調");
        JPanel buttons = new JPanel();
        buttons.add(convertButton);
        buttons.add(swapButton);

        resultLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(resultLabel, BorderLayout.CENTER);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        categoryBox.addActionListener(e -> loadUnits());
        convertButton.addActionListener(e -> convert());
        inputField.addActionListener(e -> convert());   // 在輸入框按 Enter 也能換算
        swapButton.addActionListener(e -> swapUnits());

        loadUnits();
        setVisible(true);
    }

    // 依照目前選的類別，重新填入兩個單位下拉選單
    private void loadUnits() {
        String[] units;
        switch ((String) categoryBox.getSelectedItem()) {
            case "長度": units = LENGTH.keySet().toArray(new String[0]); break;
            case "重量": units = WEIGHT.keySet().toArray(new String[0]); break;
            default:     units = TEMPERATURE;
        }
        fromBox.setModel(new DefaultComboBoxModel<>(units));
        toBox.setModel(new DefaultComboBoxModel<>(units));
        if (units.length > 1) {
            toBox.setSelectedIndex(1);
        }
        resultLabel.setText("請輸入數值後按「換算」");
        resultLabel.setForeground(Color.BLACK);
    }

    private void swapUnits() {
        int from = fromBox.getSelectedIndex();
        fromBox.setSelectedIndex(toBox.getSelectedIndex());
        toBox.setSelectedIndex(from);
        convert();
    }

    private void convert() {
        double value;
        try {
            value = Double.parseDouble(inputField.getText().trim());
        } catch (NumberFormatException ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("請輸入正確的數字");
            return;
        }

        String category = (String) categoryBox.getSelectedItem();
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();

        double result;
        if (category.equals("溫度")) {
            result = convertTemperature(value, from, to);
        } else {
            Map<String, Double> table = category.equals("長度") ? LENGTH : WEIGHT;
            result = value * table.get(from) / table.get(to);   // 先換成基準單位，再換成目標單位
        }

        resultLabel.setForeground(Color.BLACK);
        resultLabel.setText(format(value) + " " + from + " = " + format(result) + " " + to);
    }

    // 溫度不是單純乘倍數，所以先換成攝氏，再從攝氏換成目標單位
    private double convertTemperature(double value, String from, String to) {
        double celsius;
        if (from.startsWith("華氏")) {
            celsius = (value - 32) * 5 / 9;
        } else if (from.startsWith("克耳文")) {
            celsius = value - 273.15;
        } else {
            celsius = value;
        }

        if (to.startsWith("華氏")) {
            return celsius * 9 / 5 + 32;
        } else if (to.startsWith("克耳文")) {
            return celsius + 273.15;
        } else {
            return celsius;
        }
    }

    // 最多顯示 6 位小數，並去掉多餘的 0（例如 2.500000 → 2.5）
    private String format(double d) {
        String s = String.format("%.6f", d);
        s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        return s.equals("-0") ? "0" : s;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hw3::new);
    }
}
