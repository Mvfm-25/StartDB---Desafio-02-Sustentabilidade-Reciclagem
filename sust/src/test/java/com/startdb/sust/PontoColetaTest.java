package com.startdb.sust;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Testes unitários da entidade PontoColeta.
 * 
 * Estrutura: Testes agrupados por comportamento (não por getter/setter).
 * - Teste de instanciação
 * - Teste de relacionamentos
 * - Teste de dados críticos (validações futuras)
 * - Testes de casos extremos
 */
@DisplayName("PontoColeta")
public class PontoColetaTest {

    private PontoColeta pontoColeta;
    private TipoColeta tipoColeta;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        pontoColeta = new PontoColeta();
        tipoColeta = new TipoColeta();
        endereco = new Endereco();
    }

    // ==================== TESTES DE INSTANCIAÇÃO ====================
    @Nested
    @DisplayName("Instanciação")
    class InstanciacaoTests {

        @Test
        @DisplayName("Deve criar instância com construtor padrão")
        void shouldInstantiateWithDefaultConstructor() {
            assertNotNull(pontoColeta);
        }
    }

    // ==================== TESTES DE DADOS CRÍTICOS ====================
    @Nested
    @DisplayName("Identificação e Localização")
    class IdentificacaoTests {

        @Test
        @DisplayName("Deve armazenar nome do ponto corretamente")
        void shouldStorePontoName() {
            String nomePonto = "Ponto de Coleta Centro";
            pontoColeta.setNomePonto(nomePonto);
            
            assertEquals(nomePonto, pontoColeta.getNomePonto());
        }

        @Test
        @DisplayName("Deve armazenar endereço corretamente")
        void shouldStoreEndereco() {
            pontoColeta.setEndereco(endereco);
            
            assertSame(endereco, pontoColeta.getEndereco());
        }

        @Test
        @DisplayName("Deve armazenar descrição corretamente")
        void shouldStoreDescription() {
            String desc = "Coleta de eletrônicos e plásticos";
            pontoColeta.setDesc(desc);
            
            assertEquals(desc, pontoColeta.getDesc());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "A", "Ponto com nome bem longo para teste"})
        @DisplayName("Deve aceitar strings variadas no nome")
        void shouldAcceptVariousStringLengths(String nome) {
            pontoColeta.setNomePonto(nome);
            assertEquals(nome, pontoColeta.getNomePonto());
        }
    }

    // ==================== TESTES DE RELACIONAMENTOS ====================
    @Nested
    @DisplayName("Relacionamentos (ManyToOne, Embedded)")
    class RelacionamentoTests {

        @Test
        @DisplayName("Deve manter relacionamento com TipoColeta")
        void shouldMaintainTipoColetaRelationship() {
            pontoColeta.setTipoColeta(tipoColeta);
            
            assertSame(tipoColeta, pontoColeta.getTipoColeta());
        }

        @Test
        @DisplayName("Deve permitir TipoColeta null")
        void shouldAllowNullTipoColeta() {
            pontoColeta.setTipoColeta(null);
            
            assertNull(pontoColeta.getTipoColeta());
        }

        @Test
        @DisplayName("Deve manter Endereco (Embedded) corretamente")
        void shouldMaintainEmbeddedEndereco() {
            pontoColeta.setEndereco(endereco);
            
            assertSame(endereco, pontoColeta.getEndereco());
        }
    }

    // ==================== TESTES DE COLEÇÕES ====================
    @Nested
    @DisplayName("Horários de Funcionamento (ElementCollection)")
    class HorariosFuncionamentoTests {

        @Test
        @DisplayName("Deve armazenar lista de horários")
        void shouldStoreHorariosList() {
            List<HorarioFuncionamento> horarios = new ArrayList<>();
            pontoColeta.setHorariosFunc(horarios);
            
            assertSame(horarios, pontoColeta.getHorariosFunc());
        }

        @Test
        @DisplayName("Deve permitir lista vazia de horários")
        void shouldAllowEmptyHorariosList() {
            List<HorarioFuncionamento> vazia = new ArrayList<>();
            pontoColeta.setHorariosFunc(vazia);
            
            assertTrue(pontoColeta.getHorariosFunc().isEmpty());
        }

        @Test
        @DisplayName("Deve permitir horários null")
        void shouldAllowNullHorarios() {
            pontoColeta.setHorariosFunc(null);
            
            assertNull(pontoColeta.getHorariosFunc());
        }
    }

    // ==================== TESTES DE IDENTIDADE ====================
    @Nested
    @DisplayName("ID (Chave Primária)")
    class IdTests {

        @Test
        @DisplayName("Deve armazenar ID gerado pelo banco")
        void shouldStoreId() {
            pontoColeta.setId(42L);
            assertEquals(42L, pontoColeta.getId());
        }

        @Test
        @DisplayName("Deve suportar ID com valor 0")
        void shouldSupportZeroId() {
            pontoColeta.setId(0L);
            assertEquals(0L, pontoColeta.getId());
        }

        @Test
        @DisplayName("Deve suportar IDs grandes")
        void shouldSupportLargeIds() {
            long largeId = Long.MAX_VALUE;
            pontoColeta.setId(largeId);
            assertEquals(largeId, pontoColeta.getId());
        }
    }

    // ==================== TESTES DE INTEGRAÇÃO (TODOS OS CAMPOS) ====================
    @Nested
    @DisplayName("Integração - Todos os campos")
    class IntegracaoTests {

        @Test
        @DisplayName("Deve manter estado completo de um ponto de coleta")
        void shouldMaintainCompleteState() {
            // Arrange
            long expectedId = 1L;
            String expectedNome = "Ponto Central";
            String expectedDesc = "Recicláveis diversos";
            List<HorarioFuncionamento> expectedHorarios = new ArrayList<>();

            // Act
            pontoColeta.setId(expectedId);
            pontoColeta.setNomePonto(expectedNome);
            pontoColeta.setTipoColeta(tipoColeta);
            pontoColeta.setEndereco(endereco);
            pontoColeta.setDesc(expectedDesc);
            pontoColeta.setHorariosFunc(expectedHorarios);

            // Assert - Verifica se todos os campos foram preservados
            assertEquals(expectedId, pontoColeta.getId());
            assertEquals(expectedNome, pontoColeta.getNomePonto());
            assertSame(tipoColeta, pontoColeta.getTipoColeta());
            assertSame(endereco, pontoColeta.getEndereco());
            assertEquals(expectedDesc, pontoColeta.getDesc());
            assertSame(expectedHorarios, pontoColeta.getHorariosFunc());
        }

        @Test
        @DisplayName("Deve permitir atualizar campos independentemente")
        void shouldUpdateFieldsIndependently() {
            String nome1 = "Primeiro Nome";
            String nome2 = "Segundo Nome";
            
            pontoColeta.setNomePonto(nome1);
            assertEquals(nome1, pontoColeta.getNomePonto());
            
            pontoColeta.setNomePonto(nome2);
            assertEquals(nome2, pontoColeta.getNomePonto());
        }
    }

}