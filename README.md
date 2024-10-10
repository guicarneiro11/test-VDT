# Way Airlines Flight History

## Descrição do Projeto

Este aplicativo foi desenvolvido como parte de um teste técnico para a Vá de Táxi. O objetivo é fornecer uma interface simples e eficiente para visualizar o histórico de voos da companhia aérea Way Airlines.

## Funcionalidades

- Tela inicial com boas-vindas e botão para acessar o histórico de voos
- Visualização de todos os voos
- Filtragem de voos por status: concluídos, cancelados e futuros
- Detalhes de cada voo incluindo ID, status, aeroportos de partida e chegada, datas e horários de partida e chegada.

## Interface

### Home

- Na tela inicial, procurei construir uma interface limpa, porém que mostrasse o objetivo do aplicativo logo de início, inclusive já dando um nome à ele, Way Airlines Flight History.

![Home](https://imgur.com/fA8bs7b.png)

### Histórico de voos

- Enquanto na tela de voos, também procurei seguir a mesma linha da tela inicial, mantendo as cores e simplicidade exibidas anteriormente, mas agora focado no que realmente importa, que é a exibição dos dados aéreos. Para essa tela, foquei em trazer cada voo organizado em um card diferente, mantendo todas suas informações dentro desse card, porém com uma dinâmica que não deixasse o card muito poluído, assim sendo possível expandir/esconder os nomes dos aeroportos conforme a necessidade do usuário e alternar o status das tags de concluído com apenas um toque, trazendo uma melhor organização e clareza na amostragem dos dados.

![Flights](https://imgur.com/PmL30Od.png)

## Decisões Técnicas

### Arquitetura

O projeto foi desenvolvido seguindo a arquitetura MVVM (Model-View-ViewModel), garantindo uma separação clara de responsabilidades e facilitando a manutenção e testabilidade do código.

### Tecnologias e Bibliotecas Utilizadas

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose
- **Navegação**: Navigation Compose
- **Injeção de Dependência**: Koin
- **Assincronicidade**: Coroutines e Flow
- **Parsing de JSON**: Gson
- **Testes**:
  - JUnit
  - Mockk
  - Robolectric
  - Espresso
  - Turbine
- **Outras**: AndroidX Core KTX, Lifecycle ViewModel

### Simulação de API

- Para simular uma API RESTful, implementei um `FlightRepository` que lê dados do arquivo JSON local. Isso permite um fácil desenvolvimento e validação, preparando o terreno para uma futura integração com uma API real.

## Como Executar o Projeto

1. Clone o repositório: https://github.com/guicarneiro11/test-VDT.git
2. Abra o projeto no Android Studio.
3. Sincronize o projeto com os arquivos Gradle.
4. Execute o aplicativo em um emulador ou dispositivo físico.

## Executando os Testes

### Testes Unitários

Para executar os testes unitários:

1. Navegue até a pasta `app/src/test/java/com/guicarneirodev/wayairlines/`
2. Clique com o botão direito na pasta
3. Selecione "Run Tests in 'com.guicar...'" 

Alternativamente, você pode executar os testes via linha de comando:
./gradlew test

### Testes de Instrumentação

Para executar os testes de instrumentação:

1. Certifique-se de ter um emulador rodando ou um dispositivo físico conectado
2. Navegue até a pasta `app/src/androidTest/java/com/guicarneirodev/wayairlines/`
3. Clique com o botão direito na pasta
4. Selecione "Run Tests in 'com.guicar...'" 

Ou via linha de comando:
./gradlew connectedAndroidTest

## Estrutura do Projeto

- `data/`
  - `model/`: Definições de dados
  - `repository/`: Lógica de acesso aos dados
- `ui/`
  - `home/`: Tela inicial
  - `flights/`: Tela de listagem de voos
  - `navigation/`: Lógica de navegação
- `viewmodel/`: ViewModels
- `WayAirlinesApplication.kt`: Configuração do Koin

## Considerações Finais

Este projeto demonstra a implementação de um aplicativo Android moderno, utilizando as melhores práticas e tecnologias recomendadas. A arquitetura MVVM, combinada com Jetpack Compose e Coroutines, proporciona uma base sólida e escalável para o desenvolvimento.
