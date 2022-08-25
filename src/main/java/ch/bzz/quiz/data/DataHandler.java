package ch.bzz.quiz.data;


import ch.bzz.quiz.model.Answer;
import ch.bzz.quiz.model.Question;
import ch.bzz.quiz.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Answer> answerList;
    private List<Question> questionList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setAnswerList(new ArrayList<>());
        readAnswerJSON();
        setQuestionList(new ArrayList<>());
        readQuestionJSON();
    }


    /**
     * gets the only instance of this class
     * @return Get and Set
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all answers
     * @return list of answers
     */
    public List<Answer> readAllAnswers() {
        return getAnswerList();
    }


    /**
     * reads a answer by its answerID
     * @param answerID is AnswerId from Answer Class
     * @return the Answer (null=not found)
     */
    public Answer readAnswerByID(int answerID) {
        Answer answer = null;
        for (Answer entry : getAnswerList()) {
            if (entry.getAnswerID() == (answerID)) {
                answer = entry;
            }
        }
        return answer;
    }

    /**
     * reads all Questions
     * @return list of question
     */
    public List<Question> readAllQuessions() {

        return getQuestionList();
    }

    /**
     * inserts a new answer into the answerList
     *
     * @param answer the answer to be saved
     */
    public void insertAnswer(Answer answer) {
        getAnswerList().add(answer);
        writeAnswerJSON();
    }

    /**
     * updates the answerList
     */
    public void updateAnswer() {
        writeAnswerJSON();
    }

    /**
     * deletes a book identified by the answerID
     * @param answerID  the key
     * @return  success=true/false
     */
    public  boolean deleteAnswer(int answerID) {
        Answer answer = readAnswerByID(answerID);
        if (answer != null) {
            getAnswerList().remove(answer);
            writeAnswerJSON();
            return true;
        } else {
            return false;
        }
    }


    /**
     * reads a Question by its questionID
     * @param questionID is QuestionID from Question Class
     * @return the Question (null=not found)
     */
    public Question readQuestionByID(int questionID) {
        Question question = null;
        for (Question entry : getQuestionList()) {
            if (entry.getQuestionID() == (questionID)) {
                question = entry;
            }
        }
        return question;
    }

    /**
     * reads the Answers from the JSON-file
     */
    private void readAnswerJSON() {
        try {
            String path = Config.getProperty("answerJson");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Answer[] answers = objectMapper.readValue(jsonData, Answer[].class);
            for (Answer answer : answers) {
                getAnswerList().add(answer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the AnswerList to the JSON-file
     */
    private void writeAnswerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String answerPath = Config.getProperty("answerJson");
        try {
            fileOutputStream = new FileOutputStream(answerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getAnswerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the Questions from the JSON-file
     */
    private void readQuestionJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("questionJson")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Question[] questions = objectMapper.readValue(jsonData, Question[].class);
            for (Question question : questions) {
                getQuestionList().add(question);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets answerList
     *
     * @return value of answerList
     */
    private List<Answer> getAnswerList() {
        if (answerList == null){
            setAnswerList(new ArrayList<>());
            readAnswerJSON();
        }
        return answerList;
    }

    /**
     * sets answerList
     *
     * @param answerList the value to set
     */
    private void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    /**
     * gets questionList
     *
     * @return value of questionList
     */
    private List<Question> getQuestionList() {
        if (questionList == null){
            setQuestionList(new ArrayList<>());
            readQuestionJSON();
        }
        return questionList;
    }

    /**
     * inserts a new Question into the questionList
     *
     * @param question the question to be saved
     */
    public void insertQuestion(Question question) {
        getQuestionList().add(question);
        writeQuestionJSON();
    }

    /**
     * updates the question
     */
    public void updateQuestion() {
        writeQuestionJSON();
    }

    /**
     * deletes a question identified by the questionID
     * @param questionID  the key
     * @return  success=true/false
     */
    public boolean deleteQuestion(int questionID) {
        Question question = readQuestionByID(questionID);
        if (question != null) {
            getQuestionList().remove(question);
            writeQuestionJSON();
            return true;
        } else {
            return false;
        }
    }
    /**
     * writes the questionList to the JSON-file
     */
    private void writeQuestionJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String questionPath = Config.getProperty("questionJson");
        try {
            fileOutputStream = new FileOutputStream(questionPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getQuestionList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * sets questionList
     *
     * @param questionList the value to set
     */
    private void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }


}