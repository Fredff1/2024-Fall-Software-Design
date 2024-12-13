package com.lab.html_editor.service.spellcheck;


import org.apache.commons.text.similarity.LevenshteinDistance;

import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;

import java.util.Set;

import org.languagetool.language.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellCheckService {

    private final JLanguageTool langTool;

    public SpellCheckService() {
        langTool = new JLanguageTool(new AmericanEnglish());
    }

    public List<String> checkSpelling(String text) throws IOException {
        List<String> errors = new ArrayList<>();
        List<RuleMatch> matches = langTool.check(text);

        for (RuleMatch match : matches) {
            // 获取错误类型
            String errorType = match.getRule().getCategory().getName();

            // 获取建议替换词
            String suggestions = match.getSuggestedReplacements().isEmpty() 
                ? "No suggestions" 
                : String.join(", ", match.getSuggestedReplacements());
            
            // 构建错误信息，包含错误类型、建议替换词、具体位置等
            String errorDetail = String.format(
                "[%s (Type: %s)] [Position %d-%d]\n[Suggested replacements: %s]",
                match.getMessage(),
                errorType,
                match.getFromPos(),
                match.getToPos(),
                suggestions
            );

            errors.add(errorDetail);
        }

        return errors;
    }

    public List<SpellCheckError> checkSpellingErrors(String text) throws IOException {
        List<SpellCheckError> errors = new ArrayList<>();
        List<RuleMatch> matches = langTool.check(text);

        for (RuleMatch match : matches) {
            String errorWord = text.substring(match.getFromPos(), match.getToPos());
            String errorType = match.getRule().getCategory().getName();
            List<String> suggestions = match.getSuggestedReplacements();
            String message = match.getMessage();
            int startPosition = match.getFromPos();
            int endPosition = match.getToPos();

            SpellCheckError error = new SpellCheckError(
                    errorWord,
                    suggestions,
                    startPosition,
                    endPosition,
                    errorType,
                    message
            );

            errors.add(error);
        }

        return errors;
    }

    public String findClosestCommand(String inputCommand, Set<String> commandKeys) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        String closestCommand = null;
        int minDistance = Integer.MAX_VALUE;

        for (String command : commandKeys) {
            int distance = levenshtein.apply(inputCommand, command);
            if (distance < minDistance) {
                minDistance = distance;
                closestCommand = command;
            }
        }

        return closestCommand != null ? closestCommand : "None";
    }
}
