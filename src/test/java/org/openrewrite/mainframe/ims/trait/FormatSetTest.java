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
package org.openrewrite.mainframe.ims.trait;

import org.junit.jupiter.api.Test;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.ims.ImsParser;
import org.openrewrite.mainframe.ims.tree.Ims;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * What a format set means: the screen it draws, and the area the program exchanges it through.
 * <p>
 * The join is the point. An {@code MFLD} names a {@code DFLD}, so a field of the program's message
 * area has a place on the screen and a length — and the message maps onto the copybook the program
 * declares it with by order and length, never by name.
 * <p>
 * These parse directly rather than through {@code Assertions.ims}: the name field, the operand field
 * and column 72 are where the meaning is, and the test framework trims the common indentation off a
 * source block.
 */
class FormatSetTest {

    /**
     * One screen of a conversation, in the shape the fixture's {@code CLMF01} is written in.
     */
    private static final String INQUIRY =
      "CLMF01   FMT\n" +
      "         DEV   TYPE=(3270,2),FEAT=IGNORE,SYSMSG=CLMSYS,DSCA=X'00A0'," + blanks(3) + "X\n" +
      "               PFK=(CLMPFK,1='PAGE',3='EXIT')\n" +
      "         DIV   TYPE=INOUT\n" +
      "CLMDP1   DPAGE CURSOR=((3,12)),FILL=PT\n" +
      "         DFLD  'CASCADE MUTUAL - CLAIM INQUIRY',POS=(1,20),ATTR=HI\n" +
      "CLMTRAN  DFLD  POS=(2,2),LTH=8,ATTR=(PROT,HI)\n" +
      "CLMNO    DFLD  POS=(3,12),LTH=10,ATTR=(NUM,HI)\n" +
      "CLMACT   DFLD  POS=(3,38),LTH=1\n" +
      "CLMPFK   DFLD  POS=(23,60),LTH=8,ATTR=(PROT,NODISP)\n" +
      "         FMTEND\n" +
      "*\n" +
      "CLMI1I   MSG   TYPE=INPUT,SOR=(CLMF01,IGNORE),NXT=CLMI1O\n" +
      "         SEG\n" +
      "         MFLD  CLMTRAN,LTH=8\n" +
      "         MFLD  CLMNO,LTH=10\n" +
      "         MFLD  CLMACT,LTH=1\n" +
      "         MFLD  CLMPFK,LTH=8\n" +
      "         MSGEND\n";

    @Test
    void theScreenAFormatSetDraws() {
        FormatSet format = formatSets(INQUIRY).getFirst();

        assertThat(format.getName()).isEqualTo("CLMF01");
        assertThat(format.getLine()).isEqualTo(1);

        Device device = format.getDevices().getFirst();
        assertThat(device.getType()).isEqualTo("3270");
        assertThat(device.getModel()).isEqualTo(2);
        assertThat(device.isPrinter()).isFalse();
        assertThat(device.getFeatures()).containsExactly("IGNORE");
        assertThat(device.getSystemMessageField()).isEqualTo("CLMSYS");
        assertThat(device.getSystemControlArea()).isEqualTo("X'00A0'");
        assertThat(format.getDivisions()).singleElement().extracting(Division::getType).isEqualTo("INOUT");

        DevicePage page = format.getDevicePages().getFirst();
        assertThat(page.getName()).isEqualTo("CLMDP1");
        assertThat(page.getCursorPosition()).isEqualTo(new Position(3, 12));
        assertThat(page.getFill()).isEqualTo("PT");

        // A literal field is as long as its literal and names nothing, so no message can reach it.
        assertThat(page.getFields())
          .extracting(DeviceField::getName, DeviceField::getLiteral, DeviceField::getPosition,
            DeviceField::getLength, DeviceField::isProtected)
          .containsExactly(
            tuple(null, "CASCADE MUTUAL - CLAIM INQUIRY", new Position(1, 20), 30, false),
            tuple("CLMTRAN", null, new Position(2, 2), 8, true),
            tuple("CLMNO", null, new Position(3, 12), 10, false),
            tuple("CLMACT", null, new Position(3, 38), 1, false),
            tuple("CLMPFK", null, new Position(23, 60), 8, true));
        assertThat(page.getField("CLMNO")).isNotNull()
          .extracting(DeviceField::getAttributes).isEqualTo(asList("NUM", "HI"));
    }

    /**
     * The PF keys, which are the only place the words a program tests are written down: the key puts
     * its literal in a named field and the field arrives as an {@code MFLD} of the input message.
     */
    @Test
    void aFunctionKeyPutsItsLiteralInAField() {
        Device device = formatSets(INQUIRY).getFirst().getDevices().getFirst();

        assertThat(device.getFunctionKeyField()).isEqualTo("CLMPFK");
        assertThat(device.getFunctionKeys())
          .extracting(Device.FunctionKey::getNumber, Device.FunctionKey::getLiteral)
          .containsExactly(tuple(1, "PAGE"), tuple(3, "EXIT"));
        assertThat(messages(INQUIRY).getFirst().getFields())
          .extracting(MessageField::getDeviceFieldName).contains("CLMPFK");
    }

    /**
     * A message field is both a place on the screen and a displacement into the area the program
     * declares the message with — which is what lets a copybook be laid over it.
     */
    @Test
    void aMessageFieldIsAScreenFieldAndAPlaceInTheProgramsArea() {
        Message message = messages(INQUIRY).getFirst();

        assertThat(message.getName()).isEqualTo("CLMI1I");
        assertThat(message.isInput()).isTrue();
        assertThat(message.getFormatName()).isEqualTo("CLMF01");
        assertThat(message.getNextName()).isEqualTo("CLMI1O");
        assertThat(message.getFormat()).isNotNull().extracting(FormatSet::getName).isEqualTo("CLMF01");

        // Four fields of 8, 10, 1 and 8, after the four byte prefix IMS supplies and no MFLD writes.
        assertThat(message.getFields())
          .extracting(MessageField::getDeviceFieldName, MessageField::getLength, MessageField::getOffset,
            field -> field.getDeviceField() == null ? null : field.getDeviceField().getPosition())
          .containsExactly(
            tuple("CLMTRAN", 8, 4, new Position(2, 2)),
            tuple("CLMNO", 10, 12, new Position(3, 12)),
            tuple("CLMACT", 1, 22, new Position(3, 38)),
            tuple("CLMPFK", 8, 23, new Position(23, 60)));
        assertThat(message.getLength()).isEqualTo(31);
    }

    /**
     * Two device pages carrying the same labels is how one message layout serves two screens, and it
     * is the way a wrong answer hides here: taking the first field of that name places every field of
     * the continuation on the first page.
     */
    @Test
    void twoDevicePagesCarryTheSameLabels() {
        String source =
          """
          CLMF02   FMT
                   DEV   TYPE=(3270,2),FEAT=IGNORE
                   DIV   TYPE=INOUT
          CLMDP2A  DPAGE CURSOR=((3,12)),FILL=PT
          DTLSEQ1  DFLD  POS=(7,2),LTH=4,ATTR=PROT
          CLMDP2B  DPAGE CURSOR=((3,12)),FILL=PT
          DTLSEQ1  DFLD  POS=(6,2),LTH=4,ATTR=PROT
                   FMTEND
          CLMI2O   MSG   TYPE=OUTPUT,SOR=(CLMF02,IGNORE),NXT=CLMI2I
                   LPAGE SOR=(CLMDP2A)
                   SEG
                   MFLD  DTLSEQ1,LTH=4
                   MSGEND
          CLMI2P   MSG   TYPE=OUTPUT,SOR=(CLMF02,IGNORE),NXT=CLMI2I
                   LPAGE SOR=(CLMDP2B)
                   SEG
                   MFLD  DTLSEQ1,LTH=4
                   MSGEND
          """;

        assertThat(formatSets(source).getFirst().getDevicePages())
          .extracting(DevicePage::getName).containsExactly("CLMDP2A", "CLMDP2B");

        List<Message> messages = messages(source);
        assertThat(messages.getFirst().getLogicalPages()).singleElement()
          .extracting(LogicalPage::getDevicePageName).isEqualTo("CLMDP2A");
        assertThat(messages.getFirst().getFields().getFirst().getDeviceField()).isNotNull()
          .extracting(DeviceField::getPosition).isEqualTo(new Position(7, 2));
        assertThat(messages.get(1).getFields().getFirst().getDeviceField()).isNotNull()
          .extracting(DeviceField::getPosition).isEqualTo(new Position(6, 2));

        // Both messages lay the same field over the same displacement, which is why one copybook
        // serves both.
        assertThat(messages).extracting(Message::getLength).containsExactly(8, 8);
    }

    /**
     * The two shapes an {@code MFLD} takes besides a plain label: a literal, which is how a MID
     * supplies the transaction code, and a device field IMS fills with a system literal, which writes
     * no length of its own.
     */
    @Test
    void aLiteralFieldAndOneFilledBySystem() {
        String source =
          """
          IVTNO    MSG   TYPE=INPUT,SOR=(IVTNOF,IGNORE)
                   SEG
                   MFLD  'IVTNO     ',LTH=10
                   MFLD  (SDATE,DATE2)
                   MSGEND
          IVTNOF   FMT
                   DEV   TYPE=3270-A02,FEAT=IGNORE
                   DIV   TYPE=INOUT
                   DPAGE CURSOR=((10,34))
          SDATE    DFLD  POS=(8,59),LTH=8,ATTR=PROT
                   FMTEND
                   END
          """;

        Message message = messages(source).getFirst();
        assertThat(message.getFields())
          .extracting(MessageField::getDeviceFieldName, MessageField::getLiteral,
            MessageField::getLength, MessageField::getOffset)
          .containsExactly(
            // The literal keeps the blanks it was padded with: they are its length.
            tuple(null, "IVTNO     ", 10, 4),
            tuple("SDATE", null, 8, 14));
        assertThat(message.getLength()).isEqualTo(22);

        // A device page with no label is still the page a field belongs to.
        DeviceField sdate = message.getFields().get(1).getDeviceField();
        assertThat(sdate).isNotNull();
        assertThat(sdate.getDevicePage()).isNotNull().extracting(DevicePage::getName).isNull();
        assertThat(formatSets(source).getFirst().getDevices().getFirst().getType()).isEqualTo("3270-A02");
    }

    /**
     * A message written as several segments is several areas and not one long one: IMS puts its four
     * byte prefix in front of each.
     */
    @Test
    void everySegmentOfAMessageCarriesItsOwnPrefix() {
        Message message = messages(
          """
          CLMI9O   MSG   TYPE=OUTPUT,SOR=(CLMF09,IGNORE)
                   SEG
                   MFLD  HDRLINE,LTH=10
                   SEG
                   MFLD  DTLLINE,LTH=20
                   MSGEND
          """).getFirst();

        assertThat(message.getSegments()).extracting(MessageSegment::getLength, MessageSegment::getOffset)
          .containsExactly(tuple(14, 0), tuple(24, 14));
        assertThat(message.getFields()).extracting(MessageField::getOffset).containsExactly(4, 18);
        assertThat(message.getLength()).isEqualTo(38);
    }

    /**
     * A printed format is sent and never answered, which is what the missing {@code NXT=} says.
     */
    @Test
    void aPrintedFormatIsNotAnswered() {
        String source =
          """
          CLMF06   FMT
                   DEV   TYPE=3270P,FEAT=IGNORE,PAGE=(30,DEFN)
                   DIV   TYPE=OUTPUT
          CLMDP6   DPAGE
          ACKCLM   DFLD  POS=(6,20),LTH=10
                   FMTEND
          CLMI6O   MSG   TYPE=OUTPUT,SOR=(CLMF06,IGNORE)
                   SEG
                   MFLD  ACKCLM,LTH=10
                   MSGEND
          """;

        Device device = formatSets(source).getFirst().getDevices().getFirst();
        assertThat(device.isPrinter()).isTrue();
        assertThat(device.getFunctionKeyField()).isNull();
        assertThat(formatSets(source).getFirst().getDivisions().getFirst().isInput()).isFalse();
        assertThat(formatSets(source).getFirst().getDevicePages().getFirst().getCursorPosition()).isNull();
        assertThat(messages(source).getFirst().getNextName()).isNull();
    }

    private static List<FormatSet> formatSets(String source) {
        return new FormatSet.Matcher().lower(parse(source)).collect(Collectors.toList());
    }

    private static List<Message> messages(String source) {
        return new Message.Matcher().lower(parse(source)).collect(Collectors.toList());
    }

    private static SourceFile parse(String source) {
        List<SourceFile> parsed = ImsParser.builder().build()
          .parseInputs(singletonList(input(source)), null, new InMemoryExecutionContext())
          .collect(Collectors.toList());
        assertThat(parsed).hasSize(1);
        assertThat(parsed.getFirst()).isInstanceOf(Ims.CompilationUnit.class);
        assertThat(parsed.getFirst().printAll()).isEqualTo(source);
        return parsed.getFirst();
    }

    private static Parser.Input input(String source) {
        return new Parser.Input(Path.of("CLMF01.mfs"),
          () -> new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
    }

    private static String blanks(int n) {
        StringBuilder blanks = new StringBuilder();
        for (int i = 0; i < n; i++) {
            blanks.append(' ');
        }
        return blanks.toString();
    }
}
