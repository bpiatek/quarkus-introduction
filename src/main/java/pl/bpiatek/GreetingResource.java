package pl.bpiatek;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "some.property")
    String sth;

    private DbService dbService;

    public GreetingResource(DbService dbService) {
        this.dbService = dbService;
    }

    @Path("/fruit")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<DbService.Fruit>> fruit() {
        return dbService.getFruitNames();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        var unis = new ArrayList<Uni<Void>>();

        unis.add(uniWithDelay());
        unis.add(uniWithDelay());
        unis.add(uniWithDelay());
        unis.add(uniWithDelay());

        return Uni.combine().all().unis(unis).discardItems().replaceWith(sth);
    }

    private Uni<Void> uniWithDelay() {
        return Uni.createFrom().voidItem()
            .onItem().delayIt().by(Duration.ofSeconds(1))
            .invoke(() -> System.out.println(Thread.currentThread().getName()));
    }


}
