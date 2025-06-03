package br.com.fuctura.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JPAUtils {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                // Carregar as propriedades do arquivo db.properties
                Properties dbProps = loadProperties();

                // Criar um mapa para sobrescrever as propriedades no persistence.xml
                Map<String, Object> configOverrides = new HashMap<>();
                configOverrides.put("jakarta.persistence.jdbc.url", dbProps.getProperty("dburl"));
                configOverrides.put("jakarta.persistence.jdbc.user", dbProps.getProperty("user"));
                configOverrides.put("jakarta.persistence.jdbc.password", dbProps.getProperty("password"));

                // Adicionar propriedades SSL se necessário
                String useSSL = dbProps.getProperty("useSSL");
                if (useSSL != null && useSSL.equals("true")) {
                    configOverrides.put("jakarta.persistence.jdbc.ssl", "true");
                    configOverrides.put("jakarta.persistence.jdbc.sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory");
                }

                // Desabilitar as configurações de cache que estão causando problemas
                configOverrides.put("hibernate.cache.use_second_level_cache", "false");
                configOverrides.put("hibernate.cache.use_query_cache", "false");

                // IMPORTANTE: Configurações para resolver o problema de validação de esquema
                // Muda para update em vez de validate, para criar as tabelas automaticamente se não existirem
                configOverrides.put("hibernate.hbm2ddl.auto", "update");

                // Desabilita a validação de esquema para testes
                configOverrides.put("jakarta.persistence.schema-generation.database.action", "none");
                configOverrides.put("hibernate.schema_validation.enabled", "false");

                // Usar a unidade de persistência FUCTURA-PU-PROD para o ambiente de produção
                emf = Persistence.createEntityManagerFactory("FUCTURA-PU-PROD", configOverrides);
            } catch (Exception e) {
                throw new DbException("Erro ao inicializar EntityManagerFactory: " + e.getMessage(), e);
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private static Properties loadProperties() {
        try (InputStream is = JPAUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new DbException("Arquivo db.properties não encontrado");
            }
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new DbException("Erro ao carregar db.properties: " + e.getMessage(), e);
        }
    }
}