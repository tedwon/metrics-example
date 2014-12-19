package com.realtimecep;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created on 14. 12. 18..
 *
 * @author <a href="iamtedwon@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class MetricsJMXReporterExample {

    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    private final String name = UUID.randomUUID().toString().replaceAll("[{\\-}]", "");
    private final MetricRegistry metrics = new MetricRegistry();

    private final JmxReporter reporter = JmxReporter.forRegistry(metrics)
            .registerWith(mBeanServer)
            .inDomain(name)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .convertRatesTo(TimeUnit.SECONDS)
            .filter(MetricFilter.ALL)
            .inDomain("mydomain")
            .build();

    public static void main(String[] args) throws InterruptedException {
        MetricsJMXReporterExample example = new MetricsJMXReporterExample();
        example.test();

        // wait infinitely
        synchronized (MetricsJMXReporterExample.class) {
            MetricsJMXReporterExample.class.wait();
        }
    }

    void test() {
        Meter meter = metrics.meter("requests");
        metrics.register("meter", meter);
        reporter.start();
        Meter requests = metrics.meter("requests");
        requests.mark();
    }
}
