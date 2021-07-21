package com.example.wildusers.Database.OnlineDB.Api;

public class UserApi {

    private static final String ROOT_URL = "http://192.168.8.129/SmtecWildApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";

}
