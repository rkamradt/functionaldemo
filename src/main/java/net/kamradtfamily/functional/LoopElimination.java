/*
 * The MIT License
 *
 * Copyright 2020 randalkamradt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
 */
package net.kamradtfamily.functional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author randalkamradt
 */
public class LoopElimination {
   public static void main(String[] args) throws IOException {
       List<Integer> list = List.of(1, 2, 3);
       // bare for loop. 
       for(int i : list) {
           System.out.println("int = " + i);
       }
       // controlled for each
       list.forEach(i -> System.out.println("int = " + i));
       
       // side-effect, the loop alters sum
       int sum = 0;
       for(int i : list) {
           sum += i;
       }
       System.out.println("sum = " + sum);
       // no side-effect, sum is calculated by loop
       sum = list
               .stream()
               .mapToInt(i -> i)
               .sum();
       System.out.println("sum = " + sum);
       
       // side-effect, the loop alters list2
       List<Integer> list2 = new ArrayList<>();
       for(int i : list) {
           list2.add(i);
       }
       list2.forEach(i -> System.out.println("int = " + i));
       // no side effect, the second list is built by the loop
       list2 = list
                 .stream()
                 .collect(Collectors.toList());
       list2.forEach(i -> System.out.println("int = " + i));
       
       // bare for loop with index:
       for(int i = 0; i < list.size(); i++) {
           System.out.println("item at index " + i + " = " + list.get(i));
       }
       // controlled loop with index:
       IntStream.range(0, list.size())
               .forEach(i -> System.out.println("item at index " + i + " = " + list.get(i)));
       
       BufferedReader reader = new BufferedReader(
               new InputStreamReader(
                   LoopElimination.class.getResourceAsStream("/testfile.txt")));
       // while loop with clumsy looking syntax
       String line;
       while((line = reader.readLine()) != null) {
           System.out.println(line);
       }
       reader = new BufferedReader(
               new InputStreamReader(
                   LoopElimination.class.getResourceAsStream("/testfile.txt")));
       // less clumsy syntax
       reader.lines()
               .forEach(l -> System.out.println(l));
       
       InputStream is = LoopElimination.class.getResourceAsStream("/testfile.txt");
       // while loop with clumsy looking syntax
       int c;
       while((c = is.read()) != -1) {
           System.out.print((char)c);
       }
       InputStream nis = LoopElimination.class.getResourceAsStream("/testfile.txt");
       // Exception handling makes functional programming ugly
       Stream.generate(() -> {
            try {
                return nis.read();
            } catch (IOException ex) {
                throw new RuntimeException("Error reading from file", ex);
            }
        })
         .takeWhile(ch -> ch != -1)
         .forEach(ch -> System.out.print((char)(int)ch));
       System.out.println("looping with iterable");
       // use a predefined inputstream iterator:
       InputStreamIterable it = new InputStreamIterable(
            LoopElimination.class.getResourceAsStream("/testfile.txt"));
       StreamSupport.stream(it.spliterator(), true)
               .forEach(ch -> System.out.print(ch));
       
    }
    static class InputStreamIterable implements Iterable<Character> {
        public final InputStream is;
        InputStreamIterable(InputStream is) {
           this.is = is;
        }

        @Override
        public Iterator<Character> iterator() {
            return new Iterator<Character>() {
                @Override
                public boolean hasNext() {
                    try {
                        is.mark(1);
                        boolean ret = is.read() != -1;
                        is.reset();
                        return ret;
                    } catch (IOException ex) {
                        throw new RuntimeException("Error reading input stream", ex);
                    }
                }

                @Override
                public Character next() {
                    try {
                        return (char)is.read();
                    } catch (IOException ex) {
                        throw new RuntimeException("Error reading input stream", ex);
                    }
                }
            };
        }
       
    }
}
