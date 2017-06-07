package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 書籍情報入力フォーム.
 * 
 * @author mikiyo.kitaoka
 *
 */
@Data
public class RegistBookForm {
	/** 書籍名 */
	@NotEmpty(message = "値を入力してください")
	private String name;
	/** 著者 */
	@NotEmpty(message = "値を入力してください")
	private String author;
	/** 出版社 */
	@NotEmpty(message = "値を入力してください")
	private String publisher;
	/** 価格 */
	@NotNull(message = "値を入力してください")
	private Integer price;
	/** ISBNコード */
	@NotEmpty(message = "値を入力してください")
	@Pattern(message = "ISBNコードの入力形式が不正です", regexp = "[A-Za-z0-9]-[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]-[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]-[A-Za-z0-9]")
	private String isbncode;
	/** 発売日 */
	@NotEmpty(message = "値を入力してください")
	@Pattern(message ="発売日の入力形式が不正です", regexp = "[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]")
	private String saledate;
	/** 説明 */
	@NotEmpty(message = "値を入力してください")
	private String explanation;
	/** 画像 */
	private MultipartFile imageFile;
	/** 在庫数 */
	@NotNull(message = "値を入力してください")
	private Integer stock;
}
