const express = require("express");
const app = express();

app.use(express.json()); // ✅ TEM QUE FICAR AQUI

let tasks = [];

// criar tarefa
app.post("/tasks", (req, res) => {
  const task = {
    id: Date.now(),
    queue_name: req.body.queue_name,
    payload: req.body.payload,
    status: "pending",
    attempts: 0
  };

  tasks.push(task);

  res.json(task);
});

// teste
app.get("/", (req, res) => {
  res.send("API funcionando 🚀");
});

app.post('/mensagem', (req, res) => {
  console.log("Mensagem recebida:", req.body);
  res.send("Mensagem recebida com sucesso!");
});

// worker
setInterval(() => {
  const task = tasks.find(t => t.status === "pending");

  if (!task) return;

  console.log("Processando task:", task);

  task.status = "processing";

  if (task.queue_name === "email") {
    console.log("Enviando EMAIL para:", task.payload.to);
  }

  if (task.queue_name === "notificacao") {
    console.log("Enviando NOTIFICAÇÃO:", task.payload.message);
  }

  task.status = "done";

}, 3000);

app.listen(3000, () => {
  console.log("Servidor rodando na porta 3000");
});