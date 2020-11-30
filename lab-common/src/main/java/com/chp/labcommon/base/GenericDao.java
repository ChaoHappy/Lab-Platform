package com.chp.labcommon.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ObjectRetrievalFailureException;

/** 泛型DAO（数据访问对象），包括了通用的对实体类POJO的增删改查方法。
 * <p>
 * 扩展这个接口，用于实现你自己的域对象的类型安全（无需强制转型）的DAO。
 * </p>
 * @param <T> 实体类Model的类型
 * @param <PK> 实体类的主键类型 */
public interface GenericDao<T, PK extends Serializable> {

    /** 获取所有实体对象集合，等同于查找一个表的所有行
     * @return 实体对象列表 */
    List<T> getAll();

    /** 获取所有不重复的实体对象集合
     * <p>
     * 注意，如果你使用该方法，确保你的Model类，正确的实现了hashcode/equals方法。
     * </p>
     * @return 实体对象集合 */
    List<T> getAllDistinct();

    /** 获取实体对象
     * @param id 主键
     * @return 实体对象
     * @throws ObjectRetrievalFailureException 获取不到结果时抛出 */
    T get(PK id);

    /** 使用命名查询获取实体对象集合
     * @param queryName 命名查询名称
     * @param queryParams 参数Map
     * @return 实体对象集合 */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    /** 查询实体对象集合
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 实体对象集合 */
    List<T> query(String hql, Object... args);

    /** 查询实体对象。如果有多条记录符合条件则按照排序返回第一条；如果未找到数据则返回null
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 实体对象 */
    T queryUniqueObject(String hql, Object... args);

    /** 查询不带有别名的结果集合
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 数据集合 */
    List<?> queryByHqlForObj(String hql, Object... args);

    /** 检查实体对象是否存在
     * @param id 要查询的对象id
     * @return 如果对象存在则返回true，否则返回false */
    boolean exists(PK id);

    /** 保存实体对象，可以处理新建和修改
     * @param entity 需要保存的对象
     * @return 持久化对象 */
    T save(T entity);

    /** 执行sql语句
     * @param sql 要执行的sql语句
     * @param args 参数配置数组 */
    void executeBySql(String sql, Object... args);

    /** 执行hql语句
     * @param hql 需要执行的hql语句
     * @param args 参数配置数组 */
    void executeByHql(String hql, Object... args);

    /** 从hibernate缓存中清除所有对象 */
    void clear();

    /** 将对象在Session中的缓存清除掉，hibernate会强制同步状态，解决由于采用Open Session in View
     * 模式，导致在BO层设置的持久化对象没有同步的情况。如果发现BO设置某个持久化对象后，在跳转的页面上没有 显示设置后的值，可以尝试在BO处理完成对象后，使用该函数将对象从Session中清除。
     * @param entity 持久态实体对象 */
    void evict(Object entity);

    /** 删除对象
     * @param entity 需要删除的对象 */
    void delete(T entity);

    /** 删除对象
     * @param id 主键属性 */
    void delete(PK id);

    /** 删除对象，该方法将直接删除数据库中记录
     * @param entity 需要删除的对象 */
    void realDelete(T entity);

    /** 真删除集合方法，该方法直接删除数据库中记录
     * @param entities 需要删除的对象集合 */
    void realDeleteCollection(Collection<T> entities);

    /** 批量新建或修改对象集合
     * @param entities 需要保存的对象集合 */
    void batchSaveOrUpate(List<T> entities);
}
