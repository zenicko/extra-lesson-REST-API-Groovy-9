package ru.zenicko.reqres.config;

public final class EndPoints {
    public static final String users = "/users";
    public static final String usersPage2 = users +"?page={numPage}";
    public static final String userTwo = users + "/2";
    public static final String unknownTwoThree = users + "/23";
}
