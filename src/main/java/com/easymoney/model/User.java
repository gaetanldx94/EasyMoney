package com.easymoney.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String username;
    private String password;
    private String email;
    private String phone;
    private int    age;

    public User(
            String username,
            String password,
            String email,
            String phone,
            int age
    ) {
        this.username = username;
        this.password = password;
        this.email    = email;
        this.phone    = phone;
        this.age      = age;
    }

    public String getUuid    () { return uuid;     }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail   () { return email;    }
    public String getPhone   () { return phone;    }
    public int    getAge     () { return age;      }

    public void setUuid    (String uuid)     { this.uuid = uuid;         }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail   (String email)    { this.email = email;       }
    public void setPhone   (String phone)    { this.phone = phone;       }
    public void setAge     (int age)         { this.age = age;           }
}
