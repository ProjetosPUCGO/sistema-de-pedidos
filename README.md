# Sistema de Pedidos — Avaliação N2

Trabalho da disciplina de Programação Orientada a Objetos: implementação em Java puro de um sistema de pedidos com produtos, categorias, clientes, endereços, pagamentos por boleto ou cartão e relatórios.

## Integrantes

| Nome | Matrícula |
|------|-----------|
| Maria Fernanda Peres Gonçalves | _preencher_ |
| Arthur Ribeiro | _preencher_ |
| Gustavo Silveira | _preencher_ |

## Requisitos

- JDK 17 ou superior
- Nenhuma biblioteca externa

## Estrutura do projeto

```
src/
├── aplicacao/
│   └── Programa.java                 # cenário de teste obrigatório (seção 5)
└── entidades/
    ├── Categoria.java
    ├── Cidade.java
    ├── Cliente.java
    ├── Endereco.java
    ├── Estado.java
    ├── Imprimivel.java               # interface com String imprimir()
    ├── ItemPedido.java
    ├── Pagamento.java                # classe abstrata
    ├── PagamentoComBoleto.java
    ├── PagamentoComCartao.java
    ├── Pedido.java
    ├── Produto.java
    ├── RegraDeNegocioException.java  # estende RuntimeException
    ├── Telefone.java
    └── enums/
        ├── EstadoPagamento.java      # PENDENTE, QUITADO, CANCELADO
        └── TipoCliente.java          # PESSOA_FISICA, PESSOA_JURIDICA
```

## Como compilar e executar

Na raiz do projeto (pasta que contém `src`):

**Linux / macOS**

```bash
javac -d bin $(find src -name "*.java")
java -cp bin aplicacao.Programa
```

**Windows (PowerShell)**

```powershell
javac -d bin (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp bin aplicacao.Programa
```

O programa não lê nada do teclado: ele monta o cenário da seção 5 do enunciado e imprime o resultado.

## Requisitos funcionais implementados

| RF | Descrição | Onde está |
|----|-----------|-----------|
| RF01 | Cadastro de estados, cidades, categorias, produtos e clientes; rejeita CPF/CNPJ repetido | `Estado.adicionarCidade`, `Cliente`, `Programa` |
| RF02 | Criação de pedido com endereço de entrega do próprio cliente | Construtor de `Pedido` |
| RF03 | Adição de itens, somando a quantidade se o produto já estiver no pedido | `Pedido.adicionarItem` |
| RF04 | Subtotal do item e total do pedido | `ItemPedido.getSubtotal`, `Pedido.getTotal` |
| RF05 | Pagamento por boleto (5% de desconto) ou cartão (juros simples de 2% a.m. acima de 3 parcelas) | `calcularValorFinal` nas subclasses de `Pagamento` |
| RF06 | Quitação do pagamento; boleto registra `dataPagamento`; pagamento cancelado não pode ser quitado | `Pagamento.quitar` / `PagamentoComBoleto.quitar` |
| RF07 | Resumo completo do pedido montado com `imprimir()` | `Pedido.imprimir`, `ItemPedido.imprimir`, `Pagamento.imprimir` |
| RF08 | Pedidos de um cliente e total faturado (soma dos valores finais quitados) | `Cliente` / `Programa` |

## Saída do cenário de teste

> ⚠️ Substituir o bloco abaixo pela saída real gerada por `java -cp bin aplicacao.Programa`.

```
(colar aqui a saída do programa)
```

Valores de conferência do enunciado, que a saída acima deve confirmar:

| Item | Esperado |
|------|----------|
| Pedido 1 (Maria, cartão em 6x) — total | R$ 3.710,00 |
| Pedido 1 — valor final | R$ 4.155,20 |
| Pedido 2 (Loja XYZ, boleto) — total | R$ 1.000,00 |
| Pedido 2 — valor final | R$ 950,00 |
| Pedido 1 após o Mouse ir para R$ 90,00 — total | R$ 3.710,00 (inalterado) |
| Total faturado | R$ 950,00 (só o boleto foi quitado) |

Operações que devem falhar com `RegraDeNegocioException` e ter a mensagem impressa: cliente com o CPF de Maria, pedido de Maria com endereço da Loja XYZ, item com quantidade 0 e cartão em 15 parcelas.

## Decisões de projeto

**(a) Em qual classe ficou o cálculo do total e por quê?**
O subtotal fica em `ItemPedido.getSubtotal()`, porque é o item que guarda preço praticado, desconto e quantidade. O total fica em `Pedido.getTotal()`, que apenas soma os subtotais dos seus itens. Cada classe calcula com os dados que ela mesma possui, e o item usa o preço copiado no momento da compra, não o preço atual do `Produto`, então mudar o preço do produto não altera pedidos já feitos.

**(b) Por que `Pagamento` é classe abstrata e `Imprimivel` é interface, e não o contrário?**
`Pagamento` tem estado e comportamento comuns às formas de pagamento (`id`, `estado`, referência ao pedido, a lógica de quitação) e representa uma relação "é um": boleto e cartão são pagamentos. Por isso é uma classe abstrata, que não pode ser instanciada diretamente. `Imprimivel` só define uma capacidade, a de gerar um trecho de texto, compartilhada por classes sem relação de herança entre si (`Pedido`, `ItemPedido`, `Pagamento`). Como Java não tem herança múltipla de classes e a capacidade não precisa de atributos, uma interface é a escolha adequada.

**(c) Onde o polimorfismo evitou um `if` ou `switch`?**
Em `Pedido`, o valor final é obtido com `pagamento.calcularValorFinal(getTotal())` por uma referência do tipo `Pagamento`, sem testar se é boleto ou cartão: cada subclasse aplica sua própria regra (desconto de 5% ou juros). O mesmo acontece na impressão: a descrição da forma de pagamento vem da sobrescrita de `toString()`/`imprimir()` em cada subclasse, e o relatório do pedido chama `imprimir()` em cada parte sem conhecer a formatação delas.

**(d) O que cada integrante fez?**

- **Maria Fernanda Peres Gonçalves:** _preencher_
- **Arthur Ribeiro:** _preencher_
- **Gustavo Silveira:** _preencher_

## Entregas

- Checkpoint (Parte 1 — estrutura): 20/11/2026
- Entrega final (Parte 2 — comportamento): 30/11/2026