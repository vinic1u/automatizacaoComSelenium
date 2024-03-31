# Projeto de Automatização Utilizando Selenium

O projeto é uma Rotina de Execução Diária:
Ele consiste em acessar órgão fiscal eletrônico de uma cidade, realizar o download dos PDFs enviados no dia da execução do script e analisar se algum deles contém o valor requisitado pelo usuário. Se o valor estiver contido em algum PDF, ele irá enviar um email para o destinatário definido, contendo o próprio PDF como anexo.


## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar adicionar as seguintes variáveis de ambiente no seu .env

`emailRemetente`

`senha`

`porta`

`emailDestinatario`




## Referência

 - [Selenium](https://www.selenium.dev/documentation/)
 - [JavaX.Mail](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/mail/package-summary.html)
 - [PDFBox](https://javadoc.io/doc/org.apache.pdfbox/pdfbox/2.0.29/index.html)


## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/vinic1u/automatizacaoComSelenium.git
```
