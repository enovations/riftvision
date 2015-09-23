package com.sourcegasm.riftvision.game;

public class LapTimer {

	public float timer = 0;
	public boolean time = false;
	public boolean show = false;

	public LapTimer() {
		final Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {

					while (!time)
						try {
							Thread.sleep(10);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}

					timer += 0.01f;

					try {
						Thread.sleep(10);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		thread.start();
	}

	public void startTimer() {
		timer = 0;
		time = true;
		show = true;
	}

	public void stopTimer() {
		if (!time)
			show = false;
		time = false;
	}

}
