package com.example.demo.security.common;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {

    private final OrRequestMatcher orRequestMatcher;
    private final RequestMatcher   processingMatcher;

    public FilterSkipMatcher(
            List<String> pathToSkip,
            String processingPath
    ) {
        this.orRequestMatcher = new OrRequestMatcher(pathToSkip
                .stream()
                .map(this :: httpPath)
                .collect(Collectors.toList()));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    private AntPathRequestMatcher httpPath(String skipPath) {
        String[] splitStr = skipPath.split(",");

        /*
         * 배열 [1] httpMathod 방식 post get 인지 구분
         * 배열 [0] 제외하는 url
         * */
        return new AntPathRequestMatcher(
                splitStr[1],
                splitStr[0]
        );
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}