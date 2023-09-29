package com.onlyteo.sandbox.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

    @Valid
    @NotNull
    private List<AppKeyProperties> keys = new ArrayList<>();
    @Valid
    @NotNull
    private List<AppUserProperties> users = new ArrayList<>();
    @NotNull
    private List<String> whitelistedPaths = new ArrayList<>();

    public List<AppKeyProperties> getKeys() {
        return keys;
    }

    public void setKeys(List<AppKeyProperties> keys) {
        this.keys = keys;
    }

    public List<AppUserProperties> getUsers() {
        return users;
    }

    public void setUsers(List<AppUserProperties> users) {
        this.users = users;
    }

    public List<String> getWhitelistedPaths() {
        return whitelistedPaths;
    }

    public void setWhitelistedPaths(List<String> whitelistedPaths) {
        this.whitelistedPaths = whitelistedPaths;
    }

    public String[] getWhitelistedPathsAsArray() {
        return whitelistedPaths.toArray(new String[]{});
    }

    public static class AppUserProperties {

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotNull
        private List<String> roles = new ArrayList<>();

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    public static class AppKeyProperties {

        @NotBlank
        private String keyId;
        @NotBlank
        private String publicKeyLocation;
        @NotBlank
        private String privateKeyLocation;

        public String getKeyId() {
            return keyId;
        }

        public void setKeyId(String keyId) {
            this.keyId = keyId;
        }

        public String getPublicKeyLocation() {
            return publicKeyLocation;
        }

        public void setPublicKeyLocation(String publicKeyLocation) {
            this.publicKeyLocation = publicKeyLocation;
        }

        public String getPrivateKeyLocation() {
            return privateKeyLocation;
        }

        public void setPrivateKeyLocation(String privateKeyLocation) {
            this.privateKeyLocation = privateKeyLocation;
        }
    }
}
