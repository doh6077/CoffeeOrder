package ca.sheridancollege.kimdohee.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
// Configuration code goes here
	
	@Bean
	public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("frank").password(passwordEncoder.encode("123")).roles("USER").build());
		manager.createUser(User.withUsername("fahad.jan@sheridancollege.ca").password(passwordEncoder.encode("12345"))
				.roles("USER", "ADMIN").build());
		manager.createUser(User.withUsername("dohee").password(passwordEncoder.encode("123")).roles("ADMIN").build());
		return manager;
	}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector
	introspector) throws Exception {
	MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
	return http.authorizeHttpRequests(authorize -> authorize
	.requestMatchers(mvc.pattern("/secure/**")).hasRole("USER")
	.requestMatchers(mvc.pattern("/")).permitAll()
	.requestMatchers(mvc.pattern("/js/**")).permitAll()
	.requestMatchers(mvc.pattern("/css/**")).permitAll()
	.requestMatchers(mvc.pattern("/images/**")).permitAll()
	.requestMatchers(mvc.pattern("/permission-denied")).permitAll()
	.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
	.requestMatchers(mvc.pattern("/**")).denyAll()
	)
	.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
	.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
	.formLogin(form -> form.loginPage("/login").permitAll())
	.exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))
	.logout(logout -> logout.permitAll())
	.build();
	}
  
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}

}