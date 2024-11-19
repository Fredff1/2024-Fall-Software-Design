package com.lab.html_editor.service.spellcheck;

import java.util.List;

public class SpellCheckError {
    private final String errorWord;
    private final List<String> suggestions;
    private final int startPosition;
    private final int endPosition;
    private final String errorType;
    private final String message;

    public SpellCheckError(String errorWord, List<String> suggestions, int startPosition, int endPosition, String errorType, String message) {
        this.errorWord = errorWord;
        this.suggestions = suggestions;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.errorType = errorType;
        this.message = message;
    }

    public String getErrorWord() {
        return errorWord;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("[Error: %s] [Type: %s] [Position %d-%d] [Suggestions: %s]",
                message, errorType, startPosition, endPosition,
                suggestions.isEmpty() ? "No suggestions" : String.join(", ", suggestions));
    }
}
