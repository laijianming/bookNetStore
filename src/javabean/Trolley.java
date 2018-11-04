package javabean;

public class Trolley {
	private String userId; // 用户id
	private String id; // 商品id
	private int num = 1; // 商品数量
	private String imgPath; // 图片路径
	private double price; // 商品单价
	private String name; // 书名
	private double totalPrice; // 商品总价钱

	public Trolley() {
		super();
	}

	public Trolley(int num, double price) { // 计算商品数和总价钱
		super();
		this.num = num;
		this.totalPrice = price * num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		this.totalPrice = price * num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
