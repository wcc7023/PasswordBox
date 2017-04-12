package com.wang.eggroll.passwordbox.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by eggroll on 25/03/2017.
 */

@Entity
public class PasswordItem {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String item;
    @NotNull
    private String password;
    @Generated(hash = 2063643531)
    public PasswordItem(Long id, @NotNull String item, @NotNull String password) {
        this.id = id;
        this.item = item;
        this.password = password;
    }
    @Generated(hash = 665660109)
    public PasswordItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getItem() {
        return this.item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
