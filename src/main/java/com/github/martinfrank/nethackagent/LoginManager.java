package com.github.martinfrank.nethackagent;

import net.sourceforge.kolmafia.KoLCharacter;
import net.sourceforge.kolmafia.request.ApiRequest;
import net.sourceforge.kolmafia.request.LoginRequest;
import net.sourceforge.kolmafia.session.LogoutManager;

public class LoginManager {

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    public static boolean isLoggedOut() {
        ApiRequest request = new ApiRequest("status");
        request.run();
        if (request.responseText == null ||request.responseText.contains("limiteduser") || request.responseText.isEmpty()) {
            System.out.println("Session ungültig oder ausgelaufen");
            return true;
        }

        System.out.println("Session gültig");
        return false;
    }

    public static void login() {
        LoginRequest login = new LoginRequest(USERNAME, PASSWORD);
        login.run();

        if (!KoLCharacter.getUserName().equalsIgnoreCase(USERNAME)) {
            System.out.println("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
            throw new IllegalStateException("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
        }
    }

    public static void ensureLogin(){
        System.out.println("ensure login");
        if(isLoggedOut()){
            System.out.println("not logged in -> execute login");
            login();
        }else{
            System.out.println("already logged in, continue");
        }
    }

    public static void logout() {
        LogoutManager.logout();
        System.out.println("logged out complete...");
    }
}
