package com.cj.scores.service.cfg;

import com.cj.scores.dao.mapper.ScoresMapper;
import com.cj.scores.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动时检查表是否创建
 *
 * @author yuchuanWeng
 * @date 2018/8/27
 * @since 1.0
 */
@Component
@Slf4j
public class AutoCreateTableCfg implements CommandLineRunner {
    @Autowired
    private ScoresMapper scoresMapper;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 8; i++) {
            String paylogTableName = ValidatorUtil.getPaylogTableName(i);
            boolean exist = scoresMapper.isExistTable(paylogTableName) != null;
            if (!exist) {
                log.info("table `{}` has been created.", paylogTableName);
                scoresMapper.createScoresLogTable(paylogTableName);
            } else {
                log.info("table `{}` has already existed.", paylogTableName);
            }
        }
    }
}
