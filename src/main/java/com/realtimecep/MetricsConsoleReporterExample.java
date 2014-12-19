package com.realtimecep;

import com.codahale.metrics.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on 14. 12. 18..
 *
 * @author <a href="iamtedwon@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class MetricsConsoleReporterExample {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) throws InterruptedException {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();

        // wait infinitely
        synchronized (MetricsConsoleReporterExample.class) {
            MetricsConsoleReporterExample.class.wait();
        }
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }
}
