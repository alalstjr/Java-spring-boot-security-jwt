package com.example.demo.security.common;

import java.util.ArrayList;
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
            List<AntPathRequestMatcher> pathToSkip,
            String processingPath
    ) {
        this.orRequestMatcher = new OrRequestMatcher(new ArrayList<>(pathToSkip));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}