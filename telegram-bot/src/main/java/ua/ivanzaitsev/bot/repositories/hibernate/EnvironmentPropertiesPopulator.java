package ua.ivanzaitsev.bot.repositories.hibernate;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class EnvironmentPropertiesPopulator {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{(.*?)}");

    public void populate(Properties properties) {
        properties.forEach(((propertyKey, propertyValue) -> {
            Matcher matcher = VARIABLE_PATTERN.matcher(String.valueOf(propertyValue));
            if (matcher.find()) {
                String environmentVariableKey = matcher.group(1);
                String environmentVariableValue = System.getenv(environmentVariableKey);

                if (environmentVariableValue == null) {
                    throw new IllegalStateException("Failed to get environment variable = " +
                            environmentVariableKey + " for property " + propertyKey);
                }

                properties.put(propertyKey, environmentVariableValue);
            }
        }));
    }

}
