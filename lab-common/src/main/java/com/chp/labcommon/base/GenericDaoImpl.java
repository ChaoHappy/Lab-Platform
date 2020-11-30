package com.chp.labcommon.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import cn.hutool.db.DbUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;


/**
 * <P>
 * 泛型DAO（GenericDao）接口的一个简单实现类，包括对Model类型的POJO的标准增删改查操作。
 * </p>
 * <P>
 * 也可以继承该类，实现对数据库的自定义操作。
 * </p>
 * @param <T> 实体类Model的类型
 * @param <PK> 实体类的主键类型 */
public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

    /**
     * <p>
     * log4j2的日志输出对象，子类可直接使用。
     * </p>
     * <p>
     * 需要注意的是，log4j2的标准获取Log对象变为如下配置：
     * </p>
     * Logger log = LogManager.getLogger(getClass());
     * <p>
     * 其中<b>LogManager</b>和<b>Logger</b>均来自<b>org.apache.logging.log4j</b>包下
     * </p>
     */
    private Class<T>					persistentClass;

    private static final Integer	BATCH_SIZE					= 100;


    public GenericDaoImpl() {
    }

    /** 持久化对象的构造方法，可以将要持久化的对象传入。
     * @param persistentClass 需要持久化的对象类型 */
    public GenericDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /** 构造方法重载，根据需要持久化对象及sessionFactory进行持久化对象。
     * @param persistentClass 需要持久化的对象类型
     * @param sessionFactory Hibernate的SessionFactory */
    public GenericDaoImpl(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        setSessionFactory(sessionFactory);
    }

    /** 获取所有实体对象集合，等同于查找一个表的所有行
     * @return 实体对象集合 */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        final Class<T> clazz = this.persistentClass;
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                return session.createCriteria(clazz).list();
            }
        });
    }

    /** 获取所有不重复的实体对象集合
     * <p>
     * 注意，如果你使用该方法，确保你的Model类，正确的实现了hashcode/equals方法。
     * </p>
     * @return 实体对象集合 */
    @Override
    public List<T> getAllDistinct() {
        final Collection<T> result = new LinkedHashSet<T>(this.getAll());
        return new ArrayList<T>(result);
    }

    /** 获取实体对象
     * @param id 主键
     * @return 实体对象
     * @throws ObjectRetrievalFailureException 获取不到结果时抛出 */
    @Override
    public T get(final PK id) {
        final T entity = getHibernateTemplate().get(this.persistentClass, id);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }
        return entity;
    }


    /** 使用命名查询获取实体对象集合
     * @param queryName 命名查询名称
     * @param queryParams 参数Map
     * @return 实体对象集合 */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findByNamedQuery(final String queryName, final Map<String, Object> queryParams) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                final Query namedQuery = session.getNamedQuery(queryName);
                for (final String s : queryParams.keySet()) {
                    final Object val = queryParams.get(s);
                    if (val instanceof Collection) {
                        namedQuery.setParameterList(s, (Collection) val);
                    } else {
                        namedQuery.setParameter(s, val);
                    }
                }
                return namedQuery.list();
            }
        });
    }

    /** 执行查询hql
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 实体对象集合 */
    @Override
    public List<T> query(final String hql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                final Query query = getHqlQuery(session, hql, args);
                return query.list();
            }
        });
    }

    /** 执行查询hql,只获取一条记录
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 实体对象 */
    @Override
    public T queryUniqueObject(final String hql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                final Query query = getHqlQuery(session, hql, args);
                return (T) query.uniqueResult();
            }
        });
    }

    /** 查询不带有别名的结果集合
     * @param hql hql查询语句
     * @param args 参数配置数组
     * @return 数据集合 */
    @Override
    public List<?> queryByHqlForObj(final String hql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<List<?>>() {

            @Override
            public List<?> doInHibernate(Session session) throws HibernateException {
                final Query query = getHqlQuery(session, hql, args);
                return query.list();
            }
        });
    }

    /** 检查实体对象是否存在
     * @param id 要查询的对象id
     * @return 如果对象存在则返回true，否则返回false */
    @Override
    public boolean exists(PK id) {
        final T entity = getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /** 保存实体对象，可以处理新建和修改
     * @param entity 需要保存的对象
     * @return 持久化对象 */
    @Override
    public T save(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        getHibernateTemplate().flush();
        return entity;
    }

    /** 执行sql语句
     * @param sql 要执行的sql语句
     * @param args 参数配置数组 */
    @Override
    public void executeBySql(final String sql, final Object... args) {
        getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                final Query query = getSqlQuery(session, sql, args);
                query.executeUpdate();
                return null;
            }
        });
    }

    private Query getSqlQuery(Session sess, String sql, Object... args) {
        final Query query = sess.createSQLQuery(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i, args[i]);
            }
        }
        return query;
    }

    /** 执行hql语句
     * @param hql 需要执行的hql语句
     * @param args 参数配置数组 */
    @Override
    public void executeByHql(final String hql, final Object... args) {
        getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                final Query query = getHqlQuery(session, hql, args);
                query.executeUpdate();
                return null;
            }
        });
    }

    private Query getHqlQuery(Session sess, String hql, Object... args) {
        final Query query = sess.createQuery(hql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i, args[i]);
            }
        }
        return query;
    }

    /** 从hibernate缓存中清除所有对象 */
    @Override
    public void clear() {
        getHibernateTemplate().clear();
    }

    /** 将对象在Session中的缓存清除掉，hibernate会强制同步状态，解决由于采用Open Session in View
     * 模式，导致在BO层设置的持久化对象没有同步的情况。如果发现BO设置某个持久化对象后，在跳转的页面上没有 显示设置后的值，可以尝试在BO处理完成对象后，使用该函数将对象从Session中清除。
     * @param entity 持久态实体对象 */
    @Override
    public void evict(Object entity) {
        getHibernateTemplate().evict(entity);
    }

    /** 删除对象
     * @param entity 需要删除的对象 */
    @Override
    public void delete(final T entity) {
        getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                // 反射调用setIsValid方法
                try {
                    final Method method = entity.getClass().getMethod("setIsValid", int.class);
                    method.invoke(entity, 0);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                session.saveOrUpdate(entity);
                return null;
            }
        });
    }

    /** 删除对象
     * @param id 主键属性 */
    @Override
    public void delete(final PK id) {
        final Class<T> clazz = this.persistentClass;
        getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                final T entity = session.get(clazz, id);
                delete(entity);
                return null;
            }
        });
    }

    /** 删除对象，该方法将直接删除数据库中记录
     * @param entity 需要删除的对象 */
    @Override
    public void realDelete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /** 真删除集合方法，该方法直接删除数据库中记录
     * @param entities 需要删除的对象集合 */
    @Override
    public void realDeleteCollection(Collection<T> entities) {
        for (T entity : entities) {
            realDelete(entity);
        }
    }

    /** 批量新建或修改对象集合
     * @param entities 需要保存的对象集合 */
    @Override
    public void batchSaveOrUpate(final List<T> entities) {
        if (entities == null || entities.size() == 0) {
            return;
        }
        getHibernateTemplate().execute(new HibernateCallback<T>() {

            @Override
            public T doInHibernate(Session session) throws HibernateException {
                if (entities.size() <= BATCH_SIZE) {
                    for (final T entity : entities) {
                        session.saveOrUpdate(entity);
                    }
                    session.flush();
                    session.clear();
                } else {
                    int count = 0;
                    for (final T entity : entities) {
                        session.saveOrUpdate(entity);
                        if (++count % BATCH_SIZE == 0) {
                            session.flush();
                            session.clear();
                            count = 0;
                        }
                    }
                }
                return null;
            }
        });
    }
}
