import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import dataAnalysis.DataGenerator;
import dataAnalysis.OperationType;

public class AnalysisDataStructure implements OperationType{
	private int times;
	private DataGenerator dg;
	private List<String> data;
	LinkedListMultiset<String> linkedListMultiset=null;
	SortedLinkedListMultiset<String> sortedLinkedListMultiset=null;
	BstMultiset<String> bstMultiset=null;
	HashMultiset<String> hashMultiset=null;
	BalTreeMultiset<String> balTreeMultiset=null;
	public AnalysisDataStructure(int testSize) {
		times=testSize;
		dg=new DataGenerator(testSize);
		data=dg.getData();
		//init();
	}
	
	/*
	 * Initialize each data structure
	 */
	public void init() {
		linkedListMultiset = new LinkedListMultiset<String>();
		sortedLinkedListMultiset = new SortedLinkedListMultiset<String>();
		bstMultiset = new BstMultiset<String>();
		hashMultiset = new HashMultiset<String>();
		balTreeMultiset = new BalTreeMultiset<String>();
	}
	
	
	/*
	 * To operate 4 operations (add, removeall, removeone, search) for each data structure.
	 */
	private void operation(Multiset<String> multiset,List<String> data, Sign sign) {
		if(sign.equals(Sign.ADD)) {
			for(String element: data) {
				multiset.add(element);
			}
		}else if(sign.equals(Sign.REMOVEALL)) {
			for(String element: data) {
				multiset.removeAll(element);
			}
		}else if(sign.equals(Sign.REMOVEONE)) {
			for(String element: data) {
				multiset.removeOne(element);
			}
		}else if(sign.equals(Sign.SEARCH)) {
			for(String element: data) {
				multiset.search(element);
			}
		}
	}
	
	
	/*
	 * To generate test data stored into list.
	 * Actually, it is to catch data from existed list in data generator and then store them into list.
	 * These test data is parpare for operating add, removeall, removeone and search
	 */
	private List<String> generateTestData(int size) {
		Random r=new Random();
		List<String> tem=new ArrayList<String>();
		for(int i=0;i<size;i++) {
			tem.add(data.get(r.nextInt(data.size())));
		}
		return tem;
	}
	
	
	/*
	 * To calculate operation times and real running time for each data structure in different test scenario
	 * System.currentTimeMillis() is to record real running time and 
	 * System.nanoTime() is to record operation times.
	 */
	private void caculateRunTime(Multiset<String> multiset,List<String> data,List<String> data1, Sign sign1, Sign sign2) {
		long recordStartTime = System.currentTimeMillis();
		long startTimes = System.nanoTime();
		operation(linkedListMultiset,data, sign1);
		operation(linkedListMultiset,data1, sign2);
		long endTimes = System.nanoTime();
		long recordEndTime = System.currentTimeMillis();
		System.out.println("Running count: " + (int)(endTimes - startTimes)+" Run Time: "+(recordEndTime-recordStartTime));
	}
	
	/*
	 * To calculate operation times and real running time for each data structure in different test scenario
	 * System.currentTimeMillis() is to record real running time and 
	 * System.nanoTime() is to record operation times.
	 */
	private void caculateRunTime(Multiset<String> multiset,List<String> searchList,List<String> addList,List<String> removeAllList,List<String> removeOneList, Sign search, Sign add, Sign removeall,Sign removeOne) {
		long recordStartTime = System.currentTimeMillis();
		long startTimes = System.nanoTime();
		operation(linkedListMultiset,searchList, search);
		operation(linkedListMultiset,addList, add);
		operation(linkedListMultiset,removeAllList, removeall);
		operation(linkedListMultiset,removeOneList, removeOne);
		long endTimes = System.nanoTime();
		long recordEndTime = System.currentTimeMillis();
		System.out.println("Running count: " + (int)(endTimes - startTimes)+" Run Time: "+(recordEndTime-recordStartTime));
	}
	
	
	/*
	 * scenario one: add and removeone
	 * put some data into data structures to ensure there is no null.
	 * to generate add test data and removeone test data from existed list to ensure the data has been existed before.
	 * According to increase add times and decrease removeone times to compare the running time and operation times
	 * In addition, add times is from 0 to largest and removeone from largest to 0.
	 */
	public void testAddRemoveOne(int ratio) {
		if (ratio < 0 || ratio >10) {
			throw new IllegalArgumentException("Ratio is invalid.");
		}
		init();
		
		System.out.println("**************************************************************************");
		System.out.println("********Test 'add' and 'removeone' function for each data structure*******");
		//Every time, the ratio is changed
		int addTimes = (int)(times*((double)(ratio)/10));
		int removalTimes = times-addTimes;
		System.out.println("Total running times: " + times);
		System.out.println("Addtion running times: " + addTimes);
		System.out.println("Removal running times: " + removalTimes);
		
		operation(linkedListMultiset,data, Sign.ADD);
		operation(sortedLinkedListMultiset,data, Sign.ADD);
		operation(bstMultiset,data, Sign.ADD);
		operation(hashMultiset,data, Sign.ADD);
		operation(balTreeMultiset,data, Sign.ADD);
		
		//To generate add test data and removeone test data
		List<String> addData=generateTestData(addTimes);
		List<String> deleteData=generateTestData(removalTimes);
		//Start test
		System.out.println("--------------------------------------------------------------------------");
		System.out.print("bstMultiset: ");
		caculateRunTime(bstMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEONE);
		System.out.print("sortedLinkedListMultiset: ");
		caculateRunTime(sortedLinkedListMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEONE);
		System.out.print("LinkedListMultiset: ");
		caculateRunTime(linkedListMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEONE);
		System.out.print("hashMultiset: ");
		caculateRunTime(hashMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEONE);
		System.out.print("balTreeMultiset: ");
		caculateRunTime(balTreeMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEONE);
		System.out.println("--------------------------------------------------------------------------");
	}
	
	
	/*
	 * scenario one: add and removeall
	 * put some data into data structures to ensure there is no null.
	 * to generate add test data and removeall test data from existed list to ensure the data has been existed before.
	 * According to increase add times and decrease removeall times to compare the running time and operation times
	 * In addition, add times is from 0 to largest and removeall from largest to 0.
	 */
	public void testAddRemoveALL(int ratio) {
		if (ratio < 0 || ratio >10) {
			throw new IllegalArgumentException("Ratio is invalid.");
		}
		init();
		
		System.out.println("**************************************************************************");
		System.out.println("********Test 'add' and 'removeall' function for each data structure*******");
		//Every time, the ratio is changed
		int addTimes = (int)(times*((double)(ratio)/10));
		int removalTimes = times-addTimes;
		System.out.println("Total running times: " + times);
		System.out.println("Addtion running times: " + addTimes);
		System.out.println("Removal running times: " + removalTimes);
		
		
		//Add data firstly to ensure there is no null
		operation(linkedListMultiset,data, Sign.ADD);
		operation(sortedLinkedListMultiset,data, Sign.ADD);
		operation(bstMultiset,data, Sign.ADD);
		operation(hashMultiset,data, Sign.ADD);
		operation(balTreeMultiset,data, Sign.ADD);
		
		//To generate add test data and removeall test data
		List<String> addData=generateTestData(addTimes);
		List<String> deleteData=generateTestData(removalTimes);
		//Start test
		System.out.println("--------------------------------------------------------------------------");
		System.out.print("bstMultiset: ");
		caculateRunTime(bstMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEALL);
		System.out.print("sortedLinkedListMultiset: ");
		caculateRunTime(sortedLinkedListMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEALL);	
		System.out.print("LinkedListMultiset: ");
		caculateRunTime(linkedListMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEALL);
		System.out.print("hashMultiset: ");
		caculateRunTime(hashMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEALL);
		System.out.print("balTreeMultiset: ");
		caculateRunTime(balTreeMultiset,addData,deleteData, Sign.ADD,Sign.REMOVEALL);
		System.out.println("--------------------------------------------------------------------------");
	}
	
	/*
	 * scenario two: search and add, removeone, removeall
	 * put some data into data structures to ensure there is no null.
	 * to generate search test data based on the size and total test data size for add,removeone, removeall from existed list to ensure the data has been existed before.
	 * The add test size+removeone test data+removeall test data=total test data size -search data size.
	 * And add size, removeone size and removeall size is random but the total is fixed.
	 * According to increase search size and decrease other total size to compare the running time and operation times.
	 */
	public void testSearchAddRemoveAll(int ratio) {
		if (ratio < 0 || ratio >10) {
			throw new IllegalArgumentException("Ratio is invalid.");
		}
		init();
		
		System.out.println("**************************************************************************");
		System.out.println("********Test 'search' and 'add&remove' function for each data structure*******");
		//Ratio should be changed every time.
		int totalAddAndRemoveTimes = (int)(times*((double)(ratio)/10));
		int searchTimes = times-totalAddAndRemoveTimes;
		System.out.println("Total running times: " + times);
		System.out.println("Search running times: " + searchTimes);
		System.out.println("Total Addtion &Removal running times: " + totalAddAndRemoveTimes);
		
		//Add some data firstly to ensure there is no null in data structure.
		operation(linkedListMultiset,data, Sign.ADD);
		operation(sortedLinkedListMultiset,data, Sign.ADD);
		operation(bstMultiset,data, Sign.ADD);
		operation(hashMultiset,data, Sign.ADD);
		operation(balTreeMultiset,data, Sign.ADD);
		
		List<String> searchData=generateTestData(searchTimes);
		List<String> addData=new ArrayList<String>();
		List<String> removeOneData=new ArrayList<String>();
		List<String> removeAllData=new ArrayList<String>();
		Random r=new Random();
		if(totalAddAndRemoveTimes!=0)  {
			int addTimes=r.nextInt(totalAddAndRemoveTimes);
			int totalRemoveOneAndAllTimes=totalAddAndRemoveTimes-addTimes;
			int removeOneTimes=r.nextInt(totalRemoveOneAndAllTimes);
			int removeAllTimes=totalRemoveOneAndAllTimes-removeOneTimes;
			addData=generateTestData(addTimes);
			removeOneData=generateTestData(removeOneTimes);
			removeAllData=generateTestData(removeAllTimes);
		}
		
		//Start test
		System.out.println("--------------------------------------------------------------------------");
		System.out.print("bstMultiset: ");
		caculateRunTime(bstMultiset,searchData,addData,removeAllData,removeOneData,Sign.SEARCH,Sign.ADD,Sign.REMOVEALL, Sign.REMOVEONE);
		System.out.print("sortedLinkedListMultiset: ");
		caculateRunTime(sortedLinkedListMultiset,searchData,addData,removeAllData,removeOneData,Sign.SEARCH,Sign.ADD,Sign.REMOVEALL, Sign.REMOVEONE);
		System.out.print("LinkedListMultiset: ");
		caculateRunTime(linkedListMultiset,searchData,addData,removeAllData,removeOneData,Sign.SEARCH,Sign.ADD,Sign.REMOVEALL, Sign.REMOVEONE);
		System.out.print("hashMultiset: ");
		caculateRunTime(hashMultiset,searchData,addData,removeAllData,removeOneData,Sign.SEARCH,Sign.ADD,Sign.REMOVEALL, Sign.REMOVEONE);
		System.out.print("balTreeMultiset: ");
		caculateRunTime(balTreeMultiset,searchData,addData,removeAllData,removeOneData,Sign.SEARCH,Sign.ADD,Sign.REMOVEALL, Sign.REMOVEONE);
		System.out.println("--------------------------------------------------------------------------");
		
	}
	
	
	
	
	public static void main(String[] args) {
		int size=20000;
		AnalysisDataStructure ads;
		//Add and removeall
		for(int ratio = 0 ; ratio < 10; ratio++){
			ads=new AnalysisDataStructure(size);
			ads.testAddRemoveALL(ratio);
		}
		
		//Add and removeone
		for(int ratio = 0 ; ratio < 10; ratio++){
			ads=new AnalysisDataStructure(size);
			ads.testAddRemoveOne(ratio);
		}
		
		//Search and add,removeone,removeall
		for(int ratio = 0 ; ratio < 5; ratio++){
			ads=new AnalysisDataStructure(size);
			ads.testSearchAddRemoveAll(ratio);
		}

	}
	
	
}
