package dataAnalysis;

import java.util.ArrayList;

public class DataGenerator {
	
	private ArrayList<String> data=null; //Store all random data
	private int dataSize; //Record the data size generated.
	public DataGenerator(int dataSize) {
		data=new ArrayList<String>();
		this.dataSize=dataSize;
		storeData();
	}
	
	/*
	 * To store each random data into list
	 */
	private void storeData() {
		for(int i=0;i<dataSize;i++) {
			data.add(generateRandomData());
		}
	}
	
	/*
	 * To generate one random data
	 */
	private String generateRandomData() {
		String temp = "";
		char[] chars = {'a','b','c','d','e','f','g','h','x',
				'y','z','A','B','C','D','E','F','G','H',
				'X','Y','Z'};
		// Generate a random word length(1-6)
		int wordLength = (int)(Math.random()*5)+1;
		for(int i = 0 ; i < wordLength ; i ++){
			//Put a random char for this word 
			temp += chars[(int)(Math.random()*(chars.length-1))];
		}
		return temp;
	}
	
	/**
	 * @return the data
	 */
	public ArrayList<String> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<String> data) {
		this.data = data;
	}
}
