
package com.example.studentregister.config;


public final class OpenUriConfig {

    private OpenUriConfig() {
    }

    public static final String[][] OPEN_URIS = {
            {"/auth/register", "POST"},
            {"/user/gender-custom-combo", "GET"},
            {"/user/user-list-grid", "GET"},
            {"/user/change-password","PUT"},
            {"/user/check-role/{nationalCode}","GET"},
            {"/auth/login/token-less","POST"}

    };
}
