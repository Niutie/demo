package com.zzh.demo.mail;

import com.zzh.demo.entity.User;
import java.util.List;

public interface SendJunkMailService {

    boolean sendJunkMail(List<User> user);
}
