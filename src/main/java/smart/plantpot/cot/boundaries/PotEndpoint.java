package smart.plantpot.cot.boundaries;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import smart.plantpot.cot.controllers.PlantState;
import smart.plantpot.cot.entities.Pot;
import smart.plantpot.cot.entities.User;
import smart.plantpot.cot.repositories.PotRepository;
import smart.plantpot.cot.repositories.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
@Path("/pot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PotEndpoint {

    @Inject
    private PotRepository repository;
    private UserRepository userRepository;

    @GET
    public List<Pot> finAll(){
        return repository.findAll().collect(toList());
    }
    @POST
    public Response save(String id , String name , String plantType, String state , String user_id){
        final User user=userRepository.findById(user_id).orElseThrow();
        PlantState plantState= PlantState.valueOf(state);
        if (user==null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("user not found").build();
        }
       try {
           Pot pot= new Pot(id,name,plantType,plantState,user);
           repository.save(pot);
           return Response.ok().entity("Pot created successfully").build();
       } catch(Exception e){
           return Response.ok().entity("exeption"+e.getMessage()).build();
        }

    }

}
