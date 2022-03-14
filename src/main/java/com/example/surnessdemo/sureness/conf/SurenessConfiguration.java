package com.example.surnessdemo.sureness.conf;

import com.usthe.sureness.DefaultSurenessConfig;
import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.annotation.AnnotationPathTreeProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zxq
 * @date 2022/2/24 8:46 下午
 * @description: surness配置类
 */
@Configuration
public class SurenessConfiguration {
    /**
     * sureness default config bean
     * @return default config bean
     * 默认配置使用了文件数据源sureness.yml和注解作为提供账户权限数据
     * 默认配置支持了jwt, basic auth, digest auth认证
     */
    //@Bean
    public DefaultSurenessConfig surenessConfig() {
        return new DefaultSurenessConfig();
    }

    /**
     * sureness custom config
     * 不使用默认配置，使用自定义配置来配置sureness的特性
     * 这里自定义配置我们将原本的默认文件数据源替换成数据库数据源
     */
    @Bean
    ProcessorManager processorManager(SurenessAccountProvider accountProvider) {
        // 处理器Processor初始化
        List<Processor> processorList = new LinkedList<>();
        // 使用了默认的支持NoneSubject的处理器NoneProcessor
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        // 使用了默认的支持JwtSubject的处理器JwtProcessor
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        // 使用了默认支持PasswordSubject的处理器PasswordProcessor
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        // 这里注意，PasswordProcessor需要对用户账户密码验证，所以其需要账户信息提供者来给他提供想要的账户数据，
        // 这里的 SurenessAccountProvider accountProvider bean就是这个账户数据提供源，
        // 其实现bean是上面讲到的 DatabaseAccountProvider bean,即数据库实现的账户数据提供者。
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        return new DefaultProcessorManager(processorList);
    }

    @Bean
    TreePathRoleMatcher pathRoleMatcher(PathTreeProvider databasePathTreeProvider) {
        // 这里的PathTreeProvider databasePathTreeProvider 就是通过数据库来提供资源权限配置信息bean实例
        // 下面我们再实例化一个通过文件sureness.yml提供资源权限配置信息的提供者
        PathTreeProvider documentPathTreeProvider = new DocumentPathTreeProvider();
        // 下面我们再实例化一个通过注解形式@RequiresRoles @WithoutAuth提供资源权限配置信息的提供者
        AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
        // 设置注解扫描包路径，也就是你提供api的controller路径
        annotationPathTreeProvider.setScanPackages(Collections.singletonList("com.example.surnessdemo.controller"));
        // 开始初始化资源权限匹配器，我们可以把上面三种提供者都加入到匹配器中为其提供资源权限数据，匹配器中的数据就是这三个提供者提供的数据集合。
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProviderList(Arrays.asList(
                documentPathTreeProvider,
                annotationPathTreeProvider,
                databasePathTreeProvider));
        // 使用资源权限配置数据来建立对应的字典匹配树
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    SubjectFactory subjectFactory() {
        // 我们之前知道了可以有各种Processor来处理对应的Jwt,bastic,digest认证，那这些Subject怎么得到呢，就需要不同的SubjectCreator来根据请求信息创建对应的Subject对象供之后的流程使用
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        // 这里我们注册我们需要的SubjectCreator
        subjectFactory.registerSubjectCreator(Arrays.asList(
                // 注意! 强制必须首先添加一个 noSubjectCreator
                new NoneSubjectServletCreator(),
                // 注册用来创建PasswordSubject的creator
                new BasicSubjectServletCreator(),
                // 注册用来创建JwtSubject的creator
                new JwtSubjectServletCreator(),
                new JwtSubjectWsServletCreator(),
                // 注册用来创建DigestSubject的creator
                new DigestSubjectServletCreator()
                // 当然也可以自己实现一个自定义的creator，实现SubjectCreate接口即可
                //new CustomPasswdSubjectCreator()
                )
        );
        return subjectFactory;
    }

    @Bean
    SurenessSecurityManager securityManager(ProcessorManager processorManager,
                                            TreePathRoleMatcher pathRoleMatcher, SubjectFactory subjectFactory) {
        // 我们把上面初始化好的配置bean整合到一起初始化surenessSecurityManager
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        // 设置资源权限匹配者
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        // 设置subject创建工厂
        securityManager.setSubjectFactory(subjectFactory);
        // 设置处理器processor管理者
        securityManager.setProcessorManager(processorManager);
        return securityManager;
    }



}
