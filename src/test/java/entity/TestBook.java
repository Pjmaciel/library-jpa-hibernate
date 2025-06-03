package entity;

import br.com.fuctura.entity.Book;
import br.com.fuctura.entity.Category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DisplayName("Book Entity Tests")
class TestBook {

    private Category validCategory;

    @BeforeEach
    void setUp() {
        validCategory = new Category("Programming", "Programming and software development books");
        validCategory.setId(1L);
    }

    @Test
    @DisplayName("Should create book with valid data and category relationship")
    void shouldCreateBookWithValidDataAndCategory() {
        // Given
        String title = "Clean Code";
        String author = "Robert C. Martin";
        String synopsis = "A handbook of agile software craftsmanship";
        String isbn = "9780132350884";
        LocalDate releaseYear = LocalDate.of(2008, 8, 1);

        // When
        Book book = new Book(title, author, synopsis, isbn, releaseYear, validCategory);

        // Then
        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(synopsis, book.getSynopsis());
        assertEquals(isbn, book.getIsbn());
        assertEquals(releaseYear, book.getReleaseYear());
        assertEquals(validCategory, book.getCategory());
        assertNull(book.getId()); // ID should be null for new entities
        assertTrue(book.isValid());
        assertEquals("Programming", book.getCategoryName());
    }

    @Test
    @DisplayName("Should handle book creation with minimum required fields")
    void shouldHandleBookCreationWithMinimumFields() {
        // Given - usando construtor sem synopsis (que pode ser null)
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("9780134685991");
        book.setReleaseYear(LocalDate.of(2017, 12, 27));
        book.setCategory(validCategory);
        // synopsis pode ficar null

        // When & Then
        assertTrue(book.isValid()); // Deve ser válido mesmo sem synopsis
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertNull(book.getSynopsis()); // Synopsis pode ser null
        assertEquals("Programming", book.getCategoryName());

        // Test toString with null synopsis
        String result = book.toString();
        assertTrue(result.contains("Effective Java"));
        assertTrue(result.contains("Joshua Bloch"));
        assertTrue(result.contains("2017"));
    }

    @Test
    @DisplayName("Should reject book with missing required fields")
    void shouldRejectBookWithMissingRequiredFields() {
        // Test with null title
        Book bookNullTitle = new Book(null, "Author", "Synopsis", "ISBN123", LocalDate.now(), validCategory);
        assertFalse(bookNullTitle.isValid());

        // Test with empty title
        Book bookEmptyTitle = new Book("", "Author", "Synopsis", "ISBN123", LocalDate.now(), validCategory);
        assertFalse(bookEmptyTitle.isValid());

        // Test with whitespace-only title
        Book bookWhitespaceTitle = new Book("   ", "Author", "Synopsis", "ISBN123", LocalDate.now(), validCategory);
        assertFalse(bookWhitespaceTitle.isValid());

        // Test with null author
        Book bookNullAuthor = new Book("Title", null, "Synopsis", "ISBN123", LocalDate.now(), validCategory);
        assertFalse(bookNullAuthor.isValid());

        // Test with empty author
        Book bookEmptyAuthor = new Book("Title", "", "Synopsis", "ISBN123", LocalDate.now(), validCategory);
        assertFalse(bookEmptyAuthor.isValid());

        // Test with null ISBN
        Book bookNullIsbn = new Book("Title", "Author", "Synopsis", null, LocalDate.now(), validCategory);
        assertFalse(bookNullIsbn.isValid());

        // Test with empty ISBN
        Book bookEmptyIsbn = new Book("Title", "Author", "Synopsis", "", LocalDate.now(), validCategory);
        assertFalse(bookEmptyIsbn.isValid());

        // Test with null release year
        Book bookNullYear = new Book("Title", "Author", "Synopsis", "ISBN123", null, validCategory);
        assertFalse(bookNullYear.isValid());

        // Test with null category
        Book bookNullCategory = new Book("Title", "Author", "Synopsis", "ISBN123", LocalDate.now(), null);
        assertFalse(bookNullCategory.isValid());
        assertNull(bookNullCategory.getCategoryName()); // Should handle null category gracefully
    }

    @Test
    @DisplayName("Should handle all constructor variations correctly")
    void shouldHandleAllConstructorVariations() {
        // Test default constructor
        Book defaultBook = new Book();
        assertNull(defaultBook.getTitle());
        assertNull(defaultBook.getAuthor());
        assertNull(defaultBook.getCategory());
        assertFalse(defaultBook.isValid());
        assertNull(defaultBook.getCategoryName());

        // Test constructor without ID
        Book bookWithoutId = new Book("Design Patterns", "Gang of Four", "Elements of reusable OO software",
                "9780201633610", LocalDate.of(1994, 10, 31), validCategory);
        assertEquals("Design Patterns", bookWithoutId.getTitle());
        assertEquals("Gang of Four", bookWithoutId.getAuthor());
        assertNull(bookWithoutId.getId());
        assertTrue(bookWithoutId.isValid());
        assertEquals("Programming", bookWithoutId.getCategoryName());

        // Test constructor with ID
        Book bookWithId = new Book(10L, "The Pragmatic Programmer", "Dave Thomas", "From journeyman to master",
                "9780201616224", LocalDate.of(1999, 10, 20), validCategory);
        assertEquals(Long.valueOf(10), bookWithId.getId());
        assertEquals("The Pragmatic Programmer", bookWithId.getTitle());
        assertTrue(bookWithId.isValid());
    }

    @Test
    @DisplayName("Should format LocalDate correctly in toString")
    void shouldFormatLocalDateCorrectlyInToString() {
        // Given
        LocalDate specificDate = LocalDate.of(2023, 6, 15);
        Book book = new Book(1L, "Test Book", "Test Author", "Test Synopsis", "TEST123", specificDate, validCategory);

        // When
        String result = book.toString();

        // Then
        assertTrue(result.contains("2023")); // Should show only year
        assertTrue(result.contains("Test Book"));
        assertTrue(result.contains("Test Author"));
        assertTrue(result.contains("TEST123"));
        assertFalse(result.contains("06")); // Should not show month
        assertFalse(result.contains("15")); // Should not show day
    }

    @Test
    @DisplayName("Should maintain data integrity with setters and category changes")
    void shouldMaintainDataIntegrityWithSettersAndCategoryChanges() {
        // Given
        Book book = new Book();
        Category newCategory = new Category("Fiction", "Fiction books and novels");
        newCategory.setId(2L);

        // When
        book.setId(5L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setSynopsis("Dystopian social science fiction novel");
        book.setIsbn("9780451524935");
        book.setReleaseYear(LocalDate.of(1949, 6, 8));
        book.setCategory(newCategory);

        // Then
        assertEquals(Long.valueOf(5), book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("Fiction", book.getCategoryName());
        assertTrue(book.isValid());

        // Test category change
        book.setCategory(validCategory);
        assertEquals("Programming", book.getCategoryName());

        // Test category removal
        book.setCategory(null);
        assertNull(book.getCategoryName());
        assertFalse(book.isValid());
    }

    @Test
    @DisplayName("Should handle edge cases with whitespace in fields")
    void shouldHandleEdgeCasesWithWhitespaceInFields() {
        // Given
        Book book = new Book();
        book.setTitle("  Clean Architecture  ");
        book.setAuthor("  Robert C. Martin  ");
        book.setIsbn("  9780134494166  ");
        book.setReleaseYear(LocalDate.of(2017, 9, 20));
        book.setCategory(validCategory);

        // When & Then
        // Lombok preserva os espaços, mas isValid() faz trim()
        assertEquals("  Clean Architecture  ", book.getTitle());
        assertEquals("  Robert C. Martin  ", book.getAuthor());
        assertEquals("  9780134494166  ", book.getIsbn());
        assertTrue(book.isValid()); // isValid() deve fazer trim() internamente

        // Test toString formatting
        String result = book.toString();
        assertTrue(result.contains("Clean Architecture"));
        assertTrue(result.contains("Robert C. Martin"));
    }
}