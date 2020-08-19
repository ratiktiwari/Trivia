package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.MainActivity;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.trivia.controller.AppController.TAG;

public class QuestionBank {

//    public int signal = 0;

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    ArrayList <Question> questionArrayList = new ArrayList<>();

    public List<Question> getQuestions(final AnswerListAsyncResponse callback)   //we have passed this interface in order to give signal to the MainActivity.java file that we are done with the data work and the data is ready to be provided to you.
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                        Log.d("JSON Stuff", "onResponse: "+response);

                        for(int i=0; i<response.length(); i++)
                        {
                            try {
                                Question question = new Question();
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                                //Add Question objects to ArrayList

                                questionArrayList.add(question);

//                                Log.d("JSON Stuff", "onResponse: "+ question.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



// To understand below if statement:  (if(callback!=null))
//                      Any reference variable in Java has default value null.
//                        For example -:
//                   public class Test
//                   {
//                    	private static Object obj;
//                    	public static void main(String args[])
//                    	{
//                    		// it will print null;
//                    		System.out.println("Value of object obj is : " + obj);
//                    	}
//                   }

//                        Output:
//                        Value of object obj is : null
                        if(callback!=null)
                        {
                            callback.processFinished(questionArrayList);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest); //AppController is a singleton class

        return questionArrayList;
    }


}
