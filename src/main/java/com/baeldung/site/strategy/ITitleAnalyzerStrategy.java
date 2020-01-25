package com.baeldung.site.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;

public interface ITitleAnalyzerStrategy {
    boolean isTitleValid(String title, List<String> tokens, List<String> emphasizedAndItalicTokens);

    static List<ITitleAnalyzerStrategy> titleAnalyzerStrategies = Arrays.asList(new ITitleAnalyzerStrategy[] { articlesConjunctionsShortPrepositionsAnalyserStrategy(), javaMethodNameAnalyserStrategy(), simpleTitleAnalyserStrategy() });
    static String regex = "a|an|and|as|at|but|by|en|for|if|in|nor|of|on|or|per|the|to|v.?|vs.?|via|from|up|with|into|over";

    static ITitleAnalyzerStrategy articlesConjunctionsShortPrepositionsAnalyserStrategy() {
        return (title, tokens, emphasizedAndItalicTokens) -> {
            for (String token : tokens) {
                if (Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(token).matches() && !token.toLowerCase().equals(token)) {
                    return false;
                }
            }
            return true;
        };
    }

    static ITitleAnalyzerStrategy javaMethodNameAnalyserStrategy() {
        return (title, tokens, emphasizedAndItalicTokens) -> {

            for (String token : tokens) {
                if (token.contains("(")) {
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
                if (emphasizedAndItalicTokens.contains(token.trim()) || token.contains(".") || token.equals(token.toUpperCase()) || token.charAt(0) == '@' || Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(token).matches()) {
                    continue;
                }
                if (!WordUtils.capitalize(token).equals(token)) {
                    System.out.println("failure caused by:" + token);
                    return false;
                }
            }
            return true;
        };
    }
}
