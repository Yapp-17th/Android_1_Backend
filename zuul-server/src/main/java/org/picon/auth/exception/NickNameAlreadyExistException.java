package org.picon.auth.exception;

import org.picon.global.exception.ConflictException;

public class NickNameAlreadyExistException extends ConflictException {
    public NickNameAlreadyExistException(){
        super("이미 사용하고 있는 닉네임 입니다");
    }
}
