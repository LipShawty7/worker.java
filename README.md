# Worker de Email — Java + Docker

## Estrutura do Projeto

```
worker-project/
├── src/
│   ├── Worker.java        # Loop principal de polling
│   ├── Task.java          # Modelo da tarefa
│   ├── tarefa.java        # Busca próxima tarefa na API
│   ├── processTask.java   # Processa a tarefa
│   ├── EmailService.java  # Envia o email via SMTP
│   └── handleError.java   # Gerencia erros e retries
├── lib/
│   ├── jackson-databind-x.x.x.jar
│   ├── jackson-core-x.x.x.jar
│   ├── jackson-annotations-x.x.x.jar
│   └── javax.mail-x.x.x.jar         (JavaMail / Jakarta Mail)
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Dependências (pasta lib/)

Baixe os JARs e coloque em `lib/`:

| Biblioteca | Link |
|---|---|
| Jackson Databind | https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind |
| Jackson Core | https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core |
| Jackson Annotations | https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations |
| JavaMail (Jakarta) | https://mvnrepository.com/artifact/com.sun.mail/javax.mail |

## Variáveis de Ambiente

| Variável | Descrição | Padrão |
|---|---|---|
| `API_URL` | URL base da API de tarefas | `http://localhost:8080` |
| `QUEUE` | Nome da fila a consumir | `email` |
| `POLLING_INTERVAL` | Intervalo entre polls (ms) | `3000` |
| `EMAIL_USER` | Gmail para envio | — |
| `EMAIL_PASS` | Senha de app do Gmail | — |

> **Importante:** Use uma **Senha de App** do Google (não sua senha normal).  
> Gere em: https://myaccount.google.com/apppasswords

## Como rodar

### Desenvolvimento local
```bash
# Compilar
javac -cp "lib/*" -d . src/*.java

# Rodar
java -cp ".:lib/*" Worker
```

### Com Docker
```bash
# Buildar e subir
docker compose up --build

# Escalar workers
docker compose up --scale worker-email=3
```

## Formato esperado da tarefa (JSON da API)

```json
{
  "id": "abc123",
  "attempts": 0,
  "payload": {
    "to": "destinatario@email.com",
    "subject": "Assunto do email",
    "body": "Corpo do email aqui"
  }
}
```

## Endpoints esperados na API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/tasks/next/{queue}` | Retorna próxima tarefa (200) ou vazio (204/404) |
| PATCH | `/tasks/{id}/retry` | Marca para retentar |
| PATCH | `/tasks/{id}/fail` | Marca como falha definitiva |
