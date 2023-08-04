package nestorcicardini.skilltrade.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JWTAuthFilter jwtAuthFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {

		http.csrf(c -> c.disable());
		http.cors(Customizer.withDefaults()); // by default uses a Bean by the
												// name of
												// corsConfigurationSource

		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/auth/register").permitAll());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/auth/login").permitAll());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/users").authenticated());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/users/{userId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/profiles").authenticated());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/profiles/{profileId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/posts").authenticated());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/posts/{postId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/reviews").authenticated());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/reviews/{reviewId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/replies").authenticated());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/replies/{replyId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/interests").permitAll());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/interests/{interestId}").authenticated());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/langs").permitAll());
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/reviews/stars/{profileId}").authenticated());

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

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
				Arrays.asList("https://skilltrade.vercel.app"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
