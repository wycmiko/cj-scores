package com.cj.scores.web;

import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.cfg.JedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Slf4j
public class WebApplicationTests {


    @Autowired
    private JedisCache jedisCache;
    @Autowired
    private ScoresMapper scoresMapper;

    @Test
    public void test() {
        boolean test = jedisCache.tryGetDistributedLock("test", "1", 3000);
        boolean b = jedisCache.releaseDistributedLock("test", "1");
        boolean test2 = jedisCache.tryGetDistributedLock("test", "1", 3000);
        log.info("第1次获得锁 :{}, 第一次释放锁结果：{} 第二次获得锁结果={}", test, b, test2);

    }

    @Test
    public void test2() {
        String tableName = "s_test";
        boolean exist =  scoresMapper.isExistTable(tableName) != null;
        if (!exist) {
            log.info("建表操作");
            scoresMapper.createScoresLogTable(tableName);
        }
    }
}
