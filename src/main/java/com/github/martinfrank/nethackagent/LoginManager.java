package com.github.martinfrank.nethackagent;

import net.sourceforge.kolmafia.KoLCharacter;
import net.sourceforge.kolmafia.request.ApiRequest;
import net.sourceforge.kolmafia.request.LoginRequest;
import net.sourceforge.kolmafia.session.LogoutManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginManager {

    private static final Logger logger = LoggerFactory.getLogger(LoginManager.class);

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    public static boolean isLoggedOut() {
        ApiRequest request = new ApiRequest("status");
        request.run();
        if (request.responseText == null ||request.responseText.contains("limiteduser") || request.responseText.isEmpty()) {
            logger.info("Session ungültig oder ausgelaufen");
            return true;
        }

        logger.info("Session gültig");
        return false;
    }

    public static void login() {
        LoginRequest login = new LoginRequest(USERNAME, PASSWORD);
        login.run();

        if (!KoLCharacter.getUserName().equalsIgnoreCase(USERNAME)) {
            logger.error("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
            throw new IllegalStateException("Login fehlgeschlagen! - es konnten keine Spielerinformation ausgewertet werden");
        }
    }

    public static void ensureLogin(){
        logger.debug("ensure login");
        if(isLoggedOut()){
            logger.info("not logged in -> execute login");
            login();
        }else{
            logger.debug("already logged in, continue");
        }
    }

    public static void logout() {
        LogoutManager.logout();
        logger.info("logged out complete...");
    }

    public static void main (String[] args){
        logout();
    }
}
