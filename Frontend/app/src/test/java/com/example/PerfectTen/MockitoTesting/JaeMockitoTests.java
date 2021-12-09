package com.example.PerfectTen.MockitoTesting;

import static org.mockito.Mockito.when;

import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;

import org.junit.Test;
import org.mockito.Mock;

public class JaeMockitoTests {

    @Mock
    PerfectTenRequester requester;

    @Test
    public void testChangePfp() {

 //       when(requester.request()).thenReturn(Const.SUCCESS_MSG);

    }

}