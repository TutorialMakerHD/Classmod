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

package com.quartercode.classmod.test.extra.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.def.DefaultFeatureHolder;
import com.quartercode.classmod.extra.ExecutorInvokationException;
import com.quartercode.classmod.extra.FunctionExecutionException;
import com.quartercode.classmod.extra.FunctionExecutor;
import com.quartercode.classmod.extra.def.AbstractFunction;

public class AbstractFunctionTest {

    @Test
    public void testInvoke() throws FunctionExecutionException {

        Map<String, FunctionExecutor<Object>> executors = new HashMap<String, FunctionExecutor<Object>>();
        final List<Object> actualArguments = new ArrayList<Object>();
        final Object returnValue = "ReturnValue";
        executors.put("default", new FunctionExecutor<Object>() {

            @Override
            public Object invoke(FeatureHolder holder, Object... arguments) throws ExecutorInvokationException {

                actualArguments.addAll(Arrays.asList(arguments));
                return returnValue;
            }

        });

        AbstractFunction<Object> function = new AbstractFunction<Object>("testFunction", new DefaultFeatureHolder(), new ArrayList<Class<?>>(), executors);

        List<Object> arguments = new ArrayList<Object>();
        arguments.add("Test");
        arguments.add(String.class);
        arguments.add(new Object[] { "Test", 12345, true });
        Object actualReturnValue = function.invoke(arguments.toArray(new Object[arguments.size()]));

        Assert.assertEquals("Received arguments", arguments, actualArguments);
        Assert.assertEquals("Received return value", returnValue, actualReturnValue);
    }

}
