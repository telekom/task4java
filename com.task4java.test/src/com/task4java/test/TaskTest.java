package com.task4java.test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import com.task4java.test.common.InitSetup;
import com.task4java.util.concurrent.CallableTask;
import com.task4java.util.concurrent.CallableValue;
import com.task4java.util.concurrent.Task;
import com.task4java.util.concurrent.TaskException;
import com.task4java.util.concurrent.TaskFactory;
import com.task4java.util.concurrent.TaskResultException;
import com.task4java.util.log.Logger;

public class TaskTest extends TestCase {

	private static final String TAG = TaskTest.class.getName();
	
	@Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        InitSetup.initLogger();
        
        TaskFactory.unhandledExceptions = new CallableValue<Void, TaskException>()
        {
            @Override
            public Void call(TaskException value)
            {
                if (value instanceof TaskResultException)
                {
                    Logger.instance.d(TAG, "unhandledException: " + ((TaskResultException)value).getTask().toString());
                }
                
                return null;
            }
        };
    }
	
	@Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        
        Logger.instance.d(TAG, "tearDown");
        
        TaskFactory.shutdown();
    }
	
	public void testSimpleTask01() throws Exception
    {
        Logger.instance.d(TAG, "Thread Main: " + Thread.currentThread().getId());
        
        Task<String> taskResult = TaskFactory.startNew(new Callable<String>() {

            @Override
            public String call() throws Exception {

                Logger.instance.d(TAG, "Thread for task: " + Thread.currentThread().getId());
                
                Thread.sleep(1000);
                
                return "Ready";
            }
        });
        
        Logger.instance.d(TAG, "End of code");
        
        String result = taskResult.get();
        
        Logger.instance.d(TAG, "Result: " + result);
    }
	
	public void testSimpleTask02() throws Exception
    {
        Logger.instance.d(TAG, "Thread Main: " + Thread.currentThread().getId());
        
        Task<String> taskResult1 = TaskFactory.startNew(new Callable<String>() {

            @Override
            public String call() throws Exception {

                Logger.instance.d(TAG, "Thread for task1: " + Thread.currentThread().getId());
                
                Thread.sleep(1000);
                
                return "Ready1 ";
            }
        });
        
        Task<String> taskResult2 = TaskFactory.startNew(new Callable<String>() {

            @Override
            public String call() throws Exception {

                Logger.instance.d(TAG, "Thread for task2: " + Thread.currentThread().getId());
                
                Thread.sleep(1000);
                
                return "Ready2 ";
            }
        });
        
        Logger.instance.d(TAG, "End of code");
        
        String result1 = taskResult1.get();
        String result2 = taskResult2.get();
        
        Logger.instance.d(TAG, "Result: " + result1 + result2);
    }
	
	public void testContinueWith01() throws Exception {

        Logger.instance.d(TAG, "Thread Main: " + Thread.currentThread().getId());

        Task<String> taskResult = TaskFactory.startNew(new Callable<String>() {

            @Override
            public String call() throws Exception {

                Logger.instance.d(TAG, "Thread for task1: " + Thread.currentThread().getId());
                
                Thread.sleep(1000);
                
                return "Ready";
            }
        }).continueWith(new CallableTask<String, String>() {

            @Override
            public String call(Task<String> task) throws Exception {
                Logger.instance.d(TAG, "Thread for step1: " + Thread.currentThread().getId());

                return task.get() + " step1";
            }
        }).continueWith(new CallableTask<String, String>() {

            @Override
            public String call(Task<String> task) throws Exception {
                Logger.instance.d(TAG, "Thread for step2: " + Thread.currentThread().getId());

                return task.get() + " step2";
            }
        });

        Logger.instance.d(TAG, "End of code");
        Logger.instance.d(TAG, "Result: " + taskResult.get());
    }
	
	public void testContinueWith02() throws Exception {

        Logger.instance.d(TAG, "Thread Main: " + Thread.currentThread().getId());

        Task<String> taskResult = TaskFactory.startNew(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {

                Logger.instance.d(TAG, "Thread for task1: " + Thread.currentThread().getId());
                
                Thread.sleep(1000);
                
                return 20;
            }
        }).continueWith(new CallableTask<Integer, Integer>() {

            @Override
            public Integer call(Task<Integer> task) throws Exception {
                Logger.instance.d(TAG, "Thread for step1: " + Thread.currentThread().getId());

                return task.get() + 30;
            }
        }).continueWith(new CallableTask<String, Integer>() {

            @Override
            public String call(Task<Integer> task) throws Exception {
                Logger.instance.d(TAG, "Thread for step2: " + Thread.currentThread().getId());

                return task.get() + " step2";
            }
        });

        Logger.instance.d(TAG, "End of code");
        Logger.instance.d(TAG, "Result: " + taskResult.get());
    }
	
	public void testWhenAll01() throws Exception
    {
        Task<String> task1 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(2000);
                return "Task1 ";
            }});
        
        Task<String> task2 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(3000);
                return "Task2 ";
            }});
        
        Task<String> task3 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(5000);
                return "Task3 ";
            }});
               
        @SuppressWarnings("unchecked")
		Task<List<Task<String>>> taskResult = Task.whenAll(task1, task2, task3);
                
        Logger.instance.d(TAG, "End of code");
        Logger.instance.d(TAG, "Result: " + taskResult.get());
    }
	
	public void testWhenAll02() throws Exception
    {
        ExecutorService service = Executors.newSingleThreadExecutor();
        
        Task<String> task1 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(2000);
                return "Task1 ";
            }}, service);
        
        Task<String> task2 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(3000);
                return "Task2 ";
            }}, service);
        
        Task<String> task3 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(5000);
                return "Task3 ";
            }}, service);
               
        @SuppressWarnings("unchecked")
		Task<List<Task<String>>> taskResult = Task.whenAll(task1, task2, task3);
                
        Logger.instance.d(TAG, "End of code");
        Logger.instance.d(TAG, "Result: " + taskResult.get());
    }
	
	public void testWhenAll03() throws Exception
    {
        Task<String> task1 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(2000);
                return "Task1 ";
            }});
        
        Task<String> task2 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(3000);
                return "Task2 ";
            }});
        
        Task<String> task3 = TaskFactory.startNew(new Callable<String>(){

            @Override
            public String call() throws Exception
            {
                Thread.sleep(5000);
                throw new Exception("Exception!");
            }});
               
        @SuppressWarnings("unchecked")
		Task<List<Task<String>>> taskResult = Task.whenAll(task1, task2, task3);
                
        Logger.instance.d(TAG, "End of code");
        
        Logger.instance.d(TAG, "Result: " + taskResult.get().get(0).get());
        Logger.instance.d(TAG, "Result: " + taskResult.get().get(1).get());
        
        try
        {
            Logger.instance.d(TAG, "Result: " + taskResult.get().get(2).get());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
