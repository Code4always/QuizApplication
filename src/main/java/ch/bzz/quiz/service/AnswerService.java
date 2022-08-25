package ch.bzz.quiz.service;

import ch.bzz.quiz.data.DataHandler;
import ch.bzz.quiz.model.Answer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @Author: Parwiz
 * @Date: 2022-05-18
 * @Since 1.0.0-SNAPSHOT
 *
*/
@Path("answer")
public class AnswerService {
 /**
  * @return List of all Answer
 * @return answers as JSON
 */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response answerList() {
        List<Answer> answersList = DataHandler.getInstance().readAllAnswers();
        return Response
                .status(200)
                .entity(answersList)
                .build();
    }

    @GET
    @Path("read")
    @Produces({MediaType.APPLICATION_JSON})
    public Response readAnswersByID(
            @QueryParam("answerID") int answerID
    ){
        int httpStatus = 200;
        Answer answer = DataHandler.getInstance().readAnswerByID(answerID);
        if (answer == null){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(answer)
                .build();
    }
    /**
     * inserts a new book
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insetAnswer(
            @Valid @BeanParam Answer answer,
            @NotEmpty
            @FormParam("answerID") int answerID
    ) {
        answer.setAnswerID(answerID);
        DataHandler.getInstance().insertAnswer(answer);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a answer
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAnswer(
            @Valid @BeanParam Answer answer,
            @NotEmpty
            @FormParam("questionID") int questionID
    ) {
        int httpStatus = 200;
        Answer oldAnswer = DataHandler.getInstance().readAnswerByID(answer.getAnswerID());
        if (oldAnswer != null) {
            oldAnswer.setAnswerID(answer.getAnswerID());
            oldAnswer.setAnswerText(answer.getAnswerText());
            oldAnswer.setCorrectAnswer(answer.getCorrectAnswer());
            oldAnswer.setPossibleAnswer(answer.getPossibleAnswer());

            DataHandler.getInstance().updateAnswer();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a answer identified by its byID
     * @param answerID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAnswer(
            @NotEmpty
            @FormParam("answerID") int answerID
    ) {
        int httpStatus = 200;
        if (!DataHandler.getInstance().deleteAnswer(answerID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}