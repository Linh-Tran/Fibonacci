/**
 * Linh Tran
 * FibonacciThreadMain creates two threads and prints out their
 * results in a synchronized way for 10 iterations with
 * 2 seconds pause between each iteration. Where the individual
 * threads are run in a infinite loop. The worker threads are created
 * inside the inner class called WorkerThread.
 */
public class FibonacciThreadMain
{

  /**
   * Function: FibonacciThreadMain()
   * This constructor of the main class creates
   * method. It implements the a for loop that iterates and prints out the worker
   * thread result output every 2 seconds. We have a total of three threads
   * the main thread, and two workers. After 10 iterations the main thread kills
   * the two workers and each will die printing their status and when both are
   * deed the main thread will call System.exit()
   */
  public FibonacciThreadMain()
  {
    long startTime = 0; //milliseconds
    long endTime = 0;

    WorkerThread workerA = new WorkerThread("A");
    WorkerThread workerB = new WorkerThread("B");

    workerA.start();
    workerB.start();

    startTime = System.currentTimeMillis();
    for (int i = 1; i <= 10; i++)
    {
      try
      {
        Thread.sleep(2000);
        printFib(workerA);
        printFib(workerB);
        System.out.println("\n");
      }
      catch (InterruptedException e)
      {
      }
    }

    endTime = System.currentTimeMillis();

    workerA.kill();
    workerB.kill();

    while (!workerA.isAlive && !workerB.isAlive)
    {
      System.out.println("Main thread waiting for workers to die.....");
      try
      {
        workerA.join();
        workerB.join();
        System.out.println("Program ended in " + (endTime - startTime) / 1000 + " seconds");
        System.out.println("All workers are dead. Goodbye.");
        System.exit(0);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Function : printFib
   * Input: WorkerThread thread
   * Output: void
   *
   * @param thread printFib sycnronized on a thread and prints out its name, x, y, z values
   */
  public void printFib(WorkerThread thread)
  {
    synchronized (thread)
    {
      System.out.println(thread.getThreadName() + " (" + thread.getStep() + ") " +
              thread.getX() + ", " + thread.getY() + ", " + thread.getZ());
    }
  }

  /**
   * Function : main
   *
   * @param args
   * @throws Exception Starts the main thread here with new instance of the FibonacciThreadMain()
   */
  public static void main(String[] args) throws Exception
  {
    new FibonacciThreadMain();

  }

  /**
   * Linh Tran
   * This inner class WorkerThread extends thread
   * It calculated the fibbonacci series in a while continues until isAlive
   * is set to false. It also stores the fib results in long and when
   * long is maxed out it resets x and y to 1.
   */
  class WorkerThread extends Thread
  {
    private final String name;  //Name of thread set in constructor and never changed
    private long step = 0;      //Fibonacci sequence step number.
    private long x = 1;         //fib(step-2)
    private long y = 1;         //last sequence value: fib(step-1)
    private long z;             //Current sequence value: fib(step)
    private boolean isAlive;

    /**
     * Function: WorkerThread
     * Input: String name, name of the worker thread.
     * <p>
     * This constructor assign to the String passed in and set isAlive boolean
     * to true.
     *
     * @param name
     */
    public WorkerThread(String name)
    {
      this.name = name;
      this.isAlive = true;
    }

    /**
     * Function : run
     * Run is a method that is inherited from the Thread class
     * This method is executed when a Thread instance is made and start() is
     * called on that thread. This method will continously compute the fibonacci
     * sequence for a thread until isAlive is set to false that signifies to terminate
     * it.
     * <p>
     * It computes the fib sequence by fib(step) = fib(step -1) + fib(step -2), where
     * step represents the number of times the while loop is executed and when z value
     * reach max long integer x and y are reset 1.
     */
    public void run()
    {
      while (isAlive)
      {
        synchronized (this)
        {
          step++;
          x = y;
          y = z;

          if (z == 7540113804746346429L)
          {
            x = 1;
            y = 1;
          }

          z = y + x;
        }
      }
      System.out.println(name + " Dies gracefully on step=" + step);
    }

    /**
     * Function : getStep
     * Output: long step
     * Returns the current step for the thread.
     *
     * @return
     */
    public long getStep()
    {
      return this.step;
    }

    /**
     * Function : getX
     * Output : long x
     * Returns the current fib(step-2) for the thread.
     *
     * @return
     */
    public long getX()
    {
      return this.x;
    }

    /**
     * Function : getY
     * Output: long y
     * Returns the current fib(step-1) for the thread.
     *
     * @return
     */
    public long getY()
    {
      return this.y;
    }

    /**
     * Function: getX
     * Output: long z
     * Returns the current z for the thread.
     *
     * @return
     */
    public long getZ()
    {
      return this.z;
    }


    /**
     * Function: kill
     * Output: void
     * Sets thread isAlive to false to terminate it.
     */
    public void kill()
    {
      this.isAlive = false;
    }

    /**
     * Function: getThreadName
     * Output: String name
     *
     * @return Returns the current thread name
     */
    public String getThreadName()
    {
      return this.name;
    }
  }
}

