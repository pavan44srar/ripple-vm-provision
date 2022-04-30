package com.ripple.provision.vm.exception;

public class DuplicateResourceFoundException extends Exception{

    private static final long serialVersionUID = -2662838815649189811L;

    public DuplicateResourceFoundException(String msg) {
        super(msg);
    }
}
