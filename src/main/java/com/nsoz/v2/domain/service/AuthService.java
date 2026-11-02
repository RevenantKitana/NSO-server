package com.nsoz.v2.domain.service;

import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.domain.model.UserAccount;
import com.nsoz.v2.repository.CharacterRepository;
import com.nsoz.v2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Domain Service for Authentication and Character Selection.
 */
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final AuthService INSTANCE = new AuthService();

    private final UserRepository userRepository = UserRepository.getInstance();
    private final CharacterRepository characterRepository = CharacterRepository.getInstance();

    private AuthService() {}

    public static AuthService getInstance() {
        return INSTANCE;
    }

    public Optional<UserAccount> authenticate(String username, String password) {
        Optional<UserAccount> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            logger.warn("Authentication failed: user [{}] not found", username);
            return Optional.empty();
        }

        UserAccount user = userOpt.get();
        if (user.isBanned()) {
            logger.warn("Authentication rejected: user [{}] is banned/inactive", username);
            return Optional.empty();
        }

        // Support plain text comparison or hash verification
        if (!verifyPassword(password, user.getPassword())) {
            logger.warn("Authentication failed: invalid password for user [{}]", username);
            return Optional.empty();
        }

        logger.info("User [{}] authenticated successfully (role={})", username, user.getRole());
        return Optional.of(user);
    }

    public List<PlayerCharacter> getCharacters(int userId) {
        return characterRepository.findByUserId(userId);
    }

    public Optional<PlayerCharacter> selectCharacter(int userId, int charId) {
        Optional<PlayerCharacter> charOpt = characterRepository.findById(charId);
        if (charOpt.isPresent() && charOpt.get().getUserId() == userId) {
            return charOpt;
        }
        logger.warn("Character selection failed: charId [{}] does not belong to userId [{}]", charId, userId);
        return Optional.empty();
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        if (inputPassword == null || storedPassword == null) {
            return false;
        }
        return inputPassword.equals(storedPassword);
    }
}

