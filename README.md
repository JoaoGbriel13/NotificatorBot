# CRUD Discord Bot Application

## Descrição

Este projeto é um bot Discord para gerenciar o registro de pilotos, integrando com o Google Sheets e enviando notificações via WhatsApp usando a API do Twilio. O bot coleta informações dos pilotos e registra seus dados em uma planilha do Google. Além disso, o sistema verifica horários em outra planilha e envia notificações no WhatsApp 30 minutos antes do horário especificado.

## Funcionalidades

- **Registro de Pilotos**: Os usuários podem registrar seus dados no Discord através de comandos interativos, incluindo nome, data de nascimento, e-mail, endereço, ID do iRacing, e tamanho da camisa.
- **Integração com Google Sheets**: As informações registradas pelos pilotos são armazenadas em uma planilha do Google.
- **Envio de Notificações via WhatsApp**: Notificações são enviadas automaticamente usando Twilio para avisar os pilotos de seus horários de corrida.

## Tecnologias Utilizadas

- **Java Spring Boot**: Para criar e gerenciar a aplicação.
- **Google Sheets API**: Para interagir com planilhas do Google.
- **Twilio API**: Para envio de mensagens via WhatsApp.
- **JDA (Java Discord API)**: Para a integração com o Discord e gerenciamento de mensagens.
- **Lombok**: Para reduzir o boilerplate nas classes model.
- **Dotenv**: Para gerenciar tokens de API e outros segredos.

## Como Executar

### Pré-requisitos

- **Java 11+**
- **Conta no Google Cloud** com as credenciais para acessar o Google Sheets API.
- **Conta no Twilio** com o serviço de WhatsApp ativado.
- **Token de bot no Discord**.

### Passos para rodar a aplicação

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```

2. Crie um arquivo `.env` com as seguintes variáveis:
   ```bash
   discord.token=SEU_TOKEN_DISCORD
   google.api=SEU_CAMINHO_GOOGLE_API
   twilio.api=SEU_TWILIO_API_KEY
   twilio.sid=SEU_TWILIO_SID
   ```

3. Execute o comando para rodar o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. No Discord, digite `!register` para iniciar o processo de registro de um piloto.

## Estrutura do Projeto

- **Model**: Contém as classes `Driver` que representa o piloto.
- **Service**:
    - `Bot`: Gerencia a interação com o Discord.
    - `GoogleService`: Responsável pela conexão com o Google Sheets e Google Drive.
    - `SheetService`: Manipula a lógica de leitura e escrita na planilha.
    - `TwilioService`: Lida com o envio de mensagens via Twilio.
- **Utils**: Contém classes auxiliares como `ConvertTime` e `TokenManager`.

## Funcionalidades Pendentes

As seguintes melhorias ainda precisam ser implementadas:

1. **Integração Completa com Google Sheets**: Atualizar a aplicação para buscar o número de telefone do piloto que vai entrar na corrida, baseado nas informações da planilha.

2. **Finalização dos Testes**: Implementar e completar os testes automatizados para garantir a funcionalidade correta da aplicação.

3. **Suporte para Números não Verificados no Twilio**: Após adquirir uma licença paga, o sistema deve permitir o envio de mensagens para números não verificados.

---
