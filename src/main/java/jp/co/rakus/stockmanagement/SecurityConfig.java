package jp.co.rakus.stockmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ログイン認証用設定.
 * 
 * @author igamasayuki
 *
 */
@Configuration // 設定用のクラス
@EnableWebMvcSecurity // Spring Securityのウェブ用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService memberDetailsService;

	/**
	 * このメソッドをオーバーライドすることで、
	 * 特定のリクエストに対して「セキュリティ設定」を
	 * 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers( "/css/**"
						, "/img/**"
						, "/js/**"
						, "/fonts/**");
	}

	/**
	 * このメソッドをオーバーライドすることで、認可の設定や
	 * ログインアウトに関する設定ができる.
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 認可に関する設定。ここではログインフォームを表示する「/」などには
		// 任意のユーザがアクセスできるようにする。それ以外のパスには認証なしでアクセス
		// できないようにする。
		http.authorizeRequests()
			.antMatchers("/"							// ログイン画面表示用パス
						,"/member/form"				// メンバー登録画面表示用パス
						,"/member/create")			// メンバー登録処理実施用パス
			.permitAll()								// 上記のパスは全てアクセスを許可する
			.anyRequest()							// それ以外のパスは、、
			.authenticated();						// 認証が必要

//		// /adminから始まるURLはADMINロールがあるユーザのみアクセス可能
//		http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN");

		http.formLogin()
			.loginPage			("/")				// ログイン画面表示用パス
			.loginProcessingUrl	("/login")			// ログイン処理実施用パス
			.failureUrl			("/?error")			// ログイン認証エラー時のパス
			.defaultSuccessUrl	("/book/list", true)	// ログイン認証成功時のパス
			.usernameParameter	("mailAddress")		// ログイン認証に使うユーザ名のリクエストパラメータ名
			.passwordParameter	("password")			// ログイン認証に使うパスワードのリクエストパラメータ名
			;
		
		// 「ログアウト」に関する設定
		// 「AntPathRequestMatcher」クラスを使わず、「文字列のパス」を指定した場合は、
		// 「ログアウト」するために「POST」でアクセスする必要がある。
		http.logout()
			.logoutRequestMatcher(
					new AntPathRequestMatcher("/logout**"))	// ログアウト用パス
			.logoutSuccessUrl("/");							// ログアウト後の画面遷移のパス
		
		// Exceptionハンドラ
		// http.exceptionHandling()
		// .accessDeniedPage("/403");
	}

	/**
	 * 「認証」に関する設定.<br>
	 * 認証ユーザを取得する「LoginAdminUserService」の設定や<br>
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailsService)
			.passwordEncoder(new StandardPasswordEncoder());
	}

    /**
     * <pre>
     * SHA-256アルゴリズムで暗号化する実装を返します.
     * これを指定することでパスワード暗号化やマッチ確認する際に
     * @Autowired
	 * private PasswordEncoder passwordEncoder;
	 * と記載するとDIされるようになります。
     * </pre>
     * @return SHA-256アルゴリズムで暗号化する実装オブジェクト
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
    		return new StandardPasswordEncoder();
    }
}
