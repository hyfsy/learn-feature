package com.hyf.feature.actuator.security;// package com.hyf.fsfts.toy.actuator.security;
//
// import org.springframework.boot.actuate.context.ShutdownEndpoint;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
// /**
//  * @author baB_hyf
//  * @date 2020/10/21
//  */
// public class ActuatorAuthenticationConfig extends WebSecurityConfigurerAdapter {
//
// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 		// http.authorizeRequests()
// 		// 	.requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
// 		// 	.hasRole("ACTUATOR_ADMIN")
// 		// 	.requestMatchers(EndpointRequest.toAnyEndpoint())
// 		// 	.permitAll()
// 		// 	// .requestMatchers(PathRequest.toStaticResources()
// 		// 	// .atCommonLocations())
// 		// 	// .permitAll()
// 		// 	.antMatchers("/")
// 		// 	.permitAll()
// 		// 	.antMatchers("/**")
// 		// 	.authenticated()
// 		// 	.and()
// 		// 	.httpBasic();
// 	}
// }
