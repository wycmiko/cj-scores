package interf;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB 推送事件Dao 接口
 */
@Repository
public interface PushEventRepository<PushEvent, String> extends MongoRepository<PushEvent, String> {
}
