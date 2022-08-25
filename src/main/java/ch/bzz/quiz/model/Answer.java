package ch.bzz.quiz.model;

import ch.bzz.quiz.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

@Getter
@Setter
public class Answer {

    @FormParam("answerID")
    @DecimalMax(value="999")
    @DecimalMin(value="1")
    private int answerID;

    @FormParam("answerText")
    @NotEmpty
    @Size(min=20, max=100)
    private String answerText;

    @DecimalMax(value="4")
    @DecimalMin(value="1")
    @FormParam("correctAnswer")
    @NotEmpty
    private int correctAnswer;

    @DecimalMax(value="4")
    @DecimalMin(value="1")
    @FormParam("possibleAnswer")
    @NotEmpty
    private int possibleAnswer;
}