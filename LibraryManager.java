import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryManager {
    private List<Book> books;

    public LibraryManager() {
        books = new ArrayList<>();
        addInitialBooks();
    }

    private void addInitialBooks() {
        String[][] bookData = {
            {"Pan Tadeusz", "Adam Mickiewicz", "1834", "Epopeja"},
            {"Lalka", "Bolesław Prus", "1890", "Powieść"},
            {"Ferdydurke", "Witold Gombrowicz", "1937", "Powieść"},
            {"Chłopi", "Władysław Reymont", "1904", "Powieść"},
            {"Potop", "Henryk Sienkiewicz", "1886", "Powieść"},
            {"Krzyżacy", "Henryk Sienkiewicz", "1900", "Powieść"},
            {"Quo Vadis", "Henryk Sienkiewicz", "1896", "Powieść"},
            {"Zbrodnia i kara", "Fyodor Dostoevsky", "1866", "Powieść"},
            {"Nad Niemnem", "Eliza Orzeszkowa", "1888", "Powieść"},
            {"Przedwiośnie", "Stefan Żeromski", "1924", "Powieść"},
            {"Syzyfowe prace", "Stefan Żeromski", "1897", "Powieść"},
            {"Wesele", "Stanisław Wyspiański", "1901", "Dramat"},
            {"Dziady", "Adam Mickiewicz", "1823", "Dramat"},
            {"Kordian", "Juliusz Słowacki", "1834", "Dramat"},
            {"Nie-Boska komedia", "Zygmunt Krasiński", "1835", "Dramat"},
            {"Sklepy cynamonowe", "Bruno Schulz", "1934", "Opowiadania"},
            {"Inny świat", "Gustaw Herling-Grudziński", "1951", "Pamiętnik"},
            {"Medaliony", "Zofia Nałkowska", "1946", "Opowiadania"},
            {"Proces", "Franz Kafka", "1925", "Powieść"},
            {"Mała Apokalipsa", "Tadeusz Konwicki", "1979", "Powieść"},
            {"Zdążyć przed Panem Bogiem", "Hanna Krall", "1977", "Reportaż"},
            {"Granica", "Zofia Nałkowska", "1935", "Powieść"},
            {"Ocalony", "Tadeusz Różewicz", "1945", "Poezja"},
            {"Tango", "Sławomir Mrożek", "1964", "Dramat"},
            {"Solaris", "Stanisław Lem", "1961", "Science fiction"}
        };
        for (String[] book : bookData) {
            books.add(new Book(book[0], book[1], Integer.parseInt(book[2]), book[3]));
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.removeIf(b -> b.equals(book));
    }

    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword) ||
                                book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                                book.getGenre().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Book> sortBooksByTitle() {
        return books.stream().sorted((b1, b2) -> b1.getTitle().compareTo(b2.getTitle())).collect(Collectors.toList());
    }

    public List<Book> sortBooksByAuthor() {
        return books.stream().sorted((b1, b2) -> b1.getAuthor().compareTo(b2.getAuthor())).collect(Collectors.toList());
    }

    public List<Book> sortBooksByYear() {
        return books.stream().sorted((b1, b2) -> Integer.compare(b1.getYear(), b2.getYear())).collect(Collectors.toList());
    }

    public List<Book> sortBooksByGenre() {
        return books.stream().sorted((b1, b2) -> b1.getGenre().compareTo(b2.getGenre())).collect(Collectors.toList());
    }

    public List<Book> getBooks() {
        return books;
    }
}
