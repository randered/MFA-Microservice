package com.randered.mfa.constants;

public final class Constants {

    private Constants() {
    }
    public static final String EMAIL_SUCCESSFULLY_SEND = "Email successfully send to: ";
    public static final String EMAIL_FAILED = "Email delivery failed for: ";
    public static final String CODE_SUCCESSFULLY_VALIDATED = "Code was successfully validated!";
    public static final String CODE_FAILED_VALIDATION = "Code has failed the validation process!";
    public static final long TIME_DURATION = 10;
    public static final String EMAIL_SUBJECT = "Your MFA Code";
    public static final String EMAIL_BODY = "Your MFA code is: ";
    public static final String LOG_SUCCESS = "MFA code to {} send successfully.";
    public static final String LOG_FAILURE = "Failed to send MFA code to {}";
    public static final String LOG_FAILURE_EXCEPTION = "Failed to send email to ";


}
