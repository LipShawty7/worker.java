import java.net.HttpURLConnection;
import java.util.Scanner;

public class tarefa {
    public static tarefa getNextTask() {
    try {
        URL url = new URL(API_URL + "/tasks/next/" + QUEUE);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();

            // aqui você parseia JSON (simplifica depois)
            return Task.fromJson(response);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
}
