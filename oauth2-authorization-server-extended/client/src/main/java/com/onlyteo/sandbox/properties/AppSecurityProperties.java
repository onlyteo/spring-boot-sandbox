package com.onlyteo.sandbox.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

    @NotNull
    private List<String> whitelistedPaths = new ArrayList<>();

    public List<String> getWhitelistedPaths() {
        return whitelistedPaths;
    }

    public void setWhitelistedPaths(List<String> whitelistedPaths) {
        this.whitelistedPaths = whitelistedPaths;
    }

    public String[] getWhitelistedPathsAsArray() {
        return whitelistedPaths.toArray(new String[]{});
    }
}
