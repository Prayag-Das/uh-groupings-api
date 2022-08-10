package edu.hawaii.its.api.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.hawaii.its.api.exception.PasswordFoundException;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PasswordScanner {

    private static final Log logger = LogFactory.getLog(PasswordScanner.class);

    private String dirname = "src/main/resources";

    /**
     * setDirname: sets the directory name (src/main/resources)
     *
     */
    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    @PostConstruct
    public void init() throws PasswordFoundException {
        logger.info("init; check for passwords starting...");
        checkForPasswords();
        logger.info("init; check for passwords finished.");
    }

    /**
     * checkForPasswords: checks all .properties files in a directory for instance(s) of password
     *
     * @throws PasswordFoundException if any passwords are found.
     */
    private void checkForPasswords() throws PasswordFoundException {
        PatternPropertyChecker checkForPattern = new PatternPropertyChecker();

        String patternResult = "";
        List<String> fileLocations = checkForPattern.getPatternLocation(dirname, ".properties");
        if (!fileLocations.isEmpty()) {
            for (String list : fileLocations) {
                patternResult += "\n" + list;
            }
        }

        if (patternResult.length() > 0) {
            throw new PasswordFoundException(patternResult);
        }
    }
}