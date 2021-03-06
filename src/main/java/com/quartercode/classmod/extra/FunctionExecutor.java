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

import com.quartercode.classmod.base.FeatureHolder;

/**
 * A function executor can be invoked with a {@link FeatureHolder} and some arguments.
 * It returns a value of a specified type. For no return value, you can use {@link Void}.
 * 
 * @param <R> The type of the return value of the defined function.
 */
public interface FunctionExecutor<R> {

    /**
     * Invokes the defined function executor in the given {@link FeatureHolder} with the given arguments.
     * 
     * @param holder The {@link FeatureHolder} the function executor is invoked in.
     * @param arguments Some arguments for the function executor.
     * @return The value the invoked function executor returns. Can be null.
     * @throws ExecutorInvokationException The function executor sends a signal.
     */
    public R invoke(FeatureHolder holder, Object... arguments) throws ExecutorInvokationException;

}
