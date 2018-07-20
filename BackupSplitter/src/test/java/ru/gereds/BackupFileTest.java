package ru.gereds;

import org.junit.Test;
import ru.gereds.enums.Extension;
import ru.gereds.enums.ProgFlags;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class BackupFileTest {
    private String simpleFileSample() {
        StringBuilder sample = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sample.append("O000" + i);
            sample.append(" " + ProgFlags.COMMENTSTART.flag + "File " + i + ProgFlags.COMMENTEND.flag);
            sample.append(System.lineSeparator());
            sample.append(System.lineSeparator());
            sample.append("N10 G54 T0101 ");
            sample.append(System.lineSeparator());
            sample.append("M30");
            sample.append(System.lineSeparator());

        }
        return sample.toString();
    }
    private String exceptionOne() {
        StringBuilder sample = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sample.append("O000" + i);
            sample.append(" " + ProgFlags.COMMENTSTART.flag + "File " + i + ProgFlags.COMMENTEND.flag);
            sample.append(System.lineSeparator());
        }
        return sample.toString();
    }

    private String exceptionTwo() {
        StringBuilder sample = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sample.append("O000" + i);
            sample.append(" " + ProgFlags.COMMENTSTART.flag + "File " + i + ProgFlags.COMMENTEND.flag + " ");
        }
        return sample.toString();
    }

    private String exceptionThree() {
        StringBuilder sample = new StringBuilder();
            sample.append("O0001");
            sample.append(ProgFlags.COMMENTSTART.flag + "File 1" + ProgFlags.COMMENTEND.flag);
            sample.append(System.lineSeparator());
            sample.append(System.lineSeparator());
            sample.append("N10 G54 T0101 ");
            sample.append(System.lineSeparator());
            sample.append("M30");
            sample.append(System.lineSeparator());
            sample.append("O0002");
            sample.append(ProgFlags.COMMENTSTART.flag + "File 2" + ProgFlags.COMMENTEND.flag + " ");
            sample.append("O0003");
            sample.append(" " + ProgFlags.COMMENTSTART.flag + "File 3" + ProgFlags.COMMENTEND.flag + " ");
            sample.append(System.lineSeparator());
            sample.append("O0004");
            sample.append(" " + ProgFlags.COMMENTSTART.flag + "File 4" + ProgFlags.COMMENTEND.flag);
            sample.append(System.lineSeparator());
            sample.append(System.lineSeparator());
            sample.append("N10 G54 T0101 ");
            sample.append(System.lineSeparator());

        return sample.toString();
    }

    @Test
    public void whenFillThenSplitStandardFileToNCFileArray() {
        BackupFile buFile = new BackupFile(simpleFileSample());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buFile.size(); i++) {
            result.append(String.format("%s, ", buFile.get(i).getCommentName()));
        }
        assertThat(result.toString(), is("file 0, file 1, file 2, "));
    }
    /**
     * Exception 1 Some O don't have M30 but new line
     */
    @Test
    public void whenFillThenSplitExceptionOneFileToNCFileArray() {
        BackupFile buFile = new BackupFile(exceptionOne());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buFile.size(); i++) {
            result.append(String.format("%s, ", buFile.get(i).getCommentName()));
        }
        assertThat(result.toString(), is("file 0, file 1, file 2, "));
    }

    /**
     * Exception 2 Some O don't have M30 in one line;
     */
    @Test
    public void whenFillThenSplitExceptionTwoFileToNCFileArray() {
        BackupFile buFile = new BackupFile(exceptionTwo());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buFile.size(); i++) {
            result.append(String.format("%s, ", buFile.get(i).getCommentName()));
        }
        assertThat(result.toString(), is("file 0, file 1, file 2, "));
    }

    /**
     * Exception 3 Mixed;
     */
    @Test
    public void whenFillThenSplitExceptionThreeFileToNCFileArray() {
        BackupFile buFile = new BackupFile(exceptionThree());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buFile.size(); i++) {
            result.append(String.format("%s, ", buFile.get(i).getCommentName()));
        }
        assertThat(result.toString(), is("file 1, file 2, file 3, file 4, "));
    }
}
