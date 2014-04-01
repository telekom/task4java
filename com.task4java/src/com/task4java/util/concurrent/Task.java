/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: Task.java
 */
package com.task4java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import com.task4java.StringUtils;

/**
 * <p> The {@code Task} extends the {@link FutureTask} with new methods (see {@code continueWith}) to support
 * the composition of asynchronous operations. Therefor the {@code Task} can be used to wrap
 * a {@link Callable} and executes a callback function ({@link CallableTask}) if 
 * the {@code Task} is done.
 *
 * @param <V> The result type returned by this Task's {@code get} methods.
 */
public class Task<V> extends FutureTask<V> implements ITask, ITaskStart
{
    
    /** The _new task list. */
    private final ArrayList<ITaskStart> _newTaskList = new ArrayList<ITaskStart>();
    
    /** The _executor. */
    private Executor _executor;
    
    /** The _id. */
    private String _id;
    
    /** The boolean is result read. */
    private boolean _blnIsResultRead = false;
    
    /** The boolean is done. */
    private boolean _blnIsDone = false;
    
    /** The boolean is started. */
    private boolean _blnIsStarted = false;

    
    /**
     * Creates a {@code Task} that will, upon starting, execute the given {@code Callable}. 
     *
     * @param callable the callable task
     */
    public Task(Callable<V> callable)
    {
        this(callable, "");
    }
    
    /**
     * Creates a {@code Task} that will, upon starting, execute the given {@code Callable}.
     *
     * @param callable the callable task
     * @param id the id
     */
    public Task(Callable<V> callable, String id)
    {
        this(callable, TaskFactory.defaultExecutorService, id);
    }

    /**
     * Creates a {@code Task} that will, upon starting, execute the given {@code Callable}.
     *
     * @param callable the callable task
     * @param executor the executor to use for the execution of the task
     */
    public Task(Callable<V> callable, Executor executor)
    {
        this(callable, executor, "");
    }
    
    /**
     * Creates a {@code Task} that will, upon starting, execute the given {@code Callable}.
     *
     * @param callable the callable task
     * @param executor the executor to use for the execution of the task
     * @param id the id
     */
    public Task(Callable<V> callable, Executor executor, String id)
    {
        super(callable);

        _executor = executor;
        _id = id;

        if (_executor == null)
        {
            throw new IllegalArgumentException("The executor argument cannot be null.");
        }
    }

    /**
     * Finds the specified exception class within the stack trace of the given exception.
     *
     * @param <T> the generic type
     * @param ex the given exception 
     * @param target the exception class to look for
     * @return the first match of the target class
     */
    @SuppressWarnings("unchecked")
    public static <T> T findException(Exception ex, Class<T> target)
    {
        Throwable currentEx = ex;

        for (int i = 0; i < 100; i++)
        {
            if (currentEx == null)
            {
                break;
            }

            if (target.isAssignableFrom(currentEx.getClass()))
            {
                return (T) currentEx;
            }

            currentEx = currentEx.getCause();
        }

        return null;
    }
    
    /**
     * Finds the first exception whose type not equals the specified exception class.
     *
     * @param <T> the generic type
     * @param ex the given exception 
     * @param target the exception class to compare
     * @return the first match of an exception whose type not equals target.
     */
    @SuppressWarnings("unchecked")
    public static <T> T findExceptionExcept(Exception ex, Class<T> target)
    {
        Throwable currentEx = ex;

        for (int i = 0; i < 100; i++)
        {
            if (currentEx == null)
            {
                break;
            }

            if (! target.isAssignableFrom(currentEx.getClass()))
            {
                return (T) currentEx;
            }

            currentEx = currentEx.getCause();
        }

        return null;
    }

    /**
     * Creates a {@code Task} from the specified {@link TaskCompletionSource}.
     *
     * @param <V> The result type returned by the Task's {@code get} methods.
     * @param source the source to create the task from
     * @return the task
     */
    public static <V> Task<V> fromSource(final TaskCompletionSource<V> source)
    {
        final Task<V> task = new Task<V>(new Callable<V>()
        {
            @Override
            public V call() throws Exception
            {
                return null;
            }
        });

        source.setSetCallback(new CallableValue<Void, V>() {
            @Override
            public Void call(V value) {

                task.set(value);

                return null;
            }
        });

        source.setExceptionCallback(new CallableValue<Void, Throwable>() {
            @Override
            public Void call(Throwable value) {

                task.setException(value);

                return null;
            }
        });

        return task;
    }

    /**
     * Creates a {@code Task} from the specified data.
     *
     * @param <V> The result type returned by the Task's {@code get} methods.
     * @param data the data
     * @return the task
     */
    public static <V> Task<V> fromResult(final V data)
    {
        return fromResult(data, "");
    }
    
    /**
     * Creates a {@code Task} from the specified data.
     *
     * @param <V> The result type returned by the Task's {@code get} methods.
     * @param data the data
     * @param id the id
     * @return the task
     */
    public static <V> Task<V> fromResult(final V data, String id)
    {
        Task<V> task = new Task<V>(new Callable<V>()
        {
            @Override
            public V call() throws Exception
            {
                return data;
            }
        }, id);

        task.set(data);

        return task;
    }
    
    /**
     * Creates a {@code Task} from the specified {@link Throwable}.
     *
     * @param <V> The result type returned by the Task's {@code get} methods.
     * @param t the t
     * @return the task
     */
    public static <V> Task<V> fromException(final Throwable t)
    {
        return fromException(t, "");
    }
    
    /**
     * Creates a {@code Task} from the specified {@link Throwable}.
     *
     * @param <V> The result type returned by the Task's {@code get} methods.
     * @param t the t
     * @param id the id
     * @return the task
     */
    public static <V> Task<V> fromException(final Throwable t, String id)
    {
        Task<V> task = new Task<V>(new Callable<V>()
        {
            @Override
            public V call() throws Exception
            {
                return null;
            }
        }, id);

        task.setException(t);

        return task;
    }

    /**
     * Waits for the completion of all specified tasks.
     *
     * @param <VResult> the generic type
     * @param tasks the tasks to wait for
     * @return the task
     */
    public static <VResult> Task<List<Task<VResult>>> whenAll(final Task<VResult> ... tasks)
    {
        List<Task<VResult>> list = new ArrayList<Task<VResult>>();
        
        if (tasks != null)
        {
            for (Task<VResult> task: tasks)
            {
                list.add(task);
            }
        }
        
        return whenAll(list);
    }
    
    /**
     * Waits for the completion of all specified tasks.
     *
     * @param <VResult> the generic type
     * @param tasks the tasks to wait for
     * @return the task
     */
    public static <VResult> Task<List<Task<VResult>>> whenAll(final List<Task<VResult>> tasks)
    {       
        if (tasks == null || tasks.size() == 0)
        {
        	List<Task<VResult>> emptyList = new ArrayList<Task<VResult>>();
            return Task.fromResult(emptyList);
        }
        
        Task<VResult> task = tasks.get(0);

        for (int i = 1; i < tasks.size(); i++)
        {
            final int finalI = i;
            
            task = task.continueWith(new CallableTask<Task<VResult>, VResult>()
            {
                @Override
                public Task<VResult> call(Task<VResult> task) throws Exception
                {
                    task.markAsReaded();
                    
                    return tasks.get(finalI);
                }
            }).unwrap();
        }
        
        return task.continueWith(new CallableTask<List<Task<VResult>>, VResult>() {
            @Override
            public List<Task<VResult>> call(Task<VResult> task) throws Exception {
                
                task.markAsReaded();
                
                return tasks;
            }
        });
    }

    /**
     * Waits for the completion of any task.
     *
     * @param <VResult> the generic type
     * @param tasks the tasks to wait for
     * @return the task
     */
    public static <VResult> Task<VResult> whenAny(final Task<VResult> ... tasks)
    {
        List<Task<VResult>> list = new ArrayList<Task<VResult>>();

        if (tasks != null)
        {
            for (Task<VResult> task: tasks)
            {
                list.add(task);
            }
        }

        return whenAny(list);
    }

    /**
     * Waits for the completion of any task.
     *
     * @param <VResult> the generic type
     * @param tasks the tasks to wait for
     * @return the task
     */
    public static <VResult> Task<VResult> whenAny(final List<Task<VResult>> tasks)
    {
        if (tasks == null || tasks.size() == 0)
        {
            return Task.fromResult(null);
        }

        final Task<VResult> taskResult = new Task<VResult>(new Callable<VResult>()
            {
                @Override
                public VResult call() throws Exception
                {
                    return null;
                }
            });

        for (Task<VResult> task: tasks)
        {
            task.continueWith(new CallableTask<VResult, VResult>() {
                @Override
                public VResult call(Task<VResult> task) throws Exception {

                	synchronized (taskResult) {
									
	                    try
	                    {
	                        if (!taskResult.isDone())
	                        {
	                            taskResult._id = task._id;
	                            taskResult.set(task.get());
	                        }
	                    }
	                    catch (Throwable e)
	                    {
	                        taskResult.setException(e);
	                    }
                	}

                    return task.get();
                }
            });
        }

        return taskResult;
    }

    /* (non-Javadoc)
     * @see de.telekom.util.concurrent.ITaskStart#start()
     */
    @Override
    public void start()
    {
        if (_blnIsStarted)
        {
            if (TaskFactory.unhandledExceptions != null)
            {
            	TaskException ex = new TaskStartException("The task has already been started.", this);
            	
                TaskFactory.unhandledExceptions.call(ex);
            }
        }
        
        _blnIsStarted = true;
        
        _executor.execute(this);
    }
    
    /* (non-Javadoc)
     * @see java.util.concurrent.FutureTask#run()
     */
    @Override
    public void run()
    {
        _blnIsStarted = true;
        
        super.run();
    }
    
    /* (non-Javadoc)
     * @see java.util.concurrent.FutureTask#get()
     */
    @Override
    public V get() throws InterruptedException, ExecutionException
    {
        _blnIsResultRead = true;
        
        return super.get();
    }
    
    /**
     * Gets the raw result from this {@code Task} if this {@code Task} has finished
     * or null if there is no result.
     *
     * @return the raw
     */
    public V getRaw()
    {
    	
    	if (this.isDone())
    	{
    		try {
				return this.get();
			} 
    		catch (Exception e)
			{
			}
    	}
    
    	return null;
    }

    /**
     * Unwraps this {@code Task} if the result type is a {@code Task<Task<V>>}.
     *
     * @return the {@code Task<V>}
     */
    public V unwrap()
    {

        return this.continueWithInternal(new CallableTask<V, Task<V>>() {
            @Override
            public V call(Task<Task<V>> task) throws ExecutionException, InterruptedException {
                return task.get().get();
            }
        });
    }
    
    /**
     * Unwraps this {@code Task} if the result type is a {@code Task<Task<V>>}.
     *
     * @param executor the executor
     * @return the {@code Task<V>}
     */
    public V unwrap(Executor executor)
    {

        return this.continueWithInternal(new CallableTask<V, Task<V>>()
        {
            @Override
            public V call(Task<Task<V>> task) throws ExecutionException, InterruptedException
            {
                return task.get().get();
            }
        }, executor);
    }
    
    /**
     * To void.
     *
     * @return the task
     */
    public Task<Void> toVoid()
    {
    	return this.continueWith(new CallableTask<Void, V>(){

			@Override
			public Void call(Task<V> task) throws Exception {
				task.get();
				return null;
			}});
    }
    
    /**
     * To null.
     *
     * @param <VNew> the generic type
     * @return the task
     */
    public <VNew> Task<VNew> toNull()
    {
    	return this.continueWith(new CallableTask<VNew, V>(){

			@Override
			public VNew call(Task<V> task) throws Exception {
				
				task.get();
				
                return null;
            }});
    }
    
    /**
     * To object.
     *
     * @return the task
     */
    public Task<Object> toObject()
    {
        return this.continueWith(new CallableTask<Object, V>(){

            @Override
            public Object call(Task<V> task) throws Exception {
                return task.get();
            }});
    }
    
    /**
     * To type.
     *
     * @param <VNew> the generic type
     * @return the task
     */
    public <VNew> Task<VNew> toType()
    {
        return this.continueWith(new CallableTask<VNew, V>(){

            @SuppressWarnings("unchecked")
			@Override
            public VNew call(Task<V> task) throws Exception {
                return (VNew) task.get();
            }});
    }
    
    /**
     * To callable task.
     *
     * @return the callable task
     */
    public CallableTask<Task<V>, Void> toCallableTask()
    {
        return new CallableTask<Task<V>, Void>()
        {
            @Override
            public Task<V> call(Task<Void> task) throws Exception
            {
                task.get();
                
                return Task.this;
            }
        };
    }

    /**
     * Continue with.
     *
     * @param <VNew> the generic type
     * @param task the task
     * @return the task
     */
    private <VNew> Task<VNew> continueWith(Task<VNew> task)
    {
        boolean blnIsDone;

        synchronized (_newTaskList)
        {
            blnIsDone = _blnIsDone;

            if (!blnIsDone)
            {
                _newTaskList.add(task);
            }
        }

        if (blnIsDone)
        {
            task.start();
        }

        return task;
    }

    /**
     * Defines a {@link CallableTask} for continuation after this {@code Task} has finished.
     *
     * @param <VNew> the generic type
     * @param callableTask the callable task for continuation
     * @return the task
     */
    public <VNew> Task<VNew> continueWith(CallableTask<VNew, V> callableTask)
    {

        Task<VNew> newTask = new Task<VNew>(createCallable(this, callableTask), this._id);

        return continueWith(newTask);
    }

    /**
     * Defines a {@link CallableTask} for continuation after this {@code Task} has finished.
     *
     * @param <VNew> the generic type
     * @param callableTask the callable task for continuation
     * @param executor the executor
     * @return the task
     */
    public <VNew> Task<VNew> continueWith(CallableTask<VNew, V> callableTask, Executor executor)
    {

        Task<VNew> newTask = new Task<VNew>(createCallable(this, callableTask), executor, this._id);

        return continueWith(newTask);
    }
   
    /**
     * Mark as readed.
     */
    private void markAsReaded()
    {
        try
        {
            this.get();
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * Continue with internal.
     *
     * @param callableTask the callable task
     * @return the v
     */
    @SuppressWarnings("unchecked")
    private V continueWithInternal(CallableTask<V, Task<V>> callableTask)
    {
        Task<V> newTask = new Task<V>(createCallable((Task<Task<V>>) this, callableTask), this._id);

        return (V) continueWith(newTask);
    }

    /**
     * Continue with internal.
     *
     * @param callableTask the callable task
     * @param executor the executor
     * @return the v
     */
    @SuppressWarnings("unchecked")
    private V continueWithInternal(CallableTask<V, Task<V>> callableTask, Executor executor)
    {
        Task<V> newTask = new Task<V>(createCallable((Task<Task<V>>) this, callableTask), executor, this._id);

        return (V) continueWith(newTask);
    }

    /**
     * Creates the callable.
     *
     * @param <VNew> the generic type
     * @param <V> the value type
     * @param task the task
     * @param callableTask the callable task
     * @return the callable
     */
    private static <VNew, V> Callable<VNew> createCallable(final Task<V> task, final CallableTask<VNew, V> callableTask)
    {
        return new Callable<VNew>()
        {
            public VNew call() throws Exception
            {
                VNew result = callableTask.call(task);
                
                if (! task._blnIsResultRead)
                {
                    if (TaskFactory.unhandledExceptions != null)
                    {
                    	TaskException ex = new TaskResultException("The task result has not been read within the ContinueWith method.", task);
                    	
                        TaskFactory.unhandledExceptions.call(ex);
                    }
                }
                
                return result;
            }
        };
    }

    /**
     * Checks if is faulted.
     *
     * @return true, if is faulted
     */
    public boolean isFaulted()
    {
    	boolean isFaulted = false;
    	
        if (isDone())
        {
            try
            {
                this.get();
            }
            catch (Exception e)
            {
                isFaulted = true;
            } 
        }
        
        return isFaulted;
    }
    
    /* (non-Javadoc)
     * @see java.util.concurrent.FutureTask#done()
     */
    @Override
    protected void done()
    {
        super.done();

        synchronized (_newTaskList)
        {
            _blnIsDone = true;

            for (ITaskStart task : _newTaskList)
            {
                task.start();
            }

            _newTaskList.clear();
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String id = this._id;
        
        if (StringUtils.IsNullOrEmpty(this._id))
        {
            id = "<undefined>";
        }
        
        StringBuilder stb = new StringBuilder();
        
        if (isFaulted())
        {
            try
            {
                this.get();        
            }
            catch(Exception e)
            {
                stb.append("Task {status: is faulted, id: ");
                stb.append(id);
                stb.append(", exception: ");
                stb.append(e.toString()); 
                stb.append("}");
            }
        }
        else if (isDone())
        {
            try
            {
                stb.append("Task {status: is done, id: ");
                stb.append(id);
                stb.append(", value: ");
                stb.append(this.get().toString()); 
                stb.append("}");
            }
            catch(Exception e)
            {
            }
        }
        else if (isCancelled())
        {
            stb.append("Task {status: is cancelled, id: ");
            stb.append(id);
            stb.append("}");
        }
        else
        {
            stb.append("Task {status: is running, id: ");
            stb.append(id);
            stb.append("}");
        }
        
        return stb.toString();
    }
}
