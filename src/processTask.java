public static void processTask(Task task) {
    try {
        String email = task.getPayload().get("email");
        String nome = task.getPayload().get("nome");

        EmailService.sendEmail(email, nome);

        updateTaskStatus(task.getId(), "done");

    } catch (Exception e) {
        e.printStackTrace();
        handleError(task);
    }
}