package ru.gereds;
import org.junit.Test;
import ru.gereds.enums.Extension;
import ru.gereds.enums.ProgFlags;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NCProgrammeTest {
    private static final String ONAME = "O0001";
    private static final String MEMNAME = " TEST NC Code ";
    private String buildNormalSample() {
        StringBuilder sample = new StringBuilder();
        sample.append(ONAME);
        sample.append(System.lineSeparator());
        sample.append(" " + ProgFlags.COMMENTSTART.flag + " " + MEMNAME + ProgFlags.COMMENTEND.flag);
        sample.append(System.lineSeparator());
        sample.append("N10 G54 T0101 ");
        sample.append(System.lineSeparator());
        sample.append("M30");
        return sample.toString();
    }

    private String buildNoCommentSample() {
        StringBuilder sample = new StringBuilder();
        sample.append(ONAME);
        sample.append(System.lineSeparator());
        sample.append(System.lineSeparator());
        sample.append("N10 G54 T0101 ");
        sample.append(System.lineSeparator());
        sample.append("M30");
        return sample.toString();
    }

    @Test
    public void whenInitializedThenCanBuildAFileNameFromComment() {
        NCProgramme prog = new NCProgramme(this.buildNormalSample());
        String result = prog.buildFileName(true);
        assertThat(result, is(String.format("%s.%s", MEMNAME.toLowerCase().trim(), Extension.NC.name)));

    }

    @Test
    public void whenInitializedThenCanBuildAFileNameFromOName() {
        NCProgramme prog = new NCProgramme(this.buildNormalSample());
        String result = prog.buildFileName(false);
        assertThat(result, is(String.format("%s.%s", ONAME, Extension.NC.name)));

    }

    @Test
    public void whenInitializedThenCanBuildFileNameFromNoCommentFile() {
        NCProgramme prog = new NCProgramme(this.buildNoCommentSample());
        String result = prog.buildFileName(true);
        assertThat(result, is(String.format("%s.%s", "no comment", Extension.NC.name)));
    }
}