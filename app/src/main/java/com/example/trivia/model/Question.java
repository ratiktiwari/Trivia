package com.example.trivia.model;

public class Question {

    private String answer;
    private boolean answerTrue;

    public Question(String answer, boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    public Question() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }


    // toString is an overridden method for printing the instances of Question class
    //it is used to help us out in below line which is written in QuestionBank.java -   Log.d("JSON Stuff", "onResponse: "+ question);  or Log.d("JSON Stuff", "onResponse: "+ question.getAnswer);
    //question or question.toString is same as java compiler automatically understands that we want to print the object

    //THE PROPER DEFINITION OF toString() -
//    Every class in java is child of Object class either directly or indirectly.
//    Object class contains toString() method. We can use toString() method to get string representation of an object.
//    Whenever we try to print the Object reference then internally toString() method is invoked.
//    If we did not define toString() method in your class then Object class toString() method is invoked otherwise
//    our implemented/Overridden toString() method will be called.
    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
