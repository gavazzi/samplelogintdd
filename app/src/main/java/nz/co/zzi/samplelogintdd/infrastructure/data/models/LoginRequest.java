package nz.co.zzi.samplelogintdd.infrastructure.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class LoginRequest {

    //Simulating retrofit request / response objects

    @SerializedName("Username")
    public String username;

    @SerializedName("Password")
    public String password;
}
