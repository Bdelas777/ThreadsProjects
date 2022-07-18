package UsoThreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadBank {
	public static void main(String[] args) {
		
		Bank b = new Bank ();
		
		for(int i = 0; i < 100; i ++) {
			
			TransferEjecution transfer = new TransferEjecution(b,i,2000);
			
			Thread t = new Thread(transfer);
			
			t.start();
			
		}
	}
}

class Bank{
	public Bank(){
		
		accounts = new double[100];
		
		for (int index = 0; index < accounts.length; index++) {
			
			accounts[index] = 2000;
			
		}
		
		//EnoughBalance = closedbank.newCondition(); Not used synchronized
	}
	
	public synchronized void moneyTransfer(int originAccount, int destinyAccount, double money) throws InterruptedException {
		
		//closedbank.lock();Not used synchronized
		
		//try {
			
		while(accounts[originAccount] < money) {
			
			//return;
			
			//EnoughBalance.await();// We tell the code that as long as this is true, wait
			//Not used synchronized
			
			wait();
		}
				
		System.out.println(Thread.currentThread()); // This print tells us what thread is using
		
		accounts[originAccount] -= money; // Discout of account
		
		System.out.printf("%10.2f of %d to %d ", money, originAccount, destinyAccount );
		
		accounts[destinyAccount] += money; // Increment of account
		
		System.out.printf("Total Balance: %10.2f%n",getTotalBalance());
		
		notifyAll();
		
		//EnoughBalance.signalAll(); //Tells others threads that it has an action and they alive again
		
		//}/*finally {
			//closedbank.unlock();
		//}Not used synchronized*/
	}
	
	public double getTotalBalance() {
		
		double add_accounts = 0;
		
		for( double a: accounts) {
			
			add_accounts += a;
			
		}
		
		return add_accounts;
		
	}
	
	private final double [] accounts;
	
	//private Lock closedbank = new ReentrantLock(); 
	
	//private Condition EnoughBalance;
}

class TransferEjecution implements Runnable{
	//Object type bank , account of part, maximum money
	public TransferEjecution (Bank b, int of, double max) {
		
		bank = b;
		
		ofaccount = of;
		
		maxmoney = max;
		
	}
	public void run() {
	try {
		while(true) {
			
			int totheaccount =(int)(100*Math.random());
			
			double money = maxmoney*Math.random();
			
			bank.moneyTransfer(ofaccount, totheaccount, money);
			
			Thread.sleep((int)(10*Math.random()));
		}
	}catch (InterruptedException e) {
		System.out.println("Fallo en el sistema");
	}
		
	}
	
	private Bank bank;
	private int ofaccount;
	private double maxmoney;
}

// lock Method block a piece of your program so that it can only be executed by one thread
// unlock Desblock a piece of your code
//Condition Allows you to create blocks with a condition
//await makes the thread wait