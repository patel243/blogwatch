package com.baeldung.site.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;

public interface ITitleAnalyzerStrategy {
    boolean isTitleValid(String title, List<String> tokens, List<String> emphasizedAndItalicTokens);

    static List<ITitleAnalyzerStrategy> titleAnalyzerStrategies = Arrays.asList(new ITitleAnalyzerStrategy[] { articlesConjunctionsShortPrepositionsAnalyserStrategy(), javaMethodNameAnalyserStrategy(), simpleTitleAnalyserStrategy() });
    static String regexForShortPrepositions = "a|an|and|as|at|but|by|en|for|if|in|nor|of|on|or|per|the|to|v.?|vs.?|via|from|up||into|over|out";
    static String regexForExceptions = "with";

    static ITitleAnalyzerStrategy articlesConjunctionsShortPrepositionsAnalyserStrategy() {
        return (title, tokens, emphasizedAndItalicTokens) -> {
            String expectedToken = null;
            int tokenBeingAnalysed = 1;
            int firstTokenStartingWithACharacter = 1;
            for (String token : tokens) {
                firstTokenStartingWithACharacter = 1;
                if (emphasizedAndItalicTokens.contains(token.trim())) {
                    tokenBeingAnalysed++;
                    continue;
                }
                if (Pattern.compile(regexForShortPrepositions, Pattern.CASE_INSENSITIVE).matcher(token.trim()).matches()) {
                    if (Character.isDigit(Character.valueOf(title.charAt(0)))) {
                        firstTokenStartingWithACharacter = 2;
                    }
                    if (tokenBeingAnalysed == firstTokenStartingWithACharacter) {
                        expectedToken = WordUtils.capitalize(token.toLowerCase());
                    } else {
                        expectedToken = token.toLowerCase();
                    }
                    if (!expectedToken.equals(token)) {
                        return false;
                    }
                }
                tokenBeingAnalysed++;
            }
            return true;
        };
    }

    static ITitleAnalyzerStrategy javaMethodNameAnalyserStrategy() {
        return (title, tokens, emphasizedAndItalicTokens) -> {

            for (String token : tokens) {
                if (token.contains("(")) {
                    if (token.toUpperCase().equals(token)) {
                        continue;
                    }
                    if (token.contains(".")) {
                        String expetedToken = WordUtils.capitalize(Arrays.asList(token.split("\\.")).stream().map(WordUtils::uncapitalize).collect(Collectors.joining(".")));
                        if (!expetedToken.equals(token)) {
                            return false;
                        }
                    } else {
                        if (!WordUtils.uncapitalize(token, '$').equals(token)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        };
    }

    static ITitleAnalyzerStrategy simpleTitleAnalyserStrategy() {
        return (title, tokens, emphasizedAndItalicTokens) -> {

            for (String token : tokens) {
                if (emphasizedAndItalicTokens.contains(token.trim()) || token.contains("(") || token.contains(".") || token.equals(token.toUpperCase()) || token.charAt(0) == '@' || Pattern.compile(regexForShortPrepositions, Pattern.CASE_INSENSITIVE).matcher(token).matches()
                        || Pattern.compile(regexForExceptions, Pattern.CASE_INSENSITIVE).matcher(token).matches() || (token.contains("-") && token.toLowerCase().equals(token))) {
                    continue;
                }
                if (!WordUtils.capitalize(token).equals(token)) {
                    return false;
                }
            }
            return true;
        };
    }
}
