package org.picon.auth.exception;

import org.picon.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {super("Member does not exist");}
}
