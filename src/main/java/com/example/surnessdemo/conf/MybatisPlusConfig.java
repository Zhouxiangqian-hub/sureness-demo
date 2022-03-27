package com.example.surnessdemo.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zxq
 * @date 2022/3/27 5:23 下午
 * @description: MybatisPlus配置类
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.example.surnessdemo.dao")
public class MybatisPlusConfig {
    /**
     * 主体插件。
     * 该插件是核心插件,目前代理了 Executor#query 和 Executor#update 和 StatementHandler#prepare 方法
     * #属性：private List<InnerInterceptor> interceptors = new ArrayList<>();
     * #InnerInterceptor
     * mybatis-plus提供的插件都将基于此接口来实现功能
     * 目前已有的功能:
     * 自动分页: PaginationInnerInterceptor
     * 多租户: TenantLineInnerInterceptor
     * 动态表名: DynamicTableNameInnerInterceptor
     * 乐观锁: OptimisticLockerInnerInterceptor
     * sql 性能规范: IllegalSQLInnerInterceptor
     * 防止全表更新与删除: BlockAttackInnerInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //设置分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

//    /**
//     * 分页插件
//     */
//    @Bean
//    public PaginationInnerInterceptor paginationInterceptor() {
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        paginationInnerInterceptor.setOverflow(true);
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
//        return paginationInnerInterceptor;
//    }


}
