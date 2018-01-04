/**
 * Created by G_Aulia on 19 Des 2017.
 */
package com.gelaraulia.geeksfarmwebservice;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersRetrofit {

    @SerializedName("users")
    @Expose
    private List<UserRetrofit> users = null;

    public List<UserRetrofit> getUsers() {
        return users;
    }

    public void setUsers(List<UserRetrofit> users) {
        this.users = users;
    }

}
