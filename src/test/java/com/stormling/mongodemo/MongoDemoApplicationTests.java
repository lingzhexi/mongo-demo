package com.stormling.mongodemo;

import com.stormling.mongodemo.model.User;
import com.stormling.mongodemo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MongoDemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void add() {
        User user = new User();
        user.setName("武松")
                .setAge(25)
                .setCreateDate(new Date());
        userRepository.save(user);
    }

    @Test
    public void findAll() {
        List<User> all = userRepository.findAll();
        all.forEach(System.out::println);

    }

    @Test
    public void findById() {
        Optional<User> optional = userRepository.findById(new ObjectId("670750920963050cc1fb56f9"));
        if (optional.isPresent()) {
            User user = optional.get();
            System.out.println(user);
        }
    }

    @Test
    public void testFindCondition() {
        // 封装条件
        User user = new User();
        user.setAge(20);
        Example<User> userExample = Example.of(user);
        // 排序
        Sort sort = Sort.by(Sort.Direction.DESC, "age");

        List<User> all = userRepository.findAll(userExample, sort);
        all.forEach(System.out::println);
    }

    // 分页
    @Test
    public void page() {
        // 第一页从0开始
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<User> page = userRepository.findAll(pageRequest);
        List<User> content = page.getContent();
        System.out.println(content);
    }
}


