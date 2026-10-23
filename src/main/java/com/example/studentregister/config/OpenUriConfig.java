
package com.example.studentregister.config;


public final class OpenUriConfig {

    private OpenUriConfig() {
    }

    public static final String[][] OPEN_URIS = {
            {"/auth/register", "POST"},
            {"/auth/login", "POST"}
    };
}
