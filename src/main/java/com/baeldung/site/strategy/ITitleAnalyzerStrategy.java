package com.baeldung.site.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;

public interface ITitleAnalyzerStrategy {
    boolean isTitleValid(String title, List<String> tokens, List<String> emphasizedAndItalicTokens);

    static List<ITitleAnalyzerStrategy> titleAnalyzerStrategies = Arrays.asList(new ITitleAnalyzerStrategy[] { articlesConjunctionsShortPrepositionsAnalyserStrategy(), javaMethodNameAnalyserStrategy(), simpleTitleAnalyserStrategy() });
    static String regexForShortPrepositions = "a|an|and|as|at|but|by|en|for|if|in|nor|of|on|or|per|the|v.?|vs.?|via|up||into|over|out";
    static String regexForExceptions = "with|to|from";

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
            String token = null;
            int firstTokenIndexStartingWithACharacter = Character.isDigit(Character.valueOf(title.charAt(0))) ? 1 : 0;
            for (int j = 0; j < tokens.size(); j++) {
                token = tokens.get(j);
                // ignore if not first and last word
                if ((j != firstTokenIndexStartingWithACharacter && j != tokens.size()-1)
                        && (Pattern.compile(regexForExceptions, Pattern.CASE_INSENSITIVE).matcher(token).matches() || Pattern.compile(regexForShortPrepositions, Pattern.CASE_INSENSITIVE).matcher(token).matches())) {
                    continue;
                }

                if (emphasizedAndItalicTokens.contains(token.trim()) || token.contains("(") || token.contains(".") || token.equals(token.toUpperCase()) || token.charAt(0) == '@' || (token.contains("-") && token.toLowerCase().equals(token))) {
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
