package com.in28minutes.oops;

public class BookRunner {

	public static void main(String[] args) {
		Book artOfComputerProgramming = new Book(1000);
		Book effectiveJava = new Book(1000);
		Book cleanCode = new Book(1000);

//		artOfComputerProgramming.setNoOfCopies(100);
//		effectiveJava.setNoOfCopies(120);
//		cleanCode.setNoOfCopies(150);

		artOfComputerProgramming.increaseCopies(100);
		effectiveJava.increaseCopies(100);
		cleanCode.increaseCopies(100);

		artOfComputerProgramming.decreaseCopies(500);
		effectiveJava.decreaseCopies(500);
		cleanCode.decreaseCopies(500);

		System.out.println(artOfComputerProgramming.getNoOfCopies());
		System.out.println(effectiveJava.getNoOfCopies());
		System.out.println(cleanCode.getNoOfCopies());

	}

}
