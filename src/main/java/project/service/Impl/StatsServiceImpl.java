package project.service.Impl;

import org.springframework.stereotype.Service;
import project.service.StatsService;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsServiceImpl implements StatsService {

  private AtomicInteger requestCount = new AtomicInteger(0);
  private Instant startedOn = Instant.now();

  public void incRequestCount() {
    requestCount.incrementAndGet();
  }

  public int getRequestCount() {
    return requestCount.intValue();
  }

  public Instant getStartedOn() {
    return startedOn;
  }



}
