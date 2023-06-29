package nestorcicardini.skilltrade.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	JWTAuthFilter jwtAuthFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.cors(c -> c.disable());
		http.csrf(c -> c.disable());

		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/auth/register").permitAll());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/auth/login").permitAll());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/users").hasAuthority("ADMIN"));

		http.addFilterBefore(jwtAuthFilter,
				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.exceptionHandling(exceptionHandling -> exceptionHandling
				.accessDeniedHandler(new NotAdminAccessDeniedHandler()));

		return http.build();
	}

	@Bean
	PasswordEncoder pwEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
