package com.ikigai;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Main — Ikigai Career Predictor Java Server
 *
 * HOW TO RUN:
 *   javac -d out src/com/ikigai/*.java
 *   java  -cp out com.ikigai.Main
 *
 * ENDPOINTS:
 *   GET  /questions  — Returns all 20 quiz questions as JSON (edit QuestionBank.java to change)
 *   POST /predict    — Accepts user answers, returns top 3 Ikigai career matches
 *   GET  /health     — Server health check
 */
public class Main {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/questions", new QuestionsHandler());
        server.createContext("/predict",   new PredictHandler());

        server.createContext("/health", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String msg = "{\"status\":\"running\",\"port\":" + PORT
                       + ",\"questions\":" + QuestionBank.getAllQuestions().size()
                       + ",\"careers\":" + CareerDatabase.getAllCareers().size() + "}";
            byte[] b = msg.getBytes();
            exchange.sendResponseHeaders(200, b.length);
            exchange.getResponseBody().write(b);
            exchange.getResponseBody().close();
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   生き甲斐  Ikigai Career Predictor           ║");
        System.out.println("║   Java Server started  →  port " + PORT + "         ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║   GET  /questions  (dynamic quiz questions)  ║");
        System.out.println("║   POST /predict    (Ikigai matching engine)  ║");
        System.out.println("║   GET  /health                               ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║   Open  frontend/index.html in browser       ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}
