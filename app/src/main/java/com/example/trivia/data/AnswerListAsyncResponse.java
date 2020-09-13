package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;

//We have created this class to send a signal to MainActivity.java that after we got every question added up in
//our questionArrayList we are ready to supply that list to you which is MainActivity.java here.

//A method without body (no implementation) is known as abstract method.
// A method must always be declared in an abstract class, or in other words
// you can say that if a class has an abstract method, it should be declared abstract as well.



//There are some rules that need to be followed by Interface.
//
//All interface Methods are implicitly public and abstract. Even if you use keyword it will not create the problem as you can see in the second Method declaration. (Before Java 8)
//Abstract Method Definition:
//Sometimes, we require just method declaration in super-classes. This can be achieve by specifying the abstract type modifier. These methods are sometimes referred to as subclasser responsibility because they have no implementation specified in the super-class. Thus, a subclass must override them to provide method definition. To declare an abstract method, use this general form:
//abstract type method-name(parameter-list);


//Interfaces can declare only Constant. Instance variables are not allowed. This means all variables inside the Interface must be public, static, final. Variables inside Interface are implicitly public static final.
//Interface Methods cannot be static. (Before Java 8)
//Interface Methods cannot be final, strictfp or native.
//The Interface can extend one or more other Interface. Note: The Interface can only extend another interface.
//Every method in an interface is only described not implemented because all interface Methods are implicitly abstract.
//The class where an object of interface is created or where an interface is implemented must implement or define or use or describe all the
// methods which are mentioned there in the respected interface.
//like in this interface we have only declared or briefed about the method processFinished()
//but this interface is being passed as an argument in the function calling in the MainActivity.java file
//hence we have to implement or describe this function there also.
//The function description there in MainActivity.java can also be empty.


//When we pass an interface as an argument to a method, then we mean that any object of any class which has implemented that interface is
//welcomed to be an argument of that method.
//for example -:  public void method_one(List <String> myList);
//the above method has interface List as an argument and hence,
//when we call that function we can either pass arrayList or LinkedList to it because
//these both are classes which has implemented the interface List <E> .

public interface AnswerListAsyncResponse {

    void  processFinished(ArrayList<Question> questionArrayList);


}
