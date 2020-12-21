package com.shuailee.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @program: geek-calendar-framework
 * @description: Checker
 * @author: shuai.li
 * @create: 2020-05-21 18:04
 **/
public class Checker {

    private Checker() {
    }

    /**
     * 是否不为空
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotEmpty(String argument, String argumentName) throws IllegalArgumentException {
        if (StringUtils.isBlank(argument)) {
            throw new IllegalArgumentException(argumentName + " cannot be blank");
        }
    }

    /**
     * 不为空
     *
     * @param argument
     * @param argumentName
     * @param <T>
     * @throws IllegalArgumentException
     */
    public static <T> void isNotEmpty(Collection<T> argument, String argumentName) throws IllegalArgumentException {
        if (null == argument || argument.size() == 0) {
            throw new IllegalArgumentException("Collection cannot be empty." + argumentName);
        }
    }

    /**
     * 不为null
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotNull(Object argument, String argumentName) throws IllegalArgumentException {
        if (null == argument) {
            throw new IllegalArgumentException(argumentName + " cannot be null");
        }
    }

    /**
     * 不小于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void idNotNegative(int argument, String argumentName) throws IllegalArgumentException {
        if (argument < 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less zero");
        }
    }

    /**
     * 不小于等于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotNegativeOrZero(int argument, String argumentName) throws IllegalArgumentException {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less or equal zero");
        }
    }

    /**
     * 不小于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void idNotNegative(long argument, String argumentName) throws IllegalArgumentException {
        if (argument < 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less zero");
        }
    }

    /**
     * 是否不小于/等于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotNegativeOrZero(long argument, String argumentName) throws IllegalArgumentException {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less or equal zero");
        }
    }

    /**
     * 是否小于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void idNotNegative(float argument, String argumentName) throws IllegalArgumentException {
        if (argument < 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less zero");
        }
    }

    /**
     * 是否不小于/等于0
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotNegativeOrZero(float argument, String argumentName) throws IllegalArgumentException {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " cannot be less or equal zero");
        }
    }

    private static final String emailRegex = ".+@.+\\..+";

    /**
     * 是否符合Emial规则
     *
     * @param argument
     * @param argumentName
     * @throws IllegalArgumentException
     */
    public static void isNotInvalidEmail(String argument, String argumentName) throws IllegalArgumentException {
        isNotEmpty(argument, argumentName);
        if (!argument.matches(emailRegex)) {
            throw new IllegalArgumentException(argumentName + " is not a valid email address.");
        }
    }

    /**
     * suppiler 为 true,则判断失败, 会 throw 异常
     *
     * @param supplier
     * @param errorMessage
     */
    public static void predicate(Supplier<Boolean> supplier, String errorMessage) {
        if (null != supplier && supplier.get()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
