Linh Tran
CS351
10/20/17
Threads of Fibonacci Lab

Main class:
FibonacciThreadMain
    -FibonacciThreadMain()
    -main()
    -printFib()

     Inner Class:
     WorkerThread
        -run()
        -kill()

Description:

    Every 2 seconds the main thread must print each worker
    thread’s name, step number and x, y z values.

    After 10 loops of the main thread, (20 seconds), the main thread
    must tell each of the workers (via a method you create) that they
    should exit. It will take some (short) time for each of the threads
    to notice they should exit, to print a short message such as
    "This is thread X signing out", and to actually exit. This message
    MUST print from the worker thread's run() method (NOT from
    whatever method main calls to tell the worker it should exit).

    Meanwhile, the main thread should be polling each thread's
    .isAlive() method. When .isAlive() returns false for both threads,
    then main must print "Program Exit" and call System.exit().