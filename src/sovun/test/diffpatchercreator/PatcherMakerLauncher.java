/*
 * Project        NDS Anaconda
 * (c) copyright  2013
 * Company        HARMAN Automotive Systems GmbH
 *        All rights reserved
 *
 * Secrecy Level  STRICTLY CONFIDENTIAL
 *
 * File           PatcherMakerLauncher.java
 * Creation date  9 лип. 2013 <TODO: change format to yyyy-mm-dd>
 */
package sovun.test.diffpatchercreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************************************
 * TODO: add class / interface description
 * 
 * @author avasilkov
 * @since TODO: insert current Anaconda version
 ******************************************************************************/
public class PatcherMakerLauncher
{
    private String patchMakerFileName = "testdbupdate_x64.exe";

    private File patchMakerExecutableFile = new File(patchMakerFileName);

    private File sourceFileName;

    private File targetFileName;

    private File patchFileName;

    private int cacheSize = 6;

    private int wndSize = 2;

    private int gzWndAmount = 16;

    private boolean dump = false;

    public PatcherMakerLauncher(File sourceFileName, File targetFileName,
            File patchFileName)
    {
        super();
        this.sourceFileName = sourceFileName;
        this.targetFileName = targetFileName;
        this.patchFileName = patchFileName;

    }

    private List<String> getCommand()
    {
        List<String> result = new ArrayList<String>();
        result.add(patchMakerExecutableFile.getName());
        result.add("-c");
        result.add("-old");
        result.add(sourceFileName.getAbsolutePath());
        result.add("-new");
        result.add(targetFileName.getAbsolutePath());
        result.add("-cache");
        result.add(String.valueOf(cacheSize));
        result.add("-wndsize");
        result.add(String.valueOf(wndSize));
        result.add("-gz");
        result.add(String.valueOf(gzWndAmount));
        if (dump)
        {
            result.add("-dump");
        }
        return result;
    }

    public int execute() throws IOException
    {
        List<String> command = getCommand();
        ProcessBuilder probuilder = new ProcessBuilder(getCommand());

        // You can set up your work directory
        probuilder.directory(patchMakerExecutableFile.getParentFile());
        Process process = probuilder.start();
        // Read out dir output

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:\n",
                Arrays.asList(command.toArray()));
        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
        }

        // Wait to get exit value
        int exitValue = 1;
        try
        {
            exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return exitValue;
    }

}
