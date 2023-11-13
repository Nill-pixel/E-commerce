package App.Web.Config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final String[] _allowedUrls = new String[] {
                        "/login", "/signup", "/sign", "/register", "/user/**", "shopLogin", "/login/**",
                        "/processLogin","formShop",
                        "/terms", "/", "/about", "/contact", "/error", "/found",
                        "/css/**", "/fonts/**", "/js/**", "/images/**", "/admin/**",
                        "admin/css/**", "admin/fonts/**", "admin/js/**", "admin/images/**", "/libs/**",
                        "admin/**", "/detail/**", "/listShop", "saveShop", "shops",
                        "admin/", "shop", "/addProductForm", "/saveProduct",
                        "/menu", "cart", "productList", "edit", "edit/{id}", "editProduct", "/addProductForm/{id}",
                        "/addToCart/{id}", "clearCart"
        };

        @Bean
        public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
                httpSecurity.csrf().disable()
                                .authorizeHttpRequests()
                                .requestMatchers(_allowedUrls)
                                .permitAll()
                                .and()
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/")
                                                .loginProcessingUrl("/login")
                                                .permitAll())
                                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                .permitAll());

                return httpSecurity.build();
        }
}
