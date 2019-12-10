package sf.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String nickname;
    private String password;
    private String salt;
    private Date lastlogindate;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwrod) {
        this.password = passwrod;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(Date lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
