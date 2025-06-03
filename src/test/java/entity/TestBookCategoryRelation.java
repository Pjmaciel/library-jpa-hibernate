package entity;

import br.com.fuctura.entity.Book;
import br.com.fuctura.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Book-Category Relationship Tests")
class TestBookCategoryRelation {

    private Category programmingCategory;
    private Category fictionCategory;
    private Category scienceCategory;

    @BeforeEach
    void setUp() {
        programmingCategory = new Category(1L, "Programming", "Programming and software development books");
        fictionCategory = new Category(2L, "Fiction", "Fiction books and novels");
        scienceCategory = new Category(3L, "Science", "Science and research books");
    }

    @Test
    @DisplayName("Should establish correct book-category relationship and retrieve category data")
    void shouldEstablishCorrectBookCategoryRelationship() {
        // Given
        Book cleanCodeBook = new Book("Clean Code", "Robert C. Martin",
                "A handbook of agile software craftsmanship",
                "9780132350884", LocalDate.of(2008, 8, 1), programmingCategory);

        Book duneBook = new Book("Dune", "Frank Herbert",
                "Science fiction epic about desert planet Arrakis",
                "9780441172719", LocalDate.of(1965, 8, 1), fictionCategory);

        // When & Then
        assertEquals(programmingCategory, cleanCodeBook.getCategory());
        assertEquals("Programming", cleanCodeBook.getCategoryName());
        assertEquals("Programming and software development books", cleanCodeBook.getCategory().getDescription());

        assertEquals(fictionCategory, duneBook.getCategory());
        assertEquals("Fiction", duneBook.getCategoryName());
        assertEquals("Fiction books and novels", duneBook.getCategory().getDescription());

        assertTrue(cleanCodeBook.isValid());
        assertTrue(duneBook.isValid());
    }

    @Test
    @DisplayName("Should handle category reassignment and maintain data integrity")
    void shouldHandleCategoryReassignmentAndMaintainIntegrity() {
        // Given
        Book book = new Book("A Brief History of Time", "Stephen Hawking",
                "Popular science book about cosmology",
                "9780553380163", LocalDate.of(1988, 4, 1), fictionCategory);

        // Initially assigned to wrong category
        assertEquals("Fiction", book.getCategoryName());
        assertTrue(book.isValid());

        // When - reassign to correct category
        book.setCategory(scienceCategory);

        // Then
        assertEquals(scienceCategory, book.getCategory());
        assertEquals("Science", book.getCategoryName());
        assertEquals("Science and research books", book.getCategory().getDescription());
        assertTrue(book.isValid());

        // Verify old category is not affected
        assertEquals("Fiction", fictionCategory.getName());
        assertEquals("Fiction books and novels", fictionCategory.getDescription());
    }

    @Test
    @DisplayName("Should handle null category scenarios and maintain validation rules")
    void shouldHandleNullCategoryScenarios() {
        // Given - book without category
        Book orphanBook = new Book("Orphan Book", "Unknown Author",
                "A book without category",
                "9780000000000", LocalDate.of(2023, 1, 1), null);

        // When & Then
        assertNull(orphanBook.getCategory());
        assertNull(orphanBook.getCategoryName());
        assertFalse(orphanBook.isValid()); // Should be invalid without category

        // Test category assignment
        orphanBook.setCategory(programmingCategory);
        assertEquals("Programming", orphanBook.getCategoryName());
        assertTrue(orphanBook.isValid());

        // Test category removal
        orphanBook.setCategory(null);
        assertNull(orphanBook.getCategoryName());
        assertFalse(orphanBook.isValid());
    }

    @Test
    @DisplayName("Should maintain referential integrity when category changes")
    void shouldMaintainReferentialIntegrityWhenCategoryChanges() {
        // Given
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", "Author 1", "Synopsis 1", "ISBN001", LocalDate.now(), programmingCategory));
        books.add(new Book("Book 2", "Author 2", "Synopsis 2", "ISBN002", LocalDate.now(), programmingCategory));
        books.add(new Book("Book 3", "Author 3", "Synopsis 3", "ISBN003", LocalDate.now(), fictionCategory));

        // When - change category name
        programmingCategory.setName("Software Development");
        programmingCategory.setDescription("Software development and programming books");

        // Then - books should reflect the category changes
        assertEquals("Software Development", books.get(0).getCategoryName());
        assertEquals("Software Development", books.get(1).getCategoryName());
        assertEquals("Software development and programming books", books.get(0).getCategory().getDescription());
        assertEquals("Software development and programming books", books.get(1).getCategory().getDescription());

        // Fiction book should remain unchanged
        assertEquals("Fiction", books.get(2).getCategoryName());
        assertEquals("Fiction books and novels", books.get(2).getCategory().getDescription());

        // All books should still be valid
        assertTrue(books.get(0).isValid());
        assertTrue(books.get(1).isValid());
        assertTrue(books.get(2).isValid());
    }

    @Test
    @DisplayName("Should handle category with invalid data and affect book validation")
    void shouldHandleCategoryWithInvalidDataAndAffectBookValidation() {
        // Given
        Category invalidCategory = new Category();
        invalidCategory.setId(99L);
        invalidCategory.setName(null); // Invalid category
        invalidCategory.setDescription("");

        Book book = new Book("Valid Book", "Valid Author", "Valid Synopsis",
                "9780123456789", LocalDate.of(2023, 1, 1), invalidCategory);

        // When & Then
        assertFalse(invalidCategory.isValid());
        assertTrue(book.isValid()); // Book validation doesn't check category validity
        assertNull(book.getCategoryName()); // Should handle null category name gracefully

        // Fix category
        invalidCategory.setName("Fixed Category");
        invalidCategory.setDescription("Fixed description");

        assertTrue(invalidCategory.isValid());
        assertTrue(book.isValid());
        assertEquals("Fixed Category", book.getCategoryName());
    }

    @Test
    @DisplayName("Should demonstrate multiple books sharing same category instance")
    void shouldDemonstrateMultipleBooksShareSameCategoryInstance() {
        // Given
        List<Book> programmingBooks = new ArrayList<>();
        programmingBooks.add(new Book("Clean Code", "Robert C. Martin", "Craftsmanship", "ISBN1", LocalDate.now(), programmingCategory));
        programmingBooks.add(new Book("Effective Java", "Joshua Bloch", "Best practices", "ISBN2", LocalDate.now(), programmingCategory));
        programmingBooks.add(new Book("Design Patterns", "Gang of Four", "Reusable OO software", "ISBN3", LocalDate.now(), programmingCategory));

        // When & Then - all books reference the same category instance
        assertTrue(programmingBooks.stream()
                .allMatch(book -> book.getCategory() == programmingCategory)); // Same reference

        assertTrue(programmingBooks.stream()
                .allMatch(book -> "Programming".equals(book.getCategoryName())));

        assertTrue(programmingBooks.stream()
                .allMatch(Book::isValid));

        // Change category affects all books
        programmingCategory.setName("Computer Science");
        assertTrue(programmingBooks.stream()
                .allMatch(book -> "Computer Science".equals(book.getCategoryName())));
    }

    @Test
    @DisplayName("Should handle toString representation with category information")
    void shouldHandleToStringRepresentationWithCategoryInformation() {
        // Given
        Book bookWithCategory = new Book(1L, "Test Book", "Test Author", "Test Synopsis",
                "TEST123", LocalDate.of(2023, 6, 15), programmingCategory);

        Book bookWithoutCategory = new Book(2L, "Orphan Book", "Orphan Author", "Orphan Synopsis",
                "ORPHAN123", LocalDate.of(2023, 6, 15), null);

        // When
        String resultWithCategory = bookWithCategory.toString();
        String resultWithoutCategory = bookWithoutCategory.toString();

        // Then
        assertTrue(resultWithCategory.contains("Test Book"));
        assertTrue(resultWithCategory.contains("Test Author"));
        assertTrue(resultWithCategory.contains("TEST123"));
        assertTrue(resultWithCategory.contains("2023"));

        assertTrue(resultWithoutCategory.contains("Orphan Book"));
        assertTrue(resultWithoutCategory.contains("Orphan Author"));
        assertTrue(resultWithoutCategory.contains("ORPHAN123"));

        // Category name access should work independently of toString
        assertEquals("Programming", bookWithCategory.getCategoryName());
        assertNull(bookWithoutCategory.getCategoryName());
    }
}