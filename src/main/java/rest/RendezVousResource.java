package rest;

import domain.RendezVous;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

@Path("rendezvous")
@Produces({"application/json", "application/xml"})
public class RendezVousResource {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @GET
    @Path("/{id}")
    public Response getRendezVousById(@PathParam("id") Long id) {
        RendezVous rdv = entityManager.find(RendezVous.class, id);
        if (rdv != null) {
            return Response.ok(rdv).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/")
    public Response getAllRendezVous() {
        List<RendezVous> rendezvousList = entityManager.createQuery("SELECT r FROM RendezVous r", RendezVous.class).getResultList();
        return Response.ok(rendezvousList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRendezVous(RendezVous rdv) {
        entityManager.getTransaction().begin();
        entityManager.persist(rdv);
        entityManager.getTransaction().commit();
        return Response.status(Response.Status.CREATED).entity(rdv).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRendezVous(@PathParam("id") Long id, RendezVous rdv) {
        entityManager.getTransaction().begin();
        RendezVous existingRendezVous = entityManager.find(RendezVous.class, id);
        if (existingRendezVous == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingRendezVous.setTitle(rdv.getTitle());
        existingRendezVous.setDescription(rdv.getDescription());
        existingRendezVous.setDateTime(rdv.getDateTime());
        entityManager.getTransaction().commit();
        return Response.ok(existingRendezVous).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRendezVous(@PathParam("id") Long id) {
        entityManager.getTransaction().begin();
        RendezVous rdv = entityManager.find(RendezVous.class, id);
        if (rdv == null) {
            entityManager.getTransaction().commit();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.remove(rdv);
        entityManager.getTransaction().commit();
        return Response.noContent().build();
    }
}
