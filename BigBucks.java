
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
		this.setId(id);
		this.setBlockSize(blockSize);
		this.startBlock = (id * blockSize);
		this.endBlock = ((id * blockSize) + blockSize);
	}
	
	public void run() {
				
		startThread = System.currentTimeMillis();
		
		for (int i = startBlock; i < endBlock; i++) {	
			
			if (myStats.checkStatus())
				break;
			
			String intStr = Integer.toBinaryString(i);
			
			// Palindrome algo, checks binary string odd or even, compares left and right side
			if (intStr.length() % 2 == 0) {
				String frontHalf = intStr.substring(0, (intStr.length() / 2));
				String backHalf = intStr.substring((intStr.length() / 2), (intStr.length()));
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					//myStats.addCount();
					myStats.addList(Integer.parseInt(intStr, 2));
				} 
			} else {
				String frontHalf = (intStr.substring(0, ((intStr.length() - 1) / 2)));
				String backHalf = (intStr.substring(((intStr.length() + 1) / 2), (intStr.length())));	
				
				if ((frontHalf).equals(new StringBuilder(backHalf).reverse().toString())) {
					//myStats.addCount();
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
