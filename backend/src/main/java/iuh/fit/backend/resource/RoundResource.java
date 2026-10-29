package iuh.fit.backend.resource;

import iuh.fit.backend.dto.QuestionForLearnerResponse;
import iuh.fit.backend.dto.RoundResponse;
import iuh.fit.backend.dto.SubmissionRequest;
import iuh.fit.backend.dto.SubmissionResponse;
import iuh.fit.backend.service.RoundService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rounds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoundResource {
    private final RoundService roundService = new RoundService();

    @GET
    public Response getRounds() {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        List<RoundResponse> rounds = roundService.getRounds();
        return Response.ok(rounds).build();
    }

    @GET
    @Path("/{roundId}/questions")
    public Response getRoundQuestions(@PathParam("roundId") Long roundId) {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        List<QuestionForLearnerResponse> questions = roundService.getQuestionsForRound(roundId);
        return Response.ok(questions).build();
    }

    @POST
    @Path("/{roundId}/submissions")
    public Response submitAnswers(@PathParam("roundId") Long roundId, @Valid SubmissionRequest request) {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        SubmissionResponse response = roundService.submitAnswers(roundId, request);
        return Response.ok(response).build();
    }
}

