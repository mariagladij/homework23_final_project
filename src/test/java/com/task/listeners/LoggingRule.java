package com.task.listeners;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingRule implements TestRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRule.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                // Логування перед викликом тесту
                LOGGER.info("Running test: {}", description.getMethodName());
                try {
                    // Виклик самого тесту
                    base.evaluate();
                } finally {
                    // Логування після виклику тесту (навіть якщо він викинув виняток)
                    LOGGER.info("Finished test: {}", description.getMethodName());
                }
            }
        };
    }
}
