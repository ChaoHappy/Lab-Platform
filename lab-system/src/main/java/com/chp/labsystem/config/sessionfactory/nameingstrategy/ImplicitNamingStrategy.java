package com.chp.labsystem.config.sessionfactory.nameingstrategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

/** 类功能描述： 多对多关联命名规则转换 */
public class ImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private static final long		serialVersionUID	= -4907162708504390499L;

    private static final String	PRO_SUFFIX			= "_";

    @Override
    /** 配置表多对多关联关系命名规则，主表实体名称大写+"_"+关联表实体名称大写 */
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        String name = source.getOwningPhysicalTableName() + PRO_SUFFIX + source.getNonOwningPhysicalTableName();
        return toIdentifier(name.toUpperCase(), source.getBuildingContext());
    }

    @Override
    /** 设置关联关系列名规则 规则：主表实体名称+"_"+主键列名称+"_"+ */
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
        final String name = transformEntityName(source.getEntityNaming())
                + PRO_SUFFIX
                + source.getReferencedColumnName().getText()
                + PRO_SUFFIX;
        return toIdentifier(name.toUpperCase(), source.getBuildingContext());
    }
}

