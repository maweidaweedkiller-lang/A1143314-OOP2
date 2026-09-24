import java.awt.*;
import java.awt.event.*;         
import javax.swing.*;    

public class A1143314_OOP2_HW1_2 extends JFrame implements ActionListener {
    //extends JFrame：是讓這個類別「本身就是一個視窗」，所以可以直接用 this 當作視窗來操作

    // implements ActionListener：讓這個類別可以當事件傾聽者
    //    當按鈕被按下的時候，才會通知到這個類別，呼叫 actionPerformed()
    //     

   
    // 先把所有會用到的元件宣告成類別的資料成員 
    // 這樣不管在哪個 method 都可以直接存取，不用傳來傳去

    JLabel statusLabel;   // 上方狀態列：顯示「已丟了 N 次，總和是 M，平均是 X.XX」

    JLabel diceLabel;     // 中央標籤：用來顯示目前擲出的點數（大字）

    JButton rollButton;   // 下方按鈕：按下去會觸發丟骰子的動作

    
    int rollCount = 0;    // 用 rollCount 記錄已經丟了幾次
    int sumValue = 0;     // 記錄骰出點數的總和

    public A1143314_OOP2_HW1_2() {
        // 記得寫建構元，幫自己(繼承 JFrame)的類別時做初始化

        setTitle("骰子模擬器");      // 對應 JFrame 的函數：設定視窗標題文字

        setSize(400, 320);          // 設定視窗大小為 400x320

        setLocationRelativeTo(null);
        // 讓視窗開啟時置中
        //   傳入 null 代表說，相對於整個螢幕擺在中間， 
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 設定「關閉視窗時結束程式」

        

        setLayout(new BorderLayout());
        
        //   BorderLayout 把視窗切成上下左右中間，5 塊
        //   這題我們只會用到北邊（狀態列）、中間（點數顯示）、南（按鈕）

        // ---------- 上方的狀態列 ----------

        statusLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", JLabel.CENTER);
        //建立標籤並指定文字
        //   JLabel.CENTER 用來設定文字水平置中對齊

        add(statusLabel, BorderLayout.NORTH);
         
        //   frm.add(元件, BorderLayout.某位置)
        //   這裡把狀態列放在視窗的上方

        // ---------- 中間的顯示點數 ----------
        diceLabel = new JLabel("-", JLabel.CENTER);
        // 剛開始還沒擲骰子，先顯示 "-" 當作預設值

        diceLabel.setFont(new Font("Dialog", Font.BOLD, 60));
        // setFont(new Font(名稱, 樣式, 大小))
        //也可以換成 Font.PLAIN

        add(diceLabel, BorderLayout.CENTER);
        // 放在視窗的正中央


        // ---------- 下方的按鈕 ----------
        rollButton = new JButton("擲骰子");

        // JButton(String text)」建構元：建立按鈕並指定標題的文字

        rollButton.addActionListener(this);

      
        //   btn.addActionListener(傾聽者)
        //   意思是：把 this(也就是 A1143314_OOP2_HW1 自己) 向按鈕註冊，

        //   註冊後，只要按鈕被按下，Java 就會自動呼叫 this 的 actionPerformed() 方法
        //   （這就是為什麼上面 class 宣告要 implements ActionListener，

        //     因為只有實作了這個介面的物件，才「有資格」被拿來註冊當傾聽者）

        add(rollButton, BorderLayout.SOUTH);
        //  放在視窗下方 

        setVisible(true);
        //  一定要放在所有 add() 之後、最後執行
        //   如果放太早，元件還沒加進去，畫面會顯示不完整
    }

    // ---------- 事件處理方法 ----------

   
    // actionPerformed() 是 ActionListener 介面規定一定要實作的方法

    // 當按鈕被按下的時候，Java 會自動呼叫這個方法，並把「事件物件 e」傳進來

    public void actionPerformed(ActionEvent e) {
        // e 這個參數目前用不到內容，但因為要「實作介面」，方法簽名必須完全照介面的定義來寫

        int point = (int) (Math.random() * 6) + 1;
        // 用 Math.random() 產生 0.0 (含) 到 1.0 (不含) 之間的亂數

        //   乘以 6 之後範圍變成 0.0～6.0(不含)

        //   (int) 強制轉型，把小數點後面砍掉，變成 0~5 的整數
        //   最後 +1，範圍就變成規格要求的 1~6

        rollCount++;         // 每按一次，累計次數 +1

        sumValue += point;   // 累計總和，加上這次骰到的點數

        diceLabel.setText(String.valueOf(point));
        //  改變標籤上顯示的文字
        //   setText() 只能接受字串，所以要用 String.valueOf() 把 int 轉成字串

        //  依照點數去決定文字顏色 

        if (point == 6) {
            diceLabel.setForeground(Color.GREEN);
            // setForeground() 是 Swing 元件用來設定「文字顏色」的函數
            // 點數為 6 時文字變綠色
        } else if (point == 1) {
            diceLabel.setForeground(Color.RED);
            
        } else {
            diceLabel.setForeground(Color.BLACK);
            
        }

        // 更新最上面的狀態列  
        double average = (double) sumValue / rollCount;
        // 用 (double) 把 int 強制轉成浮點數再做除法
        //   因為如果兩個 int 直接相除，Java 會做「整數除法」，小數點會被捨去
      

        statusLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", rollCount, sumValue, average));
        
    }

    
    public static void main(String[] args) {
        new A1143314_OOP2_HW1_2();
        //   建立 A1143314_OOP2_HW 物件，物件一被 new 出來，
        //   建構元裡的程式碼就會自動全部執行一次（包括最後的 setVisible(true)）
        //   所以視窗就會馬上顯示出來
    }
}
