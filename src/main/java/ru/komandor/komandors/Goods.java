package ru.komandor.komandors;


public class Goods {


	private final int id;
	private final String name;
	private final Float coast;


	public Goods(int id, String name, float coast) {
		this.id = id;
		this.name = name;
		this.coast = coast;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getCoast() {
		return coast;
	}

}


