package entity;

import br.com.fuctura.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Entity Tests")
class TestCategory {

    @Test
    @DisplayName("Should create category with valid data successfully")
    void shouldCreateCategoryWithValidData() {
        // Given
        String name = "Programming";
        String description = "Books about programming and software development";

        // When
        Category category = new Category(name, description);

        // Then
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertNull(category.getId()); // ID should be null for new entities
        assertTrue(category.isValid());
    }

    @Test
    @DisplayName("Should handle category with whitespace data appropriately")
    void shouldHandleCategoryWithWhitespaceData() {
        // Given
        String nameWithSpaces = "  Science  ";
        String descriptionWithSpaces = "  Scientific books and research  ";

        // When
        Category category = new Category(nameWithSpaces, descriptionWithSpaces);

        // Then
        assertNotNull(category);
        assertEquals(nameWithSpaces, category.getName()); // Lombok preserva os espaços
        assertEquals(descriptionWithSpaces, category.getDescription());
        assertTrue(category.isValid()); // isValid() faz trim() internamente

        // Test toString formatting
        String result = category.toString();
        assertTrue(result.contains("Science"));
        assertTrue(result.contains("Scientific books"));
    }

    @Test
    @DisplayName("Should reject category with null or empty required fields")
    void shouldRejectCategoryWithNullOrEmptyFields() {
        // Test with null name
        Category categoryNullName = new Category(null, "Valid description");
        assertFalse(categoryNullName.isValid());

        // Test with empty name
        Category categoryEmptyName = new Category("", "Valid description");
        assertFalse(categoryEmptyName.isValid());

        // Test with whitespace-only name
        Category categoryWhitespaceName = new Category("   ", "Valid description");
        assertFalse(categoryWhitespaceName.isValid());

        // Test with null description
        Category categoryNullDesc = new Category("Valid name", null);
        assertFalse(categoryNullDesc.isValid());

        // Test with empty description
        Category categoryEmptyDesc = new Category("Valid name", "");
        assertFalse(categoryEmptyDesc.isValid());

        // Test with whitespace-only description
        Category categoryWhitespaceDesc = new Category("Valid name", "   ");
        assertFalse(categoryWhitespaceDesc.isValid());

        // Test with both null
        Category categoryBothNull = new Category(null, null);
        assertFalse(categoryBothNull.isValid());
    }

    @Test
    @DisplayName("Should format toString correctly for long descriptions")
    void shouldFormatToStringCorrectlyForLongDescriptions() {
        // Given
        String longDescription = "This is a very long description that exceeds fifty characters and should be truncated in the toString method output";
        Category category = new Category(1L, "Long Description Category", longDescription);

        // When
        String result = category.toString();

        // Then
        assertTrue(result.contains("Long Description Category"));
        assertTrue(result.contains("...")); // Should be truncated
        assertFalse(result.contains("should be truncated")); // End of original text should not appear
    }

    @Test
    @DisplayName("Should handle all constructor variations correctly")
    void shouldHandleAllConstructorVariations() {
        // Test default constructor
        Category defaultCategory = new Category();
        assertNull(defaultCategory.getName());
        assertNull(defaultCategory.getDescription());
        assertNull(defaultCategory.getId());
        assertFalse(defaultCategory.isValid());

        // Test constructor without ID
        Category categoryWithoutId = new Category("Fiction", "Fiction books and novels");
        assertEquals("Fiction", categoryWithoutId.getName());
        assertEquals("Fiction books and novels", categoryWithoutId.getDescription());
        assertNull(categoryWithoutId.getId());
        assertTrue(categoryWithoutId.isValid());

        // Test constructor with ID
        Category categoryWithId = new Category(10L, "History", "Historical books and biographies");
        assertEquals(Long.valueOf(10), categoryWithId.getId());
        assertEquals("History", categoryWithId.getName());
        assertEquals("Historical books and biographies", categoryWithId.getDescription());
        assertTrue(categoryWithId.isValid());
    }

    @Test
    @DisplayName("Should maintain data integrity with setters")
    void shouldMaintainDataIntegrityWithSetters() {
        // Given
        Category category = new Category();

        // When
        category.setId(5L);
        category.setName("Art");
        category.setDescription("Art and design books");

        // Then
        assertEquals(Long.valueOf(5), category.getId());
        assertEquals("Art", category.getName());
        assertEquals("Art and design books", category.getDescription());
        assertTrue(category.isValid());

        // Test modification
        category.setName("Updated Art");
        assertEquals("Updated Art", category.getName());
        assertTrue(category.isValid());

        // Test invalidation
        category.setName(null);
        assertFalse(category.isValid());
    }
}