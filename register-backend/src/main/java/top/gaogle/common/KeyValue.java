/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.gaogle.common;

import java.util.HashMap;

/**
 * <p>
 *     KV
 * </p>
 *
 * @author gaogle
 * @since 2021-04-19 10:27
 */
public class KeyValue<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = 1835338328164361493L;

    public static <K, V> KeyValue<K, V> create() {
        return new KeyValue<>();
    }

    public KeyValue<K, V> entry(K key, V value) {
        super.put(key, value);
        return this;
    }

}