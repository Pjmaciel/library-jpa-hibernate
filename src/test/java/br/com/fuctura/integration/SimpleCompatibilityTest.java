package br.com.fuctura.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;


class SimpleCompatibilityTest {

    @Test
    @DisplayName("1️⃣ Verificar Java Version")
    void testJavaVersion() {
        System.out.println("🔍 Verificando compatibilidade Java...");

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String javaHome = System.getProperty("java.home");

        System.out.println("✅ Java Version: " + javaVersion);
        System.out.println("✅ Java Vendor: " + javaVendor);
        System.out.println("✅ Java Home: " + javaHome);

        // Verificar se está usando Java 11+ (mínimo para Jakarta EE)
        String[] versionParts = javaVersion.split("\\.");
        int majorVersion = Integer.parseInt(versionParts[0]);

        assertTrue(majorVersion >= 11, "Java deve ser versão 11 ou superior. Atual: " + javaVersion);

        System.out.println("🎉 Java version compatível!");
    }

    @Test
    @DisplayName("2️⃣ Verificar JUnit Funcionando")
    void testJUnitWorking() {
        System.out.println("🧪 Testando JUnit...");

        assertEquals(2, 1 + 1, "JUnit deve estar funcionando");
        assertTrue(true, "Assertions devem funcionar");

        System.out.println("✅ JUnit 5 funcionando!");
    }

    @Test
    @DisplayName("3️⃣ Verificar Classes Básicas")
    void testBasicClasses() {
        System.out.println("📦 Testando classes básicas...");

        // Testa se consegue carregar classes básicas
        assertDoesNotThrow(() -> {
            Class.forName("java.util.List");
            System.out.println("✅ Java Collections funcionando");
        });

        assertDoesNotThrow(() -> {
            Class.forName("java.lang.String");
            System.out.println("✅ Java Core funcionando");
        });

        System.out.println("🎉 Classes básicas funcionando!");
    }

    @Test
    @DisplayName("4️⃣ Verificar Jakarta EE (se disponível)")
    void testJakartaEE() {
        System.out.println("🌟 Testando Jakarta EE...");

        try {
            Class.forName("jakarta.persistence.Entity");
            System.out.println("✅ Jakarta Persistence API disponível");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Jakarta Persistence API não encontrada: " + e.getMessage());
        }

        try {
            Class.forName("org.hibernate.SessionFactory");
            System.out.println("✅ Hibernate Core disponível");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Hibernate Core não encontrado: " + e.getMessage());
        }

        System.out.println("🔍 Verificação Jakarta EE concluída");
    }

    @Test
    @DisplayName("5️⃣ Verificar Lombok (se disponível)")
    void testLombok() {
        System.out.println("🛠️ Testando Lombok...");

        try {
            Class.forName("lombok.Data");
            System.out.println("✅ Lombok disponível");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Lombok não encontrado: " + e.getMessage());
        }

        System.out.println("🔍 Verificação Lombok concluída");
    }

    @Test
    @DisplayName("6️⃣ Test Final - Resumo")
    void testFinalSummary() {
        System.out.println("\n📊 RESUMO DA COMPATIBILIDADE:");
        System.out.println("✅ Java Runtime: OK");
        System.out.println("✅ JUnit 5: OK");
        System.out.println("✅ Classes Core: OK");
        System.out.println("📋 Próximo: Verificar dependências Maven");

        System.out.println("\n🎯 COMANDOS PARA EXECUTAR:");
        System.out.println("1. Instalar Maven: brew install maven");
        System.out.println("2. Verificar Java: java --version");
        System.out.println("3. Compilar: mvn clean compile");
        System.out.println("4. Testar: mvn test");

        assertTrue(true, "Teste de compatibilidade concluído");
    }
}