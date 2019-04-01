/*
 * Copyright (C) 2019 maximen39
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.maximen.aliexpressparser;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author maximen39
 */
public class CsvUtils {
    private static final char SEPARATOR = ',';
    private static final char NEW_LINE = '\n';
    private static final char QUOTE = '\"';

    public static void writeLine(Writer writer, List<String> columns) throws IOException {
        writeLine(writer, columns, SEPARATOR, QUOTE);
    }

    public static void writeLine(Writer writer, List<String> columns, char separator) throws IOException {
        writeLine(writer, columns, separator, QUOTE);
    }

    private static String replaceQuote(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer writer, List<String> columns, char separator, char quote) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        boolean first = true;
        for (String value : columns) {
            if (!first) {
                stringBuilder.append(separator);
            }
            stringBuilder
                    .append(quote)
                    .append(replaceQuote(value))
                    .append(quote);
            first = false;
        }
        stringBuilder.append(NEW_LINE);

        writer.write(stringBuilder.toString());
    }
}
