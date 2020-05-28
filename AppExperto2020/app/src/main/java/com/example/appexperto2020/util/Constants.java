package com.example.appexperto2020.util;

import android.app.NotificationManager;

public abstract class Constants {
    public static final String SESSION_TYPE = "Login";
    public static final String SESSION_EXPERT = "Ingresando como experto";
    public static final String SESSION_CLIENT = "Ingresando como cliente";
    public static final int GALLERY_CALLBACK_DOCS = 9;
    public static final int GALLERY_CALLBACK_PP = 15;
    public static final int GALLERY_CALLBACK_CHAT = 24;

    public static final int MESSAGE_TYPE_TEXT = 1;
    public static final int MESSAGE_TYPE_IMAGE = 2;


    public static final String FOLDER_CLIENTS = "clients";
    public static final String FOLDER_EXPERTS = "experts";
    public static final String FOLDER_PROFILE_PICTURES = "profilePictures";
    public static final String FOLDER_SERVICES = "services";


    public static final String FACEBOOK_FIRST_NAME = "first_name";
    public static final String FACEBOOK_LAST_NAME = "last_name";
    public static final String FACEBOOK_PP_URL = "picture";
    public static final String CHANNEL_ID = "ChatExperto";
    public static final String CHANNEL_NAME = "ChatExpertoMessages";
    public static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    public static final String FCM_API_KEY = "AAAAEC95IrE:APA91bFujHZ3pKHZebVImNDS1rcFmElDmdy7Drw3kZGPBpC-N0geooqNults7IczCZN8uH87ErqckluJD7aEdAjwdG9AiICxUnymAzSE0qEugjbVjEg_jJYCZp7qT4ePzkEFjqApS1uq";

    public final static String MORE ="Ver más";

}
