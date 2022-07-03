package ru.komandor.komandors;

public class Cart {

	private final String name;
	private int count;
	private final float coast;


	public Cart(String name, int count, float coast) {
		this.name = name;
		this.count = count;
		this.coast = coast;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getCoast() {
		return coast;
	}

}

