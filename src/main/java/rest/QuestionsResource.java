package rest;

import domain.Question;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Path("questions")
@Produces({"application/json", "application/xml"})
public class QuestionsResource {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    // Retrieve a specific question by ID
    @GET
    @Path("/{id}")
    public Response getQuestionById(@PathParam("id") Long id) {
        Question question = entityManager.find(Question.class, id);
        if (question != null) {
            return Response.ok(question).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Retrieve all questions, optionally filtered by type
    @GET
    @Path("/")
    public Response getAllQuestions(@QueryParam("type") String type) {
        TypedQuery<Question> query;
        if (type != null && !type.isEmpty()) {
            query = entityManager.createNamedQuery("Question.findByType", Question.class);
            query.setParameter("type", type);  // Use type as discriminator value (e.g., "MultipleChoiceQuestion")
        } else {
            query = entityManager.createQuery("SELECT q FROM Question q", Question.class);
        }
        List<Question> questionsList = query.getResultList();
        return Response.ok(questionsList).build();
    }

    // Create a new question (you would use a concrete subclass in practice)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createQuestion(Question question) {
        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();
        return Response.status(Response.Status.CREATED).entity(question).build();
    }

    // Update an existing question by ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@PathParam("id") Long id, Question questionData) {
        entityManager.getTransaction().begin();
        Question existingQuestion = entityManager.find(Question.class, id);
        if (existingQuestion == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingQuestion.setContent(questionData.getContent());
        entityManager.getTransaction().commit();
        return Response.ok(existingQuestion).build();
    }

    // Delete a question by ID
    @DELETE
    @Path("/{id}")
    public Response deleteQuestion(@PathParam("id") Long id) {
        entityManager.getTransaction().begin();
        Question question = entityManager.find(Question.class, id);
        if (question == null) {
            entityManager.getTransaction().commit();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.remove(question);
        entityManager.getTransaction().commit();
        return Response.noContent().build();
    }
}
