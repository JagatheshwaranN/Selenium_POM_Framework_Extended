package com.qa.stf.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.stf.constant.TestConstants;
import com.qa.stf.util.ExceptionHub;
import com.qa.stf.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The ExtentReport class provides utility methods for generating and managing Extent
 * Reports using the ExtentSparkReporter for test automation reporting.
 *
 * <p>Features:
 * <ul>
 *     <li>Creates an Extent Report with a dynamic file name based on the current
 *     date.</li>
 *     <li>Configures report metadata, including title, theme, and name.</li>
 *     <li>Populates system information such as OS, user, and browser details in the
 *     report.</li>
 *     <li>Ensures the necessary report directory is created before generating reports.</li>
 *     <li>Logs detailed steps for successful operations and error scenarios for
 *     troubleshooting.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class
 *       are thrown for descriptive error handling during setup failures.</li>
 *   <li>Comprehensive logging is provided for setup success and errors, using Log4j2.</li>
 * </ul>
 *
 * <p>Note:
 * <ul>
 *   <li>The class assumes the existence of configuration properties required for setup,
 *      such as report paths and browser details.</li>
 *   <li>Users must handle the invocation of the Extent Report setup method in their test
 *      framework lifecycle (e.g., @BeforeSuite in TestNG).</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * {@code
 * ExtentReports extent = ExtentReport.setupExtentReport();
 * // Add test results to the extent instance...
 * extent.flush();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.3
 */
public class ExtentReport {

    // Logger instance for the ExtentReport class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(ExtentReport.class);

    // Static instance of ExtentReports to manage and generate test execution reports
    public static ExtentReports extentReports;

    /**
     * Sets up and initializes the Extent Report with a Spark Reporter.
     *
     * <p>This method creates the report directory if it doesn't exist, initializes the ExtentSparkReporter
     * with a formatted file path including the current date, and sets up basic configurations
     * like document title, theme, and report name. System information such as OS, user, and browser details
     * is also included in the report metadata.</p>
     *
     * @return Initialized {@link ExtentReports} instance.
     * @throws ExceptionHub.ExtentException If there is an error during setup.
     */
    public static ExtentReports setupExtentReport() {
        try {
            log.info("Initializing Extent Report setup.");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TestConstants.DATE_FORMAT);
            String actualDate = simpleDateFormat.format(new Date());
            log.debug("Formatted date for report: {}", actualDate);

            ExtentSparkReporter extentSparkReporter = getExtentSparkReporter(actualDate);
            log.info("ExtentSparkReporter initialized with path: {}", extentSparkReporter.getFile().getAbsolutePath());

            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);

            extentSparkReporter.config().setDocumentTitle(TestConstants.EXTENT_REPORT_TITLE);
            extentSparkReporter.config().setTheme(Theme.DARK);
            extentSparkReporter.config().setReportName(TestConstants.EXTENT_REPORT_NAME);
            log.debug("ExtentSparkReporter configuration set: Title - {}, Theme - {}, Name - {}",
                    TestConstants.EXTENT_REPORT_TITLE, Theme.DARK, TestConstants.EXTENT_REPORT_NAME);

            extentReports.setSystemInfo(TestConstants.EXTENT_REPORT_OS_INFO, System.getProperty("os.name"));
            extentReports.setSystemInfo(TestConstants.EXTENT_REPORT_USER_INFO, System.getProperty("user.name"));
            String browserInfo = FileReader.getDataFromPropFile("Browser");
            extentReports.setSystemInfo(TestConstants.EXTENT_REPORT_BROWSER_INFO,
                    browserInfo != null ? browserInfo : "Unknown");
            log.info("System information added to the report.");

            log.info("Extent Report setup completed successfully.");
            return extentReports;

        } catch (IOException ex) {
            log.error("Error occurred during Extent Report setup: {}", ex.getMessage(), ex);
            throw new ExceptionHub.ExtentException("Error setting up Extent Report: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates and returns an ExtentSparkReporter instance with the configured file path.
     *
     * <p>This method ensures that the report directory exists before initializing the
     * reporter.</p>
     *
     * @param actualDate The formatted date to include in the report file name.
     * @return Configured {@link ExtentSparkReporter} instance.
     * @throws IOException If the directory creation fails or an I/O error occurs.
     */
    private static ExtentSparkReporter getExtentSparkReporter(String actualDate) throws IOException {
        log.debug("Ensuring report directory exists.");
        File reportDir = new File(TestConstants.CWD + TestConstants.EXTENT_REPORT_PATH);
        if (!reportDir.exists() && !reportDir.mkdirs()) {
            log.error("Failed to create report directory: {}", reportDir.getAbsolutePath());
            throw new IOException("Failed to create directory: " + reportDir.getAbsolutePath());
        }
        String reportPath = String.format(TestConstants.CWD + TestConstants.EXTENT_REPORT_PATH
                + TestConstants.EXTENT_REPORT_FILE_NAME, actualDate);
        log.info("Report path set to: {}", reportPath);
        return new ExtentSparkReporter(reportPath);
    }

}