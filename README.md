# 📱 BeyCell - Sistema de Gestão de Loja de Celulares

> Projeto prático de Programação Orientada a Objetos (POO) desenvolvido em Java.

## 📄 Sobre o Projeto
O **BeyCell** é uma aplicação desktop para gerenciamento de uma loja especializada em celulares. O sistema permite o cadastro de produtos e clientes, realização de vendas com baixa automática de estoque, geração de faturas e relatórios gerenciais.

O projeto foi desenvolvido seguindo a arquitetura em camadas (**MVC**) e utiliza persistência de dados via arquivos binários (Serialização), sem necessidade de banco de dados externo.

---

## 🚀 Funcionalidades

### 📦 Gestão de Produtos (Celulares)
* Cadastro completo (Marca, Modelo, Preço, Specs, Estoque Mínimo).
* Visualização de todos os produtos em tabela.
* **Adição de Estoque:** Botão rápido para repor unidades.
* **Remoção:** Possibilidade de excluir produtos do sistema.
* **Alerta de Estoque:** Aviso automático quando o estoque fica abaixo do mínimo definido.

### 👥 Gestão de Clientes
* Cadastro de clientes (Nome, CPF, Telefone, Endereço).
* Listagem e exclusão de clientes.

### 🛒 Vendas
* Seleção de cliente e produtos.
* Carrinho de compras (Adicionar/Remover itens antes de fechar).
* Cálculo automático do total.
* **Baixa de Estoque:** Atualiza a quantidade disponível automaticamente após a venda.
* **Fatura:** Gera um arquivo `.txt` com os detalhes da venda (data formatada, itens e total).

### 📊 Relatórios e Estatísticas
* Faturamento Total (R$).
* Ticket Médio das vendas.
* Produto Mais Vendido.
* Melhor Cliente (Quem gastou mais).
* Lista de Produtos com **Estoque Crítico**.

---

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java (JDK 8+).
* **Interface Gráfica:** Java Swing (Biblioteca nativa).
* **Navegação:** `CardLayout` (Sistema de Janela Única).
* **Persistência:** `java.io.Serializable` (Arquivos `.dat`).
* **Arquitetura:** MVC (Model - View - Controller/Service) + DAO.

---

## 📂 Estrutura do Projeto
O código está organizado em pacotes para facilitar a manutenção:

```text
src/br/com/loja/
├── model/       # Classes (Celular, Cliente, Venda)
├── view/        # Telas (Menu, Venda, Relatórios...)
├── service/     # Regras de Negócio (Cálculos, Validações)
└── dao/         # Persistência (Salvar/Carregar arquivos)

```

## ▶️ Como Rodar o Projeto
Para executar o sistema, certifique-se de ter o Java instalado. Abra o seu terminal na pasta raiz do projeto (onde está a pasta src) e execute os comandos abaixo em sequência:

### 1. Entrar na pasta do código fonte
```text
cd src
```

### 2. Compilar todos os arquivos do projeto
```text
javac br/com/loja/model/*.java br/com/loja/dao/*.java br/com/loja/service/*.java br/com/loja/view/*.java
```

### 3. Executar o sistema
```text
java br.com.loja.view.MenuPrincipal
```

## 📐 Diagrama de Classes
O projeto inclui um diagrama UML detalhado no arquivo diagrama_classes.plantuml.

### 🔎 Como visualizar o diagrama
O arquivo está escrito em linguagem PlantUML. Para visualizá-lo graficamente dentro do VS Code, siga estes passos:

#### 1.**Instalar a Extensão:**

* No VS Code, vá na aba de Extensões (Ctrl + Shift + X).

* Pesquise por "PlantUML" (do autor jebbs).

* Clique em Install.

#### **Abrir o Diagrama:**

* Abra o arquivo diagrama_classes.plantuml que está na pasta do projeto.

#### **Visualizar:**

* Clique em qualquer lugar do código do arquivo.

* Pressione Alt + D.

O diagrama aparecerá numa janela ao lado, mostrando todas as classes, atributos, métodos e relacionamentos do sistema BeyCell.

<img width="639" height="696" alt="image" src="https://github.com/user-attachments/assets/0bb60b7f-bfeb-47ba-a865-4f43c6e1edde" />

