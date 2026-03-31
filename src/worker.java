public class Worker {

    private static final String API_URL = "http://localhost:8080";
    private static final String QUEUE = "email";

    public static void main(String[] args) {

        while (true) {
            try {
                Task task = getNextTask();

                if (task != null) {
                    processTask(task);
                }

                Thread.sleep(3000); // polling (configurável)

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}