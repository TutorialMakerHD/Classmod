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

package com.quartercode.classmod.extra.def;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.Persistent;
import com.quartercode.classmod.base.def.AbstractPersistentFeature;
import com.quartercode.classmod.extra.Property;
import com.quartercode.classmod.util.ObjectAdapter;

/**
 * An object property is a simple {@link Property} which stores an object.
 * 
 * @param <T> The type of object which can be stored inside the object property.
 * @see Property
 */
@Persistent
public class ObjectProperty<T> extends AbstractPersistentFeature implements Property<T> {

    private T object;

    /**
     * Creates a new empty object property.
     * This is only recommended for direct field access (e.g. for serialization).
     */
    protected ObjectProperty() {

    }

    /**
     * Creates a new object property with the given name and {@link FeatureHolder}.
     * 
     * @param name The name of the object property.
     * @param holder The feature holder which has and uses the new object property.
     */
    public ObjectProperty(String name, FeatureHolder holder) {

        super(name, holder);
    }

    /**
     * Creates a new object property with the given name and {@link FeatureHolder}, and sets the initial value.
     * 
     * @param name The name of the object property.
     * @param holder The feature holder which has and uses the new object property.
     * @param initialValue The value the new object property has directly after creation.
     */
    public ObjectProperty(String name, FeatureHolder holder, T initialValue) {

        super(name, holder);

        set(initialValue);
    }

    @Override
    @XmlElement
    @XmlJavaTypeAdapter (ObjectAdapter.class)
    public T get() {

        return object;
    }

    @Override
    public void set(T value) {

        object = value;
    }

    @Override
    public Iterator<T> iterator() {

        Set<T> set = new HashSet<T>();
        set.add(object);
        return set.iterator();
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (object == null ? 0 : object.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ObjectProperty<?> other = (ObjectProperty<?>) obj;
        if (object == null) {
            if (other.object != null) {
                return false;
            }
        } else if (!object.equals(other.object)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [name=" + getName() + ", object=" + object + "]";
    }

}
