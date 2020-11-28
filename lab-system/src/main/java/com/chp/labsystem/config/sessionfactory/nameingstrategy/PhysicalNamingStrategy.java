package com.chp.labsystem.config.sessionfactory.nameingstrategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/** 类功能描述：类名、属性名映射数据表名、字段名命名规则转换类 */
public class PhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

    protected final transient Logger	log				= LoggerFactory.getLogger(getClass());

    private static final String		TABLE_PREFIX	= "t_";

    private static final String		PRO_SUFFIX		= "_";

    /** 将实体名称转化表名称规则 规则：将类名全部大写，并加上"T_"的前缀 */
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name.getText().toLowerCase().contains(TABLE_PREFIX)) {
            return getIdentifier(name.getText(), name.isQuoted(), jdbcEnvironment);
        }
        String tableName = TABLE_PREFIX + super.toPhysicalTableName(name, jdbcEnvironment).getText();
        return getIdentifier(tableName.toUpperCase(), name.isQuoted(), jdbcEnvironment);
    }

    /** 将实体属性名称转化数据库字段名称规则 规则：按照驼峰命名规则，在大写字母前加入"_"并且全部转换大写后在最后加入"_" */
    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name.getText().contains(PRO_SUFFIX)) {
            return getIdentifier(name.getText(), name.isQuoted(), jdbcEnvironment);
        }
        String columnName = super.toPhysicalTableName(name, jdbcEnvironment).getText() + PRO_SUFFIX;
        return getIdentifier(columnName.toUpperCase(), name.isQuoted(), jdbcEnvironment);
    }
}

