# 📋 Guia de Testes com Postman - Sustentabilidade Reciclagem

## 🔧 Configuração Base
- **URL Base**: `http://localhost:2553/api`
- **Content-Type**: `application/json`

---

## 📍 1. ENDPOINTS DE CIDADES

### 1.1 GET - Listar Todas as Cidades (MODIFICADO: RETORNO SIMPLIFICADO)
**Endpoint**: `GET /cidades/todas`

http://localhost:2553/api/cidades/todas


**Resposta Esperada (200 OK)**:
```json
[
  {
    "id": 1,
    "nome": "São Paulo",
    "uf": {
      "id": 1,
      "sigla": "SP",
      "nome": "São Paulo"
    }
  }
  // Retorna uma lista de CidadeSimplesDto (não inclui a lista "pontos")
]

1.2 GET - Buscar Cidade por Nome

Endpoint: GET /cidades/{nome}

http://localhost:2553/api/cidades/São Paulo

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "nome": "São Paulo",
  "uf": {
    "id": 1,
    "sigla": "SP",
    "nome": "São Paulo"
  },
  "pontos": [...]
}

Resposta Esperada se não encontrar (404 Not Found):

404 Not Found

🗺️ 2. ENDPOINTS DE ESTADOS (UF)
2.1 GET - Listar Todos os Estados

Endpoint: GET /ufs/todas

http://localhost:2553/api/ufs/todas

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "sigla": "SP",
    "nome": "São Paulo"
  },
  {
    "id": 2,
    "sigla": "RJ",
    "nome": "Rio de Janeiro"
  }
]

2.2 GET - Buscar Estado por Sigla

Endpoint: GET /ufs/{sigla}

http://localhost:2553/api/ufs/SP

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "sigla": "SP",
  "nome": "São Paulo"
}

2.3 GET - Listar Cidades de um Estado

Endpoint: GET /ufs/{sigla}/cidades

http://localhost:2553/api/ufs/SP/cidades

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nome": "São Paulo",
    "uf": {
      "id": 1,
      "sigla": "SP",
      "nome": "São Paulo"
    },
    "pontos": [
      // Lista completa de pontos da cidade aninhada aqui
      {
        "id": 1,
        "nomePonto": "Ponto de Coleta Centro",
        "tiposColeta": [
          {
            "id": 1,
            "nome": "plastico"
          },
          {
            "id": 2,
            "nome": "metal"
          }
        ],
        ...
      }
    ]
  }
]

🗑️ 3. ENDPOINTS DE PONTOS DE COLETA
3.1 GET - Listar Todos os Pontos (MODIFICADO: AGRUPADO POR CIDADE)

Endpoint: GET /pontos

http://localhost:2553/api/pontos

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nome": "São Paulo",
    "uf": {
      "id": 1,
      "sigla": "SP",
      "nome": "São Paulo"
    },
    "pontos": [
      {
        "id": 1,
        "nomePonto": "Ponto de Coleta Centro",
        "tiposColeta": [
          {
            "id": 1,
            "nome": "plastico"
          },
          {
            "id": 2,
            "nome": "metal"
          }
        ],
        "desc": "Ponto de coleta localizado no centro da cidade",
        "endereco": {
          "rua": "Rua das Flores",
          "numero": "123",
          "bairro": "Centro",
          "cep": "01234-567",
          "complemento": null,
          "enderecoCompleto": "Rua das Flores, 123 - Centro - 01234-567"
        },
        "horariosFunc": [
          {
            "diaSemana": "SEGUNDA",
            "horaAbertura": "07:00",
            "horaFechamento": "18:00",
            "funcionaFeriado": false
          }
        ]
        // O campo "cidade" pode ser omitido ou ser nulo, pois o ponto já está aninhado à cidade
      }
    ]
  }
]

3.2 GET - Buscar Ponto por ID

Endpoint: GET /pontos/{id}

http://localhost:2553/api/pontos/1

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "nomePonto": "Ponto de Coleta Centro",
  "tiposColeta": [...],
  "desc": "Ponto de coleta localizado no centro da cidade",
  "endereco": {...},
  "horariosFunc": [...],
  "cidade": {
    "id": 1,
    "nome": "São Paulo",
    "uf": {...}
  }
}

3.3 GET - Buscar Pontos por Tipo de Coleta ⭐

Endpoint: GET /coletas/{tipo}

http://localhost:2553/api/coletas/plastico

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    "tiposColeta": [
      {
        "id": 1,
        "nome": "plastico"
      },
      {
        "id": 2,
        "nome": "metal"
      }
    ],
    "desc": "Ponto de coleta localizado no centro da cidade",
    "endereco": {...},
    "horariosFunc": [...],
    "cidade": {...}
  }
]

Exemplos de tipos:

    /api/coletas/plastico

    /api/coletas/metal

    /api/coletas/papel

    /api/coletas/vidro

3.4 GET - Buscar Pontos por Cidade

Endpoint: GET /cidades/{nomeCidade}/pontos

http://localhost:2553/api/cidades/São Paulo/pontos

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.5 GET - Buscar Pontos por Bairro

Endpoint: GET /bairros/{nomeBairro}/pontos

http://localhost:2553/api/bairros/Centro/pontos

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.6 GET - Buscar Pontos por CEP

Endpoint: GET /ceps/{cep}/pontos

http://localhost:2553/api/ceps/01234-567/pontos

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.7 GET - Buscar Pontos por Rua

Endpoint: GET /ruas/{nomeRua}/pontos

http://localhost:2553/api/ruas/Rua das Flores/pontos

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.8 GET - Buscar Pontos por Dia da Semana

Endpoint: GET /dias-semana/{diaSemana}/pontos

http://localhost:2553/api/dias-semana/SEGUNDA/pontos

Dias válidos: SEGUNDA, TERÇA, QUARTA, QUINTA, SEXTA, SÁBADO, DOMINGO

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.9 GET - Pontos Funcionando Hoje

Endpoint: GET /pontos/hoje

http://localhost:2553/api/pontos/hoje

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.10 GET - Pontos Abertos Agora

Endpoint: GET /pontos/abertos-agora

http://localhost:2553/api/pontos/abertos-agora

Resposta Esperada (200 OK):
JSON

[
  {
    "id": 1,
    "nomePonto": "Ponto de Coleta Centro",
    ...
  }
]

3.11 POST - Criar Novo Ponto em uma Cidade

Endpoint: POST /cidades/{nomeCidade}/pontos

http://localhost:2553/api/cidades/São Paulo/pontos

Body (JSON):
JSON

{
  "nomePonto": "Ponto de Coleta Shopping",
  "tiposColeta": [
    {
      "id": 1,
      "nome": "plastico"
    },
    {
      "id": 2,
      "nome": "metal"
    }
  ],
  "desc": "Ponto localizado dentro do shopping",
  "endereco": {
    "rua": "Avenida Paulista",
    "numero": "1000",
    "bairro": "Bela Vista",
    "cep": "01310-100",
    "complemento": "Piso 2"
  },
  "horariosFunc": [
    {
      "diaSemana": "SEGUNDA",
      "horaAbertura": "10:00",
      "horaFechamento": "22:00",
      "funcionaFeriado": true
    },
    {
      "diaSemana": "DOMINGO",
      "horaAbertura": "10:00",
      "horaFechamento": "22:00",
      "funcionaFeriado": true
    }
  ]
}

Resposta Esperada (201 Created):
JSON

{
  "id": 5,
  "nomePonto": "Ponto de Coleta Shopping",
  "tiposColeta": [
    {
      "id": 1,
      "nome": "plastico"
    },
    {
      "id": 2,
      "nome": "metal"
    }
  ],
  "desc": "Ponto localizado dentro do shopping",
  "endereco": {
    "rua": "Avenida Paulista",
    "numero": "1000",
    "bairro": "Bela Vista",
    "cep": "01310-100",
    "complemento": "Piso 2",
    "enderecoCompleto": "Avenida Paulista, 1000 - Bela Vista - 01310-100 (Piso 2)"
  },
  "horariosFunc": [...],
  "cidade": {
    "id": 1,
    "nome": "São Paulo",
    "uf": {...}
  }
}

3.12 PUT - Atualizar Endereço de um Ponto

Endpoint: PUT /pontos/{id}/endereco

http://localhost:2553/api/pontos/1/endereco

Body (JSON):
JSON

{
  "rua": "Rua Nova",
  "numero": "456",
  "bairro": "Novo Bairro",
  "cep": "99999-999",
  "complemento": "Apto 101"
}

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "nomePonto": "Ponto de Coleta Centro",
  "endereco": {
    "rua": "Rua Nova",
    "numero": "456",
    "bairro": "Novo Bairro",
    "cep": "99999-999",
    "complemento": "Apto 101",
    "enderecoCompleto": "Rua Nova, 456 - Novo Bairro - 99999-999 (Apto 101)"
  },
  ...
}

3.13 GET - Listar Horários de um Ponto

Endpoint: GET /pontos/{id}/horarios

http://localhost:2553/api/pontos/1/horarios

Resposta Esperada (200 OK):
JSON

[
  {
    "diaSemana": "SEGUNDA",
    "horaAbertura": "07:00",
    "horaFechamento": "18:00",
    "funcionaFeriado": false
  },
  {
    "diaSemana": "TERÇA",
    "horaAbertura": "07:00",
    "horaFechamento": "18:00",
    "funcionaFeriado": false
  }
]

3.14 POST - Adicionar Horário a um Ponto

Endpoint: POST /pontos/{id}/horarios

http://localhost:2553/api/pontos/1/horarios

Body (JSON):
JSON

{
  "diaSemana": "DOMINGO",
  "horaAbertura": "09:00",
  "horaFechamento": "17:00",
  "funcionaFeriado": false
}

Resposta Esperada (201 Created):
JSON

{
  "id": 1,
  "nomePonto": "Ponto de Coleta Centro",
  "horariosFunc": [
    ...,
    {
      "diaSemana": "DOMINGO",
      "horaAbertura": "09:00",
      "horaFechamento": "17:00",
      "funcionaFeriado": false
    }
  ],
  ...
}

3.15 PUT - Atualizar Todos os Horários de um Ponto

Endpoint: PUT /pontos/{id}/horarios

http://localhost:2553/api/pontos/1/horarios

Body (JSON):
JSON

[
  {
    "diaSemana": "SEGUNDA",
    "horaAbertura": "08:00",
    "horaFechamento": "20:00",
    "funcionaFeriado": false
  },
  {
    "diaSemana": "TERÇA",
    "horaAbertura": "08:00",
    "horaFechamento": "20:00",
    "funcionaFeriado": false
  }
]

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "nomePonto": "Ponto de Coleta Centro",
  "horariosFunc": [
    {
      "diaSemana": "SEGUNDA",
      "horaAbertura": "08:00",
      "horaFechamento": "20:00",
      "funcionaFeriado": false
    },
    {
      "diaSemana": "TERÇA",
      "horaAbertura": "08:00",
      "horaFechamento": "20:00",
      "funcionaFeriado": false
    }
  ],
  ...
}

3.16 DELETE - Remover um Horário de um Ponto

Endpoint: DELETE /pontos/{id}/horarios/{indice}

http://localhost:2553/api/pontos/1/horarios/0

Resposta Esperada (200 OK):
JSON

{
  "id": 1,
  "nomePonto": "Ponto de Coleta Centro",
  "horariosFunc": [
    // Lista sem o horário deletado
  ],
  ...
}

3.17 DELETE - Deletar um Ponto

Endpoint: DELETE /pontos/{id}

http://localhost:2553/api/pontos/1

Resposta Esperada (204 No Content):

(vazio)

🔗 4. ENDPOINTS UTILITÁRIOS
4.1 GET - Buscar Informações de CEP (ViaCEP)

Endpoint: GET /viacep/{cep}

http://localhost:2553/api/viacep/01310-100

Resposta Esperada (200 OK):
JSON

{
  "cep": "01310-100",
  "logradouro": "Avenida Paulista",
  "complemento": "",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP",
  "ibge": "3550308",
  "gia": "",
  "ddd": "11",
  "siafi": "7107"
}

4.2 POST - Criar Endereço a partir de CEP

Endpoint: POST /endereco-por-cep

http://localhost:2553/api/endereco-por-cep?cep=01310-100&numero=1000&complemento=Piso%202

Resposta Esperada (200 OK):
JSON

{
  "rua": "Avenida Paulista",
  "numero": "1000",
  "bairro": "Bela Vista",
  "cep": "01310-100",
  "complemento": "Piso 2",
  "enderecoCompleto": "Avenida Paulista, 1000 - Bela Vista - 01310-100 (Piso 2)"
}

4.3 POST - Validar/Criar UF e Cidade

Endpoint: POST /validar-uf-cidade

http://localhost:2553/api/validar-uf-cidade?cidade=São Paulo&uf=SP

Resposta Esperada (200 OK):
JSON

{
  "endereco": null,
  "cidade": {
    "id": 1,
    "nome": "São Paulo",
    "uf": {
      "id": 1,
      "sigla": "SP",
      "nome": "São Paulo"
    },
    "pontos": []
  },
  "uf": {
    "id": 1,
    "sigla": "SP",
    "nome": "São Paulo"
  }
}

📊 Códigos de Resposta Esperados
Código	Significado	Quando Ocorre
200	OK	Sucesso em GET, PUT
201	Created	Sucesso em POST
204	No Content	Sucesso em DELETE
400	Bad Request	Erro na requisição (dados inválidos)
404	Not Found	Recurso não encontrado
500	Server Error	Erro no servidor
💡 Dicas para Testar

    Sempre verifique o Content-Type: Deve ser application/json

    Para POST/PUT: Use a aba "Body" > "raw" > selecione "JSON"

    Para parametros de query: Adicione na URL após ? (ex: ?cep=01310-100&numero=1000)

    Horários: Use formato 24h (ex: 08:00, 18:00)

    CEP: Pode incluir ou não o hífen (01310-100 ou 01310100)

🚀 Fluxo de Teste Recomendado

    ✅ GET /ufs/todas - Verificar estados

    ✅ GET /cidades/todas - Verificar cidades (Formato simplificado)

    ✅ GET /pontos - Listar todos os pontos (Formato agrupado por cidade)

    ✅ GET /coletas/plastico - Buscar por tipo

    ✅ POST /cidades/São Paulo/pontos - Criar novo ponto

    ✅ GET /pontos/{id} - Verificar ponto criado

    ✅ PUT /pontos/{id}/endereco - Atualizar endereço

    ✅ POST /pontos/{id}/horarios - Adicionar horário

    ✅ DELETE /pontos/{id} - Deletar ponto