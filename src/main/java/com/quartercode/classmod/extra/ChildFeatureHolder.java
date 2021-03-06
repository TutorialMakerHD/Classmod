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

import com.quartercode.classmod.base.Feature;
import com.quartercode.classmod.base.FeatureDefinition;
import com.quartercode.classmod.base.FeatureHolder;

/**
 * A child feature holder is a normal {@link FeatureHolder} which stores its parent {@link FeatureHolder}.
 * The parent {@link FeatureHolder} stores this child feature holder somehow.
 * A user can get {@link Feature}s through the central access method {@link #get(FeatureDefinition)}.
 * Such {@link Feature}s are defined by {@link FeatureDefinition} which describe how a feature looks like.
 * 
 * @param <P> The type the parent {@link FeatureHolder} has to have.
 * @see Feature
 * @see FeatureDefinition
 * @see FeatureHolder
 */
public interface ChildFeatureHolder<P extends FeatureHolder> extends FeatureHolder {

    /**
     * Returns the parent {@link FeatureHolder} which is storing this child feature holder.
     * 
     * @return The parent {@link FeatureHolder}.
     */
    public P getParent();

    /**
     * Changes the parent {@link FeatureHolder} which is storing this default child feature holder.
     * Should only be used if the parent {@link FeatureHolder} actually changes.
     * 
     * @param parent The new parent {@link FeatureHolder}.
     */
    public void setParent(P parent);

}
