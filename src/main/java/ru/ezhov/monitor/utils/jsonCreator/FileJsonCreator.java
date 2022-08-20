package ru.ezhov.monitor.utils.jsonCreator;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ezhov_da
 */
public class FileJsonCreator {
    private static final Logger LOG = Logger
            .getLogger(FileJsonCreator.class.getName());

    private final String pathToFolderCreateJSon;
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private final String patternIp = "%s.%s.%s.%s";

    public FileJsonCreator(String pathToFolderCreateJSon) {
        this.pathToFolderCreateJSon = pathToFolderCreateJSon;
    }

    public final File create(final int num) throws IOException {

        LOG.info("start create json file..");

        final DataJsonObjectMonitor do1 =
                new DataJsonObjectMonitor(
                        "185.159.131.132",
                        "5:52",
                        52.00,
                        "20G",
                        1048576
                );

        final String date = this.dateFormat.format(new Date());
        final String ip = String.format(this.patternIp, num, num, num, num);

        final String extension = AppConfigInstance.getConfig().fileExtension();

        final String nameFile = date + "_" + ip + extension;

        final File file = new File(
                this.pathToFolderCreateJSon
                        + File.separator
                        + nameFile);

        LOG.info("file create: " + file);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.writeValue(file, do1);

        return file;
    }
}
