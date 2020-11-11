package org.picon.auth.exception;

import org.picon.global.exception.ForbiddenException;

public class PasswordMismatchException extends ForbiddenException {
    public PasswordMismatchException(){super("Password is wrong");}
}
