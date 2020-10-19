package Mih.demo.CacheServer;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisServer {

    // 加入元素
    void setValue(String key, Map<String, Object> value);
    // 加入元素
    void setValue(String key, String value);
    // 加入元素
    void setValue(String key, Object value);
    // 获取元素
    Object getMapValue(String key);
    // 获取元素
    Object getValue(String key);

    // 删除元素
    void delValue(String key);

    //命名key，如果newKey已经存在，则newKey的原值被覆盖
    void renameKey(String oldKey, String newKey);

    //判断key是否存在
    boolean existsKey(String key);

    // 命名key，如果newKey不存在时才命名
    boolean renameKeyNotExist(String oldKey, String newKey);

    // 删除多个key
    void deleteKey(String... keys);

    // 删除key集合
    void deleteKey(Collection<String> keys);

    // 设置key的生命周期
    void expireKey(String key, long time, TimeUnit timeUnit);

    // 指定key在指定的时间过期
    void expireKeyAt(String key, Date date);

    // 查询指定key的生命周期
    long getKeyExpire(String key, TimeUnit timeUnit);

    // 设置key永不过期
    void persistKey(String key);
}
