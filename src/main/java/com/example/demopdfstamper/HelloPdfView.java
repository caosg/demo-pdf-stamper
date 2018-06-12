package com.example.demopdfstamper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfStamper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfStamperView;

@Component("hello")
public class HelloPdfView extends AbstractPdfStamperView {
	private final NumberFormat JPY = NumberFormat.getCurrencyInstance(Locale.JAPAN);

	@Override
	protected void mergePdfDocument(Map<String, Object> model, PdfStamper stamper,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Form form = (Form) model.get("form");
		AcroFields fields = stamper.getAcroFields();
		fields.setField("title", form.getTitle());
		fields.setField("issued-to", form.getIssuedTo());
		fields.setField("issued-by", form.getIssuedTo());
		fields.setField("short-description", form.getShortDescription());
		fields.setField("meta1", form.getMeta1());
		fields.setField("meta2", form.getMeta2());
		fields.setField("meta3", form.getMeta3());
		fields.setField("meta1-value", form.getMeta1Value());
		fields.setField("meta2-value", form.getMeta2Value());
		fields.setField("meta3-value", form.getMeta3Value());
		List<Form.Item> items = form.getItems();
		for (int i = 0; i < items.size(); i++) {
			Form.Item item = items.get(i);
			fields.setField("item" + (i + 1), item.getName());
			fields.setField("quantity" + (i + 1), String.valueOf(item.getQuantity()));
			fields.setField("price" + (i + 1), JPY.format(item.getUnitPrice()));
			fields.setField("unitPrice" + (i + 1), JPY.format(item.price()));
		}
		Form.TotalPrice totalPrice = form.totalPrice();
		fields.setField("tax", JPY.format(totalPrice.tax()));
		fields.setField("total-price", JPY.format(totalPrice.taxIncluded()));
		fields.setField("total-price-big", JPY.format(totalPrice.taxIncluded()));
		fields.setField("note", form.getNote());

		// freeze pdf
		stamper.setFormFlattening(true);
		stamper.setFreeTextFlattening(true);
	}

	@Override
	public String getUrl() {
		return "classpath:pdf/form.pdf";
	}
}
