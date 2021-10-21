
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class SwingGUI implements UI {
    private JFrame frame;
    private ArrayList<JButton> dropButtons;
    private JPanel panel;
    private int size;
    private final int IMG_SIZE = 150;
    private final String IMG_PATH_EMPTY = "./Circle.jpg";
    private final String IMG_PATH_RED = "./CircleRed.jpg";
    private final String IMG_PATH_YELLOW = "./CircleYellow.jpg";
    private  int frameWidth;
    private  int frameHeight;
    private ImageIcon icon_empty;
    private ImageIcon icon_one;
    private ImageIcon icon_two;
    private Human human;
    private Status[][] board;

    class DropListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int ind;
            Object o = e.getSource();
            if (o instanceof JButton) {
                JButton button = (JButton) o;
                ind = dropButtons.indexOf(button);

                if (!verifyCol(ind)) {
                    String out = "Sorry, that column is not available, try again.";
                    JLabel l = new JLabel(out);
                    l.setFont(new Font ( "Arial", Font.PLAIN, 50));
                    JOptionPane.showMessageDialog(frame,l,"Try again.",JOptionPane.WARNING_MESSAGE);
                    closeColumn(ind);
                } else {
                    int posn = drop(ind);
                    board[posn][ind] = Status.ONE; // this is the human's move, so it's ONE.
                    drawBoard();
                    human.setAnswer(ind);
                }
            } else {
                System.out.println("Unexpected error: got " + o.getClass().toString());
            }
        }

    }

    private boolean verifyCol(int col) {
        return (col >= 0 && col < board[0].length && board[0][col] == Status.NEITHER);
    }

    private void drawBoard() {
        panel.removeAll();
        this.drawTops();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageIcon icon = null;
                Status s = board[i][j];
                //System.out.println(s);
                switch (s) {
                    case ONE:
                        icon = icon_one;
                        break;
                    case TWO:
                        icon = icon_two;
                        break;
                    case NEITHER:
                        icon = icon_empty;
                        break;
                }
                JLabel label = new JLabel(icon);
                panel.add(label);
            }

        }
        panel.revalidate();

        panel.repaint();


    }

    private void drawTops() {
        for (int i = 0; i < size; i++) {
            panel.add(dropButtons.get(i));
        }
    }

    private void reset() {

        panel.removeAll();
        drawTops();
        for (int i = 0; i < size *(size); i++) {
            JLabel label = new JLabel(icon_empty);
            panel.add(label);

        }
        for (JButton j : dropButtons) {
            j.setEnabled(true);
        }
        panel.revalidate();
        panel.repaint();

    }
    public SwingGUI() { }

    @Override
    public void lastMove(int lastCol) {
        if (lastCol != -1) {
            int p = drop(lastCol);
            board[p][lastCol] = Status.TWO; // this is the AI's move, so it's TWO.

        }
        drawBoard();
    }

    private int drop(  int col) {
    int posn = 0;
        while (posn < board[col].length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }


    @Override
    public void gameOver(Status PlayerNumber) {
        drawBoard();
        String out = "";
        if (PlayerNumber == Status.NEITHER) {
            out = "It's a draw!";
        } else {
            out = "Player " + PlayerNumber + " won!";
        }
        JLabel l = new JLabel(out);
        l.setFont(new Font ( "Arial", Font.PLAIN, 50));
        JOptionPane.showMessageDialog(frame,l,"Game Over!",JOptionPane.WARNING_MESSAGE);
        for (JButton j : dropButtons) {
            j.setEnabled(false);
        }

    }

    @Override
    public void setInfo(Human h, int size) {
        human = h;
        this.size = size;
        int width = size;
        int height = size+1;

        frameWidth = (width) *IMG_SIZE;
        frameHeight = (height)*IMG_SIZE+20;

        frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);

        panel = new JPanel();
        frame.add(panel);
        panel.setLayout( new GridLayout (height,width));

        dropButtons = new ArrayList<JButton>();
        for (int i = 0; i < width; i++) {
            JButton currButton = new JButton("DROP HERE");
            currButton.setFont(new Font ( "Arial", Font.PLAIN, 15));
            currButton.addActionListener(new DropListener());
            dropButtons.add(currButton);
        }

        try {
            BufferedImage img_empty = ImageIO.read(new File(IMG_PATH_EMPTY));
            icon_empty = new ImageIcon(img_empty);
            BufferedImage img_one = ImageIO.read(new File(IMG_PATH_RED));
            icon_one = new ImageIcon(img_one);
            BufferedImage img_two = ImageIO.read(new File(IMG_PATH_YELLOW));
            icon_two = new ImageIcon(img_two);

        } catch (IOException e) {
            e.printStackTrace();
        }


        reset();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        board = new Status[size][size];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }

    }

    public void closeColumn(int colNum) {
        JButton button = dropButtons.get(colNum);
        button.setEnabled(false);
    }
}