package beans;

import java.sql.Date;

public class Coupon {
	
	protected int id;
	protected int companyId;
	protected Category category;
	protected int categoryId;
	protected String tittle;
	protected String description;
	protected Date startDate;
	protected Date endDate;
	protected int amount;
	protected double price;
	protected String image;
	
	public Coupon(int companyId, Category category, int categoryId, String tittle, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.companyId = companyId;
		this.category = category;
		this.tittle = tittle;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Coupon() {
		super();
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + companyId + ", category=" + categoryId + ", tittle=" + tittle
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coupon))
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
