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

package com.quartercode.classmod.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The object adapter is used for mapping an {@link Object} field.
 * There are some bugs in JAXB the adapter works around.
 * 
 * Note: Of course, this way of doing things is really hacky, but there's currently no other solution.
 * Also note that you must add the {@link ClassElement} class to the "classpath" of your {@link JAXBContext}.
 */
public class ObjectAdapter extends XmlAdapter<Object, Object> {

    /**
     * Creates a new object adapter.
     */
    public ObjectAdapter() {

    }

    @Override
    public Object unmarshal(Object v) {

        if (v instanceof Element) {
            return ((Element<?>) v).getObject();
        } else {
            return v;
        }
    }

    @Override
    public Object marshal(Object v) {

        if (v instanceof Class) {
            return new ClassElement((Class<?>) v);
        } else {
            return v;
        }
    }

    public static interface Element<T> {

        public T getObject();

    }

    public static class ClassElement implements Element<Class<?>> {

        @XmlElement (name = "class")
        private Class<?> object;

        protected ClassElement() {

        }

        public ClassElement(Class<?> object) {

            this.object = object;
        }

        @Override
        public Class<?> getObject() {

            return object;
        }

    }

}
