package com.stormling.mongodemo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.stormling.mongodemo.model.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class MongoTemplateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void add() {
        User user = new User();
        user.setName("林冲")
                .setAge(25)
                .setCreateDate(new Date());
        mongoTemplate.insert(user);
        mongoTemplate.save(user);
    }

    @Test
    public void findAll() {
        List<User> all = mongoTemplate.findAll(User.class);
        all.forEach(System.out::println);
    }

    @Test
    public void findById() {
        User user = mongoTemplate.findById(new ObjectId("670750920963050cc1fb56f9"), User.class);
        System.out.println(user);
    }

    @Test
    public void findByConditional() {
        //封装条件
        Criteria criteria =
                // where name=？and age=？
                Criteria.where("name").is("林冲")
                        .and("age").is(25);
        Query query = new Query(criteria);
        List<User> users = mongoTemplate.find(query, User.class);
        users.forEach(System.out::println);
    }

    //分页
    @Test
    public void page() {
        Query query = new Query();
        // limit 0,2
        List<User> users = mongoTemplate.find(query.skip(0).limit(2), User.class);
        users.forEach(System.out::println);
    }

    @Test
    public void update() {
        //Criteria
        Criteria criteria = Criteria.where("_id").is("670750920963050cc1fb56f9");
        //Query
        Query query = new Query(criteria);
        //Update
        Update update = new Update();
        update.set("name", "宋江-及时雨");
        update.set("age", 30);
        //upsert
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getMatchedCount();
        System.out.println(count);

    }

    @Test
    public void delete() {
        //Criteria
        Criteria criteria = Criteria.where("_id").is("670750920963050cc1fb56f9");
        //Query
        Query query = new Query(criteria);
        //remove
        DeleteResult remove = mongoTemplate.remove(query, User.class);
        System.out.println(remove.getDeletedCount());
    }
}
