package project.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import project.service.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatsInterceptor implements HandlerInterceptor {

  private final StatsService statsService;

  public StatsInterceptor(StatsService statsService) {
    this.statsService = statsService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    this.statsService.incRequestCount();
    return true;
  }
}
