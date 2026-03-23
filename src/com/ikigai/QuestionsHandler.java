package com.ikigai;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * QuestionsHandler — serves GET /questions
 *
 * Returns all 20 quiz questions as a JSON array.
 * The frontend uses this to dynamically render the entire quiz UI.
 *
 * To add, edit or remove questions: edit QuestionBank.java only.
 */
public class QuestionsHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        List<Question> questions = QuestionBank.getAllQuestions();
        String json = buildJson(questions);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private String buildJson(List<Question> questions) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            sb.append("{");
            sb.append("\"id\":\"").append(e(q.getId())).append("\",");
            sb.append("\"section\":").append(q.getSection()).append(",");
            sb.append("\"sectionName\":\"").append(e(q.getSectionName())).append("\",");
            sb.append("\"sectionColor\":\"").append(e(q.getSectionColor())).append("\",");
            sb.append("\"type\":\"").append(e(q.getType())).append("\",");
            sb.append("\"number\":").append(q.getNumber()).append(",");
            sb.append("\"label\":\"").append(e(q.getLabel())).append("\",");
            sb.append("\"question\":\"").append(e(q.getQuestion())).append("\",");
            sb.append("\"hint\":\"").append(e(q.getHint())).append("\",");
            sb.append("\"circle\":\"").append(e(q.getCircle())).append("\",");
            sb.append("\"maxSelect\":").append(q.getMaxSelect()).append(",");

            // Options array
            sb.append("\"options\":");
            if (q.getOptions() != null && !q.getOptions().isEmpty()) {
                sb.append("[");
                List<Question.Option> opts = q.getOptions();
                for (int j = 0; j < opts.size(); j++) {
                    Question.Option o = opts.get(j);
                    sb.append("{");
                    sb.append("\"letter\":\"").append(e(o.getLetter())).append("\",");
                    sb.append("\"text\":\"").append(e(o.getText())).append("\",");
                    sb.append("\"tag\":\"").append(e(o.getTag())).append("\"");
                    sb.append("}");
                    if (j < opts.size() - 1) sb.append(",");
                }
                sb.append("]");
            } else {
                sb.append("null");
            }
            sb.append(",");

            // Sliders array
            sb.append("\"sliders\":");
            if (q.getSliders() != null && !q.getSliders().isEmpty()) {
                sb.append("[");
                List<Question.Slider> sliders = q.getSliders();
                for (int j = 0; j < sliders.size(); j++) {
                    Question.Slider s = sliders.get(j);
                    sb.append("{");
                    sb.append("\"id\":\"").append(e(s.getId())).append("\",");
                    sb.append("\"label\":\"").append(e(s.getLabel())).append("\",");
                    sb.append("\"leftLabel\":\"").append(e(s.getLeftLabel())).append("\",");
                    sb.append("\"rightLabel\":\"").append(e(s.getRightLabel())).append("\",");
                    sb.append("\"syncKey\":\"").append(e(s.getSyncKey())).append("\"");
                    sb.append("}");
                    if (j < sliders.size() - 1) sb.append(",");
                }
                sb.append("]");
            } else {
                sb.append("null");
            }

            sb.append("}");
            if (i < questions.size() - 1) sb.append(",");
        }

        sb.append("]");
        return sb.toString();
    }

    private String e(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
