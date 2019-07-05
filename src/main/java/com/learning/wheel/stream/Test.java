package com.learning.wheel.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program wheel
 * @author: hangwei
 * @create: 2019/07/05 17:40
 */
public class Test {
    public static void main(String[] args) {
        User user = new User();
        user.setAge(1L);
        user.setName("sjaj");
        UserLogo userLogo =new UserLogo();
        List<User> list = Lists.newArrayList(user);
        List<Long> longs = list.stream().map(v->userLogo.getLogo(v)).collect(Collectors.toList());
        System.out.println(1);
    }
}
