# Perfil Profissional - Weslley Addson Silva de Carvalho

## 📌 Informações Pessoais
**Nome:** Weslley Addson Silva de Carvalho  
**CPF:** 134.481.624-00  
**Data de Nascimento:** 28/05/2001

## 📧 Contato
- **E-mail:** weslleyaddson0@gmail.com
- **Telefone:** (83) 99413-7970
- **Endereço:** Rua Venâncio Nogueira Silva, nº 269, Bairro Três Irmãs, CEP: 58423-260

---

Caso precise de mais informações, estou disponível para contato.  

---
# **ProjetoPraticoImplementacaoBackend**

## Como Executar o Projeto

1.  **Navegue até o diretório do projeto** no seu terminal.
2.  **Execute o comando** abaixo para iniciar os containers em segundo plano:

    ```
    docker-compose up -d
    ```

3.  **Aguarde a inicialização dos containers.** Você pode verificar o status dos containers com o comando `docker ps` ou observando os logs com `docker-compose logs -f`. Certifique-se de que todos os serviços necessários estejam listados e com status "Up".

**Dica Importante:** Para explorar e testar os endpoints da API de forma rápida e eficiente, utilize a collection do Postman fornecida no arquivo `ProjetoPraticoImplementacaoBackend.postman_collection.json`. Este arquivo contém todas as rotas e exemplos de corpos de requisição prontos para uso.

## Stack utilizada

**Back-end:** Spring Framework 3.4.4, Postgres DB, Java 21

## Documentação da API

Documentação da API do ProjetoPraticoImplementacaoBackend. Esta API fornece endpoints para autenticação, gerenciamento de servidores efetivos e temporários, pessoas, endereços, cidades, unidades, lotações e fotos de pessoas. Abaixo estão os detalhes de cada endpoint, incluindo métodos HTTP, URLs, corpos de requisição (quando aplicável) e autenticação.

A API está hospedada localmente em http://localhost:8080. Para autenticação, utilize tokens JWT obtidos nos endpoints de login e refresh token.

# Explicação dos Endpoints da API

**Observações Gerais:**

* **Base URL:** A os endpoints utiliza a seguinte URL base: `http://localhost:8080`.
* **Autenticação:** A maioria dos endpoints, exceto os da seção "Auth", requer autenticação via token Bearer. O token é armazenado na variável de coleção `authToken` após um login bem-sucedido.
* **Formato de Dados:** As requisições e respostas utilizam o formato JSON.

## 1. Auth

Esta seção contém endpoints para autenticação e gerenciamento de tokens de acesso.

### 1.1. `register`
* **Método:** `POST`
* **URL:** `/auth/signup`
* **Descrição:** Endpoint para registrar um novo usuário.
* **Corpo da Requisição (JSON):**
    ```json
    {
        "username": "admin",
        "password": "123456"
    }
    ```

### 1.2. `login`
* **Método:** `POST`
* **URL:** `/auth/login`
* **Descrição:** Endpoint para realizar o login e obter um token de acesso (`authToken`) e um token de refresh (`refreshToken`). Estes tokens são armazenados como variáveis de coleção.
* **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "admin",
      "password": "123456"
    }
    ```

### 1.3. `refreshToken`
* **Método:** `POST`
* **URL:** `/auth/refresh`
* **Descrição:** Endpoint para renovar o token de acesso utilizando o `refreshToken`. O novo token de acesso e refresh são atualizados nas variáveis de coleção.
* **Corpo da Requisição (JSON):**
    ```json
    {
        "refreshToken": "{{refreshToken}}"
    }
    ```

## 2. ServidorEfetivo

Esta seção contém endpoints para gerenciar informações de servidores efetivos.

### 2.1. `Listar`
* **Método:** `GET`
* **URL:** `/servidor/efetivo/lista?page=0&size=10`
* **Descrição:** Endpoint para listar servidores efetivos com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 2.2. `GetByMatricula`
* **Método:** `GET`
* **URL:** `/servidor/efetivo/1`
* **Descrição:** Endpoint para obter um servidor efetivo específico pela matrícula (o `1` no URL parece ser um exemplo de matrícula).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 2.3. `GetByUnidadeId`
* **Método:** `GET`
* **URL:** `/servidor/efetivo/unidade/4`
* **Descrição:** Endpoint para obter servidores efetivos pertencentes a uma unidade específica (o `4` no URL parece ser um exemplo de ID da unidade).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 2.4. `GetByNameEndereco`
* **Método:** `GET`
* **URL:** `/servidor/efetivo/unidade/endereco?nome=teste`
* **Descrição:** Endpoint para buscar servidores efetivos por nome ou endereço (o parâmetro `nome` com valor `teste` é um exemplo).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 2.5. `Cadastro`
* **Método:** `POST`
* **URL:** `/servidor/efetivo/cadastro`
* **Descrição:** Endpoint para cadastrar um novo servidor efetivo.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "matricula": "223912233112",
        "pessoa" :
        {
            "nome": "Weslley Carvalho",
            "dataNascimento": "1990-01-01",
            "sexo": "masculino",
            "nomeMae": "Mae",
            "nomePai": 1,
            "enderecos": [
                {
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "nome": "Cidade",
                        "uf": "PB"
                    }
                },
                {
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "nome": "Cidade1",
                        "uf": "PB"
                    }
                }
            ]
        }
    }
    ```

### 2.6. `Update`
* **Método:** `PUT`
* **URL:** `/servidor/efetivo/atualizar`
* **Descrição:** Endpoint para atualizar as informações de um servidor efetivo existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1,
        "matricula": "2239122331",
        "pessoa": {
            "id": 1,
            "nome": "Weslley Carvalho",
            "dataNascimento": "1990-01-01",
            "sexo": "masculino",
            "nomeMae": "Mae",
            "nomePai": "1",
            "enderecos": [
                {
                    "id": 1,
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "id": 4,
                        "nome": "Cidade",
                        "uf": "PB"
                    }
                },
                {
                    "id": 2,
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "id": 3,
                        "nome": "Cidade1",
                        "uf": "PB"
                    }
                }
            ]
        }
    }
    ```

### 2.7. `Delete`
* **Método:** `DELETE`
* **URL:** `/servidor/efetivo/4`
* **Descrição:** Endpoint para excluir um servidor efetivo específico (o `4` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 3. Servidortemporario

Esta seção contém endpoints para gerenciar informações de servidores temporários.

### 3.1. `Listar`
* **Método:** `GET`
* **URL:** `/servidor/temporario/lista?page=0&size=10`
* **Descrição:** Endpoint para listar servidores temporários com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 3.2. `GetById`
* **Método:** `GET`
* **URL:** `/servidor/temporario/1`
* **Descrição:** Endpoint para obter um servidor temporário específico pelo ID (o `1` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 3.3. `Cadastro`
* **Método:** `POST`
* **URL:** `/servidor/temporario/cadastro`
* **Descrição:** Endpoint para cadastrar um novo servidor temporário.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "dataAdmissao": "1999-01-01",
        "pessoa" :
        {
            "nome": "Nome",
            "dataNascimento": "1990-01-01",
            "sexo": "masculino",
            "nomeMae": "Mae",
            "nomePai": 1,
            "enderecos": [
                {
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "nome": "Cidade",
                        "uf": "PB"
                    }
                },
                {
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Bairro",
                    "cidade": {
                        "nome": "Cidade1",
                        "uf": "PB"
                    }
                }
            ]
        }
    }
    ```

### 3.4. `Update`
* **Método:** `PUT`
* **URL:** `/servidor/temporario/atualizar`
* **Descrição:** Endpoint para atualizar as informações de um servidor temporário existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1, // ID SERVIDOR TEMPORARIO
        "dataAdmissao": "1998-01-01",
        "dataDemissao": null,
        "pessoa": {
            "id": 1, // ID PESSOA
            "nome": "Weslley Carvalho",
            "dataNascimento": "1990-01-01",
            "sexo": "masculino",
            "nomeMae": "Mae",
            "nomePai": "1",
            "enderecos": [
                {
                    "id": 1, // ID ENDEREÇO
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Trés irmãs",
                    "cidade": {
                        "id": 3,
                        "nome": "Cidade",
                        "uf": "PB"
                    }
                },
                {
                    "id": 2, // ID ENDEREÇO
                    "tipoLogradouro": "Rua",
                    "logradouro": "Logradouro",
                    "numero": 32,
                    "bairro": "Trés irmãs",
                    "cidade": {
                        "id": 4,
                        "nome": "Cidade1",
                        "uf": "PB"
                    }
                }
            ]
        }
    }
    ```

### 3.5. `Delete`
* **Método:** `DELETE`
* **URL:** `/servidor/temporario/8`
* **Descrição:** Endpoint para excluir um servidor temporário específico (o `8` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 4. Pessoa

Esta seção contém endpoints para gerenciar informações de pessoas.

### 4.1. `Listar`
* **Método:** `GET`
* **URL:** `/pessoa/lista?page=0&size=10`
* **Descrição:** Endpoint para listar pessoas com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 4.2. `GetById`
* **Método:** `GET`
* **URL:** `/pessoa/18`
* **Descrição:** Endpoint para obter uma pessoa específica pelo ID (o `18` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 4.3. `Update`
* **Método:** `PUT`
* **URL:** `/pessoa/atualizar`
* **Descrição:** Endpoint para atualizar as informações de uma pessoa existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1,
        "nome": "Weslley Carvalho",
        "dataNascimento": "1990-01-01",
        "sexo": "masculino",
        "nomeMae": "mae",
        "nomePai": "1",
        "enderecos": [
            {
                "id": 1,
                "tipoLogradouro": "Rua",
                "logradouro": "Logradouro",
                "numero": 32,
                "bairro": "Bairro",
                "cidade": {
                    "id": 1,
                    "nome": "Cidade",
                    "uf": "PB"
                }
            },
            {
                "id": 2,
                "tipoLogradouro": "Rua",
                "logradouro": "Logradouro",
                "numero": 32,
                "bairro": "Bairro",
                "cidade": {
                    "id": 2,
                    "nome": "Cidade1",
                    "uf": "PB"
                }
            }
        ]
    }
    ```

### 4.4. `Delete`
* **Método:** `DELETE`
* **URL:** `/pessoa/23`
* **Descrição:** Endpoint para excluir uma pessoa específica pelo ID (o `23` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 5. Endereco

Esta seção contém endpoints para gerenciar informações de endereços.

### 5.1. `Listar`
* **Método:** `GET`
* **URL:** `/endereco/lista?page=0&size=10`
* **Descrição:** Endpoint para listar endereços com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 5.2. `GetById`
* **Método:** `GET`
* **URL:** `/endereco/98`
* **Descrição:** Endpoint para obter um endereço específico pelo ID (o `98` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 6. Cidade

Esta seção contém endpoints para gerenciar informações de cidades.

### 6.1. `Listar`
* **Método:** `GET`
* **URL:** `/cidade/lista?page=0&size=10`
* **Descrição:** Endpoint para listar cidades com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 6.2. `GetById`
* **Método:** `GET`
* **URL:** `/cidade/110`
* **Descrição:** Endpoint para obter uma cidade específica pelo ID (o `110` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 7. Unidade

Esta seção contém endpoints para gerenciar informações de unidades organizacionais.

### 7.1. `Listar`
* **Método:** `GET`
* **URL:** `/unidade/lista?page=0&size=10`
* **Descrição:** Endpoint para listar unidades com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 7.2. `GetById`
* **Método:** `GET`
* **URL:** `/unidade/18`
* **Descrição:** Endpoint para obter uma unidade específica pelo ID (o `18` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 7.3. `Cadastro`
* **Método:** `POST`
* **URL:** `/unidade/cadastro`
* **Descrição:** Endpoint para cadastrar uma nova unidade.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "nome": "Unidade 1",
        "sigla": "FP",
        "enderecos": [
            {
                "tipoLogradouro": "Rua",
                "logradouro": "Rua Ferandp",
                "numero": 32,
                "bairro": "Trés irmãs",
                "cidade": {
                    "nome": "Campina Grande2",
                    "uf": "PB"
                }
            }
        ]
    }
    ```

### 7.4. `Update`
* **Método:** `PUT`
* **URL:** `/unidade/atualizar`
* **Descrição:** Endpoint para atualizar as informações de uma unidade existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1,
        "nome": "Weslley Carvalhooo",
        "sigla": "FP",
        "enderecos": [
            {
                "id": 1,
                "tipoLogradouro": "Rua",
                "logradouro": "Logradouro",
                "numero": 32,
                "bairro": "Bairro",
                "cidade": {
                    "id": 1,
                    "nome": "Cidade",
                    "uf": "PB"
                }
            }
        ]
    }
    ```

### 7.5. `Delete`
* **Método:** `DELETE`
* **URL:** `/unidade/17`
* **Descrição:** Endpoint para excluir uma unidade específica pelo ID (o `17` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 8. Lotacao

Esta seção contém endpoints para gerenciar informações de lotações de servidores em unidades.

### 8.1. `Listar`
* **Método:** `GET`
* **URL:** `/lotacao/lista?page=0&size=10`
* **Descrição:** Endpoint para listar lotações com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 8.2. `GetById`
* **Método:** `GET`
* **URL:** `/lotacao/1`
* **Descrição:** Endpoint para obter uma lotação específica pelo ID (o `1` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 8.3. `Cadastro`
* **Método:** `POST`
* **URL:** `/lotacao/cadastro`
* **Descrição:** Endpoint para cadastrar uma nova lotação.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "dataAdmissao": "1999-01-01",
        "unidId": 1,
        "pessoaId" : 1,
        "dataLotacao": "1999-01-01",
        "portaria": "123"
    }
    ```

### 8.4. `Update`
* **Método:** `PUT`
* **URL:** `/lotacao/atualizar`
* **Descrição:** Endpoint para atualizar as informações de uma lotação existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1,
        "dataRemocao": "1998-01-01",
        "pessoaId" : 1,
        "unidId": 1,
        "dataLotacao": "1998-01-01",
        "portaria": "12312321"
    }
    ```

### 8.5. `Delete`
* **Método:** `DELETE`
* **URL:** `/lotacao/1`
* **Descrição:** Endpoint para excluir uma lotação específica pelo ID (o `1` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

## 9. FotoPessoa

Esta seção contém endpoints para gerenciar fotos de pessoas.

### 9.1. `Listar`
* **Método:** `GET`
* **URL:** `/pessoa/foto/lista?page=0&size=10`
* **Descrição:** Endpoint para listar fotos de pessoas com suporte para paginação (`page` e `size`).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 9.2. `GetById`
* **Método:** `GET`
* **URL:** `/pessoa/foto/1`
* **Descrição:** Endpoint para obter uma foto de pessoa específica pelo ID (o `1` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).

### 9.3. `UploadFiles`
* **Método:** `POST`
* **URL:** `/pessoa/foto/upload`
* **Descrição:** Endpoint para fazer upload de arquivos de fotos para uma pessoa específica.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (Form Data):**
    * `files`: Arquivos de imagem a serem enviados.
    * `pessoaId`: ID da pessoa à qual as fotos pertencem (exemplo: `1`).

### 9.4. `Update`
* **Método:** `PUT`
* **URL:** `/pessoa/foto/atualizar`
* **Descrição:** Endpoint para atualizar as informações de uma foto de pessoa existente.
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
* **Corpo da Requisição (JSON):**
    ```json
    {
        "id": 1,
        "data": "1999-01-01",
        "bucket" : "123123123",
        "hash": "123123123",
        "pessoaId": 2
    }
    ```

### 9.5. `Delete`
* **Método:** `DELETE`
* **URL:** `/pessoa/foto/1`
* **Descrição:** Endpoint para excluir uma foto de pessoa específica pelo ID (o `1` no URL parece ser um exemplo de ID).
* **Autenticação:** Requer token Bearer (`{{authToken}}`).
