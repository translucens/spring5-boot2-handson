package com.example.web.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.security.config.SecurityConfig;
import com.example.service.config.ServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * このクラスを作成するだけで、DispatcherServletがサーブレットコンテナに登録されます。
 * Servlet 3.0から導入されたServlet Initializerの機能を利用しています。
 */
// TODO 3-31 AbstractAnnotationConfigDispatcherServletInitializerクラスを継承する
public class MvcInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer  {

    // getRootConfigClasses()をオーバーライドしてnullをreturnする

    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // TODO 3-32 getServletConfigClasses()をオーバーライドして、これまで作成した全Java Configを配列で返していることを確認する（変更不要）
    // TODO 4-20 配列にSecurityConfig.classを追加する

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class,
                MvcConfig.class, SecurityConfig.class};
    }

    // TODO 3-33 getServletMappings()をオーバーライドして「/」を指定していることを確認する（変更不要）

    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }
}
