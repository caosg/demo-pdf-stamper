package com.example.demopdfstamper;

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelloController {
	@GetMapping(path = "/")
	public String index(Model model) {
		Form form = this.initializeForm();
		model.addAttribute("form", form);
		return "index";
	}

	@PostMapping(path = "hello.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public String hello(@ModelAttribute Form form, Model model) {
		form.setMeta1("請求日");
		form.setMeta1Value(JapaneseDate.now()
				.format(DateTimeFormatter.ofPattern("平成y年M月d日", Locale.JAPANESE)
						.withChronology(JapaneseChronology.INSTANCE)));
		model.addAttribute("form", form);
		return "hello";
	}

	Form initializeForm() {
		Form form = new Form();
		form.setTitle("請 求 書");
		form.setShortDescription("下記の通りご請求申し上げます");
		form.setIssuedBy("987-6543\n" + "中野県豊島市目黒42丁目3-2\n" + "山本 裕介");
		form.setIssuedTo("123-4567\n" + "邪馬台国池袋2丁目1-204\n" + "卑弥呼様");
		form.setNote("お支払い条件: 月末締め翌月末払い\n" + "納品形態: オンライン\n" + "支払い条件: 請求書発行より二週間以内\n"
				+ "お支払方法: 銀行振込\n" + "子供銀行 大人支店 支店コード: 359 普通口座 0234175\n"
				+ "口座名義 ヤマモト ユウスケ\n" + "振込手数料はお客様のご負担とさせていただきます。");
		form.setItems(new ArrayList<Form.Item>() {
			{
				add(new Form.Item() {
					{
						setName("商品1");
						setQuantity(1);
						setUnitPrice(20000);
					}
				});
			}
		});
		return form;
	}
}
