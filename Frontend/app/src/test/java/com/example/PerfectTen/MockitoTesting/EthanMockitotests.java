package com.example.PerfectTen.MockitoTesting;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.EditText;

import com.example.PerfectTen.Screens.PostCreation;
import com.example.PerfectTen.app.AppController;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EthanMockitotests {



        @Mock
        private View fakeview;
    private Object ServerMock;
    private Object AppController;

    @Test
        public void TestCommentAdapter(){

            EditText mockedTitle = mock(EditText.class);
            when(String.valueOf(mockedTitle.getText())).thenReturn("fake title");

            EditText mockedBody = mock(EditText.class);
            when(String.valueOf(mockedBody.getText())).thenReturn("fake body");

            assertEquals(String.valueOf(mockedBody.getText()),String.valueOf(mockedTitle.getText()));

            assertEquals("fake Body",mockedBody.getText());

            //int mockID = mock(AppController.getPostID());

            ServerMock mock = new ServerMock();

            System.out.print(mock.getClass());

            assertEquals(mock.toString(),ServerMock );

            //AppController mockapp = (com.example.PerfectTen.app.AppController) mock(AppController);

            //assertEquals(AppController.getPrivLevel() == "0");


        }
}
