package nz.co.zzi.samplelogintdd.infrastructure.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class LoginResponse {

    //Simulating retrofit request / response objects

    @SerializedName("Success")
    public boolean success;
}
