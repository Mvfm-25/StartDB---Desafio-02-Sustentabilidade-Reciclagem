package com.startdb.sust;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PontoColetaRepository pontoColetaRepository;
    private final TipoColetaRepository tipoColetaRepository;

    public DataInitializer(PontoColetaRepository pontoColetaRepository, 
                           TipoColetaRepository tipoColetaRepository) {
        this.pontoColetaRepository = pontoColetaRepository;
        this.tipoColetaRepository = tipoColetaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem dados para evitar duplicatas
        if (pontoColetaRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Criar tipos de coleta se não existirem
        TipoColeta tipoPlastico = new TipoColeta();
        tipoPlastico.setNome("plastico");
        tipoPlastico = tipoColetaRepository.save(tipoPlastico);

        TipoColeta tipoMetal = new TipoColeta();
        tipoMetal.setNome("metal");
        tipoMetal = tipoColetaRepository.save(tipoMetal);

        TipoColeta tivoPapel = new TipoColeta();
        tivoPapel.setNome("papel");
        tivoPapel = tipoColetaRepository.save(tivoPapel);

        TipoColeta tipoVidro = new TipoColeta();
        tipoVidro.setNome("vidro");
        tipoVidro = tipoColetaRepository.save(tipoVidro);

        // Criar pontos de coleta de exemplo
        // Ponto 1
        PontoColeta ponto1 = new PontoColeta();
        ponto1.setNomePonto("Ponto de Coleta Centro");
        ponto1.setTiposColeta(Arrays.asList(tipoPlastico, tipoMetal));
        ponto1.setDesc("Ponto de coleta localizado no centro da cidade");
        
        Endereco endereco1 = new Endereco("Rua das Flores", "123", "Centro", "01234-567", null);
        ponto1.setEndereco(endereco1);
        ponto1.setHorariosFunc(Arrays.asList(
            new HorarioFuncionamento("SEGUNDA", "07:00", "18:00", false),
            new HorarioFuncionamento("TERÇA", "07:00", "18:00", false),
            new HorarioFuncionamento("QUARTA", "07:00", "18:00", false),
            new HorarioFuncionamento("QUINTA", "07:00", "18:00", false),
            new HorarioFuncionamento("SEXTA", "07:00", "18:00", false),
            new HorarioFuncionamento("SÁBADO", "08:00", "14:00", false)
        ));

        // Ponto 2
        PontoColeta ponto2 = new PontoColeta();
        ponto2.setNomePonto("Ponto de Coleta Zona Leste");
        ponto2.setTiposColeta(Arrays.asList(tipoMetal, tivoPapel));
        ponto2.setDesc("Ponto de coleta na região leste");
        
        Endereco endereco2 = new Endereco("Avenida Brasil", "456", "Vila Mariana", "04102-000", null);
        ponto2.setEndereco(endereco2);
        ponto2.setHorariosFunc(Arrays.asList(
            new HorarioFuncionamento("SEGUNDA", "06:00", "20:00", true),
            new HorarioFuncionamento("TERÇA", "06:00", "20:00", true),
            new HorarioFuncionamento("QUARTA", "06:00", "20:00", true),
            new HorarioFuncionamento("QUINTA", "06:00", "20:00", true),
            new HorarioFuncionamento("SEXTA", "06:00", "20:00", true),
            new HorarioFuncionamento("SÁBADO", "06:00", "20:00", true),
            new HorarioFuncionamento("DOMINGO", "06:00", "20:00", true)
        ));

        // Ponto 3
        PontoColeta ponto3 = new PontoColeta();
        ponto3.setNomePonto("Ponto de Coleta Zona Norte");
        ponto3.setTiposColeta(Arrays.asList(tivoPapel, tipoVidro));
        ponto3.setDesc("Ponto de coleta na região norte");
        
        Endereco endereco3 = new Endereco("Rua da Paz", "789", "Santana", "02222-000", null);
        ponto3.setEndereco(endereco3);
        ponto3.setHorariosFunc(Arrays.asList(
            new HorarioFuncionamento("SEGUNDA", "07:30", "17:30", false),
            new HorarioFuncionamento("TERÇA", "07:30", "17:30", false),
            new HorarioFuncionamento("QUARTA", "07:30", "17:30", false),
            new HorarioFuncionamento("QUINTA", "07:30", "17:30", false),
            new HorarioFuncionamento("SEXTA", "07:30", "17:30", false)
        ));

        // Ponto 4
        PontoColeta ponto4 = new PontoColeta();
        ponto4.setNomePonto("Ponto de Coleta Zona Oeste");
        ponto4.setTiposColeta(Arrays.asList(tipoVidro, tipoPlastico));
        ponto4.setDesc("Ponto de coleta na região oeste");
        
        Endereco endereco4 = new Endereco("Rua do Comércio", "321", "Pinheiros", "05429-000", null);
        ponto4.setEndereco(endereco4);
        ponto4.setHorariosFunc(Arrays.asList(
            new HorarioFuncionamento("SEGUNDA", "08:00", "19:00", false),
            new HorarioFuncionamento("TERÇA", "08:00", "19:00", false),
            new HorarioFuncionamento("QUARTA", "08:00", "19:00", false),
            new HorarioFuncionamento("QUINTA", "08:00", "19:00", false),
            new HorarioFuncionamento("SEXTA", "08:00", "19:00", false),
            new HorarioFuncionamento("SÁBADO", "08:00", "19:00", false)
        ));

        // Salvar todos os pontos
        pontoColetaRepository.saveAll(Arrays.asList(ponto1, ponto2, ponto3, ponto4));
        
        System.out.println("✓ Base de dados populada com sucesso!");
    }
}
