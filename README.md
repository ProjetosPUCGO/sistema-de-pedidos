# Sistema de Pedidos — Avaliação N2

Trabalho da disciplina de Programação Orientada a Objetos: implementação em Java puro de um sistema de pedidos com produtos, categorias, clientes, endereços, pagamentos por boleto ou cartão e relatórios.

## Integrantes

| Nome | Matrícula |
|------|-----------|
| Maria Fernanda Peres Gonçalves | 20252012000668 |
| Arthur Ribeiro | 20261012001353 |
| Gustavo Silveira | 20261012000012 |

## Requisitos para execução

- JDK 17 ou superior.
- Nenhuma biblioteca externa.

## Compilação e execução

Execute os comandos na raiz do projeto, onde está a pasta `src`.

### Linux / macOS

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out aplicacao.Programa
```

### Windows — PowerShell

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out aplicacao.Programa
```

O cenário obrigatório deve ser executado sem entrada pelo teclado, criando os objetos e apresentando os resultados na classe `Programa`.

## Estrutura do projeto

```text
src/
├── aplicacao/
│   └── Programa.java
├── entidades/
│   ├── Categoria.java
│   ├── Cidade.java
│   ├── Cliente.java
│   ├── Endereco.java
│   ├── Estado.java
│   ├── Imprimivel.java
│   ├── ItemPedido.java
│   ├── Pagamento.java
│   ├── PagamentoComBoleto.java
│   ├── PagamentoComCartao.java
│   ├── Pedido.java
│   ├── Produto.java
│   ├── Telefone.java
│   └── enums/
│       ├── EstadoPagamento.java
│       └── TipoCliente.java
└── exception/
    └── RegraDeNegocioException.java
```

- `entidades`: classes que representam os dados, as associações e as regras de negócio.
- `entidades.enums`: enumerações dos tipos de cliente e dos estados do pagamento.
- `exception`: exceção utilizada para sinalizar violações das regras de negócio.
- `aplicacao`: execução do cenário de teste.

## Requisitos funcionais do projeto

A tabela apresenta os requisitos que devem ser conferidos na implementação.

| RF | Descrição | Classes envolvidas |
|----|-----------|--------------------|
| RF01 | Cadastrar estados, cidades, categorias, produtos e clientes, rejeitando CPF/CNPJ repetido | `Estado`, `Cidade`, `Categoria`, `Produto`, `Cliente` |
| RF02 | Criar um pedido com endereço de entrega pertencente ao próprio cliente | `Pedido`, `Cliente`, `Endereco` |
| RF03 | Adicionar itens ao pedido, somando a quantidade quando o produto já estiver presente | `Pedido`, `ItemPedido` |
| RF04 | Calcular o subtotal de cada item e o total do pedido | `ItemPedido`, `Pedido` |
| RF05 | Calcular o valor final conforme a forma de pagamento | `Pagamento`, `PagamentoComBoleto`, `PagamentoComCartao` |
| RF06 | Quitar pagamentos, registrar a data de pagamento do boleto e impedir a quitação de pagamentos cancelados | `Pagamento`, `PagamentoComBoleto` |
| RF07 | Gerar um resumo completo do pedido por meio do contrato `Imprimivel` | `Pedido`, `ItemPedido`, `Pagamento` |
| RF08 | Consultar os pedidos de um cliente e calcular o total faturado considerando apenas pagamentos quitados | `Cliente`, `Pedido`, `Pagamento`, `Programa` |

## Regras de negócio

### Produtos e categorias

- Um produto deve pertencer a pelo menos uma categoria.
- Uma categoria pode existir sem produtos associados.
- A associação entre produto e categoria deve ser mantida nos dois lados.
- A inclusão repetida da mesma associação não deve gerar duplicidade.

### Clientes e endereços

- Cada cliente deve possuir pelo menos um telefone e um endereço.
- Não pode haver dois clientes cadastrados com o mesmo CPF/CNPJ.
- O endereço de entrega de um pedido deve pertencer ao cliente desse pedido.

### Pedidos e itens

- Um pedido completo deve possuir exatamente um cliente, um endereço de entrega, um pagamento e pelo menos um item.
- O instante do pedido representa a data e a hora de sua criação.
- A quantidade de um item deve ser maior ou igual a 1.
- O preço praticado deve ser copiado do produto no momento da criação do item.
- Alterar o preço atual do produto não deve modificar o preço dos itens já criados.
- O desconto do item representa um valor em reais por unidade e deve estar entre zero e o preço unitário.
- O subtotal do item deve ser calculado por:

```text
subtotal = (preco - desconto) * quantidade
```

- O total do pedido deve ser a soma dos subtotais.
- Um produto deve aparecer apenas uma vez em cada pedido.
- Adicionar novamente um produto já presente deve somar sua quantidade à do item existente.

### Pagamentos

- Todo pagamento deve iniciar com o estado `PENDENTE`.
- Um pagamento cancelado não pode ser quitado.
- O pagamento por boleto concede 5% de desconto sobre o total do pedido.
- A data de pagamento do boleto deve permanecer nula até sua quitação.
- O pagamento por cartão deve aceitar de 1 a 12 parcelas.
- Até 3 parcelas, o cartão não deve aplicar juros.
- Acima de 3 parcelas, deve aplicar juros simples de 2% ao mês sobre o total do pedido, considerando a quantidade de parcelas.

### Relatórios e faturamento

- O relatório deve apresentar número e instante do pedido, cliente, itens, totais, pagamento e endereço de entrega, incluindo cidade e estado.
- Cada item deve apresentar produto, quantidade, preço unitário, desconto e subtotal.
- O pagamento deve apresentar sua modalidade, estado e valor final.
- O faturamento deve considerar somente os valores finais dos pedidos com pagamento `QUITADO`.

## Decisões de projeto

### Identificadores

Os identificadores das entidades são gerados por contadores estáticos nas próprias classes. O valor é atribuído no construtor e não possui setter público.

### Datas

Os atributos `instante`, `dataVencimento` e `dataPagamento` utilizam `LocalDateTime` em vez de `Date`, permitindo representar data e hora com a API `java.time`.

### Encapsulamento das listas

As listas são inicializadas dentro das entidades. Suas alterações devem ocorrer pelos métodos responsáveis por manter as regras de negócio e a consistência das associações.

Os getters retornam listas não modificáveis, utilizando `Collections.unmodifiableList(...)`. Essa proteção impede alterações na estrutura da lista pelo getter, mas não torna imutáveis os objetos contidos nela.

### Composições e entidades dependentes

A criação de cidades, endereços, telefones e itens deve respeitar suas associações obrigatórias.

Quando a criação fica concentrada na entidade responsável, construtores e métodos auxiliares com acesso de pacote ajudam a controlar o uso dessas operações.

### Preço praticado

O preço do produto é copiado para o item no momento de sua criação. O atributo `preco` de `ItemPedido` não deve possuir setter, preservando o valor praticado naquela compra.

### Associação entre pedido e pagamento

O pedido e seu pagamento devem manter referências consistentes entre si. Um pagamento associado a determinado pedido não deve ser vinculado simultaneamente a outro.

### Responsabilidade pelos cálculos

O subtotal fica em `ItemPedido.getSubtotal()`, pois o item possui os dados necessários: preço praticado, desconto e quantidade.

O total fica em `Pedido.getTotal()`, que soma os subtotais dos itens.

As regras específicas de desconto do boleto e juros do cartão ficam nas subclasses de `Pagamento`.

### Classe abstrata e interface

`Pagamento` é uma classe abstrata porque reúne estado e comportamento comuns às formas de pagamento. Boleto e cartão são especializações de pagamento.

`Imprimivel` é uma interface que define o contrato:

```java
String imprimir();
```

Ela é implementada por `Pedido`, `ItemPedido` e `Pagamento`, permitindo que cada classe produza sua parte do relatório.

### Polimorfismo

O cálculo do valor final utiliza uma referência do tipo `Pagamento`:

```java
pagamento.calcularValorFinal(getTotal());
```

Cada subclasse executa sua regra específica. O pedido não precisa identificar a modalidade por meio de `if`, `switch` ou `instanceof`.

A impressão também utiliza polimorfismo: o relatório do pedido chama `imprimir()` nos itens e no pagamento.

## Cenário de teste obrigatório

O programa deve demonstrar:

- Cadastro do estado de Goiás e das cidades Goiânia e Anápolis.
- Cadastro das categorias Informática e Livros.
- Cadastro de notebook por R$ 3.500,00, mouse por R$ 80,00 e livro de Java por R$ 120,00.
- Criação do pedido de Maria, com pagamento por cartão em 6 parcelas.
- Adição repetida do mouse, somando sua quantidade no mesmo item.
- Criação do pedido da Loja XYZ, com pagamento por boleto.
- Quitação do boleto e registro de sua data de pagamento.
- Alteração do preço atual do mouse sem alterar o valor do pedido já criado.
- Consulta dos pedidos de um cliente.
- Cálculo do total faturado considerando somente pagamentos quitados.

### Valores esperados

| Verificação | Resultado esperado |
|-------------|--------------------|
| Pedido 1 — total | R$ 3.710,00 |
| Pedido 1 — valor final no cartão em 6 parcelas | R$ 4.155,20 |
| Pedido 2 — total | R$ 1.000,00 |
| Pedido 2 — valor final no boleto | R$ 950,00 |
| Pedido 1 após alterar o preço do mouse para R$ 90,00 | R$ 3.710,00 |
| Total faturado, com apenas o boleto quitado | R$ 950,00 |

Esses valores correspondem aos descontos em reais por unidade definidos no cenário do enunciado.

### Operações inválidas

As seguintes operações devem lançar `RegraDeNegocioException`, com suas mensagens apresentadas pelo programa:

- Cadastrar outro cliente com o CPF de Maria.
- Criar um pedido de Maria utilizando um endereço da Loja XYZ.
- Adicionar um item com quantidade zero.
- Criar um pagamento por cartão em 15 parcelas.

## Saída real do programa

Após executar o cenário, substituir o conteúdo abaixo pela saída efetivamente produzida:

```text
PENDENTE: inserir a saída real da execução de aplicacao.Programa.
```

## Contribuições dos integrantes

Preencher com as atividades efetivamente realizadas por cada integrante:

- **Maria Fernanda Peres Gonçalves:** a preencher.
- **Arthur Ribeiro:** a preencher.
- **Gustavo Silveira:** a preencher.

## Entregas

- **Checkpoint — Parte 1: estrutura:** 20/11/2026.
- **Entrega final — Parte 2: comportamento:** 30/11/2026.