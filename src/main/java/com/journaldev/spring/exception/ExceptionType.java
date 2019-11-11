package com.journaldev.spring.exception;

/**
 * Перечень объектов, что могут быть не найдены по запросу пользователя.
 * Передаются в GameController через NotFoundException.
 *
 * @author maximuse98
 *
 **/
public enum  ExceptionType {
    USER,
    SESSION,
    IMAGE
}
