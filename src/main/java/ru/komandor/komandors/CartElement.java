package ru.komandor.komandors;

public class CartElement {

	private String name;
	private float coast;

	public CartElement(String name, float coast) {
		this.name = name;
		this.coast = coast;
	}

	public CartElement() {

	}

	public String getName() {
		return name;
	}

	public Float getCoast() {
		return coast;
	}
}

