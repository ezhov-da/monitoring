package ru.ezhov.monitor.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class FileNamePatternTreatmentTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public final void treatmentErrorFile() throws Exception {

        this.expectedException.expectMessage(
                "[wefwfasgasg] - Not correct name file for treatment."
                        + " Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        this.expectedException.expect(IllegalArgumentException.class);

        final FileNamePatternTreatment fileNamePatternTreatment =
                new FileNamePatternTreatment("wefwfasgasg");
        final FileJsonName fileJsonName = fileNamePatternTreatment.treatment();

    }

    @Test
    public final void treatmentSecondErrorFile() throws Exception {
        this.expectedException.expectMessage(
                "[12313.13.13123.] - Not correct name file for treatment."
                        + " Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        this.expectedException.expect(IllegalArgumentException.class);


        final FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("12313.13.13123.");
        final FileJsonName fileJsonName = fileNamePatternTreatment.treatment();
    }

    @Test
    public final void treatmentThirdErrorFile() throws Exception {
        this.expectedException.expectMessage(
                "[20170721000803_123.123.123.1235] - "
                        + "Not correct name file for treatment. "
                        + "Use yyyyMMddHHmmss_ip.ip.ip.ip template.");
        this.expectedException.expect(IllegalArgumentException.class);

        final FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("20170721000803_123.123.123.1235");
        final FileJsonName fileJsonName
                = fileNamePatternTreatment.treatment();

    }

    @Test
    public final void treatmentGoodFile() throws Exception {

        final FileNamePatternTreatment fileNamePatternTreatment
                = new FileNamePatternTreatment("20170721000803_123.123.123.123.json");
        final FileJsonName fileJsonName = fileNamePatternTreatment.treatment();

        assertEquals("FileJsonName{date=Fri Jul 21 00:08:03 MSK 2017,"
                + " ip=123.123.123.123}", fileJsonName.toString());
    }

}
