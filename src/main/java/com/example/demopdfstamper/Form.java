package com.example.demopdfstamper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Form implements Serializable {
	private String title;
	private String issuedTo;
	private String meta1;
	private String meta2;
	private String meta3;
	private String meta1Value;
	private String meta2Value;
	private String meta3Value;
	private String issuedBy;
	private String shortDescription;
	private String note;
	private List<Item> items;

	private static final BigDecimal TAX_RATE = new BigDecimal("0.08");

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getMeta1() {
		return meta1;
	}

	public void setMeta1(String meta1) {
		this.meta1 = meta1;
	}

	public String getMeta2() {
		return meta2;
	}

	public void setMeta2(String meta2) {
		this.meta2 = meta2;
	}

	public String getMeta3() {
		return meta3;
	}

	public void setMeta3(String meta3) {
		this.meta3 = meta3;
	}

	public String getMeta1Value() {
		return meta1Value;
	}

	public void setMeta1Value(String meta1Value) {
		this.meta1Value = meta1Value;
	}

	public String getMeta2Value() {
		return meta2Value;
	}

	public void setMeta2Value(String meta2Value) {
		this.meta2Value = meta2Value;
	}

	public String getMeta3Value() {
		return meta3Value;
	}

	public void setMeta3Value(String meta3Value) {
		this.meta3Value = meta3Value;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public TotalPrice totalPrice() {
		if (this.items == null) {
			return new TotalPrice(0, 0);
		}
		long value = this.items.stream().mapToLong(Item::price).sum();
		long tax = TAX_RATE.multiply(BigDecimal.valueOf(value))
				.setScale(0, RoundingMode.FLOOR).intValue();
		return new TotalPrice(value, tax);
	}

	@Override
	public String toString() {
		return "Form{" + "title='" + title + '\'' + ", issuedTo='" + issuedTo + '\''
				+ ", meta1='" + meta1 + '\'' + ", meta2='" + meta2 + '\'' + ", meta3='"
				+ meta3 + '\'' + ", meta1Value='" + meta1Value + '\'' + ", meta2Value='"
				+ meta2Value + '\'' + ", meta3Value='" + meta3Value + '\''
				+ ", issuedBy='" + issuedBy + '\'' + ", shortDescription='"
				+ shortDescription + '\'' + ", note='" + note + '\'' + ", items=" + items
				+ '}';
	}

	public static class Item implements Serializable {
		private String name;
		private int quantity;
		private int unitPrice;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public int getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(int unitPrice) {
			this.unitPrice = unitPrice;
		}

		public int price() {
			return this.unitPrice * this.quantity;
		}

		@Override
		public String toString() {
			return "Item{" + "name='" + name + '\'' + ", quantity=" + quantity
					+ ", unitPrice=" + unitPrice + '}';
		}
	}

	public static class TotalPrice {
		private final long value;
		private final long tax;

		public TotalPrice(long value, long tax) {
			this.value = value;
			this.tax = tax;
		}

		public long value() {
			return this.value;
		}

		public long tax() {
			return this.tax;
		}

		public long taxIncluded() {
			return this.value + this.tax;
		}
	}
}
