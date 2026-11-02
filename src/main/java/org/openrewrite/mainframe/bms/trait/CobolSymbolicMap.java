/*
 * Copyright 2026 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.mainframe.bms.trait;

import org.jspecify.annotations.Nullable;
import org.openrewrite.mainframe.bms.tree.Bms;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Writes the COBOL records that assembling a map set with {@code TYPE=DSECT} generates, laid out as
 * the BMS macros write them.
 */
final class CobolSymbolicMap {

    private final StringBuilder out = new StringBuilder();

    /**
     * Numbers each repeated field's output group, counting across the whole map set.
     */
    private int tables;

    private CobolSymbolicMap() {
    }

    static @Nullable String of(Mapset mapset) {
        String language = mapset.getLanguage();
        if (language == null || !language.trim().toUpperCase(Locale.ROOT).startsWith("COBOL")) {
            return null;
        }
        List<MapDefinition> maps = mapset.getMaps();
        List<List<Field>> fields = new ArrayList<>(maps.size());
        for (MapDefinition map : maps) {
            List<Field> named = map.getNamedFields();
            for (Field field : named) {
                // A field group's layout is not known, so a map set with one gets no symbolic map.
                if (field.getTree().getParameter("GRPNAME") != null) {
                    return null;
                }
            }
            fields.add(named);
        }

        String mode = upper(mapset.getMode());
        boolean input = "IN".equals(mode) || "INOUT".equals(mode);
        boolean output = !"IN".equals(mode);
        boolean automatic = "AUTO".equals(upper(mapset.getStorage()));
        String base = upper(Operands.textOf(mapset.getTree(), "BASE"));

        CobolSymbolicMap symbolicMap = new CobolSymbolicMap();
        String first = null;
        for (int i = 0; i < maps.size(); i++) {
            MapDefinition map = maps.get(i);
            boolean prefix = prefix(mapset, map);
            if (fields.get(i).isEmpty() && !prefix) {
                continue;
            }
            Set<Field.Subfield> attributes = attributes(mapset, map);
            String name = map.getName().toUpperCase(Locale.ROOT);
            String inputRecord = name + Field.Subfield.INPUT.getSuffix();
            String outputRecord = name + Field.Subfield.OUTPUT.getSuffix();
            // Without STORAGE=AUTO the maps share storage. It is the BASE the program declares,
            // else the first map's records.
            String shared = base != null ? base : automatic ? null : first;
            if (input) {
                symbolicMap.record(inputRecord, shared, prefix);
                symbolicMap.inputFields(fields.get(i), attributes);
            }
            if (output) {
                symbolicMap.record(outputRecord, input ? inputRecord : shared, prefix);
                symbolicMap.outputFields(fields.get(i), attributes, input);
            }
            if (first == null) {
                first = input ? inputRecord : outputRecord;
            }
        }
        return first == null ? null : symbolicMap.out.toString();
    }

    /**
     * Whether a map's records begin with the 12-byte terminal prefix, from its own {@code TIOAPFX},
     * else the map set's default. A null map asks for that default.
     */
    static boolean prefix(Mapset mapset, @Nullable MapDefinition map) {
        String tioapfx = map == null ? null : upper(Operands.textOf(map.getTree(), "TIOAPFX"));
        if (tioapfx == null) {
            tioapfx = upper(Operands.textOf(mapset.getTree(), "TIOAPFX"));
        }
        return tioapfx == null ? "AUTO".equals(upper(mapset.getStorage())) : "YES".equals(tioapfx);
    }

    /**
     * The attribute bytes each field of a map carries, from its own {@code DSATTS} or
     * {@code EXTATT}, else the map set's default. A null map asks for that default.
     * {@code EXTATT=YES} carries colour, programmed symbols, highlighting and validation.
     */
    static Set<Field.Subfield> attributes(Mapset mapset, @Nullable MapDefinition map) {
        Set<Field.Subfield> attributes = map == null ? null : attributes(map.getTree());
        if (attributes == null) {
            attributes = attributes(mapset.getTree());
        }
        return attributes == null ? EnumSet.noneOf(Field.Subfield.class) : attributes;
    }

    private static @Nullable Set<Field.Subfield> attributes(Bms.MacroStatement statement) {
        if (statement.getParameter("DSATTS") != null) {
            Set<Field.Subfield> attributes = EnumSet.noneOf(Field.Subfield.class);
            for (String attribute : Operands.listOf(statement, "DSATTS")) {
                Field.Subfield subfield = attribute(attribute);
                if (subfield != null) {
                    attributes.add(subfield);
                }
            }
            return attributes;
        }
        String extatt = upper(Operands.textOf(statement, "EXTATT"));
        if (extatt == null) {
            return null;
        }
        return "YES".equals(extatt) ?
                EnumSet.of(Field.Subfield.COLOR, Field.Subfield.PROGRAMMED_SYMBOLS,
                        Field.Subfield.HIGHLIGHT, Field.Subfield.VALIDATION) :
                EnumSet.noneOf(Field.Subfield.class);
    }

    private static Field.@Nullable Subfield attribute(String operand) {
        switch (operand.trim().toUpperCase(Locale.ROOT)) {
            case "COLOR":
                return Field.Subfield.COLOR;
            case "PS":
                return Field.Subfield.PROGRAMMED_SYMBOLS;
            case "HILIGHT":
                return Field.Subfield.HIGHLIGHT;
            case "VALIDN":
                return Field.Subfield.VALIDATION;
            case "OUTLINE":
                return Field.Subfield.OUTLINE;
            case "SOSI":
                return Field.Subfield.SOSI;
            case "TRANSP":
                return Field.Subfield.TRANSPARENCY;
            default:
                return null;
        }
    }

    private void record(String name, @Nullable String redefined, boolean prefix) {
        line("       01  " + name + (redefined == null ? "" : " REDEFINES " + redefined) + ".");
        if (prefix) {
            line("           02  FILLER PIC X(12).");
        }
    }

    private void inputFields(List<Field> fields, Set<Field.Subfield> attributes) {
        for (Field field : fields) {
            String name = name(field);
            Integer occurs = field.getOccurs();
            if (occurs != null && occurs > 1) {
                line("           02  " + name + "D OCCURS " + occurs + " TIMES.");
                line("             03  " + name + "L    COMP  PIC  S9(4).");
                line("             03  " + name + "F    PICTURE X.");
                if (!attributes.isEmpty()) {
                    line("             03  FILLER   PICTURE X(" + attributes.size() + ").");
                }
                line("             03  " + name + "I  PIC " + picture(field, field.getPictureIn()) + ".");
                continue;
            }
            line("           02  " + name + "L    COMP  PIC  S9(4).");
            line("           02  " + name + "F    PICTURE X.");
            line("           02  FILLER REDEFINES " + name + "F.");
            line("             03 " + name + "A    PICTURE X.");
            if (!attributes.isEmpty()) {
                line("           02  FILLER   PICTURE X(" + attributes.size() + ").");
            }
            line("           02  " + name + "I  PIC " + picture(field, field.getPictureIn()) + ".");
        }
    }

    /**
     * With an input record, the attribute redefines the flag there. Without one, the output record
     * holds the attribute where the flag would be.
     */
    private void outputFields(List<Field> fields, Set<Field.Subfield> attributes, boolean input) {
        for (Field field : fields) {
            String name = name(field);
            Integer occurs = field.getOccurs();
            String indent = "           02  ";
            if (occurs != null && occurs > 1) {
                line("           02  DFHMS" + ++tables + " OCCURS " + occurs + " TIMES.");
                line("             03  FILLER PICTURE X(2).");
                line("             03  " + name + "A    PICTURE X.");
                indent = "             03  ";
            } else if (input) {
                line("           02  FILLER PICTURE X(3).");
            } else {
                line("           02  FILLER PICTURE X(2).");
                line("           02  " + name + "A    PICTURE X.");
            }
            for (Field.Subfield attribute : attributes) {
                line(indent + name + attribute.getSuffix() + "    PICTURE X.");
            }
            line(indent + name + "O  PIC " + picture(field, field.getPictureOut()) + ".");
        }
    }

    private static String name(Field field) {
        //noinspection DataFlowIssue
        return field.getName().toUpperCase(Locale.ROOT);
    }

    private static String picture(Field field, @Nullable String picture) {
        if (picture != null) {
            return picture;
        }
        Integer length = field.getLength();
        if (length == null) {
            String written = field.getPictureOut() != null ? field.getPictureOut() : field.getPictureIn();
            length = written == null ? 1 : positions(written);
        }
        return "X(" + length + ")";
    }

    /**
     * How many characters a picture takes on the screen, which is how long a field written without
     * {@code LENGTH} is. A sign or an implied decimal point takes none.
     */
    private static int positions(String picture) {
        int positions = 0;
        for (int i = 0; i < picture.length(); i++) {
            char symbol = Character.toUpperCase(picture.charAt(i));
            int close = picture.indexOf(')', i + 1);
            if (i + 1 < picture.length() && picture.charAt(i + 1) == '(' && close > 0) {
                Integer repeat = Operands.integerOf(picture.substring(i + 2, close));
                positions += repeat == null ? 1 : repeat;
                i = close;
            } else if (symbol != 'S' && symbol != 'V') {
                positions++;
            }
        }
        return positions;
    }

    private static @Nullable String upper(@Nullable String text) {
        return text == null ? null : text.trim().toUpperCase(Locale.ROOT);
    }

    private void line(String text) {
        out.append(text).append('\n');
    }
}
