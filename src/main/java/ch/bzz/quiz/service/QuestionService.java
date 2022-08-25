package ch.bzz.quiz.service;
import ch.bzz.quiz.data.DataHandler;
import ch.bzz.quiz.model.Question;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("question")
public class QuestionService {
    /** @return List of all question
     * @return question as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response questionList() {
        List<Question> questionList = DataHandler.getInstance().readAllQuessions();
        return Response
                .status(200)
                .entity(questionList)
                .build();
    }

    /**
     * reads a question identified by the ID
     * @param questionID the key
     * @return question
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readQuestion(
            @QueryParam("questionID") int questionID
    ) {
        int httpStatus = 200;
        Question question = DataHandler.getInstance().readQuestionByID(questionID);
        if (question == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(question)
                .build();
    }


    /**
     * inserts a new question
     * @return Response
     */
    @PUT
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPublisher(
            @Valid @BeanParam Question question,
            @NotEmpty
            @FormParam("questionID") int questionID
    ) {
        question.setQuestionID(questionID);
        DataHandler.getInstance().insertQuestion(question);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a question
     * @param questionID the questionID
     * @param questionText the questionText
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateQuestion(
            @FormParam("questionID") int questionID,
            @FormParam("questionText") String questionText
    ) {
        int httpStatus = 200;
        Question question = DataHandler.getInstance().readQuestionByID(questionID);
        if (question != null) {
            question.setQuestionText(questionText);

            DataHandler.getInstance().updateQuestion();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a question identified by its ID
     * @param questionID  the questionID
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteQuestion(
            @QueryParam("questionID") int questionID
    ) {
        int httpStatus = 200;
        if (!DataHandler.getInstance().deleteQuestion(questionID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}