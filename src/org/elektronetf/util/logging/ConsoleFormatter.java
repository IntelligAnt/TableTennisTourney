package org.elektronetf.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter {
    private static final Map<Level, String> PREFIXES;
    
    static {
        Map<Level, String> prefixes = new HashMap<Level, String>();
        prefixes.put(Level.CONFIG,  "[config]");
        prefixes.put(Level.FINE,    "[debug]");
        prefixes.put(Level.FINER,   "[debug]");
        prefixes.put(Level.FINEST,  "[trace]");
        prefixes.put(Level.INFO,    "[info]");
        prefixes.put(Level.SEVERE,  "[error]");
        prefixes.put(Level.WARNING, "[warning]");
        
        PREFIXES = Collections.unmodifiableMap(prefixes);
    }
    
    private boolean printStackTrace;
    
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder()
        		.append(PREFIXES.get(record.getLevel()))
        		.append(" ")
        		.append(formatMessage(record));
        
        Throwable thrown = record.getThrown();
        if (thrown != null) {
            if (printStackTrace) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                thrown.printStackTrace(pw);
                pw.close();
                sb.append(", caused by ").append(sw.toString());
            } else {
                sb.append(": ").append(thrown.getClass().getSimpleName());
                String message = thrown.getMessage();
                if (message != null) {
                    sb.append(" (").append(message).append(")");
                }
            }
        }
        
        return sb.append("\n").toString();
    }
    
    public boolean shouldPrintStackTrace() {
        return printStackTrace;
    }

    public void setPrintStackTrace(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }
}
