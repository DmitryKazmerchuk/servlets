package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String pathFiles = "/api/posts";
  private static final String pathFilesWithNumber = "/api/posts/\\d+";
  private static final String pathId = "/";



  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(pathFiles)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(pathFilesWithNumber)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(pathId)));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST) && path.equals(pathFiles)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(pathFilesWithNumber)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(pathId)));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

