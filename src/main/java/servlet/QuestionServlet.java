package servlet;
import domain.Quiz;
import domain.ShortQuestion;
import domain.Question;

import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/questions")
public class QuestionServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("mysql");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Create a Question</h2>");
        out.println("<form method='post'>");
        out.println("Type: <select name='type'><option value='QUIZ'>Quiz</option><option value='SHORT'>Short Question</option></select><br>");
        out.println("Content: <input type='text' name='content'><br>");
        out.println("Options (comma-separated, for Quiz): <input type='text' name='options'><br>");
        out.println("<input type='submit' value='Create Question'>");
        out.println("</form>");
        out.println("<h2>Questions:</h2>");

        // Retrieve and display all questions
        EntityManager em = emf.createEntityManager();
        List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        for (Question q : questions) {
            out.println("<p>" + q.getContent() + " (Type: " + q.getClass().getSimpleName() + ")</p>");
        }
        em.close();

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String content = request.getParameter("content");
        String optionsParam = request.getParameter("options");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        if ("QUIZ".equals(type)) {
            List<String> options = new ArrayList<>();
            if (optionsParam != null && !optionsParam.isEmpty()) {
                String[] optionsArray = optionsParam.split(",");
                for (String option : optionsArray) {
                    options.add(option.trim());
                }
            }
            Quiz quiz = new Quiz(content, options);
            em.persist(quiz);
        } else if ("SHORT".equals(type)) {
            ShortQuestion shortQuestion = new ShortQuestion(content, "");
            em.persist(shortQuestion);
        }

        em.getTransaction().commit();
        em.close();

        response.sendRedirect("questions"); // Redirect to the GET method to see the updated list
    }

    @Override
    public void destroy() {
        emf.close(); // Clean up the EntityManagerFactory
    }
}
