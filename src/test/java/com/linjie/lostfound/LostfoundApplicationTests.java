package com.linjie.lostfound;

import com.linjie.lostfound.system.dao.LostRepository;
import com.linjie.lostfound.system.dao.UserRepository;
import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.model.User;
import com.linjie.lostfound.system.service.Imp.UserServiceImp;
import com.linjie.lostfound.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LostfoundApplicationTests {

    @Autowired
    LostRepository lostRepository;

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-6);
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        String dateString = s.format(calendar.getTime());
        System.out.println("dateString = " + dateString);
        lostRepository.deleteAllByLostTime(dateString);
    }

    @Test
    public void contextLoads2() {
        User user = userService.findByAccount("admin");
        System.out.println("user = " + user.getStudentId());
    }


}
