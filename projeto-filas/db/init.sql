-- permite utiulizar a funcao de criar ids automaticamente
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Cria a tabela principal do sistema
CREATE TABLE IF NOT EXISTS tasks (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- uuid: id unico
  queue_name  VARCHAR(100) NOT NULL,
  payload     JSONB NOT NULL,
  status      VARCHAR(20) NOT NULL DEFAULT 'pending',
  attempts    INT DEFAULT 0,
  created_at  TIMESTAMP DEFAULT NOW(),
  updated_at  TIMESTAMP DEFAULT NOW()
);

-- Índice para acelerar a busca por fila + status
-- (o worker vai fazer essa busca o tempo todo)
CREATE INDEX IF NOT EXISTS idx_tasks_queue_status
  ON tasks(queue_name, status);
  