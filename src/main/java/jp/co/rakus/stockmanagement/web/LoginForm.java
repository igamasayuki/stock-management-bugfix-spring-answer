package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.NotNull;

/**
 * ログイン関連のリクエストパラメータが入るフォーム.
 * @author igamasayuki
 *
 */
public class LoginForm {
	/** メールアドレス */
	@NotNull(message = "値を入力してください")
	private String mailAddress;
	/** パスワード */
	@NotNull(message = "値を入力してください")
	private String password;
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
