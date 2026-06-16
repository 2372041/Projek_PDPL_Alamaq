import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SmartExpenseWebServer {
    private static TransactionDAO transDAO = new TransactionDAO();

    public static void jalankanServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Routing File Statis
        server.createContext("/", e -> serveFile(e, "index.html", "text/html"));
        server.createContext("/style.css", e -> serveFile(e, "style.css", "text/css"));
        server.createContext("/script.js", e -> serveFile(e, "script.js", "application/javascript"));

        // API Data Dashboard
        server.createContext("/api/dashboard-data", e -> {
            String json = "{\"balance\":" + transDAO.getLiveBalance() + ",\"transactions\":" + transDAO.getAllTransactionsAsJSON() + "}";
            sendJSON(e, 200, json);
        });

        // API Transaksi
        server.createContext("/api/add-transaction", e -> {
            Map<String, String> p = parsePost(e);
            transDAO.saveTransaction(p.get("type"), 1, 1, Double.parseDouble(p.get("amount")), p.get("note"), "Web");
            sendJSON(e, 200, "{\"status\":\"success\"}");
        });

        // API Auth
        server.createContext("/api/register", e -> {
            Map<String, String> p = parsePost(e);
            boolean ok = transDAO.registerUser(p.get("username"), p.get("password"));
            sendJSON(e, ok ? 200 : 400, "{\"status\":\"" + (ok ? "success" : "failed") + "\"}");
        });

        server.createContext("/api/login", e -> {
            Map<String, String> p = parsePost(e);
            boolean ok = transDAO.validateLogin(p.get("username"), p.get("password"));
            sendJSON(e, ok ? 200 : 401, "{\"status\":\"" + (ok ? "success" : "failed") + "\"}");
        });

        server.start();
        System.out.println("[Web Server] Aktif di http://localhost:8080");
    }

    private static void sendJSON(HttpExchange e, int code, String json) throws IOException {
        byte[] b = json.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().set("Content-Type", "application/json");
        e.sendResponseHeaders(code, b.length);
        try (OutputStream os = e.getResponseBody()) { os.write(b); }
    }

    private static void serveFile(HttpExchange e, String f, String ct) throws IOException {
        File file = new File(f);
        e.getResponseHeaders().set("Content-Type", ct);
        e.sendResponseHeaders(200, file.length());
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = e.getResponseBody()) { fis.transferTo(os); }
    }

    private static Map<String, String> parsePost(HttpExchange e) throws IOException {
        String q = new BufferedReader(new InputStreamReader(e.getRequestBody())).readLine();
        Map<String, String> r = new HashMap<>();
        if (q != null) for (String p : q.split("&")) {
            String[] v = p.split("=");
            if (v.length > 1) r.put(v[0], java.net.URLDecoder.decode(v[1], StandardCharsets.UTF_8));
        }
        return r;
    }
}