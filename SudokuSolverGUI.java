import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {
    private JTextField[][] cells;
    private int[][] board = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    private int[][] originalBoard; // To store the original board for reset

    public SudokuSolverGUI() {
        cells = new JTextField[9][9];
        originalBoard = new int[9][9];
        setLayout(new GridLayout(10, 9));

        // Initialize the board with input fields
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 18));
                cells[row][col].setBackground(Color.decode("#F0F0F0"));
                cells[row][col].setForeground(Color.decode("#333333"));
                cells[row][col].setCaretColor(Color.decode("#333333"));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.decode("#CCCCCC")));
                originalBoard[row][col] = board[row][col]; // Store the original board
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                }
                add(cells[row][col]);
            }
        }

        // Solve Button
        JButton solveButton = new JButton("Solve");
        styleButton(solveButton, "#4CAF50", "#FFFFFF");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solve(0, 0)) {
                    updateBoard();
                } else {
                    JOptionPane.showMessageDialog(SudokuSolverGUI.this, "No solution exists!");
                }
            }
        });
        add(solveButton);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        styleButton(resetButton, "#FF5722", "#FFFFFF");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        add(resetButton);

        setSize(800, 500);
        getContentPane().setBackground(Color.decode("#FFFFFF"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void styleButton(JButton button, String bgColor, String fgColor) {
        button.setBackground(Color.decode(bgColor));
        button.setForeground(Color.decode(fgColor));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void resetBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = originalBoard[row][col]; // Restore original values
                cells[row][col].setText(board[row][col] == 0 ? "" : String.valueOf(board[row][col]));
                cells[row][col].setEditable(board[row][col] == 0);
                cells[row][col].setBackground(Color.decode("#F0F0F0"));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.decode("#CCCCCC")));
            }
        }
    }

    private boolean solve(int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true;
            }
        }
        if (board[row][col] != 0) {
            return solve(row + 1, col);
        }
        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (solve(row + 1, col)) {
                    return true;
                }
            }
            board[row][col] = 0;
        }
        return false;
    }

    private boolean isValid(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int gridRow = row - row % 3;
        int gridCol = col - col % 3;
        for (int r = gridRow; r < gridRow + 3; r++) {
            for (int c = gridCol; c < gridCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
                cells[row][col].setEditable(false);
                cells[row][col].setBackground(Color.decode("#F0F0F0"));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.decode("#CCCCCC")));
            }
        }
    }

    public static void main(String[] args) {
        new SudokuSolverGUI();
    }
}
