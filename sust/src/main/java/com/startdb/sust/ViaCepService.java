package com.startdb.sust;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class ViaCepService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String VIACEP_URL = "https://viacep.com.br/ws/";

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    // Busca informações de endereço pelo CEP
    public ViaCepResponse buscarPorCep(String cep){
        try {
            // Remove hífens se houver
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            
            // Valida se tem 8 dígitos
            if(cepLimpo.length() != 8){
                return null;
            }

            String url = VIACEP_URL + cepLimpo + "/json/";
            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);
            
            // Se retornar erro (CEP não encontrado)
            if(response != null && response.isErro()){
                return null;
            }
            
            return response;
        } catch (RestClientException e) {
            return null;
        }
    }

    // Cria um Endereco a partir de um CEP, integrando com as entidades UF e Cidade
    public Endereco criarEnderecoAPartirDeCep(String cep, String numero, String complemento){
        ViaCepResponse response = buscarPorCep(cep);
        
        if(response == null){
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(response.getLogradouro());
        endereco.setNumero(numero);
        endereco.setBairro(response.getBairro());
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        
        return endereco;
    }

    // Obtém ou cria uma UF a partir dos dados do ViaCEP
    public Uf obterOuCriarUf(String sigla, String nome){
        // Busca se já existe UF com essa sigla
        Uf ufExistente = ufRepository.findAll().stream()
            .filter(u -> u.getSigla().equalsIgnoreCase(sigla))
            .findFirst()
            .orElse(null);
        
        if(ufExistente != null){
            return ufExistente;
        }
        
        // Se não existe, cria uma nova
        Uf novaUf = new Uf(nome, sigla);
        return ufRepository.save(novaUf);
    }

    // Obtém ou cria uma Cidade a partir dos dados do ViaCEP
    public Cidade obterOuCriarCidade(String nomeCidade, Uf uf){
        // Busca se já existe Cidade com esse nome e UF
        Cidade cidadeExistente = cidadeRepository.findAll().stream()
            .filter(c -> c.getNome().equalsIgnoreCase(nomeCidade) && c.getUf().getId() == uf.getId())
            .findFirst()
            .orElse(null);
        
        if(cidadeExistente != null){
            return cidadeExistente;
        }
        
        // Se não existe, cria uma nova
        Cidade novaCidade = new Cidade();
        novaCidade.setNome(nomeCidade);
        novaCidade.setUf(uf);
        return cidadeRepository.save(novaCidade);
    }

    // Cria um Endereco completo integrando com as entidades UF e Cidade
    public EnderecoComCidade criarEnderecoComCidade(String cep, String numero, String complemento){
        ViaCepResponse response = buscarPorCep(cep);
        
        if(response == null){
            return null;
        }

        // Obtém ou cria a UF
        Uf uf = obterOuCriarUf(response.getUf(), ""); // Nome vazio, você pode preencher depois

        // Obtém ou cria a Cidade
        Cidade cidade = obterOuCriarCidade(response.getLocalidade(), uf);

        // Cria o Endereco
        Endereco endereco = new Endereco();
        endereco.setRua(response.getLogradouro());
        endereco.setNumero(numero);
        endereco.setBairro(response.getBairro());
        endereco.setCep(cep);
        endereco.setComplemento(complemento);

        return new EnderecoComCidade(endereco, cidade, uf);
    }
}

