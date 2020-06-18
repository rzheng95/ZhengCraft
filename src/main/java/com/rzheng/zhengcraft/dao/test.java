/*
 * Copyright 2020-2020 Richard R. Zheng (https://github.com/rzheng95)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 */

package com.rzheng.zhengcraft.dao;

import org.bukkit.Material;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class test {
    public static void main(String[] args) {

        Map<UUID, Map<String, Integer>> cache = new HashMap<>();
        Map<UUID, Map<Material, Integer>> cache2 = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();

        map.put("test1", 1);
        map.put("test2", 2);
        map.put("test3", 3);

        map2.put("test1 hello dan", 1);
        map2.put("test2 hello dan", 1);
        map2.put("test3 hello dan", 1);
        map2.put("test4 hello dan", 1);

        cache.put(UUID.randomUUID(), map);
        cache.put(UUID.randomUUID(), map2);


        AtomicInteger cnt = new AtomicInteger(0);

        cache.forEach((uuid, stringIntegerMap) -> {
            stringIntegerMap.forEach((s, i) -> {
                cnt.incrementAndGet();
            });
        });
        System.out.println(cnt.get());



//        Map<String, Integer> map3 = Stream.of(map, map2).flatMap(m -> m.entrySet().stream())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1 + v2));
    }

    public static void sayHello() {
        try {
            Method bob = test.class.getDeclaredMethod("bob", String.class);
            test t = new test();

            bob.setAccessible(true);
            bob.invoke(t,"danPrime");


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void bob(String name) {
        System.out.println(name);
    }
}
