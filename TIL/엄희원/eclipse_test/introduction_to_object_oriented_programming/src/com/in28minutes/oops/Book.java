package com.in28minutes.oops;

public class Book {
	private int noOfCopies;

	Book(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	void setNoOfCopies(int noOfCopies) {
		if (noOfCopies > 0) {
			this.noOfCopies = noOfCopies;
		}
	}

	int getNoOfCopies() {
		return noOfCopies;
	}

	public void increaseCopies(int howMuch) {
		this.noOfCopies += howMuch;
	}

	public void decreaseCopies(int howMuch) {
		setNoOfCopies(this.noOfCopies - howMuch);
	}

}
