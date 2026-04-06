public class TaskHandler {

    public static void handleError(Task task) {
        int maxRetries = 3;

        if (task.getAttempts() < maxRetries) {
            updateTaskStatus(task.getId(), "pending");
        } else {
            updateTaskStatus(task.getId(), "error");
        }
    }

    public static void updateTaskStatus(String taskId, String status) {
        System.out.println("Task " + taskId + " -> " + status);
    }
}