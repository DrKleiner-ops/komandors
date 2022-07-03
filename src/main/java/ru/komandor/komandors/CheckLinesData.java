package ru.komandor.komandors;

public class CheckLinesData {

	private final int goodsId;
	private final int countLine;
	private final int count;
	private final float sum;

	public CheckLinesData(int goodsId, int countLine, int count, float sum) {
		this.goodsId = goodsId;
		this.countLine = countLine;
		this.count = count;
		this.sum = sum;
	}


	public int getGoodsId() {
		return goodsId;
	}

	public int getCountLine() {
		return countLine;
	}

	public int getCount() {
		return count;
	}

	public float getSum() {
		return sum;
	}

}
