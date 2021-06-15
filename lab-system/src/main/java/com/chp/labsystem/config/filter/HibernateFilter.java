package com.chp.labsystem.config.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

/**
 * 类功能描述：过滤器配置
 * 如果流程中的某一步被阻塞的话，那在这期间connection就一直被占用而不释放。
 * 最有可能被阻塞的就是在写Jsp这步，一方面可能是页面内容大，response.write的时间长，
 * 另一方面可能是网速慢，服务器与用户间传输时间久。当大量这样的情况出现时，就有连接池连接不足，造成页面假死现象。
 * Open Session In View是个双刃剑，放在公网上内容多流量大的网站请慎用
 */
@WebFilter(filterName = "hibernateFilter", urlPatterns = "/*", initParams = @WebInitParam(name = "singleSession", value = "true"))
public class HibernateFilter extends OpenSessionInViewFilter {
}
