package ch.bzz.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    @FormParam("questionID")
    @DecimalMax(value="999")
    @DecimalMin(value="1")
    private int questionID;

    @FormParam("questionText")
    @NotEmpty
    @Size(min=20, max=100)
    private String questionText;
}