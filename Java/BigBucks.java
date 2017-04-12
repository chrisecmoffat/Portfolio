// Chris Moffat
// Senoir Developer Assignment
// CivicMining LLC. challenge:
// March 2, 2017
//
// Configurable's:
// Thread pool, thread count, block size, time out
//
// Question Answers:
// 1. 	Using Callable and Future allowing the Callable parameterized class to return values at the end of each thread. 
//		Also, palindrome checking could be quicker by comparing bits against each other.
//		Perhaps using an increasing and decreasing loop comparing first and last charAt's while moving inside.
//
// 2. 	Aside from googling a proof as my answer, I would use an alternate algorithm to match bits against each other.
//		Shifting first and last bits (left then right) to the register and comparing each shifted out bit.
//		Once all bits match and throw back a true boolean, this would prove my algo below is correct.
// 

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

class Processor implements Runnable {

	private int id;
	private int startBlock;
	private int blockSize;
	private int endBlock;
	private long startThread;
	private long endThread;
	private static Counter myStats = new Counter(new ReentrantLock());
	
	Lock lock = new ReentrantLock();
	
	public Processor(int id, int blockSize) {
		this.id = id;
		this.blockSize = blockSize;
		this.startBlock = (id * blockSize);
		this.endBlock = ((id * blockSize) + blockSize);
	}
	
	public void run() {
				
		startThread = System.currentTimeMillis();
		
		myStats.taskTotal(id + 1); // accounting for id beginning at 0
		
		for (int i = startBlock; i < endBlock; i++) {	
			
			myStats.highestNumCount(i);
			
			if (myStats.checkStatus())
				break; // break out on time out is triggered
			
			String intStr = Integer.toBinaryString(i);
			
			// palindrome algo, checks binary string odd or even, compares left and right side
			if (intStr.length() % 2 == 0) {
				String frontHalf = intStr.substring(0, (intStr.length() / 2));
				String backHalf = intStr.substring((intStr.length() / 2), (intStr.length()));
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					myStats.addList(Integer.parseInt(intStr, 2));
				} 
			} else {
				String frontHalf = (intStr.substring(0, ((intStr.length() - 1) / 2)));
				String backHalf = (intStr.substring(((intStr.length() + 1) / 2), (intStr.length())));	
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					myStats.addList(Integer.parseInt(intStr, 2));
				} 
			}
		}
		endThread = System.currentTimeMillis();
		myStats.threadTime(endThread - startThread, 1);
	}
}

public class BigBucks {
	
	public static void main(String[] args) throws InterruptedException {
		
//******// *********************************************
//******// performance customization *******************
		final int timeOut = 30;
		final int poolSize = 8;
		final int threads = 8;
		final int blockSize = 10000000;
//******// *********************************************
//******// *********************************************
		
		long startTime;
		long endTime;
		final Counter myStats = new Counter(new ReentrantLock());
		
		startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		
		// spins up # threads, sets block size for each thread
		for (int i = 0; i < threads; i++)
			executor.submit(new Processor(i, blockSize));
		
		executor.shutdown(); // allows no more threads to be created
		
		try {
			if (!executor.awaitTermination(timeOut, TimeUnit.SECONDS))
				myStats.terminateThread(); // trigger break out from thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Collections.sort(myStats.getList()); // sorts array list incrementally
		
		endTime = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("Palindromes:");
		System.out.println("...");
		for (int i : myStats.getList()) 
		    System.out.println(i + " binary: " + Integer.toBinaryString(i));
		System.out.println("...");
		System.out.println("Performance (millis): max: " + myStats.getMax() + ", mean: " + myStats.getMean());
		System.out.println("Palindromes computed: " + myStats.getList().size());
		System.out.println("Highest number: " + myStats.numCount());
		System.out.println("Tasks run: " + myStats.tasks());
		System.out.println("Duration: " + (endTime - startTime) + " millis.");				
	}	
}

class Counter {

	private Lock lock;
    private static long meanTime;
    private static long maxTime; 
    private static int taskNum;
    private static int numCount;
    private static int count;
    private static boolean status = false; 
	private static List<Integer> palindromeList = new ArrayList<Integer>();
    
	protected Counter(Lock myLock) {
        this.lock = myLock;
    }
	
	protected void unlock() {
		lock.unlock();
	}
	
	protected void addList(int match) {
		lock.lock();
        try {
        	palindromeList.add(match);
        } finally {
            lock.unlock();
        }
	}
	
	protected void threadTime(long time, int addValue) {
        lock.lock();
        try {
            if (time > maxTime)
            	maxTime = time;
            count =+ addValue;
            meanTime = meanTime + ((time - meanTime) / count);
        } finally {
            lock.unlock();
        }
    }
	
	protected void taskTotal(int id) {
        lock.lock();
        try {
        	if (id  > taskNum)
        		taskNum = id;
        } finally {
            lock.unlock();
        }
    }
	
	protected void highestNumCount(int num) {
		lock.lock();
        try {
        	if (num > numCount)
        		numCount = num;
        } finally {
            lock.unlock();
        }
	}
	
	protected void terminateThread() {
		status = true;
	}
	
	protected int numCount() {
		return numCount;
	}
	
	protected int tasks() {
		return taskNum;
	}

	protected boolean checkStatus() {
		return status;
	}
	
	protected List<Integer> getList() {
    	return palindromeList;
	}
	
	protected long getMean() {
        return meanTime;
    }
	
	protected long getMax() {
        return maxTime;
    }
}
					myStats.addList(Integer.parseInt(intStr, 2));
				} 
			}
		}
		endThread = System.currentTimeMillis();
		myStats.threadTime(endThread - startThread);
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

public class bigBucks {
	
	public static void main(String[] args) throws InterruptedException {
		
		long startTime;
		long endTime;
		final int timeOut = 10;
		final int poolSize = 8;
		final int blockSize = 1000;
		final Counter myStats = new Counter(new ReentrantLock());
		
		startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		
		// Spins up 8 threads
		for (int i = 0; i < 8; i++)
			executor.submit(new Processor(i, blockSize));
		
		executor.shutdown();
		
		try {
			if (!executor.awaitTermination(timeOut, TimeUnit.SECONDS))
				myStats.terminateThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Collections.sort(myStats.getList());
		
		endTime = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("Palindromes:");
		System.out.println("...");
		for (int i : myStats.getList()) 
		    System.out.println(i + " binary: " + Integer.toBinaryString(i));
		System.out.println("...");
		System.out.println("Performance (millis): max: " + myStats.getMax() + " mean: " + myStats.getMean());
		System.out.println("Palindromes computed: " + myStats.getList().size());
		System.out.println("Tasks run: ");
		System.out.println("Duration: " + ((endTime - startTime)));				
	}	
}

class Counter {

	private Lock lock;
    private static long meanTime;
    private static long maxTime; 
    private static boolean status = false; 
	private static List<Integer> palindromeList = new ArrayList<Integer>();
    
	protected Counter(Lock myLock) {
        this.lock = myLock;
    }
	
	protected void unlock() {
		lock.unlock();
	}
	
	protected void addList(int match) {
		lock.lock();
        try {
        	palindromeList.add(match);
        } finally {
            lock.unlock();
        }
	}
	
	protected void threadTime(long time) {
        lock.lock();
        try {
            if (time > maxTime)
            	maxTime = time;
            if (meanTime == 0)
            	meanTime = time;
            else
            	meanTime = (meanTime + time) / 2;
        } finally {
            lock.unlock();
        }
    }

	protected void terminateThread() {
		status = true;
	}
	
	protected boolean checkStatus() {
		return status;
	}
	
	protected List<Integer> getList() {
    	return palindromeList;
	}
	
	protected long getMean() {
        return meanTime;
    }
	
	protected long getMax() {
        return maxTime;
    }
}
