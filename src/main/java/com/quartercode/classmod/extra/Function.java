/*
 * This file is part of Classmod.
 * Copyright (c) 2014 QuarterCode <http://www.quartercode.com/>
 *
 * Classmod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Classmod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Classmod. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.classmod.extra;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import com.quartercode.classmod.base.Feature;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.Named;

/**
 * A function makes a method (also called a function) avaiable.
 * Functions are executed by different {@link FunctionExecutor}s. That makes the function concept flexible.
 * The function object itself stores a set of those {@link FunctionExecutor}s.
 * 
 * @param <R> The type of the return values of the used {@link FunctionExecutor}s. The function returns a {@link List} with these values.
 * @see FunctionExecutor
 * @see FunctionExecutorContainer
 */
public interface Function<R> extends Feature, LockableClass {

    /**
     * Returns a list of all parameters which are used by the {@link FunctionExecutor}s.
     * See {@link FunctionDefinition#setParameter(int, Class)} for further explanation.
     * 
     * @return All parameters which are used by the function.
     */
    public List<Class<?>> getParameters();

    /**
     * Returns a {@link Set} of all {@link FunctionExecutorContainer}s which are used by the function.
     * They store {@link FunctionExecutor}s which are used for actually handling a function call.
     * 
     * @return All {@link FunctionExecutorContainer}s which are used by the function.
     */
    public Set<FunctionExecutorContainer<R>> getExecutors();

    /**
     * Returns the {@link FunctionExecutorContainer} which is used by the function and has the given name.
     * 
     * @param name The name the returned {@link FunctionExecutorContainer} must have.
     * @return The {@link FunctionExecutorContainer} which has the given name.
     */
    public FunctionExecutorContainer<R> getExecutor(String name);

    /**
     * Invokes the defined function with the given arguments on all {@link FunctionExecutor}s.
     * This returns the return value of the {@link FunctionExecutor}s with the highest priority.
     * If you want the return values of all executors, use {@link #invokeRA(Object...)}.
     * 
     * @param arguments Some arguments for the {@link FunctionExecutor}s.
     * @return The value the {@link FunctionExecutor}s with the highest priority returns. May be null.
     * @throws FunctionExecutionException Something goes wrong during the invokation of a {@link FunctionExecutor}.
     */
    public R invoke(Object... arguments) throws FunctionExecutionException;

    /**
     * Invokes the defined function with the given arguments on all {@link FunctionExecutor}s.
     * This returns the values the {@link FunctionExecutor}s return in invokation order.
     * If you want the value of the executor with the highest priority, use the index 0 or {@link #invoke(Object...)}.
     * 
     * @param arguments Some arguments for the {@link FunctionExecutor}s.
     * @return The values the invoked {@link FunctionExecutor}s return. Also contains null values.
     * @throws FunctionExecutionException Something goes wrong during the invokation of a {@link FunctionExecutor}.
     */
    public List<R> invokeRA(Object... arguments) throws FunctionExecutionException;

    /**
     * The function executor container wraps around {@link FunctionExecutor}s for storing data values along with them.
     * The data isn't stored in the actual {@link FunctionExecutor} object because it should only do the execution and nothing else.
     * 
     * @param <R> The type of the value the stored {@link FunctionExecutor} returns.
     * @see FunctionExecutor
     * @see Function
     */
    public static interface FunctionExecutorContainer<R> extends Named, LockableClass {

        /**
         * Returns the name of the {@link FunctionExecutor} which is stored by the container.
         * 
         * @return The name of the stored {@link FunctionExecutor}.
         */
        @Override
        public String getName();

        /**
         * Returns the actual {@link FunctionExecutor} which is stored in the container
         * 
         * @return The stored {@link FunctionExecutor}.
         */
        public FunctionExecutor<R> getExecutor();

        /**
         * Returns a value of the given {@link Annotation} type at the stored {@link FunctionExecutor}.
         * Using this, you can read the values of every annotation at the {@link FunctionExecutor} (for example {@link Limit#value()})
         * 
         * @param type The {@link Annotation} type whose value should be retrieved (could be {@link Limit}).
         * @param name The name of the value stored in the {@link Annotation} which should be retrieved (could be "value" for {@link Limit#value()}).
         * @return The value stored in the defined variable (could be 7 for {@link Limit#value()}).
         */
        public <A extends Annotation> Object getValue(Class<A> type, String name);

        /**
         * Sets a value of the given {@link Annotation} type at the stored {@link FunctionExecutor} to the given one.
         * That allows to modify {@link Annotation} values at runtime.
         * You can compare that with reflection.
         * 
         * @param type The {@link Annotation} type whose value should be changed (could be {@link Limit}).
         * @param name The name of the value stored in the {@link Annotation} which should be changed (could be "value" for {@link Limit#value()}).
         * @param value The new value for the defined variable (could be 7 for {@link Limit#value()}).
         */
        public <A extends Annotation> void setValue(Class<A> type, String name, Object value);

        /**
         * Sets the internal invokation counter for the function executor container to 0.
         * That allows to use {@link FunctionExecutor}s which are already over their {@link Limit}.
         */
        public void resetInvokationCounter();

        /**
         * Returns if the stored {@link FunctionExecutor} is locked.
         * The {@link FunctionExecutor} doesn't need to be {@link Lockable} for this to work.
         * The check algorithm ignores the {@link Lockable} annotation and locks every {@link FunctionExecutor}.
         * 
         * @return True if the stored {@link FunctionExecutor} is locked, false if not.
         */
        @Override
        public boolean isLocked();

        /**
         * Changes if the stored {@link FunctionExecutor} is locked.
         * The {@link FunctionExecutor} doesn't need to be {@link Lockable} for this to work.
         * The check algorithm ignores the {@link Lockable} annotation and locks every {@link FunctionExecutor}.
         * 
         * @param locked True if the stored {@link FunctionExecutor} should be locked, false if not.
         */
        @Override
        public void setLocked(boolean locked);

        /**
         * Invokes the stored {@link FunctionExecutor} inside the given {@link FeatureHolder} with the given arguments.
         * 
         * @param holder The {@link FeatureHolder} the stored {@link FunctionExecutor} is invoked in.
         * @param arguments Some arguments for the stored {@link FunctionExecutor}.
         * @return The value the invoked {@link FunctionExecutor} returns. Can be null.
         * @throws ExecutorInvokationException The stored {@link FunctionExecutor} sends a signal.
         */
        public R invoke(FeatureHolder holder, Object... arguments) throws ExecutorInvokationException;

    }

}
