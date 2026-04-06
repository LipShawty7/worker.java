public class Worker {

    private static final String API_URL = System.getenv("API_URL") != null 
        ? System.getenv("API_URL") 
        : "http://localhost:3000";

    private static final String QUEUE = System.getenv("QUEUE") != null 
        ? System.getenv("QUEUE") 
        : "email";

    private static final int POLLING_INTERVAL = System.getenv("POLLING_INTERVAL") != null
        ? Integer.parseInt(System.getenv("POLLING_INTERVAL"))
        : 3000;

    public static void main(String[] args) {
        System.out.println("Worker iniciado. Queue: " + QUEUE + " | API: " + API_URL);

        while (true) {
            try {
                Task task = tarefa.getNextTask()(API_URL, QUEUE);

                if (task != null) {
                    System.out.println("Tarefa recebida: " + task.getId() + " | Tentativas: " + task.getAttempts());
                    processTask.execute(task, API_URL);
                } else {
                    System.out.println("Nenhuma tarefa na fila. Aguardando...");
                }

                Thread.sleep(POLLING_INTERVAL);

            } catch (Exception e) {
                System.err.println("Erro no worker: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
