# Sistema de Pedidos — Avaliação N2 (Parte 1: checkpoint)

## Integrantes

| Nome | Matrícula |
|------|-----------|
| Maria Fernanda Peres | 20252012000668
| Arthur Ribeiro | 20261012001353 |
| Gustavo Silveira | 20261012000012 |

## Compilação e execução

Requer JDK 17 ou superior. Na raiz do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out aplicacao.Programa
```

No Windows (PowerShell):

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out aplicacao.Programa
```

## Estrutura

- `entidades`: as 12 classes do diagrama e a interface `Imprimivel`
- `entidades.enums`: `TipoCliente` e `EstadoPagamento`
- `exception`: `RegraDeNegocioException`
- `aplicacao`: `Programa`

## Decisões da Parte 1

- **Datas:** `instante`, `dataVencimento` e `dataPagamento` usam `LocalDateTime` em vez de `Date`, por ser a API de datas atual do Java e já incluir hora.
- **Composições e entidades fracas:** `Cidade`, `Endereco`, `Telefone` e `ItemPedido` têm construtor sem modificador de acesso. Só são criados por quem os contém: `estado.adicionarCidade(...)`, `cliente.adicionarEndereco(...)`, `cliente.adicionarTelefone(...)` e `pedido.adicionarItem(...)`.
- **Preço praticado:** `ItemPedido` copia o preço do produto no construtor e não tem setter de `preco`.
- **Pagamento:** é criado já apontando para o pedido (`new PagamentoComCartao(pedido, 6)`) e associado com `pedido.setPagamento(...)`, que confere se os dois lados batem.
- **Listas:** todas são criadas dentro da classe e expostas apenas como listas não modificáveis.
