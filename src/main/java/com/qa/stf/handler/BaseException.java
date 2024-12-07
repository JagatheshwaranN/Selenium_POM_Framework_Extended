package com.qa.stf.handler;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }



    public static class OptionNotFoundException extends BaseException {

        public OptionNotFoundException(String value, String elementLabel) {
            super("Option '" + value + "' not found in the '" + elementLabel + "' dropdown.");

        }

        public OptionNotFoundException(String value, String elementLabel, Throwable throwable) {
            super("Option '" + value + "' not found in the '" + elementLabel + "' dropdown.", throwable);
        }
    }

    public static class DropDownException extends BaseException {

        public DropDownException(String value, Throwable cause) {
            super(value, cause);

        }

    }

    public static class ElementNotFoundException extends BaseException {

        public ElementNotFoundException(String elementLabel) {
            super("Element '" + elementLabel + "' is not found / modified on the DOM.");
        }

        public ElementNotFoundException(String elementLabel, Throwable throwable) {
            super("Element '" + elementLabel + "' is not found / modified on the DOM.", throwable);
        }
    }

    public static class ConfigTypeException extends BaseException {

        public ConfigTypeException(String config) {
            super("The '" + config + "' config type is not valid. Please check the configuration.");
        }
    }

    public static class InvalidDataException extends BaseException {

        public InvalidDataException(String data) {
            super("The '" + data + "' value is not valid. Please check the data.");
        }
    }

    public static class AlertNotFoundException extends BaseException {

        public AlertNotFoundException(Throwable throwable) {
            super("Alert was not present on the page.", throwable);
        }
    }

    public static class NavigationException extends BaseException {

        public NavigationException(String data, Throwable throwable) {
            super(data, throwable);
        }
    }

    public static class WindowException extends BaseException {

        public WindowException(String data, Throwable throwable) {
            super(data, throwable);
        }
    }

    public static class FrameException extends BaseException {

        public FrameException(String data, Throwable throwable) {
            super(data, throwable);
        }
    }

    public static class JavascriptExecutorException extends BaseException {

        public JavascriptExecutorException(String message) {
            super(message);
        }

        public JavascriptExecutorException(String message, Throwable cause) {
            super(message, cause);
        }
    }



}
