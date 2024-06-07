import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainApp extends JFrame {
    private LibraryManager libraryManager;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainApp() {
        libraryManager = new LibraryManager();
        setupMainFrame();
        setupButtonPanel();
        setupTable();
        displayBooks(libraryManager.getBooks());
    }

    private void setupMainFrame() {
        setTitle("Zarządzanie Książkami");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(60, 63, 65));
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(43, 43, 43));
        String[] buttonNames = {"Dodaj książkę", "Usuń książkę", "Szukaj książki", "Sortuj książki"};
        Color buttonColor = new Color(75, 110, 175);
        for (String name : buttonNames) {
            JButton button = createButton(name, buttonColor);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String name, Color background) {
        JButton button = new JButton(name);
        button.setForeground(Color.BLACK);
        button.setBackground(background);
        button.addActionListener(this::handleButtonClick);
        return button;
    }

    private void handleButtonClick(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Dodaj książkę":
                addBookDialog();
                break;
            case "Usuń książkę":
                removeSelectedBook();
                break;
            case "Szukaj książki":
                searchBookDialog();
                break;
            case "Sortuj książki":
                sortBooksDialog();
                break;
        }
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(new Object[]{"Tytuł", "Autor", "Rok", "Gatunek"}, 0);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(173, 216, 230) : new Color(224, 255, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
                return c;
            }
        };
        table.getTableHeader().setBackground(new Color(100, 149, 237));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addBookDialog() {
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField yearField = new JTextField(5);
        JTextField genreField = new JTextField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Tytuł:"));
        panel.add(titleField);
        panel.add(new JLabel("Autor:"));
        panel.add(authorField);
        panel.add(new JLabel("Rok:"));
        panel.add(yearField);
        panel.add(new JLabel("Gatunek:"));
        panel.add(genreField);
        if (JOptionPane.showConfirmDialog(null, panel, "Dodaj książkę", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Book book = new Book(titleField.getText(), authorField.getText(), Integer.parseInt(yearField.getText()), genreField.getText());
            libraryManager.addBook(book);
            displayBooks(libraryManager.getBooks());
        }
    }

    private void removeSelectedBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Book book = new Book((String) tableModel.getValueAt(selectedRow, 0), (String) tableModel.getValueAt(selectedRow, 1), 
                    (Integer) tableModel.getValueAt(selectedRow, 2), (String) tableModel.getValueAt(selectedRow, 3));
            libraryManager.removeBook(book);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Wybierz książkę do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBookDialog() {
        String keyword = JOptionPane.showInputDialog(this, "Wprowadź słowo kluczowe do wyszukiwania:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            displayBooks(libraryManager.searchBooks(keyword));
        }
    }

    private void sortBooksDialog() {
        String[] options = {"Tytuł", "Autor", "Rok", "Gatunek"};
        int choice = JOptionPane.showOptionDialog(this, "Wybierz kryterium sortowania:", "Sortuj książki",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice >= 0) {
            List<Book> sortedBooks = null;
            switch (choice) {
                case 0: sortedBooks = libraryManager.sortBooksByTitle(); break;
                case 1: sortedBooks = libraryManager.sortBooksByAuthor(); break;
                case 2: sortedBooks = libraryManager.sortBooksByYear(); break;
                case 3: sortedBooks = libraryManager.sortBooksByGenre(); break;
            }
            if (sortedBooks != null) displayBooks(sortedBooks);
        }
    }

    private void displayBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getYear(), book.getGenre()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
