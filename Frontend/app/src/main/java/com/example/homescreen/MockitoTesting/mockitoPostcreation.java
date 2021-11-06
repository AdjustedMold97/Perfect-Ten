package com.example.homescreen.MockitoTesting;


//import static org.mockito.Mockito.mock;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//
//import com.example.homescreen.Screens.PostCreation;
//
//import org.mockito.Mock;
//import static org.mockito.Mockito.when;
//import static org.testng.AssertJUnit.assertEquals;
//
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.stubbing.OngoingStubbing;
//import org.testng.annotations.Test;

//public class mockitoPostcreation {
//
//    private PostCreation post;
//
//    @Mock
//    private View fakeview;
//
//    @Test
//    public void setUpPostCreation(){
//        MockitoAnnotations.initMocks(this);
//        post = new PostCreation();
//
//
//        EditText mockedTitle = mock(EditText.class);
//        when(String.valueOf(mockedTitle.getText())).thenReturn("fake title");
//
//        EditText mockedBody = mock(EditText.class);
//        when(String.valueOf(mockedBody.getText())).thenReturn("fake body");
//
//        assertEquals(String.valueOf(mockedBody.getText()),String.valueOf(mockedTitle.getText()));
//
//    }
//
//}