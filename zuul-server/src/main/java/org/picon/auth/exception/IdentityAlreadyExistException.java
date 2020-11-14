package org.picon.auth.exception;

import org.picon.global.exception.ConflictException;

public class IdentityAlreadyExistException extends ConflictException {
    public IdentityAlreadyExistException(){
        super("이미 등록된 아이디 입니다");
    }
}
