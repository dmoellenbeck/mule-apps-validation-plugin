package com.mulesoft.services.tools.validation.plugin;

import com.mulesoft.services.tools.validation.ValidationExecutor;
import com.mulesoft.services.tools.validation.report.MessageConstants;
import com.mulesoft.services.tools.validation.report.ReportBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo( name="validate" )
public class ValidateMojo extends AbstractMojo {

    @Parameter( property = "rules.path" )
    private File rulesPath;

    @Parameter( property = "rules.pattern", defaultValue = "^apps-rules(.*).xml" )
    private String rulesPattern;

    @Parameter( property = "source.path", defaultValue = "/src/main/app" )
    private String sourcePath;

    @Parameter( property = "config.file.pattern", defaultValue = "^(.*).xml" )
    private String configFilePattern;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    /**
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        synchronized (ReportBuilder.MUTEX) {

            try {

                ArrayList<String> rules = getRules();
                ArrayList<String> files = getFiles(getSourceFolderPath());

                ValidationExecutor validationExecutor = new ValidationExecutor();
                validationExecutor.setRulesPath(rules);
                validationExecutor.setFiles(files);

                boolean overallStatus = validationExecutor.execute();
                printReport(MessageConstants.PASS);

                if (!overallStatus) {
                    printReport(MessageConstants.FAIL);
                    throw new MojoExecutionException("Error validating Mule Application");
                }

            } catch (Exception ex) {
                throw new MojoExecutionException("Error validating Mule Application", ex);
            }
        }

    }

    /**
     * Returns the rules paths.
     *
     * @return list of rules
     * @throws Exception
     */
    private ArrayList<String> getRules() throws Exception {
        List<String> fileNames = getResourceFiles();

        ArrayList<String> rules = new ArrayList<>();

        for (String filename : fileNames) {
            if (filename.trim().matches(rulesPattern)) {
                rules.add(rulesPath.toString().concat("/").concat(filename));
            }
        }

        return rules;
    }

    /**
     * Returns the rules resources.
     *
     * @return list of filenames
     * @throws Exception
     */
    private List<String> getResourceFiles() throws Exception {
        List<String> fileNames = new ArrayList<>();

        for ( File file : rulesPath.listFiles() ) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }

    /**
     * Returns Mule project configuration files.
     *
     * @param path
     * @return list of filenames
     * @throws Exception
     */
    private ArrayList<String> getFiles(String path) throws Exception{

        ArrayList<String> files = new ArrayList<>();
        File sources = new File(path);

        for ( File file : sources.listFiles() ) {
            if (file.getName().trim().matches(configFilePattern)) {
                files.add(file.toString());
            }
        }

        return files;
    }

    /**
     * Returns absolute path to project 'src/main/app' folder.
     *
     * @return absolute path
     */
    private String getSourceFolderPath() {

        return project.getModel().getProjectDirectory().toString().concat(sourcePath);
    }

    /**
     * Print the results of the plugin execution to the console log.
     *
     * @param result result of the validation (PASS or FAIL)
     */
    private void printReport(String result) {

        ReportBuilder.getReportLines(result).forEach(line -> {
            if (MessageConstants.PASS.equals(result)) {
                getLog().info(line);
            } else {
                getLog().error(line);
            }
        });

    }

}
