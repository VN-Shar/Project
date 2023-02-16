package project;

import engine.Window;

public class Main {

	public static void main(String[] args) {
		Window window = Window.get();
		window.run();
	}

	public static void printStackTrace() {
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			System.out.println(ste);
		}
	}

}
