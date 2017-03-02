import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Processor implements Runnable {

	public int id;
	public int blockSize;
	public int startBlock;
	public int endBlock;
	public long startThread;
	public long endThread;
	static Counter myCounter = new Counter(new ReentrantLock());
	
	Lock lock = new ReentrantLock();
	
	public Processor(int id, int blockSize) {
		this.id = id;
		this.blockSize = blockSize;
		this.startBlock = (id * blockSize);
		this.endBlock = ((id * blockSize) + blockSize);
	}
	
	public void run() {
				
		startThread = System.currentTimeMillis();
		
		for (int i = startBlock; i < endBlock; i++) {	
			
			if (myCounter.checkStatus())
				break;
			
			String intStr = Integer.toBinaryString(i);
			
			// Palindrome algo, checks binary string odd or even
			// Compares left and right side
			if (intStr.length() % 2 == 0) {
				String frontHalf = intStr.substring(0, (intStr.length() / 2));
				String backHalf = intStr.substring((intStr.length() / 2), (intStr.length()));
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					myCounter.addCount();
					myCounter.addList(Integer.parseInt(intStr, 2));
				} 
			} else {
				String frontHalf = (intStr.substring(0, ((intStr.length() - 1) / 2)));
				String backHalf = (intStr.substring(((intStr.length() + 1) / 2), (intStr.length())));	
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					myCounter.addCount();
					myCounter.addList(Integer.parseInt(intStr, 2));
				} 
			}
		}
		endThread = System.currentTimeMillis();
		myCounter.threadTime(endThread - startThread);
	}
}


public class bigBucks {
	
	public static void main(String[] args) throws InterruptedException {
		
		long startTime;
		long endTime;
		final int blockSize = 100000000;
		final int poolSize = 8;
		final int timeOut = 10;
		final Counter myCounter = new Counter(new ReentrantLock());
		
		startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		
		// Spins up 8 threads
		for (int i = 0; i < poolSize; i++)
			executor.submit(new Processor(i, blockSize));
		
		executor.shutdown();
		
		try {
			if (!executor.awaitTermination(timeOut, TimeUnit.SECONDS))
				myCounter.terminateThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Collections.sort(myCounter.getList());
		
		endTime = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("Palindromes:");
		System.out.println("...");
		for (int e : myCounter.getList()) 
		    System.out.println(e + " binary: " + Integer.toBinaryString(e));
		System.out.println("...");
		System.out.println("Performance (millis): max: " + myCounter.getMax() + " mean: " + myCounter.getMean());
		System.out.println("Palindromes computed: " + myCounter.listCount());
		System.out.println("Tasks run: ");
		System.out.println("Duration: " + ((endTime - startTime)));				
	}	
}

class Counter {

    public Lock lock;
    public static int count;
    public static long meanTime;
    public static long maxTime; 
    public static boolean status = false; 
	public static List<Integer> palindromeList = new ArrayList<Integer>();
    
    public Counter(Lock myLock) {
        this.lock = myLock;
    }

	public boolean checkStatus() {
        lock.lock();
        try {
    		return status;
        } finally {
            lock.unlock();
        }
	}
	
	public void terminateThread() {
        lock.lock();
        try {
    		status = true;
        } finally {
            lock.unlock();
        }
	}

	public void unlock() {
		lock.unlock();
	}

	public final int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
	
	public final void addList(int match) {
		lock.lock();
        try {
        	palindromeList.add(match);
        } finally {
            lock.unlock();
        }
	}
	
	public final List<Integer> getList() {
		lock.lock();
        try {
        	return palindromeList;
        } finally {
            lock.unlock();
        }
	}
	
	public final int listCount() {
		return palindromeList.size();
	}
	
	
	public final void addCount() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
	
	public final void threadTime(long time) {
        lock.lock();
        try {
            if (time > maxTime)
            	maxTime = time;
            if (meanTime == 0)
            	meanTime = time;
            meanTime = (meanTime + time) / 2;
        } finally {
            lock.unlock();
        }
    }
	
	public final long getMean() {
        lock.lock();
        try {
            return meanTime;
        } finally {
            lock.unlock();
        }
    }
	
	public final long getMax() {
        lock.lock();
        try {
            return maxTime;
        } finally {
            lock.unlock();
        }
    }
}
