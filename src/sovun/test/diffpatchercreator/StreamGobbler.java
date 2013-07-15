/*
 * Project        NDS Anaconda
 * (c) copyright  2013
 * Company        HARMAN Automotive Systems GmbH
 *        All rights reserved
 *
 * Secrecy Level  STRICTLY CONFIDENTIAL
 *
 * File           StreamGobbler.java
 * Creation date  2013-07-09
 */
package sovun.test.diffpatchercreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*******************************************************************************
 * The thread that reads input stream into custom reader.
 *
 * @author AVasilkov
 * @since  4.3
 ******************************************************************************/
public class StreamGobbler extends Thread {
    InputStream is;
    String type;

    private StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                System.out.println(type + "> " + line);
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}