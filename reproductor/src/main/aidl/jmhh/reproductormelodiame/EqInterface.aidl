package jmhh.reproductormelodiame;

interface EqInterface {
	int getBandLevelLow();
	int getBandLevelHigh();
	int getNumberOfBands();
	int getCenterFreq(int band);
	int getBandLevel(int band);
	void setBandLevel(int band, int level);
	boolean isRunning();
}