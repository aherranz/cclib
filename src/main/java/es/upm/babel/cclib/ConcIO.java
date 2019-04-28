/*
 * ConcIO.java - Thread safe textual input/output.
 *
 * Copyright (C) 2011-2012 Angel Herranz, Steven Ortiz
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package es.upm.babel.cclib;

import java.util.concurrent.locks.*;
import java.util.HashMap;

/**
 * This class contains some static methods that allow several threads to print
 * messages to the standard output and make easier to follow traces in
 * concurrent programs.
 *
 * @author angel
 */
public class ConcIO {
    static private Lock wrLock = new ReentrantLock();
    static private int indentLevel = 0;
    static private HashMap<Long,Integer> threadIndent = new HashMap<Long,Integer>();
    static private long initialTimeMillis = System.currentTimeMillis();

    private ConcIO() {
    }

    // This method is not thread safe!!!
    static private String indentOnThreadPrefix() {
        int indent;
        long pid = Thread.currentThread().getId();
        if (threadIndent.containsKey(pid)) {
            indent = threadIndent.get(pid);
        }
        else {
            indent = ++indentLevel;
            threadIndent.put(pid, indent);
        }
        // StringBuilder prefix = new StringBuilder();
        // for (int i = 0; i < indent; i++) {
        //     prefix.append('\t');
        // }
        // prefix.append("[" + pid + "] -> ");
        // return prefix.toString();
        String prefix = "";
        for (int i = 0; i < indent; i++) {
            prefix += "\t";
        }
        prefix += "[" + pid + "] -> ";
        return prefix;
    }

    // This method is not thread safe!!!
    static private String timePrefix() {
        long currentTimeMillis = System.currentTimeMillis();
        return String.format("%6d:",
                             currentTimeMillis - initialTimeMillis);
    }

    /**
     * Executes a safe call to System.out.println with s.
     * @param format the string to print
     * @param args arguments for substitution in format
     */
    static public void printfnl(String format, Object... args) {
       wrLock.lock();
       System.out.printf(timePrefix()
                         + indentOnThreadPrefix()
                         + format
                         + "\n",
                         args);
       wrLock.unlock();
    }
}
