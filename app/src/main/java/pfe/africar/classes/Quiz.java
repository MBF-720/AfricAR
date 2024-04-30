package pfe.africar.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz {
    private Map<String, List<String>> questions;
    private Map<String, String> correctAnswers;

    public Quiz() {
        this.questions = new HashMap<>();
        this.correctAnswers = new HashMap<>();
    }

    public void addQuestion(String question, List<String> responses, String correctResponse) {
        this.questions.put(question, responses);
        this.correctAnswers.put(question, correctResponse);
    }

    public List<String> getResponses(String question) {
        return this.questions.get(question);
    }

    public String getCorrectAnswer(String question) {
        return this.correctAnswers.get(question);
    }

    public Map<String, List<String>> getQuestions() {
        return this.questions;
    }

    public Map<String, String> getCorrectAnswers() {
        return this.correctAnswers;
    }

}