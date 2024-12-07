package com.advent.code.Logger;

import java.util.ResourceBundle;

public class Logger {
    public static System.Logger logger = new System.Logger() {
        @Override
        public String getName() {
            return "logger";
        }

        @Override
        public boolean isLoggable(Level level) {
            return true;
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        }
    };
}
