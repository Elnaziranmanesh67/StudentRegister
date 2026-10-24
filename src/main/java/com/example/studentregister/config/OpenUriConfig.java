
package com.example.studentregister.config;


public final class OpenUriConfig {

    private OpenUriConfig() {
    }

    public static final String[][] OPEN_URIS = {
            {"/auth/register", "POST"},
            {"/auth/gender-custom-combo", "GET"},
            {"/auth/user-list-grid", "GET"}

    };
}
